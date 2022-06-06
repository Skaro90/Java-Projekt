package de.studentenverwaltung;

import de.studentenverwaltung.gui.TempErrorMessageWindow;
import de.studentenverwaltung.exceptions.UserInputException;

import java.util.List;
import java.util.Optional;

public class Firma {
  
    private int firmenId;
    private String firmenname;
    private static int zaehler = 0; //max setzten in datenLaden()
//    Adresse
    private Adresse adresse;

//    Foreign Keys
    private List<Student> studentenListe;
    private Betreuer betreuer;


    public Firma(int firmenId, String firmenname, String strasse, String hausnummer, String postleitzahl, String stadt, List<Student> studentenListe, Betreuer betreuer) {

    //Adresse Klasse
    private class Adresse{
        private String strasse;
        private String hausnummer;
        private String postleitzahl;
        private String stadt;

        public Adresse(String strasse, String hausnummer, String postleitzahl, String stadt) {
            this.strasse = strasse;
            this.hausnummer = hausnummer;
            this.postleitzahl = postleitzahl;
            this.stadt = stadt;
        }

        public String getStrasse() {
            return strasse;
        }

        public String getHausnummer() {
            return hausnummer;
        }

        public String getPostleitzahl() {
            return postleitzahl;
        }

        public String getStadt() {
            return stadt;
        }
    }

    public void neuerStudent(Student student) throws UserInputException {
        if(studentenListe.contains(student)){
            TempErrorMessageWindow errorMessageWindow = new TempErrorMessageWindow();
            throw new UserInputException("Der Student ist bereits in der Firma.", errorMessageWindow);
        }

        studentenListe.add(Optional.of(student));
        student.setFirma(this);
    }

    public void betreuerWechsel(Betreuer betreuer){
        this.betreuer = betreuer;
    }

    public Firma(Integer firmenId, String firmenname, String strasse, String hausnummer, String postleitzahl, String stadt, List<Optional<Student>> studentenListe, Betreuer betreuer) {

        this.firmenId = firmenId;
        this.firmenname = firmenname;
        this.studentenListe = studentenListe;
        this.betreuer = betreuer;

        this.adresse = new Adresse(strasse, hausnummer, postleitzahl, stadt);
    }
    public Firma(String firmenname, String strasse, String hausnummer, String postleitzahl, String stadt, Betreuer betreuer){
        this.firmenname = firmenname;
        this.strasse = strasse;
        this.hausnummer = hausnummer;
        this.postleitzahl = postleitzahl;
        this.stadt = stadt;
        this.betreuer = betreuer;
        this.firmenId = zaehler;
        zaehler++;


    public boolean neuerStudent(Student student){
        for (Student stu: studentenListe) {
            if (stu.equals(student)){
                System.out.println("Student ist schon vorhanden");
                return true;
            }
        }
        studentenListe.add(student);
        return true;
    }

    public boolean betreuerWechsel(Betreuer neuerBetreuer){
        this.betreuer = neuerBetreuer;
        return true;
    }

    public Boolean studentLoeschen(Student student){
        boolean change = false;
        for (int i = 0; i < studentenListe.size(); i++){
            if (student == studentenListe.get(i)){
                studentenListe.remove(i);
                change = true;
            }
        }
        return change;

    }
}
