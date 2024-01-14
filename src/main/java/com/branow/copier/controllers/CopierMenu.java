package com.branow.copier.controllers;

import com.branow.copier.Copier;
import com.branow.copier.controllers.elements.CopierMenuDataPanel;
import com.branow.copier.data.CopierData;
import com.branow.fxtools.Alerts;
import com.branow.fxtools.Controller;
import com.branow.outfits.catcher.Catcher;
import com.branow.outfits.catcher.Functions;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;

public class CopierMenu extends Controller {

    @FXML
    private Button bChooseOut, bChoose;
    @FXML
    private ListView<Path> lvFiles;
    @FXML
    private TextField tfOut, tfIn;


    private CopierMenuDataPanel panel;

    public void initialize() {
        catchException(() -> panel = new CopierMenuDataPanel(lvFiles, tfOut,
                tfIn, stageProperty()));
        lvFiles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void bCopyAction() {
        catchException(panel::copy);
    }

    public void tfOutAction() {
        catchException(panel::setFileOut);
    }

    public void tfOutMouseClicked(MouseEvent event) {
        if (event.getClickCount() >= 2)
            bChooseOutAction();
    }

    public void tfInAction() {
        catchException(panel::addFileIn);
    }

    public void tfInMouseClicked(MouseEvent event) {
        if (event.getClickCount() >= 2)
            bChooseAction();
    }

    public void bChooseAction() {
        catchException(panel::chooseFileIn);
    }

    public void bChooseOutAction() {
        catchException(panel::chooseFileOut);
    }

    public void miRemoveAction() {
        catchException(panel::removeFilesIn);
    }


    public static Supplier<Object> getTask(final CopierData data, final Label label, final ProgressIndicator indicator) {
        return () -> {
            double progress = 1./data.getInputs().size();
            Copier copier = new Copier(data.getOutput());
            for (Path path: data.getInputs()) {
//                label.setText(label.getText() + "\n" + path + "...");
                indicator.setProgress(indicator.getProgress() + progress);
                try {
                    copier.copy(path);
                } catch (IOException e) {
                    Alerts.warning("cannot copy folder", path.toAbsolutePath().toString()).show();
                }
            }
            return null;
        };
    }


    private static void catchException (Functions.Void func) {
        Functions.ConsumerException consumer = e -> {
            Alerts.error(e).show();
            e.printStackTrace();
        };
        Catcher.intercept(func, consumer);
    }

}
