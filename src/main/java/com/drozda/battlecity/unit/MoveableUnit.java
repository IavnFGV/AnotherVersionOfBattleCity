package com.drozda.battlecity.unit;

import com.drozda.battlecity.interfaces.CanMove;
import com.drozda.battlecity.interfaces.HasGameUnits;
import com.drozda.battlecity.modifier.MovingModifier;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by GFH on 27.09.2015.
 */
public abstract class MoveableUnit extends GameUnit implements CanMove {
    private static final Logger log = LoggerFactory.getLogger(MoveableUnit.class);
    protected final MovingModifier<MoveableUnit> movingModifier;
    protected BooleanProperty engineOn = new SimpleBooleanProperty(false);
    protected ObjectProperty<Direction> direction = new SimpleObjectProperty<>(Direction.UP);
    private long velocity = 8L;

    public MoveableUnit(double minX, double minY, double width, double height, List<State> stateFlow, Map<State, Long> timeInState, HasGameUnits playground, long velocity) {
        super(minX, minY, width, height, stateFlow, timeInState, playground);
        this.velocity = velocity;
        this.movingModifier = createMovingModifier(playground);
    }

    abstract protected MovingModifier<MoveableUnit> createMovingModifier(HasGameUnits playground);

    @Override
    public void initialize(long startTime) {
        super.initialize(startTime);
        log.debug("MoveableUnit.initialize");
        heartBeats.addListener(movingModifier);
    }

    public boolean canMove() {
        return (engineOn.get() && getCurrentState() == State.ACTIVE); //todo maybe another  variants
    }

    @Override
    public Direction getDirection() {
        return direction.get();
    }

    @Override
    public long getVelocity() {
        return velocity;
    }

    public void setVelocity(long velocity) {
        this.velocity = velocity;
    }

    public void setDirection(Direction direction) {
        this.direction.set(direction);
    }

    public boolean getEngineOn() {
        return engineOn.get();
    }

    public void setEngineOn(boolean engineOn) {
        this.engineOn.set(engineOn);
    }

    public BooleanProperty engineOnProperty() {
        return engineOn;
    }

    public ObjectProperty<Direction> directionProperty() {
        return direction;
    }

    //protected CollisionManager collisionManager;
//  private Bounds newBounds;
    public enum Direction {
        UP,
        LEFT,
        DOWN,
        RIGHT;
    }

}
