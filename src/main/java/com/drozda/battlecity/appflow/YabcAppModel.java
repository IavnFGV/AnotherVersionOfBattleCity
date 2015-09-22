package com.drozda.battlecity.appflow;


import com.drozda.YabcLocalization;
import com.drozda.battlecity.model.YabcUser;
import com.drozda.fx.controller.BaseApp;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static java.util.Arrays.asList;

/**
 * Created by GFH on 18.09.2015.
 */
public class YabcAppModel {
    private static final Logger log = LoggerFactory.getLogger(YabcAppModel.class);
    public static Map<YabcContextTypes, Object> appContext = new EnumMap(YabcContextTypes.class);
    //  eSingleThreadScheduledExecutor()nw;
    private static String KEY_UNKNOWN_IS_NORMAL = "UNKNOWN_IS_NORMAL";
    private static ScheduledExecutorService service = Executors.newScheduledThreadPool(5);
    private static Stage mainStage;

    private static StringProperty mainStageTitle;

    private static StringProperty messageString = new SimpleStringProperty();
    private static BaseApp baseApp;

    private static String getDefaultString() {
        return format(YabcLocalization.getString("statusbar.helloLabel"),
                YabcAppModel.getCurrentUser().getLogin(), YabcAppModel.getCurrentUser().getTeam());
    }

    private static CustomFeatures customFeatures = new DefaultFeatures();

    public static String getMessageString() {
        return messageString.get();
    }

    private static ObjectProperty<YabcUser> currentUser = new SimpleObjectProperty(customFeatures.getLastUser());
    private static ObjectProperty<YabcState> state = new SimpleObjectProperty();

    public static void setMessageString(String messageString) {
        YabcAppModel.messageString.set(messageString);
        if (!getDefaultString().equals(getMessageString())) {
            service.schedule(() -> Platform.runLater(() -> YabcAppModel.setDefaultMessage()), 2, TimeUnit
                    .SECONDS);
        }
    }

    private static List<YabcState> allowedToChangeUserStates = asList(YabcState.Designer, YabcState.HallOfFame,
            YabcState.LevelPicker, YabcState.MainMenu, YabcState.Settings);

    static {
        appContext.put(YabcContextTypes.ADDITIONAL, new HashMap<String, Object>());
    }

    public static StringProperty messageStringProperty() {
        return messageString;
    }

    public static BaseApp getBaseApp() {
        return baseApp;
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    private static void setDefaultMessage() {
        YabcAppModel.messageString.set(getDefaultString());
    }

    public static void setMainStage(Stage mainStage) {
        YabcAppModel.mainStage = mainStage;
        mainStageTitle = mainStage.titleProperty();
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
        baseApp = loader.getController();
        setState(YabcState.MainMenu);

        mainStage.setTitle("Hello JavaFX and Maven");
        mainStage.setScene(scene);
        mainStage.sizeToScene();
        setDefaultMessage();
        mainStage.show();

    }

    public static ObjectProperty<YabcUser> currentUserProperty() {
        return currentUser;
    }

    public static YabcState getState() {
        return state.get();
    }

    public static void setState(YabcState newState) {
        if (state.get() == null) {
            state.set(newState);
        } else {
            YabcState yabcState = state.get().tryTransitionIgnoreWrong(newState);
            state.set(yabcState);
        }
    }

    public static YabcUser getCurrentUser() {
        return currentUser.get();
    }

    public static ObjectProperty<YabcState> stateProperty() {
        return state;
    }

    public static void startBattle() {
        setState(YabcState.Battle);
    }

    public static void setCurrentUser(YabcUser aCurrentUser) {
        currentUser.set(aCurrentUser);
    }

    public static boolean isUnknownNormal() {//if UNKNOWN and it is not normal
        return (boolean) getAdditionalSettings().getOrDefault(KEY_UNKNOWN_IS_NORMAL, false);
    }

    private static Map<String, Object> getAdditionalSettings() {
        return (Map<String, Object>) (appContext.get(YabcContextTypes.ADDITIONAL));
    }

    public static void stop() {
        service.shutdown();
    }

    public static void initFrame(YabcState aState) {

    }

    public static void changeUser() {
        log.info("try to change user");
    }

    public static Boolean changeUserPredicate(YabcState state) {
        return allowedToChangeUserStates.contains(state);
    }

    public static void clearUser() { // if we play under one login and then want to play as UNKNOWN -  we have to
        // handle  results correct
        getAdditionalSettings().put(KEY_UNKNOWN_IS_NORMAL, true);
    }

    public enum YabcContextTypes {
        APPFLOW,
        STATESTACK,
        ADDITIONAL

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


}
