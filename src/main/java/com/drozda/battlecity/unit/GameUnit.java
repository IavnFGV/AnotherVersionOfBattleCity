package com.drozda.battlecity.unit;


import com.drozda.battlecity.interfaces.CanChangeState;
import com.drozda.battlecity.interfaces.CanPause;
import com.drozda.battlecity.modifier.StateFlowModifier;
import javafx.beans.property.*;
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
public abstract class GameUnit extends Observable implements CanChangeState<GameUnit.State>, CanPause {
    private static final Logger log = LoggerFactory.getLogger(GameUnit.class);
    protected static Map<State, Long> defaultTimeInState = new EnumMap<>(State.class);
    protected static List<State> defaultStateFlow = asList(State.CREATING, State.ACTIVE, State.EXPLODING, State.DEAD);

    static {
        defaultTimeInState.put(State.CREATING, 2 * ONE_SECOND);
        defaultTimeInState.put(State.ACTIVE, 0L);
        defaultTimeInState.put(State.EXPLODING, ONE_SECOND / 6);
        defaultTimeInState.put(State.DEAD, 0L);
    }

    protected StateFlowModifier<? extends GameUnit> stateFlowModifier = new StateFlowModifier<>(this);
    protected ObjectProperty<Bounds> bounds = new SimpleObjectProperty<>(new BoundingBox(0, 0, 0, 0));
    protected LongProperty heartBeats = new SimpleLongProperty();
    private Map<State, Long> timeInState = new EnumMap<>(State.class);
    private ObjectProperty<State> currentState = new SimpleObjectProperty<>();
    private BooleanProperty pause = new SimpleBooleanProperty();

    private List<State> stateFlow = new LinkedList(); //TODO maybe we can use LinkedHashMap??

    public GameUnit(Bounds bounds, List<State> stateFlow, Map<State, Long>
            timeInState) {
        this.setBounds(bounds);
        if (stateFlow == null) {
            this.stateFlow.addAll(defaultStateFlow);
        } else {
            this.stateFlow.addAll(stateFlow);
        }
        if (timeInState != null) {
            for (State state : this.stateFlow) {
                this.timeInState.putIfAbsent(state, timeInState.get(state));
            }
        }
        for (State state : this.stateFlow) {
            this.timeInState.putIfAbsent(state, defaultTimeInState.get(state));
        }
        currentState.setValue(this.stateFlow.get(0));
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

    public void initialize(long startTime) {
        log.debug("GameUnit.initialize with parameters " + "startTime = [" + startTime + "]");
        this.setCurrentState(stateFlow.get(0));
        heartBeats.removeListener(stateFlowModifier);
        heartBeats.setValue(startTime);
        heartBeats.addListener(stateFlowModifier);
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

    @Override
    public String toString() {
        return "GameUnit{" +
                ", bounds=" + bounds +
                ", timeInState=" + timeInState +
                ", currentState=" + currentState +
                ", pause=" + pause +
                ", heartBeats=" + heartBeats +
                ", stateFlow=" + stateFlow +
                ", stateFlowModifier=" + stateFlowModifier +
                "} " + super.toString();
    }

    public void resetTimeInCurrentState() {
        stateFlowModifier.resetTimeInCurrentState();
    }

    public enum State {
        CREATING,
        ACTIVE,
        EXPLODING,
        DEAD,
        //special for changeableTile
        ARMOR,
        BLINK,
        //special for bonusTile
        IN_POCKET
    }

    @Override
    public State getCurrentState() {
        return currentState.get();
    }


    @Override
    public void setCurrentState(State currentState) {
        this.currentState.set(currentState);
    }


    @Override
    public Long getTimeInState(State state) {
        return timeInState.get(state);
    }

    @Override
    public void goToNextState() {
        if (getCurrentState() == State.DEAD) return;
        int curIndex = stateFlow.indexOf(getCurrentState());
        int nextIndex = (curIndex == stateFlow.size() - 1) ? 0 : curIndex + 1;
//        if (curIndex < stateFlow.size() - 1) {
        setCurrentState(stateFlow.get(nextIndex));
        setChanged();
//        }
    }


}
