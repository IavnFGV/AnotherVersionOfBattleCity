package com.drozda.yabc;

import com.drozda.appflow.AppModel;
import com.drozda.appflow.StateChangeListener;
import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 19.09.2015.
 */
public class FullApp extends Application {

    private static final Logger log = LoggerFactory.getLogger(FullApp.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        AppModel.stateProperty().addListener(new StateChangeListener());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AppModel.setMainStage(primaryStage);
        AppModel.startGame();
    }

    @Override
    public void stop() {
        AppModel.stop();
    }
}
