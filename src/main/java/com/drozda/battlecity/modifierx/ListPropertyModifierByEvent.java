package com.drozda.battlecity.modifierx;

import com.drozda.battlecity.eventx.IChangeEvent;
import javafx.beans.property.ReadOnlyListWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 21.11.2015.
 */
public abstract class ListPropertyModifierByEvent<S, E extends IChangeEvent> extends ListPropertyModifier<S> implements
        com.drozda.battlecity.interfacesx.modifier.list.ListPropertyModifierByEvent<S, E> {
    private static Logger log = LoggerFactory.getLogger(ObjectPropertyModifierByEvent.class);
    protected final Class<E> eventObjectType;

    public ListPropertyModifierByEvent(ReadOnlyListWrapper<S> listPropertyToChange, Class<E> eventObjectType) {
        super(listPropertyToChange);
        this.eventObjectType = eventObjectType;
    }

    @Override
    public void handle(E event) {
        if (listPropertyToChange == null) {
            throw new RuntimeException("ListPropertyModifierByEvent. PropertyToChange can not be null");
        }
    }

    @Override
    public Class<E> getEventObjectType() {
        return eventObjectType;
    }
}
