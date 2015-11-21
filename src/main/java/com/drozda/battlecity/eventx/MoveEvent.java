package com.drozda.battlecity.eventx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EventObject;

/**
 * Created by GFH on 21.11.2015.
 */
public class MoveEvent extends EventObject implements IMoveEvent {
    private static final Logger log = LoggerFactory.getLogger(MoveEvent.class);

    private final long deltaTime;

    public MoveEvent(Object source, long deltaTime) {
        super(source);
        this.deltaTime = deltaTime;
        log.debug("MoveEvent.MoveEvent with parameters " + "source = [" + source + "], deltaTime = [" + deltaTime + "]");
    }

    @Override
    public long getDeltaTime() {
        return deltaTime;
    }

    @Override
    public String toString() {
        return "MoveEvent{" +
                "deltaTime=" + deltaTime +
                "} " + super.toString();
    }
}
