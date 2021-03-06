package de.studentenverwaltung.gui.controllers;

import de.studentenverwaltung.Kurs;
import de.studentenverwaltung.Raum;
import de.studentenverwaltung.exceptions.UserInputException;
import de.studentenverwaltung.gui.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class KursBearbeitenController {

    private String oldName;
    private Raum raum;
    private Kurs kurs;

    @FXML
    private TextField kursNameTextField;

    @FXML
    private Button okButton;

    @FXML
    private MenuButton raumNameMenuButton;
    private static ObservableList<MenuItem> raumMenuItems = FXCollections.observableArrayList();

    @FXML
    void abortInput(ActionEvent event) {
        ((Stage) kursNameTextField.getScene().getWindow()).close();
    }

    @FXML
    void confirmInput(ActionEvent event) {
        try {
            Application.studentenVerwaltung.updateKurs(kurs, kursNameTextField.getText(), Application.studentenVerwaltung.findeRaum(raumNameMenuButton.getText()));
            MainViewController.removeKursFromList(oldName);
            MainViewController.addKursToList(kursNameTextField.getText());

            ((Stage) kursNameTextField.getScene().getWindow()).close();
        } catch (UserInputException e) {
            throw new RuntimeException(e);
        }
    }


    void initData(String kursName) {

        oldName = kursName;

        kurs = Application.studentenVerwaltung.findeKurs(kursName);
        raum = kurs.getRaum();

        kursNameTextField.setText(kursName);


        raumMenuItems = Application.studentenVerwaltung.getRaumListe().stream()
                .sorted(Comparator.comparing(Raum::getRaumNummer))
                .filter(x -> x.getKurs() == null)
                .map(x -> new MenuItem(x.getRaumNummer()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        if (raum != null) {
            raumNameMenuButton.setText(raum.getRaumNummer());

            MenuItem currentRaumMenuItem = new MenuItem(raum.getRaumNummer());

            raumMenuItems.add(0, currentRaumMenuItem);
        } else {
            raumNameMenuButton.setText("Kein Raum zugeordnet");
        }

        raumNameMenuButton.getItems().addAll(raumMenuItems);

        okButton.setDisable(raumNameMenuButton.getItems().isEmpty());

        raumNameMenuButton.getItems().forEach(x -> x.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                raumNameMenuButton.setText(x.getText());
            }
        }));

    }
}
