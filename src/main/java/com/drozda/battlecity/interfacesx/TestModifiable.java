package com.drozda.battlecity.interfacesx;

import com.drozda.battlecity.unit.MoveableUnit;
import javafx.beans.property.ReadOnlyObjectProperty;

import java.util.List;

/**
 * Created by GFH on 19.11.2015.
 */
public interface TestModifiable/* extends ObjectPropertyModifiable<MoveableUnit.Direction>*/ {
    //    @Override
    ReadOnlyObjectProperty<MoveableUnit.Direction> getObjectProperty(MoveableUnit.Direction objectPropertyType);

    //    @Override
    <I extends GameUnitObjectPropertyModifier> List<GameUnitObjectPropertyModifier> getObjectPropertyModifiers(MoveableUnit.Direction objectPropertyType, Class<I> interfaceType);
}
