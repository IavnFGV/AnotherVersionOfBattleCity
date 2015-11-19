package com.drozda.battlecity.eventx;

import com.drozda.battlecity.unitx.enumeration.BasicState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EventObject;

/**
 * Created by GFH on 20.11.2015.
 */
public class BasicStateChangeEvent extends EventObject {
    private static final Logger log = LoggerFactory.getLogger(BasicStateChangeEvent.class);
    private final BasicState newState;

    public BasicStateChangeEvent(Object source, BasicState newState) {
        super(source);
        this.newState = newState;
        log.debug("BasicStateChangeEvent.BasicStateChangeEvent with parameters " + "source = [" + source + "], newState = [" + newState + "]");
    }

    public BasicState getNewState() {
        return newState;
    }
}
