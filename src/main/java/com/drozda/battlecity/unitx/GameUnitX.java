package com.drozda.battlecity.unitx;


import com.drozda.battlecity.interfacesx.BasicStatePropertyModifiable;
import com.drozda.battlecity.interfacesx.GameUnitObjectPropertyModifier;
import com.drozda.battlecity.interfacesx.PauseStatePropertyModifiable;
import com.drozda.battlecity.unitx.enumeration.BasicState;
import com.drozda.battlecity.unitx.enumeration.PauseState;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GFH on 26.09.2015.
 */
public abstract class GameUnitX implements BasicStatePropertyModifiable, PauseStatePropertyModifiable {

    protected ReadOnlyObjectWrapper<BasicState> basicStateReadOnlyObjectWrapper = new ReadOnlyObjectWrapper<>(BasicState.SLEEP);

    protected Map<Class<? extends GameUnitObjectPropertyModifier>, List<GameUnitObjectPropertyModifier>>
            modifierBasicStateMap = new HashMap<>();

    protected Map<Class<? extends GameUnitObjectPropertyModifier>, List<GameUnitObjectPropertyModifier>>
            modifierPauseStateMap = new HashMap<>();


    @Override
    public ReadOnlyObjectProperty<BasicState> getObjectProperty(BasicState objectPropertyType) {
        return basicStateReadOnlyObjectWrapper.getReadOnlyProperty();
    }

    @Override
    public <I extends GameUnitObjectPropertyModifier> List<GameUnitObjectPropertyModifier> getObjectPropertyModifiers(BasicState objectPropertyType, Class<I> interfaceType) {
        return modifierBasicStateMap.get(interfaceType);
    }

    @Override
    public ReadOnlyObjectProperty<PauseState> getObjectProperty(PauseState objectPropertyType) {
        return pa;
    }

    @Override
    public <I extends GameUnitObjectPropertyModifier> List<GameUnitObjectPropertyModifier> getObjectPropertyModifiers(PauseState objectPropertyType, Class<I> interfaceType) {
        return modifierPauseStateMap.get(interfaceType);
    }
}
