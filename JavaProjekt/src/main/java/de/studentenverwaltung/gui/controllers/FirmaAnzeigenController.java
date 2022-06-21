package de.studentenverwaltung.gui.controllers;

import de.studentenverwaltung.Firma;
import de.studentenverwaltung.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.text.SimpleDateFormat;

public class FirmaAnzeigenController {

    @FXML
    private Label emailLabel;

    @FXML
    private Label firmaLabel;

    @FXML
    private Label geburtstagLabel;

    @FXML
    private Label hausnummerLabel;

    @FXML
    private Label nachnameLabel;

    @FXML
    private Label plzLabel;

    @FXML
    private Label stadtLabel;

    @FXML
    private Label strasseLabel;

    @FXML
    private Label telefonnummerLabel;

    @FXML
    private Label vornameLabel;

    public void initData(Firma firma) {
        firmaLabel.setText(firma.getFirmenname());
        strasseLabel.setText(firma.getStrasse());
        hausnummerLabel.setText(firma.getHausnummer());
        plzLabel.setText(firma.getPostleitzahl());
        stadtLabel.setText(firma.getStadt());

        vornameLabel.setText(firma.getBetreuer().getVorname());
        nachnameLabel.setText(firma.getBetreuer().getNachname());
        emailLabel.setText(firma.getBetreuer().getEmail());

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        geburtstagLabel.setText(format.format(firma.getBetreuer().getGeburtstag()));
        telefonnummerLabel.setText(firma.getBetreuer().getTelefonnummer());


    }

}
