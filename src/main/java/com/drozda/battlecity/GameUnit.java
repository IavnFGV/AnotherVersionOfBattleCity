package com.drozda.battlecity;


import com.drozda.battlecity.modifier.StateModifier;
import javafx.beans.property.*;
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
public class GameUnit extends Observable implements CanChangeState, CanPause {
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
    private StateModifier<GameUnit> stateModifier = new StateModifier<>(this);
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

    public void heartBeat(long currentTime) {
        this.heartBeats.set(currentTime);
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

    public ObjectProperty<State> currentStateProperty() {
        return currentState;
    }

    @Override
    public State getCurrentState() {
        return currentState.get();
    }

    public void initialize(long startTime) {
        log.debug("GameUnit.initialize with parameters " + "startTime = [" + startTime + "]");
        this.setCurrentState(stateFlow.get(0));
        heartBeats.removeListener(stateModifier);
        heartBeats.setValue(startTime);
        heartBeats.addListener(stateModifier);
    }

    @Override
    public void setCurrentState(State currentState) {
        this.currentState.set(currentState);
    }

    @Override
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


    @Override
    public Long getTimeInState(State state) {
        return timeInState.get(state);
    }

    @Override
    public void goToNextState() {
        int curIndex = stateFlow.indexOf(getCurrentState());
        if (curIndex < stateFlow.size() - 1) {
            setCurrentState(stateFlow.get(curIndex + 1));
            setChanged();
        }
    }



}
