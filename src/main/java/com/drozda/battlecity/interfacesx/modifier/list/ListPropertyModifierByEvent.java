package com.drozda.battlecity.interfacesx.modifier.list;

import com.drozda.battlecity.eventx.IChangeEvent;
import com.drozda.battlecity.interfacesx.EventHandler;

/**
 * Created by GFH on 21.11.2015.
 */
public interface ListPropertyModifierByEvent<S, E extends IChangeEvent> extends
        ListPropertyModifier<S>,
        EventHandler<E> {
}
