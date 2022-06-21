package de.studentenverwaltung;

import de.studentenverwaltung.exceptions.UserInputException;
import de.studentenverwaltung.gui.Application;
import de.studentenverwaltung.gui.ErrorMessageWindow;

import java.util.Date;

public class Student extends Person{
    private final int studentId;
    private final String matrikelnummer;
    private Vorkenntnisse vorkenntnisse;

    private final Firma firma;
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
        Application.studentenVerwaltung.getStudentenListe().remove(this);


    }

    public int getStudentId() {
        return studentId;
    }

    public Vorkenntnisse getVorkenntnisse() {
        return vorkenntnisse;
    }

    public String getMatrikelnummer() {
        return matrikelnummer;
    }

    public Firma getFirma() {
        return firma;
    }

    public Kurs getKurs() {
        return kurs;
    }

    public void vorkenntnisseAendern(Vorkenntnisse vorkenntnisse){
        this.vorkenntnisse = vorkenntnisse;
    }
}