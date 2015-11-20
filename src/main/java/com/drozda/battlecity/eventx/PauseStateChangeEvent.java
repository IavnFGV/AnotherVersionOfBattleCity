package com.drozda.battlecity.eventx;

import com.drozda.battlecity.unitx.enumeration.PauseState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 20.11.2015.
 */
public class PauseStateChangeEvent extends ChangeEvent {
    private static final Logger log = LoggerFactory.getLogger(PauseStateChangeEvent.class);

    private final PauseState newPauseState;

    public PauseStateChangeEvent(Object source, PauseState newPauseState) {
        super(source);
        this.newPauseState = newPauseState;
        log.debug("PauseStateChangeEvent.PauseStateChangeEvent with parameters " + "source = [" + source + "], newPauseState = [" + newPauseState + "]");
    }

    public PauseState getNewPauseState() {
        return newPauseState;
    }

    @Override
    public String toString() {
        return "PauseStateChangeEvent{" +
                "newPauseState=" + newPauseState +
                "} " + super.toString();
    }
}
