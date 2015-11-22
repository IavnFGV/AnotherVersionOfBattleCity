package com.drozda.battlecity.unitx;


import com.drozda.battlecity.eventx.ChangeEvent;
import com.drozda.battlecity.eventx.IBasicStateChangeEvent;
import com.drozda.battlecity.eventx.IBoundsChangeEvent;
import com.drozda.battlecity.eventx.IPauseStateChangeEvent;
import com.drozda.battlecity.interfacesx.*;
import com.drozda.battlecity.modifierx.basicstate.byevent.BasicStateModifierByBasicStateChangeEvent;
import com.drozda.battlecity.modifierx.boundslist.byevent.BoundsListModifierByChangeBoundsEvent;
import com.drozda.battlecity.modifierx.manager.SimpleListPropertyModifierManager;
import com.drozda.battlecity.modifierx.manager.SimpleObjectPropertyModifierManager;
import com.drozda.battlecity.modifierx.pausestate.byevent.PauseStateModifierByPauseStateChangeEvent;
import com.drozda.battlecity.modifierx.pausestate.byproperty.PauseStateModifierByPlaygroundStateProperty;
import com.drozda.battlecity.unitx.enumeration.BasicState;
import com.drozda.battlecity.unitx.enumeration.PauseState;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.geometry.Bounds;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

/**
 * Created by GFH on 26.09.2015.
 */
public abstract class GameUnitX implements BasicStatePropertyModifiable, PauseStatePropertyModifiable,
        EventHandler<ChangeEvent>, BoundsListModifiable {

    protected final BattleGround playground;
    protected BasicStatePropertyModifierManager basicStatePropertyModifierManager = new
            BasicStatePropertyModifierManager();
    protected PauseStatePropertyModifierManager pauseStatePropertyModifierManager;
    protected BoundsListModifierManager boundsListModifierManager = new BoundsListModifierManager();

    public GameUnitX(BattleGround playground) {
        this.playground = playground;
        // initialization in constructor because
        //inner class uses playground.stateProperty() from outer class
        pauseStatePropertyModifierManager = new PauseStatePropertyModifierManager();
    }

    public Bounds getBoundsFixedToGreed() {
        return getBoundsListProperty().get().get(0);
    }

    @Override
    public ReadOnlyListProperty<Bounds> getBoundsListProperty() {
        return boundsListModifierManager.getListProperty();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void handle(ChangeEvent event) {
        List<EventHandler> eventHandlers = getEventHandlers(event);
        if (!eventHandlers.isEmpty())
            synchronized (this) {
                eventHandlers.forEach(eventHandler -> eventHandler.handle(event));
            }
    }

    @Override
    public Class getEventObjectType() {
        throw new RuntimeException("getEventObjectType does not make sense for application");
    }

    /**
     * Need to be overrode in children for properly handling Events
     */
    protected List<EventHandler> getEventHandlers(EventObject eventObject) {
        List<EventHandler> eventHandlers = new ArrayList<>();
        eventHandlers.addAll(basicStatePropertyModifierManager.getObjectPropertyModifiersByEventObject(eventObject));
        eventHandlers.addAll(pauseStatePropertyModifierManager.getObjectPropertyModifiersByEventObject(eventObject));
        eventHandlers.addAll(boundsListModifierManager.getListPropertyModifiersByEventObject(eventObject));
        return eventHandlers;
    }

    @Override
    public String toString() {
        return "GameUnitX{" +
                "basicstate=" + getBasicStateProperty().get() +
                ", pauseState=" + getPauseStateProperty().get() +
                ", boundsListProperty= " + getBoundsListProperty().get() +
                '}';
    }

    @Override
    public ReadOnlyObjectProperty<BasicState> getBasicStateProperty() {
        return basicStatePropertyModifierManager.getObjectProperty();
    }

    @Override
    public ReadOnlyObjectProperty<PauseState> getPauseStateProperty() {
        return pauseStatePropertyModifierManager.getObjectProperty();
    }

    protected ReadOnlyListWrapper<Bounds> getReadOnlyBoundsListWrapper() {
        return boundsListModifierManager.readOnlyListWrapperProperty();
    }

    class BasicStatePropertyModifierManager extends SimpleObjectPropertyModifierManager<BasicState> {
        protected BasicStateModifierByBasicStateChangeEvent basicStateModifierByBasicStateChangeEvent =
                new BasicStateModifierByBasicStateChangeEvent(getObjectPropertyWrapper(), IBasicStateChangeEvent.class);

        public BasicStatePropertyModifierManager() {
            super();
            addObjectPropertyModifier(basicStateModifierByBasicStateChangeEvent);
            readOnlyObjectWrapper.setValue(BasicState.SLEEP);
        }
    }

    class PauseStatePropertyModifierManager extends SimpleObjectPropertyModifierManager<PauseState> {

        protected PauseStateModifierByPauseStateChangeEvent pauseStateModifierByPauseStateChangeEvent =
                new PauseStateModifierByPauseStateChangeEvent(getObjectPropertyWrapper(), IPauseStateChangeEvent.class);
        protected PauseStateModifierByPlaygroundStateProperty pauseStateModifierByPlaygroundStateProperty;

        public PauseStatePropertyModifierManager() {
            super();
            this.pauseStateModifierByPlaygroundStateProperty = new PauseStateModifierByPlaygroundStateProperty(null);
            // todo remake after refactoring battleground playground.stateProperty()
            addObjectPropertyModifier(pauseStateModifierByPauseStateChangeEvent);
            addObjectPropertyModifier(pauseStateModifierByPlaygroundStateProperty);
            readOnlyObjectWrapper.setValue(PauseState.PAUSE);
        }
    }

    class BoundsListModifierManager extends SimpleListPropertyModifierManager<Bounds> {
        protected BoundsListModifierByChangeBoundsEvent boundsListModifierByChangeBoundsEvent =
                new BoundsListModifierByChangeBoundsEvent(readOnlyListWrapper, IBoundsChangeEvent.class);

        public BoundsListModifierManager() {
            super();
            addListPropertyModifier(boundsListModifierByChangeBoundsEvent);
        }

        @Override
        protected ReadOnlyListWrapper<Bounds> readOnlyListWrapperProperty() {
            return super.readOnlyListWrapperProperty();
        }
    }
}
