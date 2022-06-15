package de.studentenverwaltung;

// TODOS: Kommentare

import de.studentenverwaltung.exceptions.UserInputException;
import de.studentenverwaltung.gui.TempErrorMessageWindow;

import java.util.ArrayList;

public class Kurs {
    private int kursId;
    private String kursName;
    private static int zaehler = 0;

    //    Foreign Keys
    private Raum raum;
    private ArrayList<Student> studentenListe;

    public Kurs(int kursId, String kursName, Raum raum) {
        this.kursId = kursId;
        this.kursName = kursName;
        this.raum = raum;
    }
    public String getKursName() {
        return kursName;
    }

    Raum getRaum(){
        return raum;
    }

    public Boolean studentLoeschen(Student student) throws UserInputException { //void?
        boolean change = false;
        /*for (int i = 0; i < studentenListe.size(); i++){
            if (student == studentenListe.get(i)){
                studentenListe.remove(i);
                change = true;
            }
        }*/
        if(!studentenListe.remove(student)){
            TempErrorMessageWindow errorMessageWindow = new TempErrorMessageWindow();
            throw new UserInputException("Der zu löschende Student ist nicht in der Kursliste vorhanden.", errorMessageWindow);
        }
        //return change;
        return true;
    }

    public void studentHinzufuegen(Student student) throws UserInputException{
        if(studentenListe.contains(student)){
            TempErrorMessageWindow errorMessageWindow = new TempErrorMessageWindow();
            throw new UserInputException("Der Student ist bereits in dem Kurs vorhanden.", errorMessageWindow);
        }

        studentenListe.add(student);
    }

    public void raumWechseln(Raum raum) throws UserInputException{
       /* try {
//            this.raum.kursLoeschen();
            this.raum = raum;
//            this.raum.kursHinzufuegen(this);
        }
        catch (Exception e){
            return e;
        }
        return null;*/

        if(raum.getKurs() != null){
            TempErrorMessageWindow errorMessageWindow = new TempErrorMessageWindow();
            throw new UserInputException("Dem Raum ist bereits ein Kurs zugeordnet. Bitte wählen Sie einen anderen Raum.", errorMessageWindow);
        }

        //raum.raumBelegen(null);
        this.raum = raum;
        //raum.raumBelegen(this);
    }

    public int getKursGroesse(){
        return this.studentenListe.size();
    }

    public int getKursId() {
        return kursId;
    }
}