package de.studentenverwaltung.gui.controllers;

import de.studentenverwaltung.Firma;
import de.studentenverwaltung.Raum;
import de.studentenverwaltung.Student;
import de.studentenverwaltung.exceptions.UserInputException;
import de.studentenverwaltung.gui.Application;
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

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class FirmenAnzeigenController implements Initializable {

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private ListView<String> firmaList;

    private static ObservableList<String> firmaListItems = FXCollections.observableArrayList();

    @FXML
    private ListView<String> studentenList;

    private static ObservableList<String> studentenListItems = FXCollections.observableArrayList();
    private static List<String> studentenMatrikelnummerList = new ArrayList<String>();

    @FXML
    void onDeleteFirmaButton(ActionEvent event) {
        if(!firmaList.getSelectionModel().getSelectedItems().isEmpty()){
            if(firmaList.getSelectionModel().getSelectedIndices().size() > 0) {
                int selectedIndex = firmaList.getSelectionModel().getSelectedIndices().get(0);

                Application.studentenVerwaltung.firmaLoeschen(Application.studentenVerwaltung.findeFirma(firmaListItems.get(selectedIndex)));

                removeItemFromFirmaList(firmaListItems.get(selectedIndex));
                studentenList.getItems().clear();
            }
        }
    }

    @FXML
    void onEditFirmaButton(ActionEvent event) {
        try {
            if(!firmaList.getSelectionModel().getSelectedItems().isEmpty()){

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de/studentenverwaltung/gui/firma-bearbeiten-dialog.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                //stage.initStyle(StageStyle.TRANSPARENT);
                stage.setTitle("Firma bearbeiten");
                stage.setScene(new Scene(root1));
                stage.setResizable(false);

                FirmaBearbeitenController controller = fxmlLoader.getController();

                int selectedIndex = firmaList.getSelectionModel().getSelectedIndices().get(0);
                controller.initData(Application.studentenVerwaltung.findeFirma(firmaListItems.get(selectedIndex)));

                stage.show();
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void onFirmaAnlegenButton(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de/studentenverwaltung/gui/firma-anlegen-dialog.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle("Firma anlegen");
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onFirmaMouseClicked(MouseEvent event) {
        studentenList.getSelectionModel().clearSelection();

        if(firmaList.getSelectionModel().getSelectedItems().isEmpty()){
            editButton.setDisable(true);
            deleteButton.setDisable(true);

        } else {
            editButton.setDisable(false);
            deleteButton.setDisable(false);

            studentenListItems = Application.studentenVerwaltung.findeFirma(firmaListItems.get(firmaList.getSelectionModel().getSelectedIndices().get(0))).getStudentenListe().stream()
                    .sorted(Comparator.comparing(Student::getNachname))
                    .map(x -> x.getNachname() + " " + x.getVorname())
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));

            studentenMatrikelnummerList = Application.studentenVerwaltung.findeFirma(firmaListItems.get(firmaList.getSelectionModel().getSelectedIndices().get(0))).getStudentenListe().stream()
                    .sorted(Comparator.comparing(Student::getNachname))
                    .map(x -> x.getMatrikelnummer())
                    .collect(Collectors.toList());

            this.studentenList.setItems(studentenListItems);
        }





        if(event.getClickCount() > 1){
            try {

                Firma firma = Application.studentenVerwaltung.findeFirma(firmaListItems.get(firmaList.getSelectionModel().getSelectedIndices().get(0)));

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de/studentenverwaltung/gui/firma-anzeigen-dialog.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                //stage.initStyle(StageStyle.TRANSPARENT);
                stage.setTitle(firma.getFirmenname());
                stage.setScene(new Scene(root1));
                stage.setResizable(false);

                FirmaAnzeigenController controller = fxmlLoader.getController();
                controller.initData(firma);

                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void onStudentenMouseClicked(MouseEvent event) {
        firmaList.getSelectionModel().clearSelection();

        editButton.setDisable(true);
        deleteButton.setDisable(true);

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

    public static void addItemToFirmaList(String firma){
        int index = Collections.binarySearch(firmaListItems, firma);
        if(index < 0) index = ~index;

        firmaListItems.add(index, firma);
    }

    public static void removeItemFromFirmaList(String firma){
        int index = firmaListItems.indexOf(firma);
        if(index >= 0){
            firmaListItems.remove(index);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        firmaListItems = Application.studentenVerwaltung.getFirmaListe().stream()
                .sorted(Comparator.comparing(Firma::getFirmenname))
                .map(x -> x.getFirmenname())
                .collect(Collectors.toCollection(FXCollections::observableArrayList));



        this.firmaList.setItems(firmaListItems);
        this.studentenList.setItems(studentenListItems);
    }
}
