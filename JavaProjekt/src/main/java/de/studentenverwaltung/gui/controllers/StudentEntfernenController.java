package de.studentenverwaltung.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StudentEntfernenController {

    MainViewController mainViewController;
    @FXML
    private Button okButton;

    @FXML
    void abortInput(ActionEvent event) {
        ((Stage) okButton.getScene().getWindow()).close();
    }

    @FXML
    void confirmDeleteStudentInput(ActionEvent event) {
        mainViewController.studentEntfernen();
        ((Stage) okButton.getScene().getWindow()).close();
    }

    public void initData(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

}
