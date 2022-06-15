package de.studentenverwaltung.gui.controllers;

import de.studentenverwaltung.Kurs;
import de.studentenverwaltung.Raum;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainViewController implements Initializable {

    private static ObservableList<String> kursListItems = FXCollections.observableArrayList();
    private static ObservableList<String> studentenListItems = FXCollections.observableArrayList();

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
    void onKursAnlegenButton(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de/studentenverwaltung/gui/kurs-anlegen-dialog.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle("Kurs anlegen");
            stage.setScene(new Scene(root1));
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
                int selectedIndex = kursList.getSelectionModel().getSelectedIndices().get(0);

                try {
                    Application.studentenVerwaltung.kursLöschen(Application.studentenVerwaltung.findeKurs(kursListItems.get(selectedIndex)));
                } catch (UserInputException e) {
                    throw new RuntimeException(e);
                }
                removeKursFromList(kursListItems.get(selectedIndex));
            }
        }
    }

    @FXML
    void onKursListClicked(MouseEvent event) {
        if(kursList.getSelectionModel().getSelectedItems().isEmpty()){
            kursBearbeitenButton.setDisable(true);
            kursEntfernenButton.setDisable(true);
        } else {
            kursBearbeitenButton.setDisable(false);
            kursEntfernenButton.setDisable(false);
        }
    }

    @FXML
    void onStudentAnlegenButton(ActionEvent event) {

    }

    @FXML
    void onStudentBearbeitenButton(ActionEvent event) {

    }

    @FXML
    void onStudentEntfernenButton(ActionEvent event) {

    }

    @FXML
    void onStudentenListClicked(MouseEvent event) {
        if(studentenList.getSelectionModel().getSelectedItems().isEmpty()){
            kursBearbeitenButton.setDisable(true);
            kursEntfernenButton.setDisable(true);
        } else {
            kursBearbeitenButton.setDisable(false);
            kursEntfernenButton.setDisable(false);
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

    public static void removeKursFromList(String kursName){

        int index = kursListItems.indexOf(kursName);
        if(index >= 0) {

            kursListItems.remove(index);
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
