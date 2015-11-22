package com.drozda.battlecity.modifierx.boundslist.byevent;

import com.drozda.battlecity.eventx.IMoveEvent;
import com.drozda.battlecity.interfacesx.BattleGround;
import com.drozda.battlecity.interfacesx.Moveable;
import com.drozda.battlecity.modifierx.ListPropertyModifierByEvent;
import com.drozda.battlecity.unitx.enumeration.Direction;
import com.drozda.battlecity.unitx.enumeration.EngineState;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Created by GFH on 21.11.2015.
 */
public class SimpleBoundsListModifierByMoveEvent extends ListPropertyModifierByEvent<Bounds, IMoveEvent> {

    static Map<Direction, BiFunction<Point2D, Integer, Point2D>> newPositionMap = new
            HashMap<>();

    static {
        newPositionMap.put(Direction.UP, ((point2D, delta) -> new Point2D(point2D.getX(), point2D.getY() - delta)));
        newPositionMap.put(Direction.RIGHT, ((point2D, delta) -> new Point2D(point2D.getX() + delta, point2D.getY())));
        newPositionMap.put(Direction.DOWN, ((point2D, delta) -> new Point2D(point2D.getX(), point2D.getY() + delta)));
        newPositionMap.put(Direction.LEFT, ((point2D, delta) -> new Point2D(point2D.getX() - delta, point2D.getY())));
    }

    protected BattleGround battleGround;
    protected Moveable moveable;


    public SimpleBoundsListModifierByMoveEvent(ReadOnlyListWrapper<Bounds> listPropertyToChange,
                                               Class<IMoveEvent> eventObjectType,
                                               BattleGround battleGround,
                                               Moveable moveable
    ) {
        super(listPropertyToChange, eventObjectType);
        this.moveable = moveable;
        this.battleGround = battleGround;
    }

    @Override
    public void handle(IMoveEvent event) {
        super.handle(event);
        if (!moveable.canMove()) return;
        Integer deltaPosition = (moveable.getSpeedProperty().get());
        if (deltaPosition == null) return;
        Direction direction = moveable.getDirectionProperty().get();
        if (direction == null) return;
        EngineState engineState = moveable.getEngineStateProperty().get();
        if (engineState == null) return;
        if (engineState == EngineState.ENABLED) {
            List<Bounds> curBounds = moveable.getBoundsListProperty().get();
            List<Bounds> newBounds = new LinkedList<>();
            for (Bounds bounds : curBounds) {
                Point2D curPosition = new Point2D(bounds.getMinX(), bounds.getMinY());
                Point2D newPosition = newPositionMap.get(direction).apply(curPosition, deltaPosition);
                newBounds.add(
                        new BoundingBox(
                                newPosition.getX(),
                                newPosition.getY(),
                                bounds.getWidth(),
                                bounds.getHeight()));
            }
            confirmNewPosition(newBounds);
        }
    }

    protected void confirmNewPosition(List<Bounds> newBounds) {
        boolean isInWorldBounds = battleGround.isInWorldBounds(newBounds);
        switch (moveable.isMoveAllowed(isInWorldBounds)) {
            case ALLOW:
                allowChange(newBounds);
                break;
            case STOP:
                break;
            case DESTROY:
                destroyChange(newBounds);
                break;
        }
    }

    protected void allowChange(List<Bounds> newBounds) {
        moveable.getBoundsListProperty().clear();
        moveable.getBoundsListProperty().addAll(newBounds);
    }

    protected void destroyChange(List<Bounds> newBounds) {
        //TODO SEND APPROPRIATE EVENT????
    }

}


