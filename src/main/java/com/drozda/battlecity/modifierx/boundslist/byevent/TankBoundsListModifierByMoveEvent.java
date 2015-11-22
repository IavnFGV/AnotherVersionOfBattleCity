package com.drozda.battlecity.modifierx.boundslist.byevent;

import com.drozda.battlecity.eventx.IMoveEvent;
import com.drozda.battlecity.interfacesx.BattleGround;
import com.drozda.battlecity.interfacesx.Moveable;
import com.drozda.battlecity.unitx.GameUnitX;
import com.drozda.battlecity.unitx.TankUnitX;
import com.drozda.battlecity.unitx.TileUnitX;
import com.drozda.battlecity.unitx.enumeration.BasicState;
import com.drozda.battlecity.unitx.enumeration.TileType;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by GFH on 22.11.2015.
 */
public class TankBoundsListModifierByMoveEvent extends SimpleBoundsListModifierByMoveEvent {
    public TankBoundsListModifierByMoveEvent(ReadOnlyListWrapper<Bounds> listPropertyToChange,
                                             Class<IMoveEvent> eventObjectType,
                                             BattleGround battleGround,
                                             Moveable moveable) {
        super(listPropertyToChange, eventObjectType, battleGround, moveable);
    }

    @Override
    protected void allowChange(List<Bounds> newBounds) {
        if (newBounds.size() > 1) {
            throw new RuntimeException("Multiple bounds for tanks are not implemented yet");
        }
        Bounds newSingleBounds = newBounds.get(0);
        Bounds curBounds = moveable.getBoundsListProperty().get(0);
        // newBounds will always intersects another bounds if we are absolutely near another
        // so we give 1 pixel on every direction to handle this situation
        Bounds newFixedBounds = new BoundingBox(newSingleBounds.getMinX() + 1,
                newSingleBounds.getMinY() + 1,
                newSingleBounds.getWidth() - 2,
                newSingleBounds.getHeight() - 2);
        Point2D result = new Point2D(newSingleBounds.getMinX(), newSingleBounds.getMinY());
        Predicate<GameUnitX> notMe = u -> (u != moveable);
        Predicate<GameUnitX> bricksAndSteelOrTanks = u -> {
            if (u instanceof TileUnitX) {
                TileUnitX tileUnitX = (TileUnitX) u;
                if (tileUnitX.getTileType() == TileType.BRICK) return true;
                if (tileUnitX.getTileType() == TileType.STEEL) return true;
            }
            return u instanceof TankUnitX;
        };
        // Predicate<GameUnitX> notBullet = u -> (!(u instanceof BulletUnit));
        // Predicate<GameUnitX> notBonus = u -> (!(u instanceof BonusUnit));
        Predicate<GameUnitX> notDead = u -> u.getBasicStateProperty().get() != BasicState.SLEEP;

        Point2D newPosition = battleGround.getUnitList().stream()
                .filter(notMe)
                .filter(bricksAndSteelOrTanks)
                .filter(notDead)
                .map(u -> {
                            if (u.getBoundsFixedToGreed().intersects(curBounds)) {
                                switch (moveable.getDirectionProperty().get()) {
                                    case UP: {
                                        double deltaY = u.getBoundsFixedToGreed().getMaxY() - newSingleBounds.getMinY();
                                        return result.add(0, deltaY);
                                    }
                                    case LEFT: {
                                        double deltaX = u.getBoundsFixedToGreed().getMaxX() - newSingleBounds.getMinX();
                                        return result.add(deltaX, 0);
                                    }
                                    case DOWN: {
                                        double deltaY = newSingleBounds.getMaxY() - u.getBoundsFixedToGreed().getMinY();
                                        return result.add(0, -deltaY);

                                    }
                                    case RIGHT: {
                                        double deltaX = newSingleBounds.getMaxX() - u.getBoundsFixedToGreed().getMinX();
                                        return result.add(-deltaX, 0);
                                    }
                                }
                            }
                            return result;
                        }
                ).filter(point2D -> (!point2D.equals(result))).findFirst().orElse(result);
        listPropertyToChange.clear();
        listPropertyToChange.add(new BoundingBox(newPosition.getX(),
                newPosition.getY(),
                curBounds.getWidth(),
                curBounds.getHeight()));
    }
}
