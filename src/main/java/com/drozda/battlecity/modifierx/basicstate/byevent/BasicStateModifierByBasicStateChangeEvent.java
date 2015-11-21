package com.drozda.battlecity.modifierx.basicstate.byevent;

import com.drozda.battlecity.eventx.IBasicStateChangeEvent;
import com.drozda.battlecity.modifierx.ObjectPropertyModifierByEvent;
import com.drozda.battlecity.unitx.enumeration.BasicState;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 20.11.2015.
 */
public class BasicStateModifierByBasicStateChangeEvent extends ObjectPropertyModifierByEvent<BasicState,
        IBasicStateChangeEvent> {
    private static final Logger log = LoggerFactory.getLogger(BasicStateModifierByBasicStateChangeEvent.class);

    public BasicStateModifierByBasicStateChangeEvent(ReadOnlyObjectWrapper<BasicState> propertyToChange, Class<IBasicStateChangeEvent> eventObjectType) {
        super(propertyToChange, eventObjectType);
    }

    @Override
    public void handle(IBasicStateChangeEvent event) {
        super.handle(event);
        log.debug("BasicStateModifierByBasicStateChangeEvent.handle with parameters " + "event = [" + event + "]");
        synchronized (propertyToChange) {
            if (propertyToChange.get() != event.getNewBasicState()) {
                log.debug("BasicStateModifierByBasicStateChangeEvent.handle Changing value from [" + propertyToChange.get() + "] to  " +
                        "[" + event.getNewBasicState() + "]");
                propertyToChange.setValue(event.getNewBasicState());
            }
        }
    }

}
