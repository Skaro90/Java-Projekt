package de.studentenverwaltung;


public class Raum {
    private final int raumId;
    private String raumNummer;
    private int kapazitaet;
    private static final int zaehler=0;

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

    private void genugPlatz(Kurs kurs){
        int groesse = 0;
        groesse = kurs.getKursGroesse();
        if(groesse <= kapazitaet){
            System.out.println("Kurs passt is den Raum.");
        } else {
            System.out.println("Kurs passt nicht in den Raum.");
        }
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

