package com.drozda.battlecity.eventx;

import com.drozda.battlecity.unitx.enumeration.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 21.11.2015.
 */
public class DirectionChangeEvent extends ChangeEvent implements IDirectionChangeEvent {
    private static final Logger log = LoggerFactory.getLogger(DirectionChangeEvent.class);

    private final Direction newDirection;

    public DirectionChangeEvent(Object source, Direction newDirection) {
        super(source);
        this.newDirection = newDirection;
        log.debug("DirectionChangeEvent.DirectionChangeEvent with parameters " + "source = [" + source
                + "], newDirection = [" + newDirection + "]");
    }

    @Override
    public Direction getNewDirection() {
        return newDirection;
    }
}
