module de.studentenverwaltung.gui {
    requires javafx.controls;
    requires javafx.fxml;


    opens de.studentenverwaltung.gui to javafx.fxml;
    opens de.studentenverwaltung.gui.controllers to javafx.fxml;
    exports de.studentenverwaltung.gui;
    exports de.studentenverwaltung.gui.controllers;
}