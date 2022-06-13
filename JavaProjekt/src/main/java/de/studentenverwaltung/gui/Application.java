package de.studentenverwaltung.gui;

import de.studentenverwaltung.Kurs;
import de.studentenverwaltung.Raum;
import de.studentenverwaltung.StudentenVerwaltung;
import de.studentenverwaltung.exceptions.UserInputException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    public static StudentenVerwaltung studentenVerwaltung = new StudentenVerwaltung();
    @Override
    public void start(Stage stage) throws IOException {
        System.out.println(getClass());
        System.out.println(getClass().getResource("main-view.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
        stage.setTitle("DHBW Studentenverwaltung");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

    }
}