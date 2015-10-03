package com.drozda.battlecity.modifier;

import com.drozda.battlecity.interfaces.CanMove;
import com.drozda.battlecity.interfaces.CanPause;
import com.drozda.battlecity.interfaces.HasGameUnits;
import com.drozda.battlecity.unit.GameUnit;
import com.drozda.battlecity.unit.MoveableUnit;
import com.drozda.battlecity.unit.TankUnit;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.Math.abs;
import static java.lang.Math.round;

/**
 * Created by GFH on 29.09.2015.
 */
public class PositionFixingModifier<T extends CanPause & CanMove> extends UnitModifier<T> implements
        ChangeListener<MoveableUnit.Direction> {
    private HasGameUnits playground;

    public PositionFixingModifier(T gameUnit, HasGameUnits playground) {
        super(gameUnit, playground);
        this.playground = playground;
    }

    @Override
    public void changed(ObservableValue<? extends MoveableUnit.Direction> observable, MoveableUnit.Direction oldValue, MoveableUnit.Direction newValue) {
        fixPosition();
    }

    public void fixPosition() {
        //  MoveableUnit gameUnit = (MoveableUnit) consumerFixPositionRequest.unit;

        long cellSize = round(gameUnit.getBounds().getWidth() / 2);

        double newX = gameUnit.getBounds().getMinX();
        double newY = gameUnit.getBounds().getMinY();

        Long x = nearest(newX, cellSize);
        Long y = nearest(newY, cellSize);

        if (abs(newX - x) < (cellSize / 2 + 1)) {
            newX = Double.valueOf(x);
        }
        if (abs(newY - y) < (cellSize / 2 + 1)) {
            newY = Double.valueOf(y);
        }
//        setNewBounds(new BoundingBox(newX, newY, cellSize * 2, cellSize * 2));
        //   if (collisionManager != null) {
        //        collisionManager.fixPosition(this, newX, newY);
        //   }
        fixPosition(gameUnit, newX, newY, playground);
    }

    private long nearest(double num, long base) {
        return (round(num / (base * 1.)) * base);
    }

    private void fixPosition(//ConsumerFixPositionRequest consumerFixPositionRequest
                             T activeUnit, Double newValueX, Double newValueY,
                             HasGameUnits world
    ) {
//        Object unit = consumerFixPositionRequest.unit;
//        double newValueX = consumerFixPositionRequest.newValueX;
//        double newValueY = consumerFixPositionRequest.newValueY;
//        HasGameUnits world = consumerFixPositionRequest.world;

        // MoveableUnit activeUnit = (MoveableUnit) unit;

        Predicate<GameUnit> onlyTank = gameUnit -> (gameUnit instanceof TankUnit);
        Predicate<GameUnit> notMe = gameUnit -> (gameUnit != activeUnit);
        Predicate<GameUnit> onlyTankAndNotMe = onlyTank.and(notMe);
        List<GameUnit> tanks = world.getUnitList().stream().filter(onlyTankAndNotMe).collect(Collectors.toList());
        if (tanks.size() == 0) {// no more tanks
            activeUnit.setBounds(
                    new BoundingBox(
                            newValueX, newValueY,
                            activeUnit.getBounds().getWidth(),
                            activeUnit.getBounds().getHeight()));
            return;
        }
        if (!tanks.stream().map(gameUnit -> {
            Bounds newBounds = new BoundingBox(newValueX + 1,
                    newValueY + 1,
                    activeUnit.getBounds().getWidth() - 2,
                    activeUnit.getBounds().getHeight() - 2);
            return newBounds.intersects(gameUnit.getBounds());
        }).anyMatch(b -> (b == true))) {
            activeUnit.setBounds(
                    new BoundingBox(
                            newValueX, newValueY,
                            activeUnit.getBounds().getWidth(),
                            activeUnit.getBounds().getHeight()));
        }
    }

    private T getUnit() {
        return (T) gameUnit;
    }


}
