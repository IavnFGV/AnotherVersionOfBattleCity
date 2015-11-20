package com.drozda.battlecity.modifierx.basicstate.byevent;

import com.drozda.battlecity.eventx.BasicStateChangeEvent;
import com.drozda.battlecity.modifierx.ObjectPropertyModifierByEvent;
import com.drozda.battlecity.unitx.enumeration.BasicState;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 20.11.2015.
 */
public class BasicStateModifierByEvent extends ObjectPropertyModifierByEvent<BasicState, BasicStateChangeEvent> {
    private static final Logger log = LoggerFactory.getLogger(BasicStateModifierByEvent.class);

    public BasicStateModifierByEvent(ReadOnlyObjectWrapper<BasicState> propertyToChange, Class<BasicStateChangeEvent> eventObjectType) {
        super(propertyToChange, eventObjectType);
    }

    @Override
    public void handle(BasicStateChangeEvent event) {
        super.handle(event);
        log.debug("BasicStateModifierByEvent.handle with parameters " + "event = [" + event + "]");
        synchronized (propertyToChange) {
            if (propertyToChange.get() != event.getNewBasicState()) {
                log.debug("BasicStateModifierByEvent.handle Changing value from [" + propertyToChange.get() + "] to  " +
                        "[" + event.getNewBasicState() + "]");
                propertyToChange.setValue(event.getNewBasicState());
            }
        }
    }

}
