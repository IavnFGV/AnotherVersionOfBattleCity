package com.drozda.battlecity.unitx;


import com.drozda.battlecity.interfacesx.BasicStatePropertyModifiable;
import com.drozda.battlecity.interfacesx.GameUnitObjectPropertyModifier;
import com.drozda.battlecity.interfacesx.PauseStatePropertyModifiable;
import com.drozda.battlecity.modifierx.basicstate.onevent.BasicStateModifierByEvent;
import com.drozda.battlecity.modifierx.manager.SimpleObjectPropertyModifierManager;
import com.drozda.battlecity.unitx.enumeration.BasicState;
import com.drozda.battlecity.unitx.enumeration.PauseState;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

import java.util.List;

/**
 * Created by GFH on 26.09.2015.
 */
public abstract class GameUnitX implements BasicStatePropertyModifiable, PauseStatePropertyModifiable {


    protected BasicStateModifierByEvent basicStateModifierByEvent = new BasicStateModifierByEvent();
    protected BasicStatePropertyModifierManager basicStatePropertyModifierManager = new
            BasicStatePropertyModifierManager(BasicState.class);
    protected PauseStatePropertyModifierManager pauseStatePropertyModifierManager = new
            PauseStatePropertyModifierManager(PauseState.class);

    public GameUnitX() {
        pauseStatePropertyModifierManager.getObjectPropertyWrapper().setValue(PauseState.PAUSE);
        basicStatePropertyModifierManager.getObjectPropertyWrapper().setValue(BasicState.SLEEP);
        basicStatePropertyModifierManager.addObjectPropertyModifier(basicStateModifierByEvent);

    }

    @Override
    public ReadOnlyObjectProperty<BasicState> getBasicStateProperty() {
        return basicStatePropertyModifierManager.getObjectProperty();
    }

    @Override
    public <I extends GameUnitObjectPropertyModifier> List<GameUnitObjectPropertyModifier> getBasicStatePropertyModifiers(Class<I> interfaceType) {
        return basicStatePropertyModifierManager.getObjectPropertyModifiers(interfaceType);
    }

    @Override
    public ReadOnlyObjectProperty<PauseState> getPauseStateProperty() {
        return pauseStatePropertyModifierManager.getObjectProperty();
    }

    @Override
    public <I extends GameUnitObjectPropertyModifier> List<GameUnitObjectPropertyModifier> getPausePropertyModifiers(Class<I> interfaceType) {
        return pauseStatePropertyModifierManager.getObjectPropertyModifiers(interfaceType);
    }

    class BasicStatePropertyModifierManager extends SimpleObjectPropertyModifierManager<BasicState> {
        public BasicStatePropertyModifierManager(Class<BasicState> type) {
            super(type);
        }

        @Override
        protected ReadOnlyObjectWrapper<BasicState> getObjectPropertyWrapper() {
            return super.getObjectPropertyWrapper();
        }
    }

    class PauseStatePropertyModifierManager extends SimpleObjectPropertyModifierManager<PauseState> {
        public PauseStatePropertyModifierManager(Class<PauseState> type) {
            super(type);
        }

        @Override
        protected ReadOnlyObjectWrapper<PauseState> getObjectPropertyWrapper() {
            return super.getObjectPropertyWrapper();
        }
    }
}
