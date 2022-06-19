package de.studentenverwaltung.gui.controllers;

import de.studentenverwaltung.Kurs;
import de.studentenverwaltung.Raum;
import de.studentenverwaltung.Student;
import de.studentenverwaltung.StudentenVerwaltung;
import de.studentenverwaltung.exceptions.UserInputException;
import de.studentenverwaltung.gui.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class MainViewController implements Initializable {

    private static ObservableList<String> kursListItems = FXCollections.observableArrayList();
    private static ObservableList<String> studentenListItems = FXCollections.observableArrayList();
    private static List<String> studentenMatrikelnummerList = new ArrayList<String>();

    @FXML
    private ListView<String> kursList;

    @FXML
    private ListView<String> studentenList;


    @FXML
    private Button kursAnlegenButton;

    @FXML
    private Button kursBearbeitenButton;

    @FXML
    private Button kursEntfernenButton;

    @FXML
    private Button studentAnlegenButton;

    @FXML
    private Button studentBearbeitenButton;

    @FXML
    private Button studentEntfernenButton;


    @FXML
    void onKursAnlegenButton(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de/studentenverwaltung/gui/kurs-anlegen-dialog.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle("Kurs anlegen");
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onKursBearbeitenButton(ActionEvent event) {
        try {
            if(!kursList.getSelectionModel().getSelectedItems().isEmpty()){

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de/studentenverwaltung/gui/kurs-bearbeiten-dialog.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                //stage.initStyle(StageStyle.TRANSPARENT);
                stage.setTitle("Kurs bearbeiten");
                stage.setScene(new Scene(root1));
                stage.setResizable(false);

                KursBearbeitenController controller = fxmlLoader.getController();

                int selectedIndex = kursList.getSelectionModel().getSelectedIndices().get(0);
                controller.initData(kursListItems.get(selectedIndex));

                stage.show();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onKursEntfernenButton(ActionEvent event) {
        if(!kursList.getSelectionModel().getSelectedItems().isEmpty()){
            if(kursList.getSelectionModel().getSelectedIndices().size() > 0) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de/studentenverwaltung/gui/kurs-entfernen-dialog.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    //stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setTitle("Kurs entfernen");
                    stage.setScene(new Scene(root1));
                    stage.setResizable(false);

                    KursEntfernenController controller = fxmlLoader.getController();

                    controller.initData(this);

                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void kursEntfernen(){
        int selectedIndex = kursList.getSelectionModel().getSelectedIndices().get(0);

        try {
            Application.studentenVerwaltung.kursLöschen(Application.studentenVerwaltung.findeKurs(kursListItems.get(selectedIndex)));
        } catch (UserInputException e) {
            throw new RuntimeException(e);
        }
        removeKursFromList(kursListItems.get(selectedIndex));
        studentenList.getItems().clear();
    }

    @FXML
    void onKursListClicked(MouseEvent event) {

        studentenList.getSelectionModel().clearSelection();

        if(kursList.getSelectionModel().getSelectedItems().isEmpty()){
            kursBearbeitenButton.setDisable(true);
            kursEntfernenButton.setDisable(true);
            studentAnlegenButton.setDisable(true);
            studentBearbeitenButton.setDisable(true);
            studentEntfernenButton.setDisable(true);
        } else {
            kursBearbeitenButton.setDisable(false);
            kursEntfernenButton.setDisable(false);
            studentAnlegenButton.setDisable(false);
            studentBearbeitenButton.setDisable(true);
            studentEntfernenButton.setDisable(true);



            studentenListItems = Application.studentenVerwaltung.findeKurs(kursListItems.get(kursList.getSelectionModel().getSelectedIndices().get(0))).getStudentenListe().stream()
                    .sorted(Comparator.comparing(Student::getNachname))
                    .map(x -> x.getNachname() + " " + x.getVorname())
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));

            studentenMatrikelnummerList = Application.studentenVerwaltung.findeKurs(kursListItems.get(kursList.getSelectionModel().getSelectedIndices().get(0))).getStudentenListe().stream()
                    .sorted(Comparator.comparing(Student::getNachname))
                    .map(x -> x.getMatrikelnummer())
                    .collect(Collectors.toList());

            this.studentenList.setItems(studentenListItems);

        }


    }

    @FXML
    void onStudentAnlegenButton(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de/studentenverwaltung/gui/student-anlegen-dialog.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle("Student anlegen");
            stage.setScene(new Scene(root1));
            stage.setResizable(false);

            StudentAnlegenController controller = fxmlLoader.getController();

            controller.initData(kursListItems.get(kursList.getSelectionModel().getSelectedIndices().get(0)));

            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onStudentBearbeitenButton(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de/studentenverwaltung/gui/student-bearbeiten-dialog.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle("Student bearbeiten");
            stage.setScene(new Scene(root1));
            stage.setResizable(false);

            StudentBearbeitenController controller = fxmlLoader.getController();

            Student student = Application.studentenVerwaltung.findeStudent(studentenMatrikelnummerList.get(studentenList.getSelectionModel().getSelectedIndices().get(0)));

            controller.initData(student);

            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onStudentEntfernenButton(ActionEvent event) {
        if(!studentenList.getSelectionModel().getSelectedItems().isEmpty()){
            if(studentenList.getSelectionModel().getSelectedIndices().size() > 0) {

                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de/studentenverwaltung/gui/student-entfernen-dialog.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    //stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setTitle("Student entfernen");
                    stage.setScene(new Scene(root1));
                    stage.setResizable(false);

                    StudentEntfernenController controller = fxmlLoader.getController();

                    controller.initData(this);

                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void studentEntfernen(){
        int selectedIndex = studentenList.getSelectionModel().getSelectedIndices().get(0);

        try {
            Student student = Application.studentenVerwaltung.findeStudent(studentenMatrikelnummerList.get(selectedIndex));
            String matrikelnummer = student.getMatrikelnummer();
            Application.studentenVerwaltung.exmatrikulieren(student);
            removeStudentFromList(matrikelnummer);
        } catch (UserInputException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onStudentenListClicked(MouseEvent event) {
        if(studentenList.getSelectionModel().getSelectedItems().isEmpty()){
            studentBearbeitenButton.setDisable(true);
            studentEntfernenButton.setDisable(true);
        } else {
            studentBearbeitenButton.setDisable(false);
            studentEntfernenButton.setDisable(false);

            if(event.getClickCount() > 1){
                try {

                    Student student = Application.studentenVerwaltung.findeStudent(studentenMatrikelnummerList.get(studentenList.getSelectionModel().getSelectedIndices().get(0)));

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de/studentenverwaltung/gui/student-anzeigen-dialog.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    //stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setTitle(student.getVorname() + " " + student.getNachname());
                    stage.setScene(new Scene(root1));
                    stage.setResizable(false);

                    StudentAnzeigenController controller = fxmlLoader.getController();
                    controller.initData(student);

                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    @FXML
    void buttonRaumeAnzeigenClicked(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de/studentenverwaltung/gui/raume-anzeigen-dialog.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle("Räume anzeigen");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void buttonFirmenAnzeigenClicked(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de/studentenverwaltung/gui/firmen-anzeigen-dialog.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle("Firmen anzeigen");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public static void addKursToList(String kursName){
        int index = Collections.binarySearch(kursListItems, kursName);
        if(index < 0) index = ~index;

        kursListItems.add(index, kursName);

    }

    public static void addStudentToList(String student, String matrikelnummer){
        int index = Collections.binarySearch(studentenListItems, student);
        if(index < 0) index = ~index;

        studentenListItems.add(index, student);
        studentenMatrikelnummerList.add(index, matrikelnummer);
    }

    public static void removeKursFromList(String kursName){

        int index = kursListItems.indexOf(kursName);
        if(index >= 0) {

            kursListItems.remove(index);
        }
    }

    public static void removeStudentFromList(String matrikelnummer){
        int index = studentenMatrikelnummerList.indexOf(matrikelnummer);
        if(index >= 0){
            studentenListItems.remove(index);
            studentenMatrikelnummerList.remove(index);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        kursListItems = Application.studentenVerwaltung.getKursListe().stream()
                .sorted(Comparator.comparing(Kurs::getKursName))
                .map(x -> x.getKursName())
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        this.kursList.setItems(kursListItems);
    }
}
