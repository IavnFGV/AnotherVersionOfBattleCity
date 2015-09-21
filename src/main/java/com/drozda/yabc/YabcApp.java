package com.drozda.yabc;

import com.drozda.battlecity.appflow.StateChangeListener;
import com.drozda.battlecity.appflow.YabcAppModel;
import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 19.09.2015.
 */
public class YabcApp extends Application {

    private static final Logger log = LoggerFactory.getLogger(YabcApp.class);
    // public Stage stage;


    @Override
    public void start(Stage primaryStage) throws Exception {


        YabcAppModel.setMainStage(primaryStage);
        YabcAppModel.startGame();
//        String fxmlFile = "/com/drozda/fx/fxml/BaseApp.fxml";
//        log.debug("Loading FXML for main view from: {}", fxmlFile);
//        FXMLLoader loader = new FXMLLoader();
//        Parent rootNode = loader.load(getClass().getResourceAsStream(fxmlFile));
//
//        log.debug("Showing JFX scene");
//        Scene scene = new Scene(rootNode);
//        // scene.getStylesheets().add("/styles/styles.css");
//
//        FXMLLoader loader1 = new FXMLLoader();
//        Parent rootNode1 = loader1.load(getClass().getResourceAsStream("/com/drozda/fx/fxml/MainMenu.fxml"));

//        Scene scene1 = new Scene(rootNode);

//        ((BaseApp)(loader.getController())).setSubScene(rootNode1);
//        ((BaseApp)(loader.getController())).getBorderPane().setCenter(rootNode1);


//        YabcAppModel.setMainStage(primaryStage);


//        primaryStage.setTitle("Hello JavaFX and Maven");
//        primaryStage.setScene(scene);
//        primaryStage.sizeToScene();
//        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        YabcAppModel.stateProperty().addListener(new StateChangeListener());
    }

    @Override
    public void stop() {
        YabcAppModel.stop();
    }
}
