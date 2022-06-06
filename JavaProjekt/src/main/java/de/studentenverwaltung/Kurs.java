package de.studentenverwaltung;

import de.studentenverwaltung.gui.TempErrorMessageWindow;
import de.studentenverwaltung.exceptions.UserInputException;


import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Kurs {
    private int kursId;
    private String kursName;
    private static int zaehler = 0;

//    Foreign Keys
    private Raum raum;
    private ArrayList<Student> studentenListe;


    public Kurs(int kursId, String kursName, Raum raum) {

    // Getter mit Package-Sichtbarkeit


    Raum getRaum() {
        return raum;
    }

    List<Optional<Student>> getStudentenListe() {
        return studentenListe;
    }

    public void studentHinzufuegen(Student student){
        this.studentenListe.add(Optional.of(student));
    }

    public void raumWechseln(Raum newRaum) throws UserInputException{
        if(newRaum.getKurs() != null){
            TempErrorMessageWindow errorMessageWindow = new TempErrorMessageWindow();
            throw new UserInputException("Dem Raum ist bereits ein Kurs zugeordnet. Bitte wählen Sie einen anderen Raum.", errorMessageWindow);
        }

        raum.raumBelegen(null);
        raum = newRaum;
        newRaum.raumBelegen(this);
    }

    public Kurs(int kursId, String kursName, Raum raum, List<Optional<Student>> studentenListe) {
        this.kursId = kursId;
        this.kursName = kursName;
        this.raum = raum;
    }

    public Kurs(String kursName, Raum raum){
        this.kursName = kursName;
        this.raum = raum;
        this.kursId = zaehler;
        zaehler++;
    }

    public String getKursName() {
        return kursName;
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

    public Throwable studentHinzufuegen(Student student){
        try {
            studentenListe.add(student);
        }catch (Exception e){
            return e.getCause();
        }
        return null;
    }

    public Throwable raumWechseln(Raum raum){
        try {
//            this.raum.kursLoeschen();
            this.raum = raum;
//            this.raum.kursHinzufuegen(this);
        }
        catch (Exception e){
            return e;
        }
        return null;
    }

    public int kursGroeße(){
        return this.studentenListe.size();
    }
}
