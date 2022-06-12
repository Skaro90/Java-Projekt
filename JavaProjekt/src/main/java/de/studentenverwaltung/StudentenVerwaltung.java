package de.studentenverwaltung;

import de.studentenverwaltung.exceptions.UserInputException;
import de.studentenverwaltung.gui.ErrorMessageWindow;
import javafx.css.StyleableProperty;

import java.util.ArrayList;
import java.util.Date;

public class StudentenVerwaltung{
    //    Foreign Keys
    private ArrayList<Betreuer> betreuerListe;
    private ArrayList<Firma> firmaListe;
    private ArrayList<Student> studentenListe;
    private ArrayList<Kurs> kursListe;
    private ArrayList<Raum> raumListe = new ArrayList<Raum>();

    public StudentenVerwaltung() {
        //this.datenLaden();
    }

    //private datenLaden(){ }

    public Student studentAnlegen(String name, String vorname, Date geburtsdatum, String email, String matrikelNummer, Firma firma, Kurs kurs, Student.Vorkenntnisse vk){
        Student s = new Student(name,vorname,geburtsdatum,email,matrikelNummer,firma,kurs,vk);
        this.studentenListe.add(s);
        //dbfunc aufrufen
        return s;
    }

    public Firma firmaAnlegen(String firmenname,String strasse, String hausnummer, String postleitzahl, String stadt, Betreuer betreuer){
        Firma f = new Firma(firmenname,strasse,hausnummer,postleitzahl,stadt,betreuer);
        this.firmaListe.add(f);
        //dbfunc
        return f;
    }

    public Betreuer betreuerAnlegen(String nachname, String vorname, String email, Date geburtstag, String telefonnummer, Firma firma){
        Betreuer b = new Betreuer(nachname,vorname,email,geburtstag,telefonnummer,firma);
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
        if(kurs != null){
            if(findeRaum(raumNummer) != null){
                ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
                throw new UserInputException("Es existiert bereits ein Raum mit diesem Namen.", errorMessageWindow);
            }
            if(kurs.getKursGroesse() > kapazitaet){
                ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
                throw new UserInputException("Der Raum besitzt nicht genügend Kapazität für den Kurs.", errorMessageWindow);
            }
        }

        Raum r = new Raum(raumNummer,kapazitaet,kurs);
        this.raumListe.add(r);
        //dbfunc
        return r;
    }

    public void raumLöschen(Raum raum) throws UserInputException{
        if(raum.getKurs() != null){
            ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
            throw new UserInputException("Dieser Raum ist bereits einem Kurs zugeordnet. Bitte ordne dem Kurs " + raum.getKurs().getKursName() + " einen anderen Raum zu.", errorMessageWindow);
        }

        this.raumListe.remove(raum);
        //dbfunc
        raum = null;
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

    public ArrayList<Raum> getRaumListe(){
        return raumListe;
    }
}