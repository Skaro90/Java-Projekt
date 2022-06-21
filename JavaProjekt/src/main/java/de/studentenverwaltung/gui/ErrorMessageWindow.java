package de.studentenverwaltung.gui;

import de.studentenverwaltung.gui.controllers.ErrorMessageWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ErrorMessageWindow implements ErrorCallback {
    @Override
    public void showErrorMessage(String errorMessage) {
        try {
            ErrorMessageWindowController.errorExceptionMessage = errorMessage;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de/studentenverwaltung/gui/error-message-window.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.getIcons().add(new Image(getClass().getResource("DHBW-logo.png").toString()));
            stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle("Error");
            stage.setScene(new Scene(root1));


            stage.show();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
