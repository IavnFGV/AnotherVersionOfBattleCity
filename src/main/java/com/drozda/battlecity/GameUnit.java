package com.drozda.battlecity;


import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by GFH on 26.09.2015.
 */
public class GameUnit extends Observable {
    private static final Logger log = LoggerFactory.getLogger(GameUnit.class);

    private Map<State, Long> timeInState = new EnumMap<>(State.class);
    private ObjectProperty<State> currentState = new SimpleObjectProperty<>();
    private BooleanProperty pause = new SimpleBooleanProperty();
    private LongProperty heartBeats = new SimpleLongProperty();
    private ListProperty<BonusUnit.BonusType> bonusList = new SimpleListProperty<>();
    private List<State> stateFlow = new LinkedList();

    public State getCurrentState() {
        return currentState.get();
    }

    public void setCurrentState(State currentState) {
        this.currentState.set(currentState);
    }

    public ObjectProperty<State> currentStateProperty() {
        return currentState;
    }

    public boolean isPause() {
        return pause.get();
    }

    public void setPause(boolean pause) {
        this.pause.set(pause);
    }

    public BooleanProperty pauseProperty() {
        return pause;
    }

    public ObservableList<BonusUnit.BonusType> getBonusList() {
        return bonusList.get();
    }

    public void setBonusList(ObservableList<BonusUnit.BonusType> bonusList) {
        this.bonusList.set(bonusList);
    }

    public ListProperty<BonusUnit.BonusType> bonusListProperty() {
        return bonusList;
    }


    public enum State {
        CREATING,
        ACTIVE,
        EXPLODING,
        DEAD;
    }

    protected abstract class HeartBeatListener implements ChangeListener<Number> {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            log.info("HeartBeatListener.changed");
            log.info("observable = [" + observable + "], oldValue = [" + oldValue + "], newValue = [" + newValue + "]");
            if (isPause()) {
                return;
            }
            long deltaTime = newValue.longValue() - oldValue.longValue();
            perform(deltaTime);
        }

        public abstract void perform(long deltaTime);
    }

    protected class StateChanger extends HeartBeatListener {
        private long timeInCurrentState;

        @Override
        public void perform(long deltaTime) {
            log.info("StateChanger.perform");
            log.info("deltaTime = [" + deltaTime + "]");
            timeInCurrentState += deltaTime;
            if (timeIsUp()) {
                goToNextState();
            }
        }

        private boolean timeIsUp() {
            log.debug("StateChanger.timeIsUp");
            long timeInState = getTimeInState(getCurrentState());
            if ((timeInState > 0) && (timeInState <= timeInCurrentState)) {
                return true;
            }
            return false;
        }

        private void goToNextState() {
            int curIndex = stateFlow.indexOf(currentState);
            if (curIndex < stateFlow.size() - 1) {
                setCurrentState(stateFlow.get(curIndex + 1));
            }
        }
    }

    protected class BasicHeartBeatListener extends HeartBeatListener {

        public void perform(long deltaTime) {
            changeBasicState(deltaTime);
        }

        public void changeBasicState(Long deltaTime) {
            if (getTimeInState(getBasicState()) > 0) { // so we can tick time
                setLeftTimeInBasicState(getLeftTimeInBasicState() - deltaTime);
                if ((getLeftTimeInBasicState()) <= 0) {
                    if (getBasicState() == ua.drozda.battlecity.core.GameUnit.BasicState.CREATING) {
                        setBasicState(ua.drozda.battlecity.core.GameUnit.BasicState.ACTIVE);
                    } else {
                        setBasicState(BasicState.DEAD);
                    }
                    setChanged();
                }
            }
        }
    }
}
