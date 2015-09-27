package com.drozda.battlecity;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.List;
import java.util.Map;

/**
 * Created by GFH on 27.09.2015.
 */
public class MoveableUnit extends GameUnit implements CanMove {
    protected BooleanProperty engineOn = new SimpleBooleanProperty(false);
    protected ObjectProperty<Direction> direction = new SimpleObjectProperty<>(Direction.UP);
    private long velocity = 8L;

    public MoveableUnit(double minX, double minY, double width,
                        double height, List<State> stateFlow, Map<State, Long> timeInState) {
        super(minX, minY, width, height, stateFlow, timeInState);
    }

    @Override
    public boolean getEngineOn() {
        return engineOn.get();
    }

    public void setEngineOn(boolean engineOn) {
        this.engineOn.set(engineOn);
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
