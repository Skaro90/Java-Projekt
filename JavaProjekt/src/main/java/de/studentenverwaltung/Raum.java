package de.studentenverwaltung;

import de.studentenverwaltung.gui.TempErrorMessageWindow;
import de.studentenverwaltung.exceptions.UserInputException;

public class Raum {
    private int raumId;
    private String raumNummer;
    private int kapazitaet;
    private static int zaehler=0;

//    Foreign Keys
    private Kurs kurs;


    public Raum(int raumId, String raumNummer, int kapazitaet, Kurs kurs) {
        this.raumId = raumId;
        this.raumNummer = raumNummer;
        this.kapazitaet = kapazitaet;
        this.kurs = kurs;
    }

    public Raum(String raumNummer, int kapazitaet, Kurs kurs){
        this.raumNummer = raumNummer;
        this.kapazitaet = kapazitaet;
        this.kurs = kurs;
        this.raumId = zaehler;
        zaehler++;
    }


    public String getRaumNummer() {
        return raumNummer;
    }
}
