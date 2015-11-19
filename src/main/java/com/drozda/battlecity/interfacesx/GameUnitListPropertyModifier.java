package com.drozda.battlecity.interfacesx;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.ListChangeListener;

import java.util.EventObject;

/**
 * Created by GFH on 19.11.2015.
 */
public interface GameUnitListPropertyModifier extends GameUnitPropertyModifier {
    interface GameUnitListPropertyModifierByPropertyListening<T, P extends SimpleListProperty<T>>
            extends GameUnitListPropertyModifier, ListChangeListener<T> {
        @Override
        void onChanged(Change<? extends T> c);

        void setPropertyToListen(P propertyToListen);
    }

    interface GameUnitListPropertyModifierByEventListening<E extends EventObject> extends
            GameUnitListPropertyModifier,
            EventHandler<E> {
        @Override
        void handle(E event);
    }
}
