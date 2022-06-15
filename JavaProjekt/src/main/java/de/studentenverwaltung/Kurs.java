package de.studentenverwaltung;

// TODOS: Kommentare

import de.studentenverwaltung.exceptions.UserInputException;
import de.studentenverwaltung.gui.ErrorMessageWindow;

import java.util.ArrayList;

public class Kurs {
    private int kursId;
    private String kursName;
    private static int zaehler = 0;

    //    Foreign Keys
    private Raum raum;
    private ArrayList<Student> studentenListe = new ArrayList<Student>();

    public Kurs(int kursId, String kursName, Raum raum) {
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

    public Raum getRaum(){
        return raum;
    }

    public ArrayList<Student> getStudentenListe(){
        return studentenListe;
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
            ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
            throw new UserInputException("Der zu löschende Student ist nicht in der Kursliste vorhanden.", errorMessageWindow);
        }
        //return change;
        return true;
    }

    public void studentHinzufuegen(Student student) throws UserInputException{
        if(studentenListe.contains(student)){
            ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
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

        if(raum != null){
            if(raum.getKurs() != null){
                ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
                throw new UserInputException("Dem Raum ist bereits ein Kurs zugeordnet. Bitte wählen Sie einen anderen Raum.", errorMessageWindow);
            }


            //raum.raumBelegen(null);
            this.raum = raum;
            raum.kursHinzufuegen(this);
        } else {
            this.raum = null;
        }


        //raum.raumBelegen(this);
    }

    public void kursNameAendern(String newName){
        this.kursName = newName;
    }

    public int getKursGroesse(){
        return this.studentenListe.size();
    }
}