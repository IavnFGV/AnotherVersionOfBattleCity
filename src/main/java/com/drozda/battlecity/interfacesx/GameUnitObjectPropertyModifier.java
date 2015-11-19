package com.drozda.battlecity.interfacesx;


import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.EventObject;

/**
 * Created by GFH on 19.11.2015.
 */
public interface GameUnitObjectPropertyModifier extends GameUnitPropertyModifier {

    interface GameUnitObjectPropertyModifierByPropertyListening<T, P extends SimpleObjectProperty<T>>
            extends GameUnitObjectPropertyModifier, ChangeListener<T> {
        @Override
        void changed(ObservableValue<? extends T> observable, T oldValue, T newValue);

        void setPropertyToListen(P propertyToListen);
    }

    interface GameUnitObjectPropertyModifierByEventListening<E extends EventObject> extends GameUnitObjectPropertyModifier,
            EventHandler<E> {
        @Override
        void handle(E event);
    }
}
