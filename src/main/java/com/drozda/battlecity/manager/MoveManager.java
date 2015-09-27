package com.drozda.battlecity.manager;


import com.drozda.battlecity.*;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Created by GFH on 27.09.2015.
 */
public class MoveManager {
    private static final Logger log = LoggerFactory.getLogger(MoveManager.class);
    private static Set<TileUnit.TileType> rideTiles = EnumSet.of(TileUnit.TileType.FOREST, TileUnit.TileType.ICE);

    public static void confirmNewPosition(ConsumerRequest consumerRequest) {
        log.debug("MoveManager.confirmNewPosition with parameters " + "consumerRequest = [" + consumerRequest + "]");

        Object unit = consumerRequest.unit;
        double newValueX = consumerRequest.newValueX;
        double newValueY = consumerRequest.newValueY;
        HasGameUnits world = consumerRequest.world;

        MoveableUnit activeUnit = (MoveableUnit) unit;
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

        Point2D newPosition = world.getUnitList().stream()
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
//    public void fixPosition(ActiveUnit activeUnit, Double newX, Double newY) {
//        Predicate<GameUnit> onlyTank = gameUnit -> (gameUnit instanceof TankUnit);
//        Predicate<GameUnit> notMe = gameUnit -> (gameUnit != activeUnit);
//        Predicate<GameUnit> onlyTankAndNotMe = onlyTank.and(notMe);
//        List<GameUnit> tanks = world.getUnitList().stream().filter(onlyTankAndNotMe).collect(Collectors.toList());
//        if (tanks.size() == 0) {// no more tanks
//            activeUnit.setX(newX);
//            activeUnit.setY(newY);
//            return;
//        }
//        if (!tanks.stream().map(gameUnit -> {
//            Bounds newBounds = new BoundingBox(newX + 1,
//                    newY + 1,
//                    activeUnit.getWidth() - 2,
//                    activeUnit.getHeight() - 2);
//            return newBounds.intersects(gameUnit.getBounds());
//        }).anyMatch(b -> (b == true))) {
//            activeUnit.setX(newX);
//            activeUnit.setY(newY);
//        }
//    }

    private static Boolean canRide(GameUnit unit) {
        Boolean result = false;
        if (unit instanceof TileUnit) {
            result = (rideTiles.contains(((TileUnit) unit).getTileType()));
        }
        return result;
    }

    public static class ConsumerRequest {
        Object unit;
        double newValueX;
        double newValueY;
        HasGameUnits world;

        public ConsumerRequest(Object unit, double newValueX, double newValueY, HasGameUnits world) {
            if (!(unit instanceof MoveableUnit)) {
                log.error("unit is not instanceof MoveableUnit");
                throw new RuntimeException("unit is not instanceof MoveableUnit");
            }
            this.unit = unit;
            this.newValueX = newValueX;
            this.newValueY = newValueY;
            this.world = world;
        }

        @Override
        public String toString() {
            return "ConsumerRequest{" +
                    "unit=" + unit +
                    ", newValueX=" + newValueX +
                    ", newValueY=" + newValueY +
                    ", world=" + world +
                    '}';
        }
    }


}
