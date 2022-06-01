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

    public StudentenVerwaltung(List<Optional<Betreuer>> betreuerListe, List<Optional<Firma>> firmaListe, List<Optional<Student>> studentenListe, List<Optional<Kurs>> kursListe, List<Optional<Raum>> raumListe) {
        this.betreuerListe = betreuerListe;
        this.firmaListe = firmaListe;
        this.studentenListe = studentenListe;
        this.kursListe = kursListe;
        this.raumListe = raumListe;
    }
}
