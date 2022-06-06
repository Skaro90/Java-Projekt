package JavaProjekt.src.main.java.de.studentenverwaltung;

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
}
