package com.drozda.battlecity.interfacesx.modifier.object;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Created by GFH on 20.11.2015.
 */
public interface ObjectPropertyModifierByProperty<T, S>
        extends ObjectPropertyModifier<S>, ChangeListener<T> {
    @Override
    void changed(ObservableValue<? extends T> observable, T oldValue, T newValue);

    <P extends ReadOnlyObjectProperty<T>> void setPropertyToListen(P propertyToListen);
}
