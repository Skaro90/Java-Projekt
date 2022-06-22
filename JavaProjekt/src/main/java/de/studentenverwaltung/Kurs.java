package de.studentenverwaltung;

// TODOS: Kommentare

import de.studentenverwaltung.exceptions.UserInputException;
import de.studentenverwaltung.gui.ErrorMessageWindow;

import java.util.ArrayList;

public class Kurs {
    private final int kursId;
    private String kursName;

    //    Foreign Keys
    private Raum raum;
    private final ArrayList<Student> studentenListe = new ArrayList<Student>();

    public Kurs(int kursId, String kursName, Raum raum) {
        this.kursId = kursId;
        this.kursName = kursName;
        this.raum = raum;

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

    public void studentLoeschen(Student student) throws UserInputException {

        if(!studentenListe.remove(student)){
            ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
            throw new UserInputException("Der zu löschende Student ist nicht in der Kursliste vorhanden.", errorMessageWindow);
        }
    }

    public void studentHinzufuegen(Student student) throws UserInputException{
        if(studentenListe.contains(student)){
            ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
            throw new UserInputException("Der Student ist bereits in dem Kurs vorhanden.", errorMessageWindow);
        }

        studentenListe.add(student);
    }

    public void raumWechseln(Raum raum) throws UserInputException{

        if(raum != null){
            if(raum.getKurs() != null){
                ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
                throw new UserInputException("Dem Raum ist bereits ein Kurs zugeordnet. Bitte wählen Sie einen anderen Raum.", errorMessageWindow);
            }

            this.raum = raum;
            raum.kursHinzufuegen(this);
        } else {
            this.raum = null;
        }

    }

    public void kursNameAendern(String newName){
        this.kursName = newName;
    }

    public int getKursGroesse(){
        return this.studentenListe.size();
    }

    public int getKursId() {
        return kursId;
    }
}