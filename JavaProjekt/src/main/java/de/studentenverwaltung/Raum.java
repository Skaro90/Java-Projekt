package JavaProjekt.src.main.java.de.studentenverwaltung;

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
}
