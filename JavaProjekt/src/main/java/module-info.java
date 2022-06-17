module de.studentenverwaltung.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens de.studentenverwaltung.gui.controllers to javafx.fxml;
    exports de.studentenverwaltung.gui;
    exports de.studentenverwaltung.gui.controllers;
}