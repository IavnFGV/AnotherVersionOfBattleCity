package com.drozda.appflow;


import com.drozda.YabcLocalization;
import com.drozda.appflow.config.AppData;
import com.drozda.fx.controller.BaseApp;
import com.drozda.fx.dialog.Dialog;
import com.drozda.model.*;
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

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Arrays.asList;

/**
 * Created by GFH on 18.09.2015.
 */
public class AppModel {
    private static final Logger log = LoggerFactory.getLogger(AppModel.class);
    public static Map<YabcContextTypes, Object> appContext = new EnumMap(YabcContextTypes.class);
    public static AppData appData = AppData.loadConfig(null);//TODO Maybe set from args
    //  eSingleThreadScheduledExecutor()nw;
    private static String KEY_UNKNOWN_IS_NORMAL = "UNKNOWN_IS_NORMAL";
    private static int BITSET_SIZE_FOR_USERCHECK = 3;
    private static ScheduledExecutorService service = Executors.newScheduledThreadPool(5);
    private static Stage mainStage;
    private static StringProperty mainStageTitle;
    private static StringProperty messageString = new SimpleStringProperty();
    private static BaseApp baseApp;
    private static ObjectProperty<AppUser> currentUser = new SimpleObjectProperty(appData.getLastUser());
    private static ObjectProperty<AppState> state = new SimpleObjectProperty();
    private static List<AppState> allowedToChangeUserStates = asList(AppState.Designer, AppState.HallOfFame,
            AppState.LevelPicker, AppState.MainMenu, AppState.Settings);

    static {
        appContext.put(YabcContextTypes.ADDITIONAL, new HashMap<String, Object>());
        currentUser.addListener((observable, oldValue, newValue) -> {
            setDefaultMessage();
            saveLastUser(newValue);
        });
    }

    public static void saveLastUser(AppUser appUser) {
        log.debug("AppModel.saveLastUser with parameters " + "appUser = [" + appUser + "]");
        if (appUser == null) return;
        appData.setLastUser(appUser);
        if (!appData.getAppUsers().stream().//NOT contains new login
                map(appUser1 -> appUser1.getLogin()).
                collect(Collectors.toSet()).contains(appUser)) {
            appData.getAppUsers().add(appUser);
        }
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

    public static void setMainStage(Stage mainStage) {
        AppModel.mainStage = mainStage;
        mainStageTitle = mainStage.titleProperty();
    }

    public static void startGame() throws Exception {
        String fxmlFile = "/com/drozda/fx/fxml/BaseApp.fxml";
        log.debug("Loading FXML for main view from: {}", fxmlFile);
        FXMLLoader loader = new FXMLLoader();
        Parent rootNode = loader.load(AppModel.class.getResourceAsStream(fxmlFile));
        log.debug("Showing JFX scene");
        Scene scene = new Scene(rootNode);
        baseApp = loader.getController();
        setState(AppState.MainMenu);

        mainStage.setTitle("Yet Another Battlecity clone on JavaFX and Maven");
        mainStage.setScene(scene);
        mainStage.sizeToScene();
        setDefaultMessage();
        mainStage.setResizable(false);
        mainStage.show();

    }

    private static void setDefaultMessage() {
        AppModel.messageString.set(getDefaultString());
    }

    private static String getDefaultString() {
        if (getCurrentUser() == null) {
            return YabcLocalization.getString("statusbar.helloLabel.defaultuser");
        }
        return format(YabcLocalization.getString("statusbar.helloLabel"),
                AppModel.getCurrentUser().getLogin(), AppModel.getCurrentUser().getTeam());
    }

    public static AppUser getCurrentUser() {
        return currentUser.get();
    }

    public static void setCurrentUser(AppUser aCurrentUser) {
        currentUser.set(aCurrentUser);
    }

    public static ObjectProperty<AppUser> currentUserProperty() {
        return currentUser;
    }

    public static AppState getState() {
        return state.get();
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

    public static boolean isLoginRequired() {
        return !isLoggedIn() && !isUnknownNormal();
    }

    public static boolean isLoggedIn() {
        return getCurrentUser() != null;
    }

    public static boolean isUnknownNormal() {//if UNKNOWN and it is not normal
        return (boolean) getAdditionalSettings().getOrDefault(KEY_UNKNOWN_IS_NORMAL, false);
    }

    private static Map<String, Object> getAdditionalSettings() {
        return (Map<String, Object>) (appContext.get(YabcContextTypes.ADDITIONAL));
    }

    public static void stop() {
        AppData.saveConfig(appData);
        service.shutdown();
    }

    public static void initFrame(AppState aState) {

    }

    public static void processLoginDialogResponse(LoginDialogResponse dialogResponse) {
        log.debug("AppModel.processLoginDialogResponse with parameters " + "dialogResponse = [" + dialogResponse + "]");
        if (dialogResponse == null) {
            return;
        }

        if (dialogResponse.isUnknownNormal()) {
            log.debug("try to set to default");
            setUnknownIsNormal(true);
            AppModel.setCurrentUser(null);
            AppModel.setMessageString(YabcLocalization.getString("statusbar.change.user.to.null"));
            return;
        }
        if (
                (!dialogResponse.getUserInfo().getLogin().isEmpty()) &&
                        (!dialogResponse.getUserInfo().getTeam().isEmpty()) &&
                        (dialogResponse.getUserInfo().getPasswordHash() != 0)) {
            log.debug("try to set another user");
            setUnknownIsNormal(false);//TODO REPLACE

            CheckUsernameAndPasswordResult checkUsernameAndPasswordResult = checkUsernameAndPassword(dialogResponse
                    .getUserInfo());

            LoginDialogResponseProcessor loginDialogResponseProcessor = LoginDialogResponseProcessor.
                    newLoginDialogResponseProcessor(dialogResponse, checkUsernameAndPasswordResult);
            loginDialogResponseProcessor.execute();
        }
    }

    public static void setUnknownIsNormal(boolean isNormal) {
        getAdditionalSettings().put(KEY_UNKNOWN_IS_NORMAL, isNormal);
    }

    private static CheckUsernameAndPasswordResult checkUsernameAndPassword(AppUser appUser) {
        if (appUser == null) {
            throw new NullPointerException("appUser is null");
        }
        if (appUser.getTeam() == null) {
            throw new NullPointerException("appUser.team is null");
        }

        int appUserIndex = appData.getAppUsers().indexOf(appUser);
        boolean isLoginExists = appUserIndex >= 0;
        boolean isTeamExists = appData.getAppTeams()
                .stream().map(AppTeam::getName).collect(Collectors.toSet())
                .contains(appUser.getTeam());

//        boolean isPasswordCorrect = isLoginExists ? (appData.getAppUsers().get(appUserIndex).getPasswordHash()
//                .equals(appUser.getPasswordHash())):
//                false;
        boolean isPasswordCorrect = isLoginExists && (appData.getAppUsers().get(appUserIndex).getPasswordHash()
                .equals(appUser.getPasswordHash()));
        return new CheckUsernameAndPasswordResult(isLoginExists, isTeamExists, isPasswordCorrect);
    }

    public static String getMessageString() {
        return messageString.get();
    }

    public static void setMessageString(String messageString) {
        AppModel.messageString.set(messageString);
        if (!getDefaultString().equals(getMessageString())) {
            service.schedule(() -> Platform.runLater(() -> AppModel.setDefaultMessage()), 2, TimeUnit
                    .SECONDS);
        }
    }

    public static void createNewTeam(String teamName) {

    }

    public static void createNewUser(String login, Integer passwordHash) {
        Dialog.showNewUserDialog(AppModel::processNewUserDialogResponse, login);
    }

    public static void processNewUserDialogResponse(NewUserDialogResponse newUserDialogResponse) {
        AppModel.setMessageString(String.format(YabcLocalization.getString("statusbar.create.user.complete"),
                newUserDialogResponse.getUser().getLogin()));
        appData.getAppUsers().add(newUserDialogResponse.getUser());
    }

    public static Boolean changeUserPredicate(AppState state) {
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

    public static class CheckUsernameAndPasswordResult {
        public BitSet bitSet = new BitSet(BITSET_SIZE_FOR_USERCHECK);

        public CheckUsernameAndPasswordResult(boolean... bitState) {
            for (int i = 0; i < BITSET_SIZE_FOR_USERCHECK; i++) {
                if (bitState[i]) bitSet.set(i);
            }
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


}
