package com.drozda.battlecity.modifierx.enginestate.byevent;

import com.drozda.battlecity.eventx.IEngineStateChangeEvent;
import com.drozda.battlecity.modifierx.ObjectPropertyModifierByEvent;
import com.drozda.battlecity.unitx.enumeration.EngineState;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 22.11.2015.
 */
public class EngineStateModifierByEngineStateChangeEvent extends ObjectPropertyModifierByEvent<EngineState,
        IEngineStateChangeEvent> {
    private static Logger log = LoggerFactory.getLogger(EngineStateModifierByEngineStateChangeEvent.class);

    public EngineStateModifierByEngineStateChangeEvent(ReadOnlyObjectWrapper<EngineState> propertyToChange, Class<IEngineStateChangeEvent> eventObjectType) {
        super(propertyToChange, eventObjectType);
    }

    @Override
    public void handle(IEngineStateChangeEvent event) {
        super.handle(event);
        log.debug("EngineStateModifierByEngineStateChangeEvent.handle with parameters " + "event = [" + event + "]");
        synchronized (propertyToChange) {
            if (propertyToChange.get() != event.getNewEngineState()) {
                log.debug("EngineStateModifierByEngineStateChangeEvent.handle Changing value from [" + propertyToChange.get() + "] to  " +
                        "[" + event.getNewEngineState() + "]");
                propertyToChange.setValue(event.getNewEngineState());
            }
        }
    }
}
