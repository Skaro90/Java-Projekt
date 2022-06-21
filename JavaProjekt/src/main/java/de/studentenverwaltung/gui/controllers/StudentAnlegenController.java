package de.studentenverwaltung.gui.controllers;

import de.studentenverwaltung.Firma;
import de.studentenverwaltung.Student;
import de.studentenverwaltung.exceptions.UserInputException;
import de.studentenverwaltung.gui.Application;
import de.studentenverwaltung.gui.ErrorMessageWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class StudentAnlegenController implements Initializable {

    private String kursName;

    @FXML
    private Button confirmStudentButton;

    @FXML
    private TextField studentEmailTextField;

    @FXML
    private MenuButton studentFirmaButton;

    @FXML
    private MenuButton studentVorkenntnisseButton;

    private static ObservableList<MenuItem> firmaMenuItems = FXCollections.observableArrayList();

    @FXML
    private DatePicker studentGeburtstagField;

    @FXML
    private TextField studentNachnameTextField;

    @FXML
    private TextField studentMatrikelnummerTextField;

    @FXML
    private TextField studentVornameTextField;

    @FXML
    void abortInput(ActionEvent event) {
        ((Stage) confirmStudentButton.getScene().getWindow()).close();
    }

    @FXML
    void confirmStudentInput(ActionEvent event) throws UserInputException {
        Student.Vorkenntnisse vorkenntnisse;
        switch (studentVorkenntnisseButton.getText()) {
            case "Absoluter Anfänger":
                vorkenntnisse = Student.Vorkenntnisse.Absoluter_Anfaenger;
                break;
            case "Anfänger":
                vorkenntnisse = Student.Vorkenntnisse.Anfaenger;
                break;
            case "Laie":
                vorkenntnisse = Student.Vorkenntnisse.Laie;
                break;
            case "Fortgeschrittener":
                vorkenntnisse = Student.Vorkenntnisse.Fortgeschrittener;
                break;
            case "Experte":
                vorkenntnisse = Student.Vorkenntnisse.Experte;
                break;
            default:
                vorkenntnisse = Student.Vorkenntnisse.Absoluter_Anfaenger;
                break;
        }

        if (studentGeburtstagField.getValue() == null) {
            ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
            throw new UserInputException("Bitte überprüfen Sie das Geburtsdatum.", errorMessageWindow);
        }

        try {
            Application.studentenVerwaltung.studentAnlegen(studentNachnameTextField.getText(), studentVornameTextField.getText(), Date.from(Instant.from(studentGeburtstagField.getValue().atStartOfDay(ZoneId.systemDefault()))), studentEmailTextField.getText(), studentMatrikelnummerTextField.getText(),
                    Application.studentenVerwaltung.findeFirma(studentFirmaButton.getText()), Application.studentenVerwaltung.findeKurs(kursName), vorkenntnisse);
            MainViewController.addStudentToList(studentNachnameTextField.getText() + " " + studentVornameTextField.getText(), studentMatrikelnummerTextField.getText());

            ((Stage) confirmStudentButton.getScene().getWindow()).close();
        } catch (UserInputException e) {
            throw new RuntimeException(e);
        }
    }

    public void initData(String kurs) {
        kursName = kurs;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        firmaMenuItems = Application.studentenVerwaltung.getFirmaListe().stream()
                .sorted(Comparator.comparing(Firma::getFirmenname))
                .map(x -> new MenuItem(x.getFirmenname()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        if (firmaMenuItems.isEmpty()) {
            studentFirmaButton.setText("Keine Firmen vorhanden");
            confirmStudentButton.setDisable(true);
        } else {
            studentFirmaButton.getItems().setAll(firmaMenuItems);
            studentFirmaButton.setText(firmaMenuItems.get(0).getText());

            confirmStudentButton.setDisable(false);
        }


        studentVorkenntnisseButton.getItems().forEach(x -> x.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                studentVorkenntnisseButton.setText(x.getText());
            }
        }));

        studentFirmaButton.getItems().forEach(x -> x.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                studentFirmaButton.setText(x.getText());
            }
        }));
    }
}
