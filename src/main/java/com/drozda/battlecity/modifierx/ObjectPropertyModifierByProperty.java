package com.drozda.battlecity.modifierx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;

/**
 * Created by GFH on 20.11.2015.
 */
public abstract class ObjectPropertyModifierByProperty<T, S>
        extends ObjectPropertyModifier<S>
        implements com.drozda.battlecity.interfacesx.modifier.object.ObjectPropertyModifierByProperty<T, S> {

    protected ReadOnlyObjectProperty<T> propertyToListen;

    public ObjectPropertyModifierByProperty(ObjectProperty<T> propertyToListen) {
        setPropertyToListen(propertyToListen);
    }

    @Override
    public <P extends ReadOnlyObjectProperty<T>> void setPropertyToListen(P propertyToListen) {
        this.propertyToListen = propertyToListen;
        propertyToListen.addListener(this);
    }
}
