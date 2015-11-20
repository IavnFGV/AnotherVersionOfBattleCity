package com.drozda.battlecity.modifierx;

import javafx.beans.property.ObjectProperty;

/**
 * Created by GFH on 20.11.2015.
 */
public abstract class ObjectPropertyModifierByProperty<T, S>
        extends ObjectPropertyModifier<S>
        implements com.drozda.battlecity.interfacesx.ObjectPropertyModifierByProperty<T, S> {

    protected ObjectProperty<T> propertyToListen;

    public ObjectPropertyModifierByProperty(ObjectProperty<T> propertyToListen) {
        setPropertyToListen(propertyToListen);
    }

    @Override
    public <P extends ObjectProperty<T>> void setPropertyToListen(P propertyToListen) {
        this.propertyToListen = propertyToListen;
        propertyToListen.addListener(this);
    }
}
