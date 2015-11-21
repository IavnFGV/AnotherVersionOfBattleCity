package com.drozda.battlecity.interfacesx.modifiable;

import javafx.beans.property.ReadOnlyListProperty;

/**
 * Unfortunately we cant inherit this generic interface because of type erase
 * so we use it only like a helper to generate concrete interfaces and then delete {@code extends ObjectPropertyModifiable<T, Class>}
 */
public interface ListPropertyModifiable<T> {
    ReadOnlyListProperty<T> getListProperty();
}
