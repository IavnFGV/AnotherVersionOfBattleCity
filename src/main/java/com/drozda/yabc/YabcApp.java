package com.drozda.yabc;

import com.drozda.battlecity.appflow.StateChangeListener;
import com.drozda.battlecity.appflow.YabcAppModel;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 19.09.2015.
 */
public class YabcApp extends Application {

    private static final Logger log = LoggerFactory.getLogger(YabcApp.class);


    @Override
    public void start(Stage primaryStage) throws Exception {

        String fxmlFile = "/com/drozda/fx/fxml/MainMenu.fxml";
        log.debug("Loading FXML for main view from: {}", fxmlFile);
        FXMLLoader loader = new FXMLLoader();
        Parent rootNode = loader.load(getClass().getResourceAsStream(fxmlFile));

        log.debug("Showing JFX scene");
        Scene scene = new Scene(rootNode);
        // scene.getStylesheets().add("/styles/styles.css");

        primaryStage.setTitle("Hello JavaFX and Maven");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void init() {
        YabcAppModel.stateProperty().addListener(new StateChangeListener());
    }
}
