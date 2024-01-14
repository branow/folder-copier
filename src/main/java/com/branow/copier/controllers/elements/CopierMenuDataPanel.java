package com.branow.copier.controllers.elements;

import com.branow.copier.ResourceSearcher;
import com.branow.copier.data.CopierDataFile;
import com.branow.copier.data.FilesManager;
import com.branow.editors.edit.FileText;
import com.branow.fxtools.Stages;
import com.branow.fxtools.task.TaskStages;
import com.branow.outfits.checker.ParametersChecker;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CopierMenuDataPanel {

    private final ListView<Path> paths;
    private final TextFileField output;
    private final TextFileField input;
    private final ObjectProperty<Stage> owner;

    private CopierDataFile file;

    public CopierMenuDataPanel(ListView<Path> paths, TextField tOut, TextField tIn,
                               ObjectProperty<Stage> owner) throws IOException {
        this.paths = paths;
        this.output = new TextFileField(tOut, owner);
        this.input = new TextFileField(tIn, owner);
        this.owner = owner;
        setCopierDataFile();
    }

    public void copy() throws IOException {
        Image image =  ResourceSearcher.SEARCHER.png("copier", "img");
        Stage stage = Stages.stage("Copying", image, owner.get());
        TaskStages.setBarView(stage, new CopyingTask(file.getData()), ResourceSearcher.SEARCHER.css("style").toString());
        stage.show();
    }

    public void setFileOut() {
        Path path = validate(output.getText());
        file.setOutput(path);
    }

    public void addFileIn() {
        Path path = validate(input.getText());
        paths.getItems().add(path);
        file.addInput(path);
    }

    public void removeFilesIn() {
        List<Path> list = paths.getSelectionModel().getSelectedItems();
        file.removeInputs(list);
        paths.getItems().removeAll(list);
    }

    public void chooseFileOut() {
        output.chooseFile();
        setFileOut();
    }

    public void chooseFileIn() {
        input.chooseFile();
    }

    private void setCopierDataFile() throws IOException {
        file = new CopierDataFile(new FileText(FilesManager.DATA));
        paths.getItems().setAll(file.getInputs());
        output.textProperty().set(file.getOutput().toString());
    }

    private Path validate(String path) {
        ParametersChecker.isNullOrEmptyThrow(path);
        Path file = Path.of(path);
        if (paths.getItems().contains(file))
            throw new IllegalArgumentException("the file already was included");
        if (!Files.exists(file))
            throw new IllegalArgumentException("the file does not exist");
        return file;
    }


}
