package com.drozda.battlecity.eventx;

import com.drozda.battlecity.unitx.enumeration.BasicState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 20.11.2015.
 */
public class BasicStateChangeEvent extends ChangeEvent {
    private static final Logger log = LoggerFactory.getLogger(BasicStateChangeEvent.class);
    private final BasicState newBasicState;

    public BasicStateChangeEvent(Object source, BasicState newBasicState) {
        super(source);
        this.newBasicState = newBasicState;
        log.debug("BasicStateChangeEvent.BasicStateChangeEvent with parameters " + "source = [" + source + "], newBasicState = [" + newBasicState + "]");
    }

    public BasicState getNewBasicState() {
        return newBasicState;
    }

    @Override
    public String toString() {
        return "BasicStateChangeEvent{" +
                "newBasicState=" + newBasicState +
                "} " + super.toString();
    }
}
