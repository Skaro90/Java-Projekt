package de.studentenverwaltung.gui.controllers;

import de.studentenverwaltung.Kurs;
import de.studentenverwaltung.Raum;
import de.studentenverwaltung.StudentenVerwaltung;
import de.studentenverwaltung.exceptions.UserInputException;
import de.studentenverwaltung.gui.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class KursAnlegenController {

    @FXML
    private TextField kursNameTextField;
    @FXML
    private MenuButton raumNameMenuButton;

    @FXML
    private MenuItem raumMenuItem1;

    @FXML
    private MenuItem raumMenuItem2;

    @FXML
    void abortInput(ActionEvent event) {
        ((Stage)raumNameMenuButton.getScene().getWindow()).close();
    }

    @FXML
    void confirmInput(ActionEvent event) {

        ((Stage)raumNameMenuButton.getScene().getWindow()).close();
    }

    @FXML
    void selectRaum(ActionEvent event) {
        raumNameMenuButton.setText(((MenuItem)event.getSource()).getText());
    }

}
