package de.studentenverwaltung.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ErrorMessageWindowController implements Initializable {

    public static String errorExceptionMessage = "ErrorMessage";

    @FXML
    private Button confirmButton;

    @FXML
    private Label errorMessage;

    @FXML
    void confirmInput(ActionEvent event) {
        System.out.println(confirmButton.getText());
        ((Stage) errorMessage.getScene().getWindow()).close();
    }


    public void setErrorMessage(String message) {
        System.out.println(message);
        System.out.println(confirmButton.getText());
        errorMessage.setText(message);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorMessage.setText(errorExceptionMessage);
    }
}
