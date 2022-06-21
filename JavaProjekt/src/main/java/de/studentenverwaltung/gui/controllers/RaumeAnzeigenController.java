package de.studentenverwaltung.gui.controllers;

import de.studentenverwaltung.Raum;
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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RaumeAnzeigenController implements Initializable {

    @FXML
    private ListView<Integer> raumKapazitaetList;
    private static ObservableList<Integer> raumKapazitaetItems = FXCollections.observableArrayList();

    @FXML
    private ListView<String> raumNameList;
    private static ObservableList<String> raumNameItems = FXCollections.observableArrayList();

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    public static void addItemToList(String raumName, int raumKapazitaet) {
        int index = Collections.binarySearch(raumNameItems, raumName);
        if (index < 0) index = ~index;

        raumNameItems.add(index, raumName);
        raumKapazitaetItems.add(index, raumKapazitaet);

    }

    public static void removeItemFromList(String raumName) {

        int index = raumNameItems.indexOf(raumName);
        if (index >= 0) {

            raumNameItems.remove(index);
            raumKapazitaetItems.remove(index);
        }
    }

    @FXML
    void onKapazitaetMouseClicked(MouseEvent event) {
        if (raumKapazitaetList.getSelectionModel().getSelectedItems().isEmpty() && raumNameList.getSelectionModel().getSelectedItems().isEmpty()) {
            editButton.setDisable(true);
            deleteButton.setDisable(true);
        } else {
            editButton.setDisable(false);
            deleteButton.setDisable(false);
        }

        raumNameList.getSelectionModel().clearSelection();
    }

    @FXML
    void onRaumnameMouseClicked(MouseEvent event) {
        if (raumKapazitaetList.getSelectionModel().getSelectedItems().isEmpty() && raumNameList.getSelectionModel().getSelectedItems().isEmpty()) {
            editButton.setDisable(true);
            deleteButton.setDisable(true);
        } else {
            editButton.setDisable(false);
            deleteButton.setDisable(false);
        }

        raumKapazitaetList.getSelectionModel().clearSelection();
    }

    @FXML
    void onRaumAnlegenButton(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de/studentenverwaltung/gui/raum-anlegen-dialog.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.getIcons().add(new Image(getClass().getResource("/de/studentenverwaltung/gui/DHBW-logo.png").toString()));
            stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle("Raum anlegen");
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onEditRaumButton(ActionEvent event) {
        try {
            if (!raumNameList.getSelectionModel().getSelectedItems().isEmpty()) {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de/studentenverwaltung/gui/raum-bearbeiten-dialog.fxml"));
                Parent root1 = fxmlLoader.load();
                Stage stage = new Stage();
                stage.getIcons().add(new Image(getClass().getResource("/de/studentenverwaltung/gui/DHBW-logo.png").toString()));
                stage.initModality(Modality.APPLICATION_MODAL);
                //stage.initStyle(StageStyle.TRANSPARENT);
                stage.setTitle("Raum bearbeiten");
                stage.setScene(new Scene(root1));
                stage.setResizable(false);

                RaumBearbeitenController controller = fxmlLoader.getController();

                int selectedIndex = raumNameList.getSelectionModel().getSelectedIndices().get(0);
                controller.initData(raumNameItems.get(selectedIndex), raumKapazitaetItems.get(selectedIndex));

                stage.show();
            }

            if (!raumKapazitaetList.getSelectionModel().getSelectedItems().isEmpty()) {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de/studentenverwaltung/gui/raum-bearbeiten-dialog.fxml"));
                Parent root1 = fxmlLoader.load();
                Stage stage = new Stage();
                stage.getIcons().add(new Image(getClass().getResource("/de/studentenverwaltung/gui/DHBW-logo.png").toString()));
                stage.initModality(Modality.APPLICATION_MODAL);
                //stage.initStyle(StageStyle.TRANSPARENT);
                stage.setTitle("Raum bearbeiten");
                stage.setScene(new Scene(root1));
                stage.setResizable(false);

                RaumBearbeitenController controller = fxmlLoader.getController();

                int selectedIndex = raumKapazitaetList.getSelectionModel().getSelectedIndices().get(0);
                controller.initData(raumNameItems.get(selectedIndex), raumKapazitaetItems.get(selectedIndex));

                stage.show();
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onDeleteRaumButton(ActionEvent event) {

        if (!raumNameList.getSelectionModel().getSelectedItems().isEmpty()) {

            if (raumNameList.getSelectionModel().getSelectedIndices().size() > 0) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de/studentenverwaltung/gui/raum-entfernen-dialog.fxml"));
                    Parent root1 = fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.getIcons().add(new Image(getClass().getResource("/de/studentenverwaltung/gui/DHBW-logo.png").toString()));
                    stage.initModality(Modality.APPLICATION_MODAL);
                    //stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setTitle("Raum entfernen");
                    stage.setScene(new Scene(root1));
                    stage.setResizable(false);

                    RaumEntfernenController controller = fxmlLoader.getController();

                    controller.initData(this);

                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }

        if (!raumKapazitaetList.getSelectionModel().getSelectedItems().isEmpty()) {

            if (raumKapazitaetList.getSelectionModel().getSelectedIndices().size() > 0) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de/studentenverwaltung/gui/raum-entfernen-dialog.fxml"));
                    Parent root1 = fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.getIcons().add(new Image(getClass().getResource("/de/studentenverwaltung/gui/DHBW-logo.png").toString()));
                    stage.initModality(Modality.APPLICATION_MODAL);
                    //stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setTitle("Raum entfernen");
                    stage.setScene(new Scene(root1));
                    stage.setResizable(false);

                    RaumEntfernenController controller = fxmlLoader.getController();

                    controller.initData(this);

                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    public void raumEntfernen() {
        if (!raumNameList.getSelectionModel().getSelectedItems().isEmpty()) {

            try {
                if (raumNameList.getSelectionModel().getSelectedIndices().size() > 0) {
                    int selectedIndex = raumNameList.getSelectionModel().getSelectedIndices().get(0);

                    Application.studentenVerwaltung.raumLöschen(Application.studentenVerwaltung.findeRaum(raumNameItems.get(selectedIndex)));
                    removeItemFromList(raumNameItems.get(selectedIndex));
                }
            } catch (UserInputException e) {
                throw new RuntimeException(e);
            }

        }

        if (!raumKapazitaetList.getSelectionModel().getSelectedItems().isEmpty()) {

            try {
                if (raumKapazitaetList.getSelectionModel().getSelectedIndices().size() > 0) {
                    int selectedIndex = raumKapazitaetList.getSelectionModel().getSelectedIndices().get(0);

                    Application.studentenVerwaltung.raumLöschen(Application.studentenVerwaltung.findeRaum(raumNameItems.get(selectedIndex)));
                    removeItemFromList(raumNameItems.get(selectedIndex));
                }
            } catch (UserInputException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        raumNameItems = Application.studentenVerwaltung.getRaumListe().stream()
                .sorted(Comparator.comparing(Raum::getRaumNummer))
                .map(x -> x.getRaumNummer())
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        raumKapazitaetItems = Application.studentenVerwaltung.getRaumListe().stream()
                .sorted(Comparator.comparing(Raum::getRaumNummer))
                .map(x -> x.getKapazitaet())
                .collect(Collectors.toCollection(FXCollections::observableArrayList));


        this.raumNameList.setItems(raumNameItems);
        this.raumKapazitaetList.setItems(raumKapazitaetItems);
    }
}
