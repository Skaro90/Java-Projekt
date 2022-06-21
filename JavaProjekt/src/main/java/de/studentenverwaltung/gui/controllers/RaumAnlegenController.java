package de.studentenverwaltung.gui.controllers;

import de.studentenverwaltung.exceptions.UserInputException;
import de.studentenverwaltung.gui.Application;
import de.studentenverwaltung.gui.ErrorMessageWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RaumAnlegenController {

    @FXML
    private TextField raumKapazitaetTextField;

    @FXML
    private TextField raumNameTextField;

    @FXML
    void abortInput(ActionEvent event) {
        ((Stage) raumKapazitaetTextField.getScene().getWindow()).close();
    }

    @FXML
    void confirmInput(ActionEvent event) throws UserInputException {
        try {
            Application.studentenVerwaltung.raumAnlegen(raumNameTextField.getText(), Integer.parseInt(raumKapazitaetTextField.getText()), null);
            RaumeAnzeigenController.addItemToList(raumNameTextField.getText(), Integer.parseInt(raumKapazitaetTextField.getText()));

            ((Stage) raumNameTextField.getScene().getWindow()).close();
        } catch (UserInputException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
            throw new UserInputException("Bitte überprüfen Sie die Kapazität.", errorMessageWindow);
        }
        ((Stage) raumNameTextField.getScene().getWindow()).close();


    }

}
