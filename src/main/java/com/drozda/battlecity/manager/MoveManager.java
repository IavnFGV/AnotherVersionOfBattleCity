package com.drozda.battlecity.manager;


import com.drozda.battlecity.*;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.Math.abs;
import static java.lang.Math.round;

/**
 * Created by GFH on 27.09.2015.
 */
public class MoveManager {
    private static final Logger log = LoggerFactory.getLogger(MoveManager.class);
    private static Set<TileUnit.TileType> rideTiles = EnumSet.of(TileUnit.TileType.FOREST, TileUnit.TileType.ICE);

    public static void confirmNewPosition(ConsumerNewPositionRequest consumerNewPositionRequest) {
        log.debug("MoveManager.confirmNewPosition with parameters " + "consumerNewPositionRequest = [" + consumerNewPositionRequest + "]");

        Object unit = consumerNewPositionRequest.unit;
        double newValueX = consumerNewPositionRequest.newValueX;
        double newValueY = consumerNewPositionRequest.newValueY;
        HasGameUnits world = consumerNewPositionRequest.world;

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

    private static Boolean canRide(GameUnit unit) {
        Boolean result = false;
        if (unit instanceof TileUnit) {
            result = (rideTiles.contains(((TileUnit) unit).getTileType()));
        }
        return result;
    }

    private static void fixPosition(ConsumerFixPositionRequest consumerFixPositionRequest) {
        MoveableUnit gameUnit = (MoveableUnit) consumerFixPositionRequest.unit;

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
        fixPosition(gameUnit, newX, newY, consumerFixPositionRequest.world);
    }

    private static long nearest(double num, long base) {
        return (round(num / (base * 1.)) * base);
    }

    public static void fixPosition(//ConsumerFixPositionRequest consumerFixPositionRequest
                                   MoveableUnit activeUnit, Double newValueX, Double newValueY,
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

    public static class ConsumerNewPositionRequest {
        Object unit;
        double newValueX;
        double newValueY;
        HasGameUnits world;

        public ConsumerNewPositionRequest(Object unit, double newValueX, double newValueY, HasGameUnits world) {
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
            return "ConsumerNewPositionRequest{" +
                    "unit=" + unit +
                    ", newValueX=" + newValueX +
                    ", newValueY=" + newValueY +
                    ", world=" + world +
                    '}';
        }
    }

    public static class ConsumerFixPositionRequest {

        Object unit;
        HasGameUnits world;

        public ConsumerFixPositionRequest(Object unit, HasGameUnits world) {
            this.unit = unit;
            this.world = world;
        }

        @Override
        public String toString() {
            return "ConsumerFixPositionRequest{" +
                    "unit=" + unit +
                    ", world=" + world +
                    '}';
        }
    }


}
