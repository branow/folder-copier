package com.branow.copier.controllers.elements;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class TextFileField {

    private TextField text;
    private ObjectProperty<Stage> owner;

    public TextFileField(TextField text, ObjectProperty<Stage> owner) {
        this.text = text;
        this.owner = owner;
    }

    public void chooseFile() {
        File file = new DirectoryChooser().showDialog(owner.get().getOwner());
        if (file != null)
            text.setText(file.getAbsolutePath());
    }

    public String getText() {
        return text.getText();
    }

    public StringProperty textProperty() {
        return text.textProperty();
    }
}
