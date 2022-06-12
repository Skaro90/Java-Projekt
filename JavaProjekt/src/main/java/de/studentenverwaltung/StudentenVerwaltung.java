package de.studentenverwaltung;

import de.studentenverwaltung.exceptions.UserInputException;
import de.studentenverwaltung.gui.TempErrorMessageWindow;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StudentenVerwaltung{
    //    Foreign Keys
    private ArrayList<Betreuer> betreuerListe = new ArrayList<>();
    private ArrayList<Firma> firmaListe= new ArrayList<>();
    private ArrayList<Student> studentenListe= new ArrayList<>();
    private ArrayList<Kurs> kursListe= new ArrayList<>();
    private ArrayList<Raum> raumListe= new ArrayList<>();
    private Datenbank datenbank;

    public StudentenVerwaltung() {
        this.datenLaden();
    }

    private void datenLaden(){
        datenbank = new Datenbank();
        datenbank.zeigeStudenten();

        //datenbank.Studentanlegen("korl","karl",new Date(),"fabio@mail","5",new Firma(1),new Kurs(1),Student.Vorkenntnisse.Experte);

       try{
        ResultSet betreuerRS = datenbank.ladeBetreuer();
        while(betreuerRS.next()){
            Betreuer b = new Betreuer(betreuerRS.getString("name"),betreuerRS.getString("email"),betreuerRS.getString("vorname"),betreuerRS.getDate("geburtstag"),betreuerRS.getInt("bid"),betreuerRS.getString("telefonnummer"));
            betreuerListe.add(b);
        }


        ResultSet firmaRS = datenbank.ladeFirma();
        while(firmaRS.next()){
            Betreuer b = null;
            for(Betreuer element: betreuerListe){
                if(element.getBetreuerId() == firmaRS.getInt("bid")){
                    b = element;
                }
            }
            Firma f = new Firma(firmaRS.getInt("fid"),firmaRS.getString("bezeichnung"),firmaRS.getString("strasse"),firmaRS.getString("hausnummer"),firmaRS.getString("plz"),firmaRS.getString("stadt"),b);
            firmaListe.add(f);
        }

        ResultSet raumRS = datenbank.ladeRaum();
        while(raumRS.next()){
            Raum r = new Raum(raumRS.getInt("rid"),raumRS.getString("nummer"),raumRS.getInt("kapazität"),null);
            raumListe.add(r);
        }

        ResultSet kursRs = datenbank.ladeKurs();
        while(kursRs.next()){
            Raum r = null;
            for(Raum element: raumListe){
                if(element.getRaumId() == kursRs.getInt("rid")){
                    r = element;
                }
            }
            Kurs k = new Kurs(kursRs.getInt("kid"),kursRs.getString("bezeichnung"),r);
            kursListe.add(k);
        }

        ResultSet studentRS = datenbank.ladeStudent();
        while(studentRS.next()){
            Firma f = null;
            for(Firma element: firmaListe){
                if(element.getFirmenId() == studentRS.getInt("fid")){
                    f=element;
                }
            }
            Kurs k = null;
            for(Kurs element: kursListe){
                if(element.getKursId() == studentRS.getInt("kid")){
                    k=element;
                }
            }
            Student s = new Student(studentRS.getString("name"),studentRS.getString("vorname"),studentRS.getString("email"),studentRS.getDate("geburtstag"),studentRS.getInt("sid"),studentRS.getString("matrikelnummer"), Student.Vorkenntnisse.values()[studentRS.getInt("Vorkenntnisse")],f,k);
            studentenListe.add(s);
        }

        }catch(Exception e){
            e.printStackTrace();
        }
       for(Student element: studentenListe){
           System.out.println(element.getMatrikelnummer()+" "+element.getKurs().getRaum().getRaumNummer()+" "+ element.getVorkenntnisse()+" "+element.getFirma().getFirmenname()+ " " + element.getGeburtstag());
       }
    }

    public Student studentAnlegen(String name, String vorname, Date geburtsdatum, String email, String matrikelNummer, Firma firma, Kurs kurs, Student.Vorkenntnisse vk) throws UserInputException {
        for (Student element: studentenListe) {
            if (element.getMatrikelnummer().equals(matrikelNummer)){
                TempErrorMessageWindow tmp = new TempErrorMessageWindow();
                throw new UserInputException("matrikelnummer exestiert bereits",tmp);
            }
        }
        int tmp = datenbank.Studentanlegen(name, vorname, geburtsdatum, email, matrikelNummer, firma, kurs, vk);
        System.out.println(tmp);
        Student s = new Student(name,vorname,geburtsdatum,email,matrikelNummer,firma,kurs,vk);
        this.studentenListe.add(s);
        return s;
    }

    public Firma firmaAnlegen(String firmenname,String strasse, String hausnummer, String postleitzahl, String stadt, Betreuer betreuer){
        Firma f = new Firma(firmenname,strasse,hausnummer,postleitzahl,stadt,betreuer);
        this.firmaListe.add(f);
        //dbfunc
        return f;
    }

    public Betreuer betreuerAnlegen(String nachname, String vorname, String email, Date geburtstag, String telefonnummer){
        Betreuer b = new Betreuer(nachname,vorname,email,geburtstag,telefonnummer);
        this.betreuerListe.add(b);
        //dbfunc
        return b;
    }

    public Kurs kursAnlegen(String kursName, Raum raum){
        Kurs k = new Kurs(kursName,raum);
        this.kursListe.add(k);
        //dbfunc
        return k;
    }

    public Raum raumAnlegen(String raumNummer, int kapazitaet, Kurs kurs) throws UserInputException{
        if(kurs.getKursGroesse() > kapazitaet){
            TempErrorMessageWindow errorMessageWindow = new TempErrorMessageWindow();
            throw new UserInputException("Der Raum besitzt nicht genügend Kapazität für den Kurs.", errorMessageWindow);
        }
        Raum r = new Raum(raumNummer,kapazitaet,kurs);
        this.raumListe.add(r);
        //dbfunc
        return r;
    }

    public Betreuer findeBetreuer(String email){
        for (Betreuer tmp : betreuerListe){
            if(tmp.getEmail().equals(email)){
                return tmp;
            }
        }
        return null;
    }

    public Student findeStudent(String matrikelNummer){
        for(Student tmp: studentenListe){
            if(tmp.getMatrikelnummer().equals(matrikelNummer)){
                return tmp;
            }
        }
        return null;
    }

    public Raum findeRaum(String raumNummer){
        for(Raum tmp: raumListe){
            if(tmp.getRaumNummer().equals(raumNummer)){
                return tmp;
            }
        }
        return null;
    }

    public Kurs findeKurs(String bezeichnung){
        for(Kurs tmp: kursListe){
            if(tmp.getKursName().equals(bezeichnung)){
                return tmp;
            }
        }
        return null;
    }

    public void exmatrikulieren(Student student) throws UserInputException {
        //firma/kurs löschen (neue func in student) -- DONE?
        student.exmatrikulieren();
        studentenListe.remove(student);
        //dbfunc
    }

    public void raumZuweisen(Kurs kurs, Raum raum) throws UserInputException {
        kurs.raumWechseln(raum);
        //dbfunc
    }

    public void studentVersetzen(Student student, Kurs kurs) throws UserInputException {
        student.versetzen(kurs);
        //dbfunc
    }

    public void betreuerWechseln(Firma firma, Betreuer betreuer){
        firma.betreuerWechsel(betreuer);
        //dbfunc
    }

    public void test() throws UserInputException{
        TempErrorMessageWindow errorMessageWindow = new TempErrorMessageWindow();
        throw new UserInputException("Krasser Fehler, Achtung!", errorMessageWindow);
    }
}