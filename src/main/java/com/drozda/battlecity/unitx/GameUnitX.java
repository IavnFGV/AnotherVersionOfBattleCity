package com.drozda.battlecity.unitx;


import com.drozda.battlecity.eventx.BasicStateChangeEvent;
import com.drozda.battlecity.eventx.PauseStateChangeEvent;
import com.drozda.battlecity.interfaces.BattleGround;
import com.drozda.battlecity.interfacesx.BasicStatePropertyModifiable;
import com.drozda.battlecity.interfacesx.EventHandler;
import com.drozda.battlecity.interfacesx.PauseStatePropertyModifiable;
import com.drozda.battlecity.modifierx.basicstate.byevent.BasicStateModifierByEvent;
import com.drozda.battlecity.modifierx.manager.SimpleObjectPropertyModifierManager;
import com.drozda.battlecity.modifierx.pausestate.byevent.PauseStateModifierByEvent;
import com.drozda.battlecity.modifierx.pausestate.byproperty.PauseStateModifierByProperty;
import com.drozda.battlecity.unitx.enumeration.BasicState;
import com.drozda.battlecity.unitx.enumeration.PauseState;
import javafx.beans.property.ReadOnlyObjectProperty;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

/**
 * Created by GFH on 26.09.2015.
 */
public abstract class GameUnitX implements BasicStatePropertyModifiable, PauseStatePropertyModifiable,
        EventHandler {

    protected final BattleGround playground;
    protected BasicStatePropertyModifierManager basicStatePropertyModifierManager = new
            BasicStatePropertyModifierManager();
    protected PauseStatePropertyModifierManager pauseStatePropertyModifierManager;

    public GameUnitX(BattleGround playground) {
        this.playground = playground;
        pauseStatePropertyModifierManager = new PauseStatePropertyModifierManager();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void handle(EventObject event) {
        List<EventHandler> eventHandlers = getEventHandlers(event);
        if (!eventHandlers.isEmpty())
            synchronized (this) {
                eventHandlers.forEach(eventHandler -> eventHandler.handle(event));
            }
    }

    /**
     * Need to be overrode in children for properly handling Events
     */
    protected List<EventHandler> getEventHandlers(EventObject eventObject) {
        List<EventHandler> eventHandlers = new ArrayList<>();
        eventHandlers.addAll(basicStatePropertyModifierManager.getObjectPropertyModifiersByEventObject(eventObject));
        eventHandlers.addAll(pauseStatePropertyModifierManager.getObjectPropertyModifiersByEventObject(eventObject));
        return eventHandlers;
    }

    @Override
    public ReadOnlyObjectProperty<BasicState> getBasicStateProperty() {
        return basicStatePropertyModifierManager.getObjectProperty();
    }


    @Override
    public ReadOnlyObjectProperty<PauseState> getPauseStateProperty() {
        return pauseStatePropertyModifierManager.getObjectProperty();
    }

    class BasicStatePropertyModifierManager extends SimpleObjectPropertyModifierManager<BasicState> {
        protected BasicStateModifierByEvent basicStateModifierByEvent =
                new BasicStateModifierByEvent(getObjectPropertyWrapper(), BasicStateChangeEvent.class);

        public BasicStatePropertyModifierManager() {
            super();
            addObjectPropertyModifier(basicStateModifierByEvent);
            readOnlyObjectWrapper.setValue(BasicState.SLEEP);
        }
    }

    class PauseStatePropertyModifierManager extends SimpleObjectPropertyModifierManager<PauseState> {

        protected PauseStateModifierByEvent pauseStateModifierByEvent =
                new PauseStateModifierByEvent(getObjectPropertyWrapper(), PauseStateChangeEvent.class);
        protected PauseStateModifierByProperty pauseStateModifierByProperty;

        public PauseStatePropertyModifierManager() {
            super();
            this.pauseStateModifierByProperty = new PauseStateModifierByProperty(playground.stateProperty());
            addObjectPropertyModifier(pauseStateModifierByEvent);
            addObjectPropertyModifier(pauseStateModifierByProperty);
            readOnlyObjectWrapper.setValue(PauseState.PAUSE);
        }
    }
}
