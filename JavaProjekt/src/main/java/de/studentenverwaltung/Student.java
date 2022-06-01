package de.studentenverwaltung;

import de.gui.TempErrorMessageWindow;
import de.studentenverwaltung.exceptions.UserInputException;

import java.util.Date;

public class Student extends Person{
    private int studentId; // static? Als Zähler?
    private String matrikelnummer;
    private Vorkenntnisse vorkenntnisse;

//    Foreign Keys
    private Firma firma;
    private Kurs kurs;

    private enum Vorkenntnisse{
        Absoluter_Anfaenger,
        Anfaenger,
        Laie,
        Fortgeschrittener,
        Experte
    }

    Firma getFirma(){
        return firma;
    }

    void setFirma(Firma firma){
        this.firma = firma;
    }

    public void versetzen(Kurs newKurs) throws UserInputException{
        if(!(newKurs.getRaum().getKapazitaet() > newKurs.getStudentenListe().size())){
            TempErrorMessageWindow errorMessageWindow = new TempErrorMessageWindow();
            throw new UserInputException("Der Kurs-Raum besitzt nicht die Kapazität für einen weiteren Studenten. Bitte ordnen Sie dem Kurs einen neuen Raum zu, bevor Sie den Studenten dem Kurs hinzufügen.", errorMessageWindow);
        }

        this.kurs.getStudentenListe().remove(this);
        this.kurs = newKurs;
        newKurs.studentHinzufuegen(this);
    }

    public Student(String nachname, String vorname, String email, Date geburtstag, int studentId, String matrikelnummer, Vorkenntnisse vorkenntnisse, Firma firma, Kurs kurs) {
        super(nachname, vorname, email, geburtstag);
        this.studentId = studentId;
        this.matrikelnummer = matrikelnummer;
        this.vorkenntnisse = vorkenntnisse;
        this.firma = firma;
        this.kurs = kurs;
    }
}
