package de.studentenverwaltung;

import java.util.Date;

public class Betreuer extends Person{
    private Integer betreuerId;
    private String telefonnummer;
    private static int zaehler;

//    Foreign Keys
    private Firma firma;

    public Betreuer(String nachname, String vorname, String email, Date geburtstag, int betreuerId, String telefonnummer, Firma firma) {
        super(nachname, vorname, email, geburtstag);
        this.betreuerId = betreuerId;
        this.telefonnummer = telefonnummer;
        this.firma = firma;
    }
}
