package com.drozda.appflow;


import com.drozda.YabcLocalization;
import com.drozda.fx.controller.BaseApp;
import com.drozda.model.AppUser;
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
public class AppModel {
    private static final Logger log = LoggerFactory.getLogger(AppModel.class);
    public static Map<YabcContextTypes, Object> appContext = new EnumMap(YabcContextTypes.class);
    //  eSingleThreadScheduledExecutor()nw;
    private static String KEY_UNKNOWN_IS_NORMAL = "UNKNOWN_IS_NORMAL";
    private static ScheduledExecutorService service = Executors.newScheduledThreadPool(5);
    private static Stage mainStage;

    private static StringProperty mainStageTitle;

    private static StringProperty messageString = new SimpleStringProperty();
    private static BaseApp baseApp;
    private static CustomFeatures customFeatures = new DefaultFeatures();    private static String getDefaultString() {
        return format(YabcLocalization.getString("statusbar.helloLabel"),
                AppModel.getCurrentUser().getLogin(), AppModel.getCurrentUser().getTeam());
    }
    private static ObjectProperty<AppUser> currentUser = new SimpleObjectProperty(customFeatures.getLastUser());
    private static ObjectProperty<AppState> state = new SimpleObjectProperty();    public static String getMessageString() {
        return messageString.get();
    }
    private static List<AppState> allowedToChangeUserStates = asList(AppState.Designer, AppState.HallOfFame,
            AppState.LevelPicker, AppState.MainMenu, AppState.Settings);

    static {
        appContext.put(YabcContextTypes.ADDITIONAL, new HashMap<String, Object>());
    }

    public static StringProperty messageStringProperty() {
        return messageString;
    }    public static void setMessageString(String messageString) {
        AppModel.messageString.set(messageString);
        if (!getDefaultString().equals(getMessageString())) {
            service.schedule(() -> Platform.runLater(() -> AppModel.setDefaultMessage()), 2, TimeUnit
                    .SECONDS);
        }
    }

    public static BaseApp getBaseApp() {
        return baseApp;
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void setMainStage(Stage mainStage) {
        AppModel.mainStage = mainStage;
        mainStageTitle = mainStage.titleProperty();
    }

    public static void startGame() throws Exception {

        //     setState();

        // initializing baseview

        String fxmlFile = "/com/drozda/fx/fxml/BaseApp.fxml";
        log.debug("Loading FXML for main view from: {}", fxmlFile);
        FXMLLoader loader = new FXMLLoader();
        Parent rootNode = loader.load(AppModel.class.getResourceAsStream(fxmlFile));
        log.debug("Showing JFX scene");
        Scene scene = new Scene(rootNode);
        baseApp = loader.getController();
        setState(AppState.MainMenu);

        mainStage.setTitle("Hello JavaFX and Maven");
        mainStage.setScene(scene);
        mainStage.sizeToScene();
        setDefaultMessage();
        mainStage.show();

    }

    public static ObjectProperty<AppUser> currentUserProperty() {
        return currentUser;
    }

    public static AppState getState() {
        return state.get();
    }    private static void setDefaultMessage() {
        AppModel.messageString.set(getDefaultString());
    }

    public static void setState(AppState newState) {
        if (state.get() == null) {
            state.set(newState);
        } else {
            AppState appState = state.get().tryTransitionIgnoreWrong(newState);
            state.set(appState);
        }
    }

    public static ObjectProperty<AppState> stateProperty() {
        return state;
    }

    public static void startBattle() {
        setState(AppState.Battle);
    }

    public static boolean isUnknownNormal() {//if UNKNOWN and it is not normal
        return (boolean) getAdditionalSettings().getOrDefault(KEY_UNKNOWN_IS_NORMAL, false);
    }

    private static Map<String, Object> getAdditionalSettings() {
        return (Map<String, Object>) (appContext.get(YabcContextTypes.ADDITIONAL));
    }

    public static void stop() {
        service.shutdown();
    }    public static AppUser getCurrentUser() {
        return currentUser.get();
    }

    public static void initFrame(AppState aState) {

    }

    public static void changeUser() {
        log.info("try to change user");
    }

    public static Boolean changeUserPredicate(AppState state) {
        return allowedToChangeUserStates.contains(state);
    }    public static void setCurrentUser(AppUser aCurrentUser) {
        currentUser.set(aCurrentUser);
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