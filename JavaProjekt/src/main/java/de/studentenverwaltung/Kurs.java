package JavaProjekt.src.main.java.de.studentenverwaltung;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;

public class Kurs {
    private Integer kursId;
    private String kursName;

//    Foreign Keys
    private Raum raum;
    private List<Optional<Student>> studentenListe;

    public Kurs(Integer kursId, String kursName, Raum raum, List<Optional<Student>> studentenListe) {
        this.kursId = kursId;
        this.kursName = kursName;
        this.raum = raum;
        this.studentenListe = studentenListe;
    }
}
