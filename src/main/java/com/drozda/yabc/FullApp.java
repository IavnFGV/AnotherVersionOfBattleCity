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
    // public Stage stage;

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


//        AppModel.setMainStage(primaryStage);


//        primaryStage.setTitle("Hello JavaFX and Maven");
//        primaryStage.setScene(scene);
//        primaryStage.sizeToScene();
//        primaryStage.show();

    }

    @Override
    public void stop() {
        AppModel.stop();
    }
}
