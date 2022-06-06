package JavaProjekt.src.main.java.de.studentenverwaltung;

import java.util.Date;

public class Student extends Person{
    private int studentId;
    private String matrikelnummer;
    private Vorkenntnisse vorkenntnisse;
    private static int zaehler = 0; //max setzten in datenLaden()

    private Firma firma;
    private Kurs kurs;

    public enum Vorkenntnisse{
        Absoluter_Anfaenger,
        Anfaenger,
        Laie,
        Fortgeschrittener,
        Experte
    }

    public Student(String nachname, String vorname, String email, Date geburtstag, int studentId, String matrikelnummer, Vorkenntnisse vorkenntnisse, Firma firma, Kurs kurs) {
        super(nachname, vorname, email, geburtstag);
        this.studentId = studentId;
        this.matrikelnummer = matrikelnummer;
        this.vorkenntnisse = vorkenntnisse;
        this.firma = firma;
        this.kurs = kurs;
    }

    public Student(String nachname, String vorname, Date geburtsdatum,String email,String matrikelnummer, Firma firma,Kurs kurs,Vorkenntnisse vorkenntnisse){
        super(nachname, vorname, email, geburtsdatum);
        this.matrikelnummer = matrikelnummer;
        this.vorkenntnisse = vorkenntnisse;
        this.firma = firma;
        this.kurs = kurs;
        this.studentId = zaehler;
        zaehler++;
    }

    public String getMatrikelnummer() {
        return matrikelnummer;
    }

    public Firma getFirma() {
        return firma;
    }

    public Kurs getKurs() {
        return kurs;
    }
}
