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

    //noch löschen
    public Betreuer(int id){
        super();
        this.betreuerId = id;
    }

    public int getBetreuerId() {
        return betreuerId;
    }
}
