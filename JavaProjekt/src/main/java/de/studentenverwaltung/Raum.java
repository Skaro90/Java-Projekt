package de.studentenverwaltung;


public class Raum {
    private final int raumId;
    private String raumNummer;
    private int kapazitaet;

    //    Foreign Keys
    private Kurs kurs;

    public Raum(int raumId, String raumNummer, int kapazitaet, Kurs kurs) {
        this.raumId = raumId;
        this.raumNummer = raumNummer;
        this.kapazitaet = kapazitaet;
        this.kurs = kurs;
    }


    public String getRaumNummer() {
        return raumNummer;
    }

    public Kurs getKurs(){
        return kurs;
    }

    public int getKapazitaet(){
        return kapazitaet;
    }

    public void nummerAendern(String RNm){
        this.raumNummer = RNm;
    }

    public void kapazitaetAendern(int kapa){
        this.kapazitaet = kapa;

    }

    public boolean kursHinzufuegen(Kurs kurs) {
        this.kurs = kurs;
        return true;
    }

    public void kursLoeschen() {
        this.kurs = null;
    }

    public int getRaumId() {
        return raumId;
    }
}

