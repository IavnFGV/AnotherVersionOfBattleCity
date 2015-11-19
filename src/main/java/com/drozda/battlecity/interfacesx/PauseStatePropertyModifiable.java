package com.drozda.battlecity.interfacesx;

import com.drozda.battlecity.unitx.enumeration.PauseState;
import javafx.beans.property.ReadOnlyObjectProperty;

import java.util.List;

/**
 * Created by GFH on 19.11.2015.
 */
public interface PauseStatePropertyModifiable/* extends ObjectPropertyModifiable<PauseState>*/ {
    //    @Override
    ReadOnlyObjectProperty<PauseState> getObjectProperty(PauseState objectPropertyType);

    //    @Override
    <I extends GameUnitObjectPropertyModifier> List<GameUnitObjectPropertyModifier> getObjectPropertyModifiers(PauseState objectPropertyType, Class<I> interfaceType);
}
