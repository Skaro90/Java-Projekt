package de.studentenverwaltung.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class FirmaEntfernenController {

    FirmenAnzeigenController firmenAnzeigenController;
    @FXML
    private Button okButton;

    @FXML
    void abortInput(ActionEvent event) {
        ((Stage)okButton.getScene().getWindow()).close();
    }

    @FXML
    void confirmDeleteFirmaInput(ActionEvent event) {
        firmenAnzeigenController.firmaEntfernen();
        ((Stage)okButton.getScene().getWindow()).close();
    }

    public void initData(FirmenAnzeigenController firmenAnzeigenController){
        this.firmenAnzeigenController = firmenAnzeigenController;
    }

}
