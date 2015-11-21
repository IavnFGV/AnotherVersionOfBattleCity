package com.drozda.battlecity.interfacesx.modifier.object;

import com.drozda.battlecity.eventx.IChangeEvent;
import com.drozda.battlecity.interfacesx.EventHandler;

/**
 * Created by GFH on 20.11.2015.
 */
public interface ObjectPropertyModifierByEvent<S, E extends IChangeEvent> extends
        ObjectPropertyModifier<S>,
        EventHandler<E> {
}
