package com.drozda.battlecity.interfacesx;

import com.drozda.battlecity.unit.GameUnit;
import com.drozda.battlecity.unit.MoveableUnit;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

import java.util.List;

/**
 * Created by GFH on 19.11.2015.
 */
public class TestModifiableClass implements TestModifiable, TestModifiable1 {
    protected ReadOnlyObjectWrapper<MoveableUnit.Direction> testField = new ReadOnlyObjectWrapper<>();

    @Override
    public ReadOnlyObjectProperty<MoveableUnit.Direction> getObjectProperty(MoveableUnit.Direction objectPropertyType) {
        return null;
    }

    @Override
    public <I extends GameUnitObjectPropertyModifier> List<GameUnitObjectPropertyModifier> getObjectPropertyModifiers(MoveableUnit.Direction objectPropertyType, Class<I> interfaceType) {
        return null;
    }

    @Override
    public ReadOnlyObjectProperty<GameUnit.State> getObjectProperty(GameUnit.State objectPropertyType) {
        return null;
    }

    @Override
    public <I extends GameUnitObjectPropertyModifier> List<GameUnitObjectPropertyModifier> getObjectPropertyModifiers(GameUnit.State objectPropertyType, Class<I> interfaceType) {
        return null;
    }
}
