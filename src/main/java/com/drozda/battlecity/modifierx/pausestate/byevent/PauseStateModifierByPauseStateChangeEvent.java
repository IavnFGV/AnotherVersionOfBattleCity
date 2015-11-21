package com.drozda.battlecity.modifierx.pausestate.byevent;

import com.drozda.battlecity.eventx.IPauseStateChangeEvent;
import com.drozda.battlecity.modifierx.ObjectPropertyModifierByEvent;
import com.drozda.battlecity.unitx.enumeration.PauseState;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 20.11.2015.
 */
public class PauseStateModifierByPauseStateChangeEvent extends ObjectPropertyModifierByEvent<PauseState,
        IPauseStateChangeEvent> {
    private static final Logger log = LoggerFactory.getLogger(PauseStateModifierByPauseStateChangeEvent.class);


    public PauseStateModifierByPauseStateChangeEvent(ReadOnlyObjectWrapper<PauseState> propertyToChange, Class<IPauseStateChangeEvent> eventObjectType) {
        super(propertyToChange, eventObjectType);
    }

    @Override
    public void handle(IPauseStateChangeEvent event) {
        super.handle(event);
        log.debug("PauseStateModifierByPauseStateChangeEvent.handle with parameters " + "event = [" + event + "]");
        synchronized (propertyToChange) {
            if (propertyToChange.get() != event.getNewPauseState()) {
                log.debug("PauseStateModifierByPauseStateChangeEvent.handle Changing value from [" + propertyToChange.get() + "] to  " +
                        "[" + event.getNewPauseState() + "]");
                propertyToChange.setValue(event.getNewPauseState());
            }
        }
    }
}
