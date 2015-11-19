package com.drozda.battlecity.interfacesx;

import com.drozda.battlecity.unit.GameUnit;
import javafx.beans.property.ReadOnlyObjectProperty;

import java.util.List;

/**
 * Created by GFH on 19.11.2015.
 */
public interface TestModifiable1/* extends ObjectPropertyModifiable<GameUnit.State>*/ {
    //    @Override
    ReadOnlyObjectProperty<GameUnit.State> getObjectProperty(GameUnit.State objectPropertyType);

    //    @Override
    <I extends GameUnitObjectPropertyModifier> List<GameUnitObjectPropertyModifier> getObjectPropertyModifiers(GameUnit.State objectPropertyType, Class<I> interfaceType);
}
