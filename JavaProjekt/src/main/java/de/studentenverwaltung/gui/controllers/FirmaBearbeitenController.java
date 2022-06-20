package de.studentenverwaltung.gui.controllers;

import de.studentenverwaltung.Firma;
import de.studentenverwaltung.exceptions.UserInputException;
import de.studentenverwaltung.gui.Application;
import de.studentenverwaltung.gui.ErrorMessageWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

public class FirmaBearbeitenController {

    private String oldName;
    @FXML
    private TextField betreuerEmailTextField;

    @FXML
    private DatePicker betreuerGeburtstagField;

    @FXML
    private TextField betreuerNachnameTextField;

    @FXML
    private TextField betreuerTelefonnummerTextField;

    @FXML
    private TextField betreuerVornameTextField;

    @FXML
    private Button confirmFirmaButton;

    @FXML
    private TextField firmaHausnummerTextField;

    @FXML
    private TextField firmaNameTextField;

    @FXML
    private TextField firmaPlzTextField;

    @FXML
    private TextField firmaStadtTextField;

    @FXML
    private TextField firmaStrasseTextField;

    @FXML
    void abortInput(ActionEvent event) {
        ((Stage)firmaNameTextField.getScene().getWindow()).close();
    }

    @FXML
    void confirmFirmaBearbeitenInput(ActionEvent event) throws UserInputException {
        try {
            if(firmaNameTextField.getText().isEmpty() || firmaStrasseTextField.getText().isEmpty() || firmaHausnummerTextField.getText().isEmpty() || firmaPlzTextField.getText().isEmpty() || firmaStadtTextField.getText().isEmpty() || betreuerVornameTextField.getText().isEmpty() || betreuerNachnameTextField.getText().isEmpty() || betreuerEmailTextField.getText().isEmpty() || betreuerTelefonnummerTextField.getText().isEmpty()){
                ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
                throw new UserInputException("Bitte Überprüfen Sie Ihre Eingaben.", errorMessageWindow);
            }
            Application.studentenVerwaltung.updateFirma(Application.studentenVerwaltung.findeFirma(oldName), firmaNameTextField.getText(), firmaStrasseTextField.getText(), firmaHausnummerTextField.getText(), firmaPlzTextField.getText(), firmaStadtTextField.getText(),
                    betreuerNachnameTextField.getText(), betreuerVornameTextField.getText(), betreuerEmailTextField.getText(), Date.from(Instant.from(betreuerGeburtstagField.getValue().atStartOfDay(ZoneId.systemDefault()))), betreuerTelefonnummerTextField.getText());
            FirmenAnzeigenController.addItemToFirmaList(firmaNameTextField.getText());
            FirmenAnzeigenController.removeItemFromFirmaList(oldName);

            ((Stage)firmaNameTextField.getScene().getWindow()).close();
        } catch(NullPointerException e){
            ErrorMessageWindow errorMessageWindow = new ErrorMessageWindow();
            throw new UserInputException("Bitte Überprüfen Sie das Geburtsdatum des Betreuers.", errorMessageWindow);
        }
    }

    public void initData(Firma firma){
        firmaNameTextField.setText(firma.getFirmenname());
        firmaStrasseTextField.setText(firma.getStrasse());
        firmaHausnummerTextField.setText(firma.getHausnummer());
        firmaPlzTextField.setText(firma.getPostleitzahl());
        firmaStadtTextField.setText(firma.getStadt());

        betreuerVornameTextField.setText(firma.getBetreuer().getVorname());
        betreuerNachnameTextField.setText(firma.getBetreuer().getNachname());
        betreuerEmailTextField.setText(firma.getBetreuer().getEmail());
        betreuerTelefonnummerTextField.setText(firma.getBetreuer().getTelefonnummer());
        betreuerGeburtstagField.setValue(firma.getBetreuer().getGeburtstag().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        oldName = firma.getFirmenname();

    }

}
