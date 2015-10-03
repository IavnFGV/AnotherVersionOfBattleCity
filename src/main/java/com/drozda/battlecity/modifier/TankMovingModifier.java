package com.drozda.battlecity.modifier;

import com.drozda.battlecity.interfaces.CanPause;
import com.drozda.battlecity.interfaces.HasGameUnits;
import com.drozda.battlecity.unit.BulletUnit;
import com.drozda.battlecity.unit.GameUnit;
import com.drozda.battlecity.unit.MoveableUnit;
import com.drozda.battlecity.unit.TileUnit;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Created by GFH on 30.09.2015.
 */
public class TankMovingModifier extends MovingModifier<MoveableUnit> {
    private static final Logger log = LoggerFactory.getLogger(TankMovingModifier.class);

    private static Set<TileUnit.TileType> rideTiles = EnumSet.of(TileUnit.TileType.FOREST, TileUnit.TileType.ICE);

    public TankMovingModifier(CanPause gameUnit, HasGameUnits playground) {
        super(gameUnit, playground);
    }

    @Override
    protected void confirmNewPosition(MoveableUnit activeUnit, double newValueX, double newValueY, HasGameUnits playground) {
        Bounds newFixedBounds = new BoundingBox(newValueX + 1,
                // newBounds will always intersects another bounds if we are absolutely near another
                // so we give 1 pixel on every direction to handle this situation
                newValueY + 1,
                activeUnit.getBounds().getWidth() - 2,
                activeUnit.getBounds().getHeight() - 2);
        Bounds newBounds = new BoundingBox(newValueX,
                newValueY,
                activeUnit.getBounds().getWidth(),
                activeUnit.getBounds().getHeight());
        Point2D result = new Point2D(newValueX, newValueY);
        Predicate<GameUnit> notMe = u -> (u != activeUnit);
        Predicate<GameUnit> notBullet = u -> (!(u instanceof BulletUnit));

        Point2D newPosition = playground.getUnitList().stream()
                .filter(notMe)
                .filter(notBullet)
                .map(u -> {
                            if (!canRide(u) && u.getBounds().intersects(newFixedBounds)) {
//                        System.out.println("OtherUnit =" + u.getClass() + ";activeUnit = [" + activeUnit
//                                .getClass() +
//                                "]");
                                switch (activeUnit.getDirection()) {
                                    case UP: {
                                        double deltaY = u.getBounds().getMaxY() - newBounds.getMinY();
                                        return result.add(0, deltaY);
                                    }
                                    case LEFT: {
                                        double deltaX = u.getBounds().getMaxX() - newBounds.getMinX();
                                        return result.add(deltaX, 0);
                                    }
                                    case DOWN: {
                                        double deltaY = newBounds.getMaxY() - u.getBounds().getMinY();
                                        return result.add(0, -deltaY);

                                    }
                                    case RIGHT: {
                                        double deltaX = newBounds.getMaxX() - u.getBounds().getMinX();
                                        return result.add(-deltaX, 0);
                                    }
                                }
                            }
                            return result;
                        }
                ).filter(point2D -> (!point2D.equals(result))).findFirst().orElse(result);
        activeUnit.setBounds(new BoundingBox(newPosition.getX(), newPosition.getY(), activeUnit.getBounds().getWidth(),
                activeUnit.getBounds().getHeight()));
    }


    private static Boolean canRide(GameUnit unit) {
        Boolean result = false;
        if (unit instanceof TileUnit) {
            result = (rideTiles.contains(((TileUnit) unit).getTileType()));
        }
        return result;
    }


}
