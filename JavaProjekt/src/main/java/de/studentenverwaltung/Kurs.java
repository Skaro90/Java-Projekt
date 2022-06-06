package JavaProjekt.src.main.java.de.studentenverwaltung;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Kurs {
    private Integer kursId;
    private String kursName;

//    Foreign Keys
    private Raum raum;
    private ArrayList<Student> studentenListe;

    public Kurs(Integer kursId, String kursName, Raum raum, ArrayList<Student> studentenListe) {
        this.kursId = kursId;
        this.kursName = kursName;
        this.raum = raum;
        this.studentenListe = studentenListe;
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
