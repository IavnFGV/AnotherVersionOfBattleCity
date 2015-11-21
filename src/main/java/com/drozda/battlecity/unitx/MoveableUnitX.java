package com.drozda.battlecity.unitx;

import com.drozda.battlecity.eventx.IDirectionChangeEvent;
import com.drozda.battlecity.eventx.IEngineStateChangeEvent;
import com.drozda.battlecity.eventx.IMoveEvent;
import com.drozda.battlecity.eventx.ISpeedChangeEvent;
import com.drozda.battlecity.interfaces.BattleGround;
import com.drozda.battlecity.interfacesx.DirectionModifiable;
import com.drozda.battlecity.interfacesx.EngineStateModifiable;
import com.drozda.battlecity.interfacesx.EventHandler;
import com.drozda.battlecity.interfacesx.SpeedModifiable;
import com.drozda.battlecity.modifierx.bondslist.byevent.BoundsListModifierByMoveEvent;
import com.drozda.battlecity.modifierx.direction.byevent.DirectionModifierByChangeDirectionEvent;
import com.drozda.battlecity.modifierx.enginestate.byevent.EngineStateModifierByEngineStateChangeEvent;
import com.drozda.battlecity.modifierx.manager.SimpleObjectPropertyModifierManager;
import com.drozda.battlecity.modifierx.speed.byevent.SpeedModifierBySpeedChangeEvent;
import com.drozda.battlecity.unitx.enumeration.Direction;
import com.drozda.battlecity.unitx.enumeration.EngineState;
import javafx.beans.property.ReadOnlyObjectProperty;

import java.util.EventObject;
import java.util.List;

/**
 * Created by GFH on 21.11.2015.
 */
public class MoveableUnitX extends GameUnitX implements DirectionModifiable, SpeedModifiable,
        EngineStateModifiable {
    protected DirectionPropertyModifierManager directionPropertyModifierManager;
    protected SpeedModifierManager speedModifierManager;
    protected EngineStateModifierManager engineStateModifierManager;

    // must be added to correct manager
    protected BoundsListModifierByMoveEvent boundsListModifierByMoveEvent;

    public MoveableUnitX(BattleGround playground, Direction startDirection) {
        super(playground);
        directionPropertyModifierManager = new DirectionPropertyModifierManager(startDirection);
        speedModifierManager = new SpeedModifierManager();
        engineStateModifierManager = new EngineStateModifierManager(EngineState.DISABLED);

        boundsListModifierByMoveEvent = new BoundsListModifierByMoveEvent(boundsListModifierManager
                .readOnlyListWrapperProperty(), IMoveEvent.class, getDirectionProperty(), getEngineStateProperty(),
                getSpeedProperty());

        // must be added to correct manager
        boundsListModifierManager.addListPropertyModifier(boundsListModifierByMoveEvent);
    }

    @Override
    public ReadOnlyObjectProperty<Direction> getDirectionProperty() {
        return directionPropertyModifierManager.getObjectProperty();
    }

    @Override
    public ReadOnlyObjectProperty<EngineState> getEngineStateProperty() {
        return engineStateModifierManager.getObjectProperty();
    }

    @Override
    public ReadOnlyObjectProperty<Integer> getSpeedProperty() {
        return speedModifierManager.getObjectProperty();
    }

    @Override
    protected List<EventHandler> getEventHandlers(EventObject eventObject) {
        List<EventHandler> eventHandlers = super.getEventHandlers(eventObject);
        eventHandlers.addAll(directionPropertyModifierManager.getObjectPropertyModifiersByEventObject(eventObject));
        eventHandlers.addAll(speedModifierManager.getObjectPropertyModifiersByEventObject(eventObject));
        eventHandlers.addAll(engineStateModifierManager.getObjectPropertyModifiersByEventObject(eventObject));
        return eventHandlers;
    }

    @Override
    public String toString() {
        return "MoveableUnitX{" +
                "directionProperty=" + getDirectionProperty().get() +
                ", speedProperty=" + getSpeedProperty().get() +
                ", engineProperty=" + getEngineStateProperty().get() +
                "} " + super.toString();
    }

    class DirectionPropertyModifierManager extends SimpleObjectPropertyModifierManager<Direction> {
        protected DirectionModifierByChangeDirectionEvent directionModifierByChangeDirectionEvent =
                new DirectionModifierByChangeDirectionEvent(readOnlyObjectWrapper, IDirectionChangeEvent.class);

        public DirectionPropertyModifierManager(Direction startDirection) {
            addObjectPropertyModifier(directionModifierByChangeDirectionEvent);
            readOnlyObjectWrapper.setValue(startDirection);
        }
    }

    class SpeedModifierManager extends SimpleObjectPropertyModifierManager<Integer> {
        protected SpeedModifierBySpeedChangeEvent speedModifierBySpeedChangeEvent =
                new SpeedModifierBySpeedChangeEvent(readOnlyObjectWrapper, ISpeedChangeEvent.class);

        public SpeedModifierManager() {
            addObjectPropertyModifier(speedModifierBySpeedChangeEvent);
            readOnlyObjectWrapper.setValue(23); //TODO REPLACE AFTER TESTING
        }
    }

    class EngineStateModifierManager extends SimpleObjectPropertyModifierManager<EngineState> {
        protected EngineStateModifierByEngineStateChangeEvent engineStateModifierByEngineStateChangeEvent =
                new EngineStateModifierByEngineStateChangeEvent(getObjectPropertyWrapper(), IEngineStateChangeEvent
                        .class);

        public EngineStateModifierManager(EngineState startEngineState) {
            addObjectPropertyModifier(engineStateModifierByEngineStateChangeEvent);
            readOnlyObjectWrapper.setValue(startEngineState);
        }
    }
}
