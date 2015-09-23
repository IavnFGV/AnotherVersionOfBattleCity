package com.drozda.appflow;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by GFH on 20.09.2015.
 */
public enum AppState {

    MainMenu {

        {
            initialized = true;
            initialize();
        }

        @Override
        public com.drozda.fx.controller.MainMenu getController() {
            return (com.drozda.fx.controller.MainMenu) getYabcFrame().getController();
        }

        @Override
        public List<AppState> getAllowedTransitions() {
            return asList(new AppState[]{Battle, Designer, LevelPicker, HallOfFame,
                    Settings});
        }
    },
    Battle {
        {
            initialized = true;
            initialize();
        }

        @Override
        public com.drozda.fx.controller.Battle getController() {
            return (com.drozda.fx.controller.Battle) getYabcFrame().getController();
        }

        @Override
        public List<AppState> getAllowedTransitions() {
            return asList(new AppState[]{MainMenu, Battle, Designer, LevelPicker,
                    Settings});
        }
    },
    Designer {
        {
            initialized = true;
            initialize();
        }

        @Override
        public com.drozda.fx.controller.Designer getController() {
            return (com.drozda.fx.controller.Designer) getYabcFrame().getController();
        }

        @Override
        public List<AppState> getAllowedTransitions() {
            return asList(new AppState[]{MainMenu, Battle});
        }
    },
    LevelPicker {
        {
            initialized = true;
            initialize();
        }

        @Override
        public com.drozda.fx.controller.LevelPicker getController() {
            return (com.drozda.fx.controller.LevelPicker) getYabcFrame().getController();
        }

        @Override
        public List<AppState> getAllowedTransitions() {
            return asList(new AppState[]{MainMenu, Battle, Designer});
        }
    },
    HallOfFame {
        {
            initialized = true;
            initialize();
        }

        @Override
        public com.drozda.fx.controller.HallOfFame getController() {
            return (com.drozda.fx.controller.HallOfFame) getYabcFrame().getController();
        }

        @Override
        public List<AppState> getAllowedTransitions() {
            return asList(new AppState[]{MainMenu});
        }
    },
    Settings {
        {
            initialized = true;
            initialize();
        }

        @Override
        public com.drozda.fx.controller.Settings getController() {
            return (com.drozda.fx.controller.Settings) getYabcFrame().getController();
        }

        @Override
        public List<AppState> getAllowedTransitions() {
            return asList(new AppState[]{MainMenu, Battle});
        }
    };

    protected final Logger log = LoggerFactory.getLogger(AppState.class);

    protected String fxmlBase = "/com/drozda/fx/fxml/";
    protected AppModel.YabcFrame yabcFrame;
    protected boolean initialized;

    public AppModel.YabcFrame getYabcFrame() {
        if (!isInitialized()) {
            initialize();
        }
        return yabcFrame;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void initialize() {
        log.debug("Loading FXML for AppState {} from: {}", this, getFxmlFile());
        FXMLLoader loader = new FXMLLoader();
        Parent rootNode = null;
        try {
            rootNode = loader.load(getClass().getResourceAsStream(getFxmlFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        yabcFrame = new AppModel.YabcFrame(rootNode, loader.getController());
    }

    public String getFxmlFile() {
        return fxmlBase + this.toString() + ".fxml";
    }

    public AppState tryTransitionIgnoreWrong(AppState newState) {
        if (!canTransition(newState)) {
            return this;
        } else {
            return newState;
        }
    }

    public boolean canTransition(AppState newState) {
        if (newState == null) {
            throw new IllegalArgumentException("newState can not be null");
        }
        return getAllowedTransitions().contains(newState);
    }

    public abstract List<AppState> getAllowedTransitions();

    public AppState tryTransition(AppState newState) {
        if (!canTransition(newState)) {
            throw new IllegalArgumentException("Cant make transition from " + this + "to " + newState);
        } else {
            return newState;
        }
    }

    public abstract Object getController();

}
