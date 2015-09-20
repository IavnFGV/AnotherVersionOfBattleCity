package com.drozda.battlecity.appflow;

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
public enum YabcState {

    MainMenu {
        @Override
        public List<YabcState> getAllowedTransitions() {
            return asList(new YabcState[]{Battle, Designer, LevelPicker, HallOfFame,
                    Settings});
        }

        @Override
        public void initialize() {

            log.debug("Loading FXML for main view from: {}", getFxmlFile());
            FXMLLoader loader = new FXMLLoader();
            Parent rootNode = null;
            try {
                rootNode = loader.load(getClass().getResourceAsStream(getFxmlFile()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            yabcFrame = new YabcAppModel.YabcFrame(rootNode, loader.getController());
        }
    },
    Battle {
        @Override
        public List<YabcState> getAllowedTransitions() {
            return asList(new YabcState[]{MainMenu, Battle, Designer, LevelPicker,
                    Settings});
        }
    },
    Designer {
        @Override
        public List<YabcState> getAllowedTransitions() {
            return asList(new YabcState[]{MainMenu, Battle});
        }
    },
    LevelPicker {
        @Override
        public List<YabcState> getAllowedTransitions() {
            return asList(new YabcState[]{MainMenu, Battle, Designer});
        }
    },
    HallOfFame {
        @Override
        public List<YabcState> getAllowedTransitions() {
            return asList(new YabcState[]{MainMenu});
        }
    },
    Settings {
        @Override
        public List<YabcState> getAllowedTransitions() {
            return asList(new YabcState[]{MainMenu, Battle});
        }
    };

    private static final Logger log = LoggerFactory.getLogger(YabcState.class);

    protected String fxmlBase = "/com/drozda/fx/fxml/";
    protected YabcAppModel.YabcFrame yabcFrame;
    protected boolean initialized;

    public YabcAppModel.YabcFrame getYabcFrame() {
        if (!isInitialized()) {
            initialize();
        }
        return yabcFrame;
    }

    public abstract List<YabcState> getAllowedTransitions();

    public boolean canTransition(YabcState newState) {
        if (newState == null) {
            throw new IllegalArgumentException("newState can not be null");
        }
        return getAllowedTransitions().contains(newState);
    }

    public YabcState tryTransitionIgnoreWrong(YabcState newState) {
        if (!canTransition(newState)) {
            return this;
        } else {
            return newState;
        }
    }

    public YabcState tryTransition(YabcState newState) {
        if (!canTransition(newState)) {
            throw new IllegalArgumentException("Cant make transition from " + this + "to " + newState);
        } else {
            return newState;
        }
    }

    public String getFxmlFile() {
        return fxmlBase + this.toString() + ".fxml";
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void initialize() {
    }




}
