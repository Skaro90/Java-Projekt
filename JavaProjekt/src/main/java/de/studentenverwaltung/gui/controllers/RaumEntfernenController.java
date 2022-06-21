package de.studentenverwaltung.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RaumEntfernenController {

    RaumeAnzeigenController raumeAnzeigenController;
    @FXML
    private Button okButton;

    @FXML
    void abortInput(ActionEvent event) {
        ((Stage) okButton.getScene().getWindow()).close();
    }

    @FXML
    void confirmDeleteRaumInput(ActionEvent event) {
        raumeAnzeigenController.raumEntfernen();
        ((Stage) okButton.getScene().getWindow()).close();
    }

    public void initData(RaumeAnzeigenController raumeAnzeigenController) {
        this.raumeAnzeigenController = raumeAnzeigenController;
    }

}
