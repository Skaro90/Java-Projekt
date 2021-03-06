package de.studentenverwaltung;

import java.util.Date;

public class Betreuer extends Person{
    private final int betreuerId;
    private String telefonnummer;


    public Betreuer(String nachname, String vorname, String email, Date geburtstag, int betreuerId, String telefonnummer) {
        super(nachname, vorname, email, geburtstag);
        this.betreuerId = betreuerId;
        this.telefonnummer = telefonnummer;
    }



    public int getBetreuerId() {
        return betreuerId;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public void telefonnummerAendern(String telefonnummer){
        this.telefonnummer = telefonnummer;
    }

}
