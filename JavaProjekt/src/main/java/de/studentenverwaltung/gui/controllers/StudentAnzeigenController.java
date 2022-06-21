package de.studentenverwaltung.gui.controllers;

import de.studentenverwaltung.Student;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.text.SimpleDateFormat;

public class StudentAnzeigenController {

    @FXML
    private Label emailLabel;

    @FXML
    private Label firmaLabel;

    @FXML
    private Label geburtstagLabel;

    @FXML
    private Label matrikelnummerLabel;

    @FXML
    private Label nachnameLabel;

    @FXML
    private Label vorkenntnisseLabel;

    @FXML
    private Label vornameLabel;

    public void initData(Student student) {
        vornameLabel.setText(student.getVorname());
        nachnameLabel.setText(student.getNachname());
        emailLabel.setText(student.getEmail());

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        geburtstagLabel.setText(format.format(student.getGeburtstag()));
        matrikelnummerLabel.setText(student.getMatrikelnummer());
        firmaLabel.setText(student.getFirma().getFirmenname());

        String vk;

        switch (student.getVorkenntnisse()) {
            case Absoluter_Anfaenger:
                vk = "Absoluter Anfänger";
                break;
            case Anfaenger:
                vk = "Anfänger";
                break;
            case Laie:
                vk = "Laie";
                break;
            case Fortgeschrittener:
                vk = "Fortgeschrittener";
                break;
            case Experte:
                vk = "Experte";
                break;
            default:
                vk = "Absoluter Anfänger";
                break;

        }
        vorkenntnisseLabel.setText(vk);
    }

}
