package com.drozda.battlecity.interfacesx.modifier.object;

import javafx.beans.property.ListProperty;
import javafx.collections.ListChangeListener;

/**
 * Created by GFH on 21.11.2015.
 */
public interface ObjectPropertyModifierByListProperty<T, S>
        extends ObjectPropertyModifier<S>, ListChangeListener<T> {
    <P extends ListProperty<T>> void setListPropertyToListen(P propertyToListen);
}