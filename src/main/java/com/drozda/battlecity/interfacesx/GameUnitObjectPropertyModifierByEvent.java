package com.drozda.battlecity.interfacesx;

import java.util.EventObject;

/**
 * Created by GFH on 20.11.2015.
 */
public interface GameUnitObjectPropertyModifierByEvent<S, E extends EventObject> extends GameUnitObjectPropertyModifier<S>,
        EventHandler<E> {
    @Override
    void handle(E event);

    Class<E> getEventObjectType();
}
