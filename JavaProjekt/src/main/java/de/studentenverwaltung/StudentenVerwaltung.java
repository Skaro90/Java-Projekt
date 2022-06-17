package de.studentenverwaltung;

import de.studentenverwaltung.exceptions.UserInputException;
import de.studentenverwaltung.gui.ErrorMessageWindow;
import javafx.css.StyleableProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class StudentenVerwaltung{
    //    Foreign Keys
    private ArrayList<Betreuer> betreuerListe = new ArrayList<Betreuer>();
    private ArrayList<Firma> firmaListe = new ArrayList<Firma>();
    public ArrayList<Student> studentenListe = new ArrayList<Student>();
    private ArrayList<Kurs> kursListe = new ArrayList<Kurs>();
    private ArrayList<Raum> raumListe = new ArrayList<Raum>();

    public StudentenVerwaltung() {
        //this.datenLaden();
        try {
            Raum a = raumAnlegen("Raum 1", 2, null);
            Raum b = raumAnlegen("Raum 2", 10, null);
            Raum c = raumAnlegen("Raum 3", 12, null);
            Raum d = raumAnlegen("Raum 4", 19, null);

            Kurs k = kursAnlegen("Kurs 1", a);

            a.kursHinzufuegen(k);
            b.kursHinzufuegen(kursAnlegen("Kurs 2", b));
            c.kursHinzufuegen(kursAnlegen("Kurs 3", c));
            d.kursHinzufuegen(kursAnlegen("Kurs 4", d));

            Firma f = firmaAnlegen("DB", "Strasse", "1", "30165", "Hannover", betreuerAnlegen("Hartmann", "Fixi", "fixi.hartmann@in.de", new Date(System.currentTimeMillis()), "123456489"));
            Firma g = firmaAnlegen("DB Cargo", "Strasse", "1", "68309", "Mannheim", betreuerAnlegen("Rainer", "Zufall", "a@b.d", new Date(System.currentTimeMillis()), "123456489"));

            Student s = studentAnlegen("Jauch", "Günther", new Date(System.currentTimeMillis()), "günther.jauch@rtl.de", "123456", f, k, Student.Vorkenntnisse.Experte);
            Student ss = studentAnlegen("Gause", "Gundula", new Date(System.currentTimeMillis()), "gause.gundula@zdf.de", "654321", f, k, Student.Vorkenntnisse.Absoluter_Anfaenger);
        } catch (UserInputException e) {
            throw new RuntimeException(e);
        }
    }

    //private datenLaden(){ }

    public Student studentAnlegen(String name, String vorname, Date geburtsdatum, String email, String matrikelNummer, Firma firma, Kurs kurs, Student.Vorkenntnisse vk) throws UserInputException {
        if(kurs.getRaum().getKapazitaet() <= kurs.getKursGroesse()){
            ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
            throw new UserInputException("Die Kapazität des Kursraums reicht nicht für einen weiteren Studenten aus. Bitte weisen Sie dem Kurs einen neuen Raum zu, bevor Sie den Studenten anlegen.", errorMessageWindow);
        }
        Student s = new Student(name,vorname,geburtsdatum,email,matrikelNummer,firma,kurs,vk);
        firma.neuerStudent(s);
        kurs.studentHinzufuegen(s);
        this.studentenListe.add(s);
        //dbfunc aufrufen
        return s;
    }

    public void updateStudent(Student student, String nachname, String vorname, String email, Date geburtstag, Kurs kurs, Student.Vorkenntnisse vorkenntnisse) throws UserInputException {
        student.nachnameAendern(nachname);
        student.vornameAendern(vorname);
        student.emailAendern(email);
        student.geburtstagAendern(geburtstag);
        student.versetzen(kurs);
        student.vorkenntnisseAendern(vorkenntnisse);

        //dbfunc

    }

    public Firma firmaAnlegen(String firmenname,String strasse, String hausnummer, String postleitzahl, String stadt, Betreuer betreuer){
        Firma f = new Firma(firmenname,strasse,hausnummer,postleitzahl,stadt,betreuer);
        this.firmaListe.add(f);
        //dbfunc
        return f;
    }

    public void updateFirma(Firma firma, String firmenname,String strasse, String hausnummer, String postleitzahl, String stadt, String betreuerNachname, String betreuerVorname, String betreuerEmail, Date betreuerGeburtstag, String betreuerTelefonnummer) throws UserInputException {
        if(findeFirma(firmenname) != null){
            ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
            throw new UserInputException("Es existiert bereits eine Firma mit diesem Namen.", errorMessageWindow);
        }
        firma.firmennameAendern(firmenname);
        firma.adresseAendern(strasse, hausnummer, postleitzahl, stadt);
        firma.betreuerAendern(betreuerNachname, betreuerVorname, betreuerEmail, betreuerGeburtstag, betreuerTelefonnummer);



    }

    public void firmaLoeschen(Firma firma){
        if(firma.getStudentenListe().isEmpty()){
            this.firmaListe.remove(firma);
        } else {
            while(!firma.getStudentenListe().isEmpty()){
                try {
                    firma.getStudentenListe().get(0).exmatrikulieren();
                } catch (UserInputException e) {
                    throw new RuntimeException(e);
                }
            }
            this.firmaListe.remove(firma);
        }

    }

    public Betreuer betreuerAnlegen(String nachname, String vorname, String email, Date geburtstag, String telefonnummer){
        Betreuer b = new Betreuer(nachname,vorname,email,geburtstag,telefonnummer);
        this.betreuerListe.add(b);
        //dbfunc
        return b;
    }

    public Kurs kursAnlegen(String kursName, Raum raum) throws UserInputException {
        if(findeKurs(kursName) != null){
            ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
            throw new UserInputException("Es existiert bereits ein Kurs mit diesem Namen.", errorMessageWindow);
        }

        Kurs k = new Kurs(kursName,raum);
        raum.kursHinzufuegen(k);
        this.kursListe.add(k);
        //dbfunc
        return k;
    }

    public void updateKurs(Kurs kurs, String kursName, Raum raum) throws UserInputException {
        if(findeKurs(kursName) != null && findeKurs(kursName) != kurs){
            ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
            throw new UserInputException("Es existiert bereits ein Kurs mit diesem Namen.", errorMessageWindow);
        }

        /*if(raum.getKurs() != null){
            ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
            throw new UserInputException("Dem Raum ist bereits ein Kurs zugeordnet.", errorMessageWindow);
        }*/

        //DIE ABFRAGE IST BEREITS IN KURS.JAVA VORHANDEN

        kurs.getRaum().kursLoeschen();

        kurs.kursNameAendern(kursName);
        kurs.raumWechseln(raum);

    }

    public void kursLöschen(Kurs kurs) throws UserInputException {
        if(kurs.getStudentenListe().isEmpty()){
            kurs.getRaum().kursHinzufuegen(null);
            kurs.raumWechseln(null);
            this.kursListe.remove(kurs);
        } else {
            kurs.getStudentenListe().forEach(x -> {
                try {
                    x.exmatrikulieren();
                    kurs.raumWechseln(null);
                    kurs.getRaum().kursHinzufuegen(null);
                    this.kursListe.remove(kurs);
                } catch (UserInputException e) {
                    throw new RuntimeException(e);
                }
            });
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

        if(raum.getKurs().getKursGroesse() > kapa){
            ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
            throw new UserInputException("Die neue Kapazität ist nicht ausreichend für den dem Raum zugeordneten Kurs.", errorMessageWindow);
        }
        raum.nummerAendern(rnm);
        raum.kapazitaetAendern(kapa);
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