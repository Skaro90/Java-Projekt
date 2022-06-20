package de.studentenverwaltung.gui.controllers;

import de.studentenverwaltung.Kurs;
import de.studentenverwaltung.exceptions.UserInputException;
import de.studentenverwaltung.gui.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class KursEntfernenController {

    MainViewController mainViewController;
    @FXML
    private Button okButton;

    @FXML
    void abortInput(ActionEvent event) {
        ((Stage)okButton.getScene().getWindow()).close();
    }

    @FXML
    void confirmDeleteKursInput(ActionEvent event) {
        mainViewController.kursEntfernen();
        ((Stage)okButton.getScene().getWindow()).close();
    }

    public void initData(MainViewController mainViewController){
        this.mainViewController = mainViewController;
    }

}
