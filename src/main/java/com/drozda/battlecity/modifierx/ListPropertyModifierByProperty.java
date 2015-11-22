package com.drozda.battlecity.modifierx;

import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;

/**
 * Created by GFH on 21.11.2015.
 */
public abstract class ListPropertyModifierByProperty<T, S>
        extends ListPropertyModifier<S>
        implements com.drozda.battlecity.interfacesx.modifier.list.ListPropertyModifierByProperty<T, S> {

    protected ReadOnlyObjectProperty<T> propertyToListen;

    public ListPropertyModifierByProperty(ReadOnlyListWrapper<S> propertyToChange, ReadOnlyObjectProperty<T>
            propertyToListen) {
        super(propertyToChange);
        setPropertyToListen(propertyToListen);
    }

    @Override
    public <P extends ReadOnlyObjectProperty<T>> void setPropertyToListen(P propertyToListen) {
        this.propertyToListen = propertyToListen;
        propertyToListen.addListener(this);
    }
}
