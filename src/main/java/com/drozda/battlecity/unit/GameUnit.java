package com.drozda.battlecity.unit;


import com.drozda.battlecity.interfaces.CanChangeState;
import com.drozda.battlecity.interfaces.CanPause;
import com.drozda.battlecity.interfaces.Collideable;
import com.drozda.battlecity.modifier.StateFlowModifier;
import javafx.beans.property.*;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.shape.Shape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.drozda.battlecity.StaticServices.ONE_SECOND;
import static java.util.Arrays.asList;

/**
 * Created by GFH on 26.09.2015.
 */
public abstract class GameUnit extends Observable implements CanChangeState<GameUnit.State>, CanPause, Collideable {
    private static final Logger log = LoggerFactory.getLogger(GameUnit.class);
    protected static Map<State, Long> defaultTimeInState = new EnumMap<>(State.class);
    protected static List<State> defaultStateFlow = asList(State.CREATING, State.ACTIVE, State.EXPLODING, State.DEAD);
    protected static EnumSet<State> statesForCollisionProcess = EnumSet.of(State.ACTIVE, State.ARMOR, State.BLINK);

    static {
        defaultTimeInState.put(State.CREATING, 2 * ONE_SECOND);
        defaultTimeInState.put(State.ACTIVE, 0L);
        defaultTimeInState.put(State.EXPLODING, ONE_SECOND / 3);
        defaultTimeInState.put(State.DEAD, 0L);
    }

    protected Shape shape;
    protected StateFlowModifier<? extends GameUnit> stateFlowModifier = new StateFlowModifier<>(this);
    protected ObjectProperty<Bounds> bounds = new SimpleObjectProperty<>(new BoundingBox(0, 0, 0, 0));
    protected LongProperty heartBeats = new SimpleLongProperty();
    protected Map<State, Long> timeInState = new EnumMap<>(State.class);
    protected List<State> stateFlow = new LinkedList(); //TODO maybe we can use LinkedHashMap??
    private ObjectProperty<State> currentState = new SimpleObjectProperty<>();
    private BooleanProperty pause = new SimpleBooleanProperty();
    private ObjectProperty<CollisionProcessState> collisionProcessState = new SimpleObjectProperty<>
            (CollisionProcessState.READY);

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
//        shape = Area.
    }

    public boolean intersects(GameUnit unit) {
        return getBounds().intersects(unit.getBounds());
    }

    public Bounds getBounds() {
        return bounds.get();
    }

    public void setBounds(Bounds bounds) {
        this.bounds.set(bounds);
    }

    @Override
    public boolean isTakingPartInCollisionProcess() {
        return statesForCollisionProcess.contains(getCurrentState());
    }

    @Override
    public CollisionProcessState getCollisionProcessState() {
        return collisionProcessState.get();
    }

    @Override
    public void setCollisionProcessState(CollisionProcessState collisionProcessState) {
        this.collisionProcessState.set(collisionProcessState);
    }

    @Override
    public State getCurrentState() {
        return currentState.get();
    }


    //  protected Map<State,Long> timeInStateMap = new LinkedHashMap<>()
//    @Override
//    public Long getTimeInState(State state) {
//        return timeInState.get(state);
//    }

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

    public ObjectProperty<State> currentStateProperty() {
        return currentState;
    }

    public ObjectProperty<CollisionProcessState> collisionProcessStateProperty() {
        return collisionProcessState;
    }

    public void heartBeat(long currentTime) {
        this.heartBeats.set(currentTime);
    }

    public ObjectProperty<Bounds> boundsProperty() {
        return bounds;
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
                "currentState=" + currentState +
                ", bounds=" + bounds +
                ", pause=" + pause +
                ", collisionProcessState=" + collisionProcessState +
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


}
