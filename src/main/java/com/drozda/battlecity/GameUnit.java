package com.drozda.battlecity;


import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.drozda.battlecity.StaticServices.ONE_SECOND;
import static java.util.Arrays.asList;

/**
 * Created by GFH on 26.09.2015.
 */
public class GameUnit extends Observable {
    private static final Logger log = LoggerFactory.getLogger(GameUnit.class);
    protected static Map<State, Long> defaultTimeInState = new EnumMap<>(State.class);

    static {
        defaultTimeInState.put(State.CREATING, 2 * ONE_SECOND);
        defaultTimeInState.put(State.ACTIVE, 0L);
        defaultTimeInState.put(State.EXPLODING, ONE_SECOND / 6);
        defaultTimeInState.put(State.DEAD, 0L);

    }

    protected List<State> defaultStateFlow = asList(State.CREATING, State.ACTIVE, State.EXPLODING, State.DEAD);
    protected ObjectProperty<Bounds> bounds = new SimpleObjectProperty<>(new BoundingBox(0, 0, 0, 0));
    private Map<State, Long> timeInState = new EnumMap<>(State.class);
    private ObjectProperty<State> currentState = new SimpleObjectProperty<>();
    private BooleanProperty pause = new SimpleBooleanProperty();
    private LongProperty heartBeats = new SimpleLongProperty();
    private ListProperty<BonusUnit.BonusType> bonusList = new SimpleListProperty<>();
    private List<State> stateFlow = new LinkedList(); //TODO maybe we can use LinkedHashMap??

    public GameUnit(double minX, double minY, double width, double height, List<State> stateFlow, Map<State, Long>
            timeInState) {
        this.setBounds(new BoundingBox(minX, minY, width, height));
        if (stateFlow == null) {
            this.stateFlow.addAll(defaultStateFlow);
            this.timeInState.putAll(defaultTimeInState);
        } else {
            this.timeInState.putAll(timeInState);
            for (State state : stateFlow) {
                this.timeInState.putIfAbsent(state, defaultTimeInState.get(state));
            }
        }


    }

    public Bounds getBounds() {
        return bounds.get();
    }

    public void setBounds(Bounds bounds) {
        this.bounds.set(bounds);
    }
    //  protected Map<State,Long> timeInStateMap = new LinkedHashMap<>();

    public ObjectProperty<Bounds> boundsProperty() {
        return bounds;
    }

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

    protected Long getTimeInState(State state) {
        return timeInState.get(state);
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
                setChanged();
            }
        }
    }

}
