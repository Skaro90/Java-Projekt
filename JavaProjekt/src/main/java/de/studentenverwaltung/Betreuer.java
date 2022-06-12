package de.studentenverwaltung;

import java.util.Date;

public class Betreuer extends Person{
    private int betreuerId;
    private String telefonnummer;
    private static int zaehler=0;


    public Betreuer(String nachname, String vorname, String email, Date geburtstag, int betreuerId, String telefonnummer) {
        super(nachname, vorname, email, geburtstag);
        this.betreuerId = betreuerId;
        this.telefonnummer = telefonnummer;
    }

    public Betreuer(String nachname, String vorname, String email, Date geburtstag, String telefonnummer){
        super(nachname, vorname, email, geburtstag);
        this.telefonnummer = telefonnummer;
        this.betreuerId = zaehler;
        zaehler++;
    }

    public int getBetreuerId() {
        return betreuerId;
    }
}
