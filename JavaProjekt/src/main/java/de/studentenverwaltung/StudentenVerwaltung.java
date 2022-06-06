package de.studentenverwaltung;

import java.util.List;
import java.util.Optional;

public class StudentenVerwaltung {
//    Foreign Keys
    private List<Optional<Betreuer>> betreuerListe;
    private List<Optional<Firma>> firmaListe;
    private List<Optional<Student>> studentenListe;
    private List<Optional<Kurs>> kursListe;
    private List<Optional<Raum>> raumListe;

    public StudentenVerwaltung() {

    }
}
