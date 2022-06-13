package de.studentenverwaltung;

import de.studentenverwaltung.exceptions.UserInputException;
import de.studentenverwaltung.gui.Application;
import de.studentenverwaltung.gui.ErrorMessageWindow;

import java.util.Date;

public class Student extends Person{
    private int studentId;
    private String matrikelnummer;
    private Vorkenntnisse vorkenntnisse;
    private static int zaehler = 0; //max setzten in datenLaden()

    private Firma firma;
    private Kurs kurs;

    public enum Vorkenntnisse{
        Absoluter_Anfaenger,
        Anfaenger,
        Laie,
        Fortgeschrittener,
        Experte
    }

    public Student(String nachname, String vorname, String email, Date geburtstag, int studentId, String matrikelnummer, Vorkenntnisse vorkenntnisse, Firma firma, Kurs kurs) {
        super(nachname, vorname, email, geburtstag);
        this.studentId = studentId;
        this.matrikelnummer = matrikelnummer;
        this.vorkenntnisse = vorkenntnisse;
        this.firma = firma;
        this.kurs = kurs;
    }

    public Student(String nachname, String vorname, Date geburtsdatum,String email,String matrikelnummer, Firma firma,Kurs kurs,Vorkenntnisse vorkenntnisse){
        super(nachname, vorname, email, geburtsdatum);
        this.matrikelnummer = matrikelnummer;
        this.vorkenntnisse = vorkenntnisse;
        this.firma = firma;
        this.kurs = kurs;
        this.studentId = zaehler;
        zaehler++;
    }

    public String getMatrikelnummer() {
        return matrikelnummer;
    }

    public void versetzen(Kurs kurs) throws UserInputException {
        /*try {
            this.kurs.studentLoeschen(this);
            this.kurs = kurs;
            this.kurs.studentHinzufuegen(this);

        } catch (Exception e) {
            e.printStackTrace();
            return e.getCause();
        }
        return null;*/

        if(!(kurs.getRaum().getKapazitaet() > kurs.getKursGroesse())){
            ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
            throw new UserInputException("Der Kurs-Raum besitzt nicht die Kapazität für einen weiteren Studenten. Bitte ordnen Sie dem Kurs einen neuen Raum zu, bevor Sie den Studenten dem Kurs hinzufügen.", errorMessageWindow);
        }

        //try{
            this.kurs.studentLoeschen(this);
            this.kurs = kurs;
            this.kurs.studentHinzufuegen(this);
        /*} catch (Exception e){
            e.printStackTrace();
        }*/


    }

    public void exmatrikulieren() throws UserInputException {
        /*try {
            this.firma.studentLoeschen(this);
            this.kurs.studentLoeschen(this);
        } catch (Exception e){
            return e.getCause();
        }
        return null;*/

        this.firma.studentLoeschen(this);
        this.kurs.studentLoeschen(this);
        Application.studentenVerwaltung.studentenListe.remove(this);


    }
}