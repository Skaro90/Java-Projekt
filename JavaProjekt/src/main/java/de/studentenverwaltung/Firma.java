package de.studentenverwaltung;

import de.studentenverwaltung.exceptions.UserInputException;
import de.studentenverwaltung.gui.TempErrorMessageWindow;

import java.util.List;
import java.util.Optional;

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
    private List<Student> studentenListe;
    private Betreuer betreuer;

    public Firma(int firmenId, String firmenname, String strasse, String hausnummer, String postleitzahl, String stadt, List<Student> studentenListe, Betreuer betreuer) {
        this.firmenId = firmenId;
        this.firmenname = firmenname;
        this.strasse = strasse;
        this.hausnummer = hausnummer;
        this.postleitzahl = postleitzahl;
        this.stadt = stadt;
        this.studentenListe = studentenListe;
        this.betreuer = betreuer;
    }
    public Firma(String firmenname, String strasse, String hausnummer, String postleitzahl, String stadt, Betreuer betreuer) {
        this.firmenname = firmenname;
        this.strasse = strasse;
        this.hausnummer = hausnummer;
        this.postleitzahl = postleitzahl;
        this.stadt = stadt;
        this.betreuer = betreuer;
        this.firmenId = zaehler;
        zaehler++;
    }

    public void neuerStudent(Student student) throws UserInputException {
            if (studentenListe.contains(student)){
                TempErrorMessageWindow errorMessageWindow = new TempErrorMessageWindow();
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
            TempErrorMessageWindow errorMessageWindow = new TempErrorMessageWindow();
            throw new UserInputException("Der zu löschende Nutzer ist nicht in der Liste vorhanden.", errorMessageWindow);
        }


        return change;

    }
}