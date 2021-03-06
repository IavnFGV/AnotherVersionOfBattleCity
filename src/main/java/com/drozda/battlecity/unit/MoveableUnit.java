package com.drozda.battlecity.unit;

import com.drozda.battlecity.interfaces.ActionCommandGenerator;
import com.drozda.battlecity.interfaces.BattleGround;
import com.drozda.battlecity.interfaces.CanMove;
import com.drozda.battlecity.interfaces.HasGameUnits;
import com.drozda.battlecity.modifier.MovingModifier;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Bounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by GFH on 27.09.2015.
 */
public abstract class MoveableUnit extends GameUnit implements CanMove {
    private static final Logger log = LoggerFactory.getLogger(MoveableUnit.class);
    protected final MovingModifier<? extends MoveableUnit> movingModifier;
    protected BooleanProperty engineOn = new SimpleBooleanProperty(false);
    protected ObjectProperty<Direction> direction = new SimpleObjectProperty<>(Direction.UP);
    protected ActionCommandGenerator actionCommandGenerator;
    protected BattleGround playground;
    private long velocity;

    public MoveableUnit(Bounds bounds, List<State> stateFlow, Map<State, Long> timeInState, BattleGround playground, long
            velocity) {
        super(bounds, stateFlow, timeInState);
        this.velocity = velocity;
        this.playground = playground;
        this.movingModifier = createMovingModifier(playground);
    }

    abstract protected MovingModifier<? extends MoveableUnit> createMovingModifier(HasGameUnits playground);

    public ActionCommandGenerator getActionCommandGenerator() {
        return actionCommandGenerator;
    }

    public void setActionCommandGenerator(ActionCommandGenerator actionCommandGenerator) {
        this.actionCommandGenerator = actionCommandGenerator;
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

    @Override
    public void heartBeat(long currentTime) {
        if (actionCommandGenerator != null) {
            ProcessActionCommandResult processActionCommandResult =
                    processActionCommand(actionCommandGenerator.extractActionCommand());
            log.debug(processActionCommandResult.toString());
        }
        super.heartBeat(currentTime);
    }

    @Override
    public void initialize(long startTime) {
        super.initialize(startTime);
        log.debug("MoveableUnit.initialize");
        heartBeats.addListener(movingModifier);
    }

    public ProcessActionCommandResult processActionCommand(ActionCommand actionCommand) {
        try {
            if (isPause()) return ProcessActionCommandResult.IGNORED;
            if (actionCommand.direction != null) {
                this.setDirection(actionCommand.direction);
                this.setEngineOn(true);
                return ProcessActionCommandResult.SUCCESS;
            } else {
                setEngineOn(false);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ProcessActionCommandResult.EXCEPTION;
        }
        return ProcessActionCommandResult.IGNORED;
    }

    //protected CollisionManager collisionManager;
//  private Bounds newBounds;
    public enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    public enum ProcessActionCommandResult {
        SUCCESS,
        IGNORED,
        EXCEPTION
    }

    public static class ActionCommand {
        public Direction direction;
        public boolean fire;

        public ActionCommand(Direction direction, boolean fire) {
            this.direction = direction;
            this.fire = fire;
        }
    }

}
