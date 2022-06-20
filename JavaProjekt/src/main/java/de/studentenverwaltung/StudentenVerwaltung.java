package de.studentenverwaltung;

import de.studentenverwaltung.exceptions.UserInputException;
import de.studentenverwaltung.gui.ErrorMessageWindow;
import de.studentenverwaltung.gui.controllers.MainViewController;
import javafx.css.StyleableProperty;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class StudentenVerwaltung{
    //    Foreign Keys

    private ArrayList<Betreuer> betreuerListe = new ArrayList<Betreuer>();
    private ArrayList<Firma> firmaListe = new ArrayList<Firma>();
    private ArrayList<Student> studentenListe = new ArrayList<Student>();
    private ArrayList<Kurs> kursListe = new ArrayList<Kurs>();
    private ArrayList<Raum> raumListe = new ArrayList<Raum>();
    private Datenbank datenbank;

    public StudentenVerwaltung() {
        this.datenLaden();
        //betreuerAnlegen("Knappe", "Fabio", "fabio.knappe@mail.com", new Date(), "45");
        //datenbank.firmaanlegen("testfirma","T6","20B","34281","Gberg",new Betreuer(1));
        //datenbank.Studentanlegen("korl","karl",new Date(),"fabio@mail","5",new Firma(6),kursListe.get(0),Student.Vorkenntnisse.Experte);
        //datenbank.kursanlegen("tinf2",null);
        //datenbank.raumanlegen("5",30);
        //datenbank.exmatrikulieren(studentenListe.get(1));
        //datenbank.raumZuweisen(kursListe.get(3),raumListe.get(1));
        //datenbank.studentVersetzen(studentenListe.get(0),kursListe.get(3));
        //datenbank.betreuerWechseln(firmaListe.get(1),betreuerListe.get(1));
        //datenbank.firmalöschen(firmaListe.get(1));
        //datenbank.betreuerloeschen(betreuerListe.get(2));
        //datenbank.kursloeschen(kursListe.get(1));
        //datenbank.kursupdate(kursListe.get(1),"TIiiiiNF2");
        //datenbank.studentupdate(studentenListe.get(0),"Müller","Thomas","esmüllert@mail.com",new Date());
    }

   

    private void datenLaden(){
        try{
        datenbank = new Datenbank();
        datenbank.zeigeStudenten();

        ResultSet betreuerRS = datenbank.ladeBetreuer();
        while(betreuerRS.next()){
            Betreuer b = new Betreuer(betreuerRS.getString("name"),betreuerRS.getString("vorname"),betreuerRS.getString("email"),new java.util.Date(betreuerRS.getDate("geburtstag").getTime()),betreuerRS.getInt("bid"),betreuerRS.getString("telefonnummer"));
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
            r.kursHinzufuegen(k);
            kursListe.add(k);
        }

        ResultSet studentRS = datenbank.ladeStudent();
        while(studentRS.next()){
            Firma firma = null;
            for(Firma element: firmaListe){
                if(element.getFirmenId() == studentRS.getInt("fid")){
                    firma=element;
                }
            }
            Kurs kurs = null;
            for(Kurs element: kursListe){
                if(element.getKursId() == studentRS.getInt("kid")){
                    kurs=element;
                }
            }
            Student s = new Student(studentRS.getString("name"),studentRS.getString("vorname"),studentRS.getString("email"),new java.util.Date(studentRS.getDate("geburtstag").getTime()),studentRS.getInt("sid"),studentRS.getString("matrikelnummer"), Student.Vorkenntnisse.values()[studentRS.getInt("Vorkenntnisse")],firma,kurs);
            studentenListe.add(s);
            firma.neuerStudent(s);
            kurs.studentHinzufuegen(s);
        }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Student studentAnlegen(String name, String vorname, Date geburtsdatum, String email, String matrikelNummer, Firma firma, Kurs kurs, Student.Vorkenntnisse vk) throws UserInputException {
      if(kurs.getRaum().getKapazitaet() <= kurs.getKursGroesse()){
            ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
            throw new UserInputException("Die Kapazität des Kursraums reicht nicht für einen weiteren Studenten aus. Bitte weisen Sie dem Kurs einen neuen Raum zu, bevor Sie den Studenten anlegen.", errorMessageWindow);
        }  
      for (Student element: studentenListe) {
            if (element.getMatrikelnummer().equals(matrikelNummer)){
                ErrorMessageWindow tmp = new ErrorMessageWindow();
                throw new UserInputException("matrikelnummer exestiert bereits",tmp);
            }
        }
        int tmp = datenbank.Studentanlegen(name, vorname, geburtsdatum, email, matrikelNummer, firma, kurs, vk);
        
        Student s = new Student(name,vorname, email, geburtsdatum, tmp,matrikelNummer, vk, firma,kurs);
        firma.neuerStudent(s);
        kurs.studentHinzufuegen(s);
        this.studentenListe.add(s);
        return s;
    }
  
  public void updateStudent(Student student, String nachname, String vorname, String email, Date geburtstag, Kurs kurs) throws UserInputException {
        student.nachnameAendern(nachname);
        student.vornameAendern(vorname);
        student.emailAendern(email);
        student.geburtstagAendern(geburtstag);
        student.versetzen(kurs);

        datenbank.studentupdate(student, nachname, vorname, email, geburtstag);
        datenbank.studentVersetzen(student, kurs);

    }


    public Firma firmaAnlegen(String firmenname,String strasse, String hausnummer, String postleitzahl, String stadt, Betreuer betreuer)throws UserInputException {
        for(Firma element: firmaListe){
            if(element.getFirmenname().equals(firmenname)){
                ErrorMessageWindow tmp = new ErrorMessageWindow();
                throw new UserInputException("firmenname exestiert bereits",tmp);
            }
        }
        int fid = datenbank.firmaanlegen(firmenname,strasse,hausnummer,postleitzahl,stadt,betreuer);
        Firma f = new Firma(fid,firmenname,strasse,hausnummer,postleitzahl,stadt,betreuer);

        this.firmaListe.add(f);
        return f;
    }
  
   public void updateFirma(Firma firma, String firmenname,String strasse, String hausnummer, String postleitzahl, String stadt, String betreuerNachname, String betreuerVorname, String betreuerEmail, Date betreuerGeburtstag, String betreuerTelefonnummer) throws UserInputException {
        if(findeFirma(firmenname) != null && !firma.getFirmenname().equals(firmenname)){
            ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
            throw new UserInputException("Es existiert bereits eine Firma mit diesem Namen.", errorMessageWindow);
        }
        datenbank.firmenupdate(firma, firmenname, strasse, hausnummer, postleitzahl, stadt, betreuerNachname, betreuerVorname, betreuerEmail, betreuerGeburtstag, betreuerTelefonnummer);
        firma.firmennameAendern(firmenname);
        firma.adresseAendern(strasse, hausnummer, postleitzahl, stadt);
        firma.betreuerAendern(firma.getBetreuer(), betreuerNachname, betreuerVorname, betreuerEmail, betreuerGeburtstag, betreuerTelefonnummer);


    }

  
  public void firmaLoeschen(Firma firma){ //DB FUNKTION
        if(!firma.getStudentenListe().isEmpty()){
            while(!firma.getStudentenListe().isEmpty()){
                try {
                    datenbank.exmatrikulieren(firma.getStudentenListe().get(0));
                    firma.getStudentenListe().get(0).exmatrikulieren();
                } catch (UserInputException e) {
                    throw new RuntimeException(e);
                }
            }
        }
      datenbank.firmaloeschen(firma);
      datenbank.betreuerloeschen(firma.getBetreuer());
        this.firmaListe.remove(firma);

    }
  
  

    public Betreuer betreuerAnlegen(String nachname, String vorname, String email, Date geburtstag, String telefonnummer) throws UserInputException{
        for(Betreuer element: betreuerListe){
            if(element.getEmail().equals(email)){
                ErrorMessageWindow tmp = new ErrorMessageWindow();
                throw new UserInputException("email exestiert bereits",tmp);
            }
        }
        int tmp= datenbank.betreueranlegen(nachname, vorname, email, geburtstag, telefonnummer);
        Betreuer b = new Betreuer(nachname,vorname,email,geburtstag,tmp,telefonnummer);

        this.betreuerListe.add(b);
        return b;
    }


    public Kurs kursAnlegen(String kursName, Raum raum) throws UserInputException{
        if(findeKurs(kursName) != null){
            ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
            throw new UserInputException("Es existiert bereits ein Kurs mit diesem Namen.", errorMessageWindow);
        }
        int tmp = datenbank.kursanlegen(kursName,raum);
        Kurs k = new Kurs(tmp,kursName,raum);

        raum.kursHinzufuegen(k);
        this.kursListe.add(k);
        return k;
    }

    public void updateKurs(Kurs kurs, String kursName, Raum raum) throws UserInputException {  //SPÄTER TESTEN
        if(findeKurs(kursName) != null && findeKurs(kursName) != kurs){
            ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
            throw new UserInputException("Es existiert bereits ein Kurs mit diesem Namen.", errorMessageWindow);
        }

        kurs.getRaum().kursLoeschen();

        kurs.kursNameAendern(kursName);
        kurs.raumWechseln(raum);
        datenbank.kursupdate(kurs, kursName);
        datenbank.raumZuweisen(kurs, raum);

    }

    public void kursLöschen(Kurs kurs) throws UserInputException {
        if(kurs.getStudentenListe().isEmpty()){
            datenbank.kursloeschen(kurs);
            kurs.getRaum().kursHinzufuegen(null);
            kurs.raumWechseln(null);
            this.kursListe.remove(kurs);

        } else {

            while (!kurs.getStudentenListe().isEmpty()){
                datenbank.exmatrikulieren(kurs.getStudentenListe().get(0));
                kurs.getStudentenListe().get(0).exmatrikulieren();

            }
            try {
                datenbank.kursloeschen(kurs);
                kurs.getRaum().kursHinzufuegen(null);
                kurs.raumWechseln(null);
                this.kursListe.remove(kurs);
            } catch (UserInputException e) {
                throw new RuntimeException(e);
            }

        }

    }

    public Raum raumAnlegen(String raumNummer, int kapazitaet, Kurs kurs) throws UserInputException{
        if(raumNummer==""){
            ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
            throw new UserInputException("Ungültiger Raumname.", errorMessageWindow);
        }
        if(kapazitaet < 0){
            ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
            throw new UserInputException("Ungültige Kapazität", errorMessageWindow);
        }
        if(kurs != null){
            if(kurs.getKursGroesse() > kapazitaet){
                ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
                throw new UserInputException("Der Raum besitzt nicht genügend Kapazität für den Kurs.", errorMessageWindow);
            }
        }
        if(findeRaum(raumNummer) != null){
            ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
            throw new UserInputException("Es existiert bereits ein Raum mit diesem Namen.", errorMessageWindow);
        }

        for(Raum element: raumListe){
            if(element.getRaumNummer().equals(raumNummer)){
                ErrorMessageWindow tmp = new ErrorMessageWindow();
                throw new UserInputException("Raumnummer exestiert bereits",tmp);
            }
        }
        int tmp = datenbank.raumanlegen(raumNummer,kapazitaet);
        Raum r = new Raum(tmp,raumNummer,kapazitaet,kurs);

        this.raumListe.add(r);
        return r;
    }

    public void raumLöschen(Raum raum) throws UserInputException{
        if(raum.getKurs() != null){
            ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
            throw new UserInputException("Dieser Raum ist bereits einem Kurs zugeordnet. Bitte ordnen Sie dem Kurs " + raum.getKurs().getKursName() + " einen anderen Raum zu, bevor Sie ihn löschen.", errorMessageWindow);
        }

        this.raumListe.remove(raum);
        datenbank.raumloeschen(raum);

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

    public Firma findeFirma(String firmenName){
        for (Firma tmp: firmaListe) {
            if(tmp.getFirmenname().equals(firmenName)){
                return tmp;
            }
        }
        return null;
    }

    public void exmatrikulieren(Student student) throws UserInputException {
        //firma/kurs löschen (neue func in student) -- DONE?
        student.exmatrikulieren();
        studentenListe.remove(student);
        datenbank.exmatrikulieren(student);
    }

    public void studentVersetzen(Student student, Kurs kurs) throws UserInputException {
        student.versetzen(kurs);
        datenbank.studentVersetzen(student,kurs);
    }

    public void betreuerWechseln(Firma firma, Betreuer betreuer){
        firma.betreuerWechsel(betreuer);
        datenbank.betreuerWechseln(firma,betreuer);
    }

    public ArrayList<Raum> getRaumListe(){
        return raumListe;
    }


    public ArrayList<Kurs> getKursListe() {
        return kursListe;
    }

    public ArrayList<Firma> getFirmaListe() {
        return firmaListe;
    }

    public ArrayList<Student> getStudentenListe() {
        return studentenListe;
    }

    public void raumUpdate(Raum raum, String rnm, int kapa) throws UserInputException {
        if (rnm==""){
            ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
            throw new UserInputException("Ungültiger Raumname", errorMessageWindow);
        }

        if (kapa < 0){
            ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
            throw new UserInputException("Ungültige Kapazität", errorMessageWindow);
        }

        if(raum.getKurs() != null){
            if(raum.getKurs().getKursGroesse() > kapa){
                ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
                throw new UserInputException("Die neue Kapazität ist nicht ausreichend für den dem Raum zugeordneten Kurs.", errorMessageWindow);
            }
        }

        raum.nummerAendern(rnm);
        raum.kapazitaetAendern(kapa);
        datenbank.raumupdate(raum,rnm,kapa);
    }

    public Raum getRaumById(int rId) throws UserInputException{
        for (int i = 0; i < raumListe.size(); i++){
            if (raumListe.get(i).getRaumId()==rId){
                return raumListe.get(i);
            }
        }
        ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
        throw new UserInputException("Raum wurde nicht gefunden, RaumId inkorrekt", errorMessageWindow);
    }




}