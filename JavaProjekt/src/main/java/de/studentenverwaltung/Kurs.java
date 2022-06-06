package de.studentenverwaltung;

import de.studentenverwaltung.gui.TempErrorMessageWindow;
import de.studentenverwaltung.exceptions.UserInputException;

import java.util.List;
import java.util.Optional;

public class Kurs {
    private int kursId; //static? Zähler?
    private String kursName;

//    Foreign Keys
    private Raum raum;
    private List<Optional<Student>> studentenListe;

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
        this.studentenListe = studentenListe;
    }
}
