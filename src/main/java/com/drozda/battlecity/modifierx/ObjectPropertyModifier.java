package com.drozda.battlecity.modifierx;

import javafx.beans.property.ReadOnlyObjectWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 20.11.2015.
 */
public abstract class ObjectPropertyModifier<S> implements com.drozda.battlecity.interfacesx.ObjectPropertyModifier<S> {
    private static Logger log = LoggerFactory.getLogger(ObjectPropertyModifier.class);
    protected ReadOnlyObjectWrapper<S> propertyToChange;

    public void setPropertyToChange(ReadOnlyObjectWrapper<S> propertyToChange) {
        log.debug("ObjectPropertyModifier.setPropertyToChange with parameters " + "propertyToChange = [" + propertyToChange + "]");
        this.propertyToChange = propertyToChange;
    }

    @Override
    public String toString() {
        return "ObjectPropertyModifier{" +
                "propertyToChange=" + propertyToChange +
                '}';
    }
}
