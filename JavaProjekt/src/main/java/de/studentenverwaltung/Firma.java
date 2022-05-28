package JavaProjekt.src.main.java.de.studentenverwaltung;

import java.util.List;
import java.util.Optional;

public class Firma {
    private Integer firmenId;
    private String firmenname;
//    Adresse
    private String strasse;
    private String hausnummer;
    private String postleitzahl;
    private String stadt;

//    Foreign Keys
    private List<Optional<Student>> studentenListe;
    private Betreuer betreuer;

    public Firma(Integer firmenId, String firmenname, String strasse, String hausnummer, String postleitzahl, String stadt, List<Optional<Student>> studentenListe, Betreuer betreuer) {
        this.firmenId = firmenId;
        this.firmenname = firmenname;
        this.strasse = strasse;
        this.hausnummer = hausnummer;
        this.postleitzahl = postleitzahl;
        this.stadt = stadt;
        this.studentenListe = studentenListe;
        this.betreuer = betreuer;
    }
}
