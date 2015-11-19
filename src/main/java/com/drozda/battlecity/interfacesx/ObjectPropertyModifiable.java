package com.drozda.battlecity.interfacesx;

import javafx.beans.property.ReadOnlyObjectProperty;

import java.util.List;

/**
 * Unfortunately we cant inherit this generic interface because of type erase
 * so we use it only like a helper to generate concrete interfaces and then delete {@code extends ObjectPropertyModifiable<T, Class>}
 */
public interface ObjectPropertyModifiable<T> {

    ReadOnlyObjectProperty<T> getObjectProperty();

    <I extends GameUnitObjectPropertyModifier> List<GameUnitObjectPropertyModifier>
    getObjectPropertyModifiers(Class<I> interfaceType);

}
