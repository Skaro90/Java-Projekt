package JavaProjekt.src.main.java.de.studentenverwaltung;

import java.sql.SQLOutput;

public class Raum {
    private Integer raumId;
    private String raumNummer;
    private Integer kapazitaet;

//    Foreign Keys
    private Kurs kurs;

    public Raum(Integer raumId, String raumNummer, Integer kapazitaet, Kurs kurs) {
        this.raumId = raumId;
        this.raumNummer = raumNummer;
        this.kapazitaet = kapazitaet;
        this.kurs = kurs;
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
