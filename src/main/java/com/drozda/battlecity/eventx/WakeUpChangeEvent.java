package com.drozda.battlecity.eventx;

import com.drozda.battlecity.unitx.enumeration.BasicState;
import com.drozda.battlecity.unitx.enumeration.PauseState;
import javafx.geometry.Bounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by GFH on 21.11.2015.
 */
public class WakeUpChangeEvent extends ChangeEvent implements IBasicStateChangeEvent, IPauseStateChangeEvent, IBoundsChangeEvent {
    private static final Logger log = LoggerFactory.getLogger(WakeUpChangeEvent.class);

    private final PauseState newPauseState;
    private final BasicState newBasicState;
    private final List<Bounds> newBounds;

    public WakeUpChangeEvent(Object source, PauseState newPauseState, BasicState newBasicState, List<Bounds> newBounds) {
        super(source);
        log.debug("WakeUpChangeEvent.WakeUpChangeEvent with parameters " + "source = [" + source + "], newPauseState = ["
                + newPauseState + "], newBasicState = [" + newBasicState + "], newBounds = [" + newBounds + "]");
        this.newPauseState = newPauseState;
        this.newBasicState = newBasicState;
        this.newBounds = newBounds;
    }

    @Override
    public List<Bounds> getNewBounds() {
        return newBounds;
    }

    @Override
    public BasicState getNewBasicState() {
        return newBasicState;
    }

    @Override
    public PauseState getNewPauseState() {
        return newPauseState;
    }
}
