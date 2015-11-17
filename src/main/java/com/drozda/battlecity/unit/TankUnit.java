package com.drozda.battlecity.unit;

import com.drozda.battlecity.StaticServices;
import com.drozda.battlecity.interfaces.BattleGround;
import com.drozda.battlecity.interfaces.CanFire;
import com.drozda.battlecity.interfaces.HasGameUnits;
import com.drozda.battlecity.modifier.MovingModifier;
import com.drozda.battlecity.modifier.PositionFixingModifier;
import com.drozda.battlecity.modifier.TankMovingModifier;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by GFH on 15.11.2015.
 */
public abstract class TankUnit<E extends Enum> extends MoveableUnit implements CanFire {
    private static final Logger log = LoggerFactory.getLogger(TankUnit.class);
    protected final PositionFixingModifier<MoveableUnit> fixingModifier;
    protected IntegerProperty lifes = new SimpleIntegerProperty(1);
    private E tankType;
    private IntegerProperty activeBullets = new SimpleIntegerProperty(0);

    public TankUnit(Bounds bounds, List<State> stateFlow, Map<State, Long> timeInState, BattleGround playground, E
            tankType, long velocity) {
        super(bounds, stateFlow, timeInState, playground, velocity);
        this.timeInState.putIfAbsent(State.EXPLODING, StaticServices.TANK_EXPLODING_TIME);
        this.tankType = tankType;
        fixingModifier = new PositionFixingModifier<>(this, playground);
        this.directionProperty().addListener(fixingModifier);
    }

    public abstract long getBulletSpeed();

    public abstract BulletUnit.Type getBulletType();

    public IntegerProperty activeBulletsProperty() {
        return activeBullets;
    }

    @Override
    public IsMoveAllowedResult isMoveAllowed(boolean isInWorldBounds) {
        return isInWorldBounds ? IsMoveAllowedResult.ALLOW : IsMoveAllowedResult.STOP;
    }

    public int getLifes() {
        return lifes.get();
    }

    public void setLifes(int lifes) {
        this.lifes.set(lifes);
    }

    public IntegerProperty lifesProperty() {
        return lifes;
    }

    @Override
    protected MovingModifier<TankUnit> createMovingModifier(HasGameUnits playground) {
        return new TankMovingModifier(this, playground);
    }

    @Override
    public ProcessActionCommandResult processActionCommand(ActionCommand actionCommand) {
        ProcessActionCommandResult superResult = super.processActionCommand(actionCommand);
        if ((getActiveBullets() == 0) &&
                (actionCommand.fire)) {
            fire();
            return ProcessActionCommandResult.SUCCESS;
        }
        return superResult;
    }

    public int getActiveBullets() {
        return activeBullets.get();
    }

    public void setActiveBullets(int activeBullets) {
        this.activeBullets.set(activeBullets);
    }

    @Override
    public boolean fire() { // todo for testing
        if (!isBulletAvailable()) return false;
        playground.fire(this);
        return true;
    }

    protected boolean isBulletAvailable() {
        return !isPause();
    }

    public Point2D getBulletStartPosition() {
        Point2D result = new Point2D(getBounds().getMinX(), getBounds().getMinY());
        switch (getDirection()) {
            case UP:
                return result.add(16 - 4, -8); //size of image
            case LEFT:
                return result.add(-8, 16 - 4); // size of image
            case DOWN:
                return result.add(16 - 4, 32);
            case RIGHT:
                return result.add(32, 16 - 4);
        }
        return result;
    }

    public E getTankType() {
        return tankType;
    }

    public void setTankType(E tankType) {
        this.tankType = tankType;
    }
}
