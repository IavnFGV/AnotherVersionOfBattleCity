package com.drozda.battlecity.modifierx;

import javafx.beans.property.ObjectProperty;

/**
 * Created by GFH on 21.11.2015.
 */
public abstract class ListPropertyModifierByProperty<T, S>
        extends ListPropertyModifier<S>
        implements com.drozda.battlecity.interfacesx.modifier.list.ListPropertyModifierByProperty<T, S> {

    protected ObjectProperty<T> propertyToListen;

    public ListPropertyModifierByProperty(ObjectProperty<T> propertyToListen) {
        setPropertyToListen(propertyToListen);
    }

    @Override
    public <P extends ObjectProperty<T>> void setPropertyToListen(P propertyToListen) {
        this.propertyToListen = propertyToListen;
    }
}
