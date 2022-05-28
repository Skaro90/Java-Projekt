package JavaProjekt.src.main.java.de.studentenverwaltung;

import java.util.Date;

public class Betreuer extends Person{
    private Integer betreuerId;
    private String telefonnummer;
    private Integer zaehler;

//    Foreign Keys
    private Firma firma;

    public Betreuer(String nachname, String vorname, String email, Date geburtstag, Integer betreuerId, String telefonnummer, Integer zaehler, Firma firma) {
        super(nachname, vorname, email, geburtstag);
        this.betreuerId = betreuerId;
        this.telefonnummer = telefonnummer;
        this.zaehler = zaehler;
        this.firma = firma;
    }
}
