package de.studentenverwaltung.gui;

import de.studentenverwaltung.Datenbank;
import de.studentenverwaltung.Kurs;
import de.studentenverwaltung.Raum;
import de.studentenverwaltung.StudentenVerwaltung;
import de.studentenverwaltung.exceptions.UserInputException;
import de.studentenverwaltung.gui.controllers.MainViewController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.Statement;
import java.util.Optional;

public class Application extends javafx.application.Application {

    public static StudentenVerwaltung studentenVerwaltung = new StudentenVerwaltung();
    public static Stage mainStage;
    @Override
    public void start(Stage stage) throws IOException {
        if(Datenbank.error == true){


                    Alert alert = new Alert(Alert.AlertType.ERROR);

                    ((Stage)alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);

                    alert.setTitle("Datenbank Fehler");
                    alert.setHeaderText(null);
                    alert.setContentText("Fehler bei der Verbindung zur Datenbank! Bitte überprüfen Sie die config.properties Datei.");

                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.get() == ButtonType.OK){
                        Platform.exit();
                    }


        } else {
            System.out.println(getClass());
            System.out.println(getClass().getResource("main-view.fxml"));
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
            stage.setTitle("DHBW Studentenverwaltung");
            stage.setScene(scene);
            mainStage = stage;
            stage.show();
        }
    }

    public static void main(String[] args) {
        launch();

    }
}