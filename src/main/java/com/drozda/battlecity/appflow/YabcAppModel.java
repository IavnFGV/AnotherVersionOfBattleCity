package com.drozda.battlecity.appflow;


import com.drozda.fx.controller.BaseApp;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GFH on 18.09.2015.
 */
public class YabcAppModel {

    public static Map<YabcContextTypes, Object> appContext = new EnumMap(YabcContextTypes.class);
    private static final Logger log = LoggerFactory.getLogger(YabcAppModel.class);
    private static Stage mainStage;

    private static StringProperty mainStageTitle;

    private static BaseApp baseApp;

    public static BaseApp getBaseApp() {
        return baseApp;
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void startGame() throws Exception {

        //     setState();

        // initializing baseview

        String fxmlFile = "/com/drozda/fx/fxml/BaseApp.fxml";
        log.debug("Loading FXML for main view from: {}", fxmlFile);
        FXMLLoader loader = new FXMLLoader();
        Parent rootNode = loader.load(YabcAppModel.class.getResourceAsStream(fxmlFile));
        log.debug("Showing JFX scene");
        Scene scene = new Scene(rootNode);
        baseApp=loader.getController();
        setState(YabcState.MainMenu);

        mainStage.setTitle("Hello JavaFX and Maven");
        mainStage.setScene(scene);
        mainStage.sizeToScene();
        mainStage.show();
    }

    public static void setMainStage(Stage mainStage) {
        YabcAppModel.mainStage = mainStage;
        mainStageTitle = mainStage.titleProperty();
    }

    static {
        appContext.put(YabcContextTypes.ADDITIONAL, new HashMap<String, Object>());
    }

    private static ObjectProperty<YabcUser> currentUser = new SimpleObjectProperty(new YabcUser());

    public static YabcUser getCurrentUser() {
        return currentUser.get();
    }

    public static ObjectProperty<YabcUser> currentUserProperty() {
        return currentUser;
    }

    public static void setCurrentUser(YabcUser aCurrentUser) {
        currentUser.set(aCurrentUser);
    }

    private static ObjectProperty<YabcState> state = new SimpleObjectProperty();

    public static YabcState getState() {
        return state.get();
    }

    public static ObjectProperty<YabcState> stateProperty() {
        return state;
    }

    public static void setState(YabcState newState) {
        if (state.get() == null) {
            state.set(newState);
        } else {
            YabcState yabcState = state.get().tryTransitionIgnoreWrong(newState);
            state.set(yabcState);
        }
    }

    public static void startBattle() {
        setState(YabcState.Battle);
    }

    private static Map<String, Object> getAdditionalSettings() {
        return (Map<String, Object>) (appContext.get(YabcContextTypes.ADDITIONAL));
    }

    public static boolean needLogin() {
        if (getCurrentUser().name.equals("UNKNOWN") && (getAdditionalSettings().getOrDefault("UNKNOWN_IS_NORMAL", false)
                .equals(false))) { //if UNKNOWN and it is not normal
            return true;
        }
        return false;
    }

    public enum YabcContextTypes {
        APPFLOW,
        STATESTACK,
        ADDITIONAL

    }

    public static class YabcUser {
        private String name = "UNKNOWN";
        private String team = "UNKNOWN";

        public YabcUser(String name, String team) {
            this.name = name;
            this.team = team;
        }

        public YabcUser() {
        }
    }

    public static class YabcFrame<T> {
        private Parent root;

        private T controller;

        public YabcFrame(Parent root, T controller) {
            this.root = root;
            this.controller = controller;
        }

        public Parent getRoot() {
            return root;
        }

        public T getController() {
            return controller;
        }

    }

    public static void initFrame(YabcState aState) {

    }

    public static void changeUser() {
        log.info("try to change user");
    }

}
