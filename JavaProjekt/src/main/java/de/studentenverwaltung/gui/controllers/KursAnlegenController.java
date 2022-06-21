package de.studentenverwaltung.gui.controllers;

import de.studentenverwaltung.Kurs;
import de.studentenverwaltung.Raum;
import de.studentenverwaltung.StudentenVerwaltung;
import de.studentenverwaltung.exceptions.UserInputException;
import de.studentenverwaltung.gui.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class KursAnlegenController implements Initializable {

    @FXML
    private TextField kursNameTextField;
    @FXML
    private MenuButton raumNameMenuButton;

    @FXML
    private Button okButton;

    private static ObservableList<MenuItem> raumMenuItems = FXCollections.observableArrayList();

    @FXML
    void abortInput(ActionEvent event) {
        ((Stage) raumNameMenuButton.getScene().getWindow()).close();
    }

    @FXML
    void confirmInput(ActionEvent event) {
        try {
            Application.studentenVerwaltung.kursAnlegen(kursNameTextField.getText(), Application.studentenVerwaltung.findeRaum(raumNameMenuButton.getText()));
            MainViewController.addKursToList(kursNameTextField.getText());
        } catch (UserInputException e) {
            throw new RuntimeException(e);
        }


        ((Stage) raumNameMenuButton.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        raumMenuItems = Application.studentenVerwaltung.getRaumListe().stream()
                .sorted(Comparator.comparing(Raum::getRaumNummer))
                .filter(x -> x.getKurs() == null)
                .map(x -> new MenuItem(x.getRaumNummer()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        if (raumMenuItems.isEmpty()) {
            if (Application.studentenVerwaltung.getRaumListe().isEmpty())
                raumNameMenuButton.setText("Keine Räume vorhanden.");
            else
                raumNameMenuButton.setText("Keine leeren Räume vorhanden.");

            okButton.setDisable(true);

        } else {
            raumNameMenuButton.getItems().setAll(raumMenuItems);
            raumNameMenuButton.setText(raumMenuItems.get(0).getText());

            okButton.setDisable(false);
        }


        raumNameMenuButton.getItems().forEach(x -> x.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                raumNameMenuButton.setText(x.getText());
            }
        }));

    }
}
