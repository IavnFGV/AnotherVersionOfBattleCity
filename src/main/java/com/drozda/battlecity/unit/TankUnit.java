package com.drozda.battlecity.unit;

import com.drozda.battlecity.interfaces.CanFire;
import com.drozda.battlecity.interfaces.Collideable;
import com.drozda.battlecity.interfaces.HasGameUnits;
import com.drozda.battlecity.modifier.MovingModifier;
import com.drozda.battlecity.modifier.PositionFixingModifier;
import com.drozda.battlecity.modifier.TankMovingModifier;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by GFH on 15.11.2015.
 */
public abstract class TankUnit<E extends Enum> extends MoveableUnit implements Collideable<GameUnit>, CanFire {
    private static final Logger log = LoggerFactory.getLogger(TankUnit.class);
    protected final PositionFixingModifier<MoveableUnit> fixingModifier;
    protected IntegerProperty lifes = new SimpleIntegerProperty(1);
    private E tankType;

    public TankUnit(Bounds bounds, List<State> stateFlow, Map<State, Long> timeInState, HasGameUnits playground, E
            tankType, long velocity) {
        super(bounds, stateFlow, timeInState, playground, velocity);
        this.tankType = tankType;
        fixingModifier = new PositionFixingModifier<>(this, playground);
        this.directionProperty().addListener(fixingModifier);
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
        if (actionCommand.fire) {
            fire();
            return ProcessActionCommandResult.SUCCESS;
        }
        return superResult;
    }

    @Override
    public boolean fire() { // todo for testing
        if (!isBulletAvailable()) return false;
        Point2D startPosition = getBulletStartPosition();
        double bulletWidth = playground.getBulletWidth();
        double bulletHeight = playground.getBulletHeight();
        BulletUnit bulletUnit = new BulletUnit(
                new BoundingBox(startPosition.getX(), startPosition.getY(), bulletWidth, bulletHeight),
                playground,
                10l, //todo replace with mind!!
                this,
                BulletUnit.Type.SIMPLE);
        bulletUnit.initialize(heartBeats.get());
        bulletUnit.setEngineOn(true);
        Platform.runLater(() -> playground.getUnitList().add(bulletUnit));
        return true;
    }

    protected boolean isBulletAvailable() {
        return !isPause();
    }

    protected Point2D getBulletStartPosition() {
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
