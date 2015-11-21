package com.drozda.battlecity.eventx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 21.11.2015.
 */
public class SpeedChangeEvent extends ChangeEvent implements ISpeedChangeEvent {

    private static Logger log = LoggerFactory.getLogger(SpeedChangeEvent.class);
    final int newSpeed;

    public SpeedChangeEvent(Object source, int newSpeed) {
        super(source);
        this.newSpeed = newSpeed;
        log.debug("SpeedChangeEvent.SpeedChangeEvent with parameters " + "source = [" + source +
                "], newSpeed = [" + newSpeed + "]");
    }

    @Override
    public int getNewSpeed() {
        return newSpeed;
    }
}
