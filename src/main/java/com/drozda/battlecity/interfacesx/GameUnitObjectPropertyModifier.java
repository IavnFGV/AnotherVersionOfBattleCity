package com.drozda.battlecity.interfacesx;


import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.EventObject;

/**
 * Created by GFH on 19.11.2015.
 */
public interface GameUnitObjectPropertyModifier<S> extends GameUnitPropertyModifier {
    void setPropertyToChange(ReadOnlyObjectWrapper<S> propertyToChange);

    interface GameUnitObjectPropertyModifierByProperty<T, S, P extends SimpleObjectProperty<T>>
            extends GameUnitObjectPropertyModifier<S>, ChangeListener<T> {
        @Override
        void changed(ObservableValue<? extends T> observable, T oldValue, T newValue);

        void setPropertyToListen(P propertyToListen);
    }

    interface GameUnitObjectPropertyModifierByEvent<S, E extends EventObject> extends GameUnitObjectPropertyModifier<S>,
            EventHandler<E> {
        @Override
        void handle(E event);
    }
}
