package com.drozda.battlecity.interfacesx;

import java.util.EventObject;

/**
 * Created by GFH on 20.11.2015.
 */
public interface ObjectPropertyModifierByEvent<S, E extends EventObject> extends
        ObjectPropertyModifier<S>,
        EventHandler<E> {
}
