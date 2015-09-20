package com.drozda.battlecity.appflow;

import com.benjiweber.statemachine.NextState;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
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

    static {
        appContext.put(YabcContextTypes.ADDITIONAL, new HashMap<String, Object>());
    }

    private static ObjectProperty<YabcUser> currentUser = new SimpleObjectProperty<>(new YabcUser());

    public static YabcUser getCurrentUser() {
        return currentUser.get();
    }

    public static ObjectProperty<YabcUser> currentUserProperty() {
        return currentUser;
    }

    public static void setCurrentUser(YabcUser aCurrentUser) {
        currentUser.set(aCurrentUser);
    }

    private static ObjectProperty<YabcState> state = new SimpleObjectProperty<>(new YabcState.MainMenu());

    public static YabcState getState() {
        return state.get();
    }

    public static ObjectProperty<YabcState> stateProperty() {
        return state;
    }

    public static void setState(final NextState newState) {
        YabcState yabcState = (YabcState) state.get().tryTransition(newState).ignoreIfInvalid();
        state.set(yabcState);
    }

    public static void startBattle() {
        setState(YabcState.Battle::new);
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
        private Scene scene;

        private T controller;

        public YabcFrame(Scene scene, T controller) {
            this.scene = scene;
            this.controller = controller;
        }

        public Scene getScene() {
            return scene;
        }

        public T getController() {
            return controller;
        }

    }

    public void initFrame(Class<? extends YabcState> aClass) {

    }

    public static void changeUser() {
        log.info("try to change user");
    }

}
