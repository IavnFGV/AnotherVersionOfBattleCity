package com.drozda.battlecity.modifierx.boundslist.byproperty;

import com.drozda.battlecity.interfacesx.BattleGround;
import com.drozda.battlecity.interfacesx.Moveable;
import com.drozda.battlecity.modifierx.ListPropertyModifierByProperty;
import com.drozda.battlecity.unitx.GameUnitX;
import com.drozda.battlecity.unitx.TankUnitX;
import com.drozda.battlecity.unitx.enumeration.BasicState;
import com.drozda.battlecity.unitx.enumeration.Direction;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by GFH on 22.11.2015.
 */
public class BoundsListModifierByDirectionProperty extends
        ListPropertyModifierByProperty<Direction, Bounds> {
    protected BattleGround battleGround;
    protected Moveable moveable;


    public BoundsListModifierByDirectionProperty(ReadOnlyListWrapper<Bounds> propertyToChange,
                                                 ReadOnlyObjectProperty<Direction> propertyToListen,
                                                 BattleGround battleGround,
                                                 Moveable moveable) {
        super(propertyToChange, propertyToListen);
        this.battleGround = battleGround;
        this.moveable = moveable;
    }

    @Override
    public void changed(ObservableValue<? extends Direction> observable, Direction oldValue, Direction newValue) {
        fixPosition();
    }

    public void fixPosition() {
        if (listPropertyToChange.get() == null) return;
        if (listPropertyToChange.get().size() > 1) {
            throw new RuntimeException("Multiple bounds fixing does not implemented yet");
        }
        Bounds curBounds = listPropertyToChange.get().get(0);
        long cellSize = Math.round(curBounds.getWidth() / 2);

        double newX = curBounds.getMinX();
        double newY = curBounds.getMinY();

        Long x = nearest(newX, cellSize);
        Long y = nearest(newY, cellSize);

        if (Math.abs(newX - x) < (cellSize / 2 + 1)) {
            newX = Double.valueOf(x);
        }
        if (Math.abs(newY - y) < (cellSize / 2 + 1)) {
            newY = Double.valueOf(y);
        }
        fixPosition(curBounds, newX, newY);
    }

    private long nearest(double num, long base) {
        return (Math.round(num / (base * 1.)) * base);
    }

    private void fixPosition(Bounds curBounds, Double newValueX, Double newValueY
    ) {
        Predicate<GameUnitX> onlyTank = gameUnit -> (gameUnit instanceof TankUnitX);
        Predicate<GameUnitX> notMe = gameUnit -> (gameUnit != moveable);
        Predicate<GameUnitX> onlyTankAndNotMe = onlyTank.and(notMe);
        Predicate<GameUnitX> notDead = gameUnit1 -> gameUnit1.getBasicStateProperty().get() == BasicState.ACTIVE;
        List<GameUnitX> tanks = battleGround.getUnitList().stream()
                .filter(notDead)
                .filter(onlyTankAndNotMe)
                .collect(Collectors.toList());
        if (tanks.size() == 0) {// no more tanks
            listPropertyToChange.clear();
            listPropertyToChange.add(
                    new BoundingBox(
                            newValueX, newValueY,
                            curBounds.getWidth(),
                            curBounds.getHeight()));
            return;
        }
        if (!tanks.stream().map(gameUnit -> {
            Bounds newBounds = new BoundingBox(newValueX + 1,
                    newValueY + 1,
                    curBounds.getWidth() - 2,
                    curBounds.getHeight() - 2);
            return newBounds.intersects(curBounds);
        }).anyMatch(b -> (b == true))) {
            listPropertyToChange.clear();
            listPropertyToChange.add(
                    new BoundingBox(
                            newValueX, newValueY,
                            curBounds.getWidth(),
                            curBounds.getHeight()));
        }
    }
}
