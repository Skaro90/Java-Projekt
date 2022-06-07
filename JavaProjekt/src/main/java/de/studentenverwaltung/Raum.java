package de.studentenverwaltung;

import java.sql.SQLOutput;

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

    Kurs getKurs(){
        return kurs;
    }

    int getKapazitaet(){
        return kapazitaet;
    }
    }

    private void genugPlatz(Kurs kurs){
        int groesse = 0;
        groesse = kurs.kursGroeße();
        if(groesse <= kapazitaet){
            System.out.println("Kurs passt is den Raum.");
        } else {
            System.out.println("Kurs passt nicht in den Raum.");
        }
    }

    public boolean raumHinzufuegen(Kurs kurs){
        this.kurs = kurs;
        return true;
    }

    public void raumLoeschen() {
        this.kurs = null;
    }

}

