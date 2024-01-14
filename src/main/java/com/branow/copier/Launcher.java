package com.branow.copier;

import com.branow.fxtools.Controller;
import com.branow.fxtools.Frame;
import com.branow.fxtools.Stages;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {

    public static final ResourceSearcher searcher = new ResourceSearcher(Launcher.class);

    @Override
    public void start(Stage stage) throws IOException {
        Stages.stage(stage, "Copier", searcher.png("copier", "img"));
        Frame<Controller> frame = Stages.loadIntoFrameOn(stage, searcher.fxml("menu-view"));
        frame.getStage().show();
    }

    public static void main(String[] args) {
        launch();
    }


}