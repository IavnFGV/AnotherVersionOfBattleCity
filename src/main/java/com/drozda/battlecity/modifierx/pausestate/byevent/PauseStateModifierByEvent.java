package com.drozda.battlecity.modifierx.pausestate.byevent;

import com.drozda.battlecity.eventx.PauseStateChangeEvent;
import com.drozda.battlecity.modifierx.ObjectPropertyModifierByEvent;
import com.drozda.battlecity.unitx.enumeration.PauseState;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 20.11.2015.
 */
public class PauseStateModifierByEvent extends ObjectPropertyModifierByEvent<PauseState, PauseStateChangeEvent> {
    private static final Logger log = LoggerFactory.getLogger(PauseStateModifierByEvent.class);

    public PauseStateModifierByEvent(ReadOnlyObjectWrapper<PauseState> propertyToChange, Class<PauseStateChangeEvent> eventObjectType) {
        super(propertyToChange, eventObjectType);
    }

    @Override
    public void handle(PauseStateChangeEvent event) {
        super.handle(event);
        log.debug("PauseStateModifierByEvent.handle with parameters " + "event = [" + event + "]");
        synchronized (propertyToChange) {
            if (propertyToChange.get() != event.getNewPauseState()) {
                log.debug("PauseStateModifierByEvent.handle Changing value from [" + propertyToChange.get() + "] to  " +
                        "[" + event.getNewPauseState() + "]");
                propertyToChange.setValue(event.getNewPauseState());
            }
        }
    }
}
