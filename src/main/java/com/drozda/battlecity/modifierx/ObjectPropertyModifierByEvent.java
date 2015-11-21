package com.drozda.battlecity.modifierx;

import com.drozda.battlecity.eventx.IChangeEvent;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 20.11.2015.
 */
public abstract class ObjectPropertyModifierByEvent<S, E extends IChangeEvent> extends ObjectPropertyModifier<S> implements com
        .drozda.battlecity.interfacesx.modifier.object.ObjectPropertyModifierByEvent<S, E> {
    private static Logger log = LoggerFactory.getLogger(ObjectPropertyModifierByEvent.class);
    protected final Class<E> eventObjectType;

    public ObjectPropertyModifierByEvent(ReadOnlyObjectWrapper<S> propertyToChange, Class<E> eventObjectType) {
        this.eventObjectType = eventObjectType;
        setPropertyToChange(propertyToChange);
    }

    @Override
    public void handle(E event) {
        if (propertyToChange == null) {
            throw new RuntimeException("ObjectPropertyModifierByEvent. PropertyToChange can not be null");
        }
    }

    @Override
    public Class<E> getEventObjectType() {
        return eventObjectType;
    }

}
