package com.drozda.battlecity.interfacesx.modifier.list;

import com.drozda.battlecity.interfacesx.EventHandler;

import java.util.EventObject;

/**
 * Created by GFH on 21.11.2015.
 */
public interface ListPropertyModifierByEvent<S, E extends EventObject> extends
        ListPropertyModifier<S>,
        EventHandler<E> {
}
