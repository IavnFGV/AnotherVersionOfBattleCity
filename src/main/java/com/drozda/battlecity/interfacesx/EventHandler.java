package com.drozda.battlecity.interfacesx;

import java.util.EventListener;
import java.util.EventObject;

/**
 * Created by GFH on 19.11.2015.
 */
@FunctionalInterface
public interface EventHandler<T extends EventObject> extends EventListener {
    /**
     * Invoked when a specific event of the type for which this handler is
     * registered happens.
     *
     * @param event the event which occurred
     */
    void handle(T event);
}