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


    // Methoden mit Package-Sichtbarkeit

    Kurs getKurs(){
        return kurs;
    }

    int getKapazitaet() {
        return kapazitaet;
    }

    public boolean genugPlatz(Kurs kurs){
        if(kapazitaet >= kurs.getStudentenListe().size()){
            return true;
        }

        return false;
    }

    public void raumBelegen(Kurs kurs) throws UserInputException {
        if(!genugPlatz(kurs)){
            TempErrorMessageWindow errorMessageWindow = new TempErrorMessageWindow();
            throw new UserInputException("Der Raum hat nicht genug Platz für den Kurs. Bitte wählen Sie einen anderen Raum.", errorMessageWindow);
        }
        this.kurs = kurs;
    }

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
