module com.branow.folder.copier {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.tools;
    requires java.outfits;
    requires java.file.editors;

    opens com.branow.copier to javafx.fxml;
    exports com.branow.copier;
    exports com.branow.copier.data;
    opens com.branow.copier.data to javafx.fxml;
    exports com.branow.copier.controllers;
    opens com.branow.copier.controllers to javafx.fxml;
    exports com.branow.copier.controllers.elements;
    opens com.branow.copier.controllers.elements to javafx.fxml;
}