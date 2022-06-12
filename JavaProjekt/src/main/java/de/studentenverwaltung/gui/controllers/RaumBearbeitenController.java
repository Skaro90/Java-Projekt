package de.studentenverwaltung.gui.controllers;

import de.studentenverwaltung.Raum;
import de.studentenverwaltung.exceptions.UserInputException;
import de.studentenverwaltung.gui.Application;
import de.studentenverwaltung.gui.ErrorMessageWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RaumBearbeitenController {

    private String oldName;

    private Raum raum;
    @FXML
    private TextField raumKapazitaetTextField;

    @FXML
    private TextField raumNameTextField;

    @FXML
    void abortInput(ActionEvent event) {
        ((Stage)raumKapazitaetTextField.getScene().getWindow()).close();
    }

    @FXML
    void confirmInput(ActionEvent event) throws UserInputException{
        try {
            raum.kapazitaetAendern(Integer.parseInt(raumKapazitaetTextField.getText()));
            raum.nummerAendern(raumNameTextField.getText());

            RaumeAnzeigenController.removeItemFromList(oldName);
            RaumeAnzeigenController.addItemToList(raumNameTextField.getText(), Integer.parseInt(raumKapazitaetTextField.getText()));

            ((Stage)raumNameTextField.getScene().getWindow()).close();
        } catch (NumberFormatException e) {
            ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
            throw new UserInputException("Bitte Überprüfen Sie die Kapazität.", errorMessageWindow);
        }

    }

    void initData(String raumNummer, int kapazitaet){

        oldName = raumNummer;

        raumNameTextField.setText(raumNummer);
        raumKapazitaetTextField.setText(Integer.toString(kapazitaet));

        raum = Application.studentenVerwaltung.findeRaum(raumNummer);
    }
}
