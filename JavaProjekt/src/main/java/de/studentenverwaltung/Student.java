package JavaProjekt.src.main.java.de.studentenverwaltung;

import java.util.Date;

public class Student extends Person{
    private Integer studentId;
    private String matrikelnummer;
    private Vorkenntnisse vorkenntnisse;

//    Foreign Keys
    private Firma firma;
    private Kurs kurs;

    private enum Vorkenntnisse{
        Absoluter_Anfaenger,
        Anfaenger,
        Laie,
        Fortgeschrittener,
        Experte
    }

    public Student(String nachname, String vorname, String email, Date geburtstag, Integer studentId, String matrikelnummer, Vorkenntnisse vorkenntnisse, Firma firma, Kurs kurs) {
        super(nachname, vorname, email, geburtstag);
        this.studentId = studentId;
        this.matrikelnummer = matrikelnummer;
        this.vorkenntnisse = vorkenntnisse;
        this.firma = firma;
        this.kurs = kurs;
    }

    public Throwable versetzen(Kurs kurs){
        try {
            this.kurs.studentLoeschen(this);
            this.kurs = kurs;
            this.kurs.studentHinzufuegen(this);

        } catch (Exception e) {
            e.printStackTrace();
            return e.getCause();
        }
        return null;
    }

    public Throwable exmatrikulieren(){
        try {
            this.firma.studentLoeschen(this);
            this.kurs.studentLoeschen(this);
        } catch (Exception e){
            return e.getCause();
        }
        return null;
    }
}
