package com.drozda.battlecity.interfacesx;

import com.drozda.battlecity.eventx.IChangeEvent;

import java.util.EventListener;

/**
 * Created by GFH on 19.11.2015.
 */
public interface EventHandler<E extends IChangeEvent> extends EventListener {
    /**
     * Invoked when a specific event of the type for which this handler is
     * registered happens.
     *
     * @param event the event which occurred
     */
    void handle(E event);

    Class<E> getEventObjectType();
}