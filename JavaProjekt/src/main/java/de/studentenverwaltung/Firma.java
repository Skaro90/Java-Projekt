package de.studentenverwaltung;

import de.studentenverwaltung.exceptions.UserInputException;
import de.studentenverwaltung.gui.Application;
import de.studentenverwaltung.gui.ErrorMessageWindow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Firma {
    private int firmenId;
    private String firmenname;
    private static int zaehler = 0; //max setzten in datenLaden()
    //    Adresse
    private String strasse;
    private String hausnummer;
    private String postleitzahl;
    private String stadt;

    //    Foreign Keys
    private List<Student> studentenListe = new ArrayList<Student>();
    private Betreuer betreuer;

    public Firma(int firmenId, String firmenname, String strasse, String hausnummer, String postleitzahl, String stadt, Betreuer betreuer) {
        this.firmenId = firmenId;
        this.firmenname = firmenname;
        this.strasse = strasse;
        this.hausnummer = hausnummer;
        this.postleitzahl = postleitzahl;
        this.stadt = stadt;
        this.betreuer = betreuer;
    }
 

    public void neuerStudent(Student student) throws UserInputException {
            if (studentenListe.contains(student)){
                ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
                throw new UserInputException("Der Student ist bereits in der Firma.", errorMessageWindow);
            }
        studentenListe.add(student);
            //student.setFirma(this);
    }

    public void betreuerWechsel(Betreuer neuerBetreuer){
        this.betreuer = neuerBetreuer;
    }

    public boolean studentLoeschen(Student student) throws UserInputException{ //void?
        boolean change = false;
        /*for (int i = 0; i < studentenListe.size(); i++){
            if (student == studentenListe.get(i)){
                studentenListe.remove(i);
                change = true;
            }
        }*/
        if(!studentenListe.remove(student)){
            ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
            throw new UserInputException("Der zu löschende Nutzer ist nicht in der Liste vorhanden.", errorMessageWindow);
        }


        return change;

    }


    public int getFirmenId() {
        return firmenId;
    }
  
    public void firmennameAendern(String newName){
        this.firmenname = newName;
    }

    public void adresseAendern(String strasse, String hausnummer, String postleitzahl, String stadt){
        this.strasse = strasse;
        this.hausnummer = hausnummer;
        this.postleitzahl = postleitzahl;
        this.stadt = stadt;
    }

    public void betreuerAendern(Betreuer betreuer, String nachname, String vorname, String email, Date geburtstag, String telefonnummer) throws UserInputException{
        betreuer.nachnameAendern(nachname);
        betreuer.vornameAendern(vorname);
        betreuer.emailAendern(email);
        betreuer.geburtstagAendern(geburtstag);
        betreuer.telefonnummerAendern(telefonnummer);
    }

    public String getFirmenname(){
        return firmenname;

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

    public List<Student> getStudentenListe() {
        return studentenListe;
    }

    public Betreuer getBetreuer() {
        return betreuer;
    }
}