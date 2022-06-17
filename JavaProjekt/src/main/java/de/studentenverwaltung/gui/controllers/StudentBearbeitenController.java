package de.studentenverwaltung.gui.controllers;

import de.studentenverwaltung.Firma;
import de.studentenverwaltung.Kurs;
import de.studentenverwaltung.Student;
import de.studentenverwaltung.StudentenVerwaltung;
import de.studentenverwaltung.exceptions.UserInputException;
import de.studentenverwaltung.gui.Application;
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

public class StudentBearbeitenController implements Initializable {


    private static Student student;
    @FXML
    private Button confirmStudentButton;

    @FXML
    private TextField studentEmailTextField;

    @FXML
    private DatePicker studentGeburtstagField;

    @FXML
    private MenuButton studentKursButton;

    private static ObservableList<MenuItem> kursMenuItems = FXCollections.observableArrayList();

    @FXML
    private TextField studentNachnameTextField;

    @FXML
    private MenuButton studentVorkenntnisseButton;

    @FXML
    private TextField studentVornameTextField;

    @FXML
    void abortInput(ActionEvent event) {
        ((Stage)confirmStudentButton.getScene().getWindow()).close();
    }

    @FXML
    void confirmStudenBearbeitentInput(ActionEvent event) {

        Student.Vorkenntnisse vorkenntnisse;
        switch (studentVorkenntnisseButton.getText()){
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

        try {
            Kurs k = student.getKurs();

            Application.studentenVerwaltung.updateStudent(Application.studentenVerwaltung.findeStudent(student.getMatrikelnummer()), studentNachnameTextField.getText(), studentVornameTextField.getText(), studentEmailTextField.getText(), Date.from(Instant.from(studentGeburtstagField.getValue().atStartOfDay(ZoneId.systemDefault()))),
                    Application.studentenVerwaltung.findeKurs(studentKursButton.getText()), vorkenntnisse);

            MainViewController.removeStudentFromList(student.getMatrikelnummer());
            if(Application.studentenVerwaltung.findeStudent(student.getMatrikelnummer()).getKurs() == k){
                MainViewController.addStudentToList(studentNachnameTextField.getText() + " " + studentVornameTextField.getText(), student.getMatrikelnummer());
            }



            ((Stage)confirmStudentButton.getScene().getWindow()).close();
        } catch (UserInputException e) {
            throw new RuntimeException(e);
        }
    }

    public void initData(Student student){
        studentNachnameTextField.setText(student.getNachname());
        studentVornameTextField.setText(student.getVorname());
        studentEmailTextField.setText(student.getEmail());
        studentGeburtstagField.setValue(student.getGeburtstag().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        studentKursButton.setText(student.getKurs().getKursName());

        this.student = student;

        String vk;

        switch (student.getVorkenntnisse()) {
            case Absoluter_Anfaenger:
                vk = "Absoluter Anfänger";
                break;
            case Anfaenger:
                vk = "Anfänger";
                break;
            case Laie:
                vk = "Laie";
                break;
            case Fortgeschrittener:
                vk = "Fortgeschrittener";
                break;
            case Experte:
                vk = "Experte";
                break;
            default:
                vk = "Absoluter Anfänger";
                break;

        }
            studentVorkenntnisseButton.setText(vk);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         kursMenuItems = Application.studentenVerwaltung.getKursListe().stream()
                .sorted(Comparator.comparing(Kurs::getKursName))
                .map(x -> new MenuItem(x.getKursName()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        if(kursMenuItems.isEmpty()){
            studentKursButton.setText("Keine Kurse vorhanden");
            confirmStudentButton.setDisable(true);
        } else {
            studentKursButton.getItems().setAll(kursMenuItems);
            studentKursButton.setText(kursMenuItems.get(0).getText());

            confirmStudentButton.setDisable(false);
        }


        studentVorkenntnisseButton.getItems().forEach(x -> x.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                studentVorkenntnisseButton.setText(x.getText());
            }
        }));

        studentKursButton.getItems().forEach(x -> x.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                studentKursButton.setText(x.getText());
            }
        }));
    }
}
