package com.drozda.battlecity.modifierx.basicstate.onevent;

import com.drozda.battlecity.eventx.BasicStateChangeEvent;
import com.drozda.battlecity.interfacesx.GameUnitObjectPropertyModifier;
import com.drozda.battlecity.unitx.enumeration.BasicState;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 20.11.2015.
 */
public class BasicStateModifierByEvent implements GameUnitObjectPropertyModifier
        .GameUnitObjectPropertyModifierByEvent<BasicState, BasicStateChangeEvent> {
    private static final Logger log = LoggerFactory.getLogger(BasicStateModifierByEvent.class);
    private ReadOnlyObjectWrapper<BasicState> propertyToChange;

    public BasicStateModifierByEvent(ReadOnlyObjectWrapper<BasicState> propertyToChange) {
        setPropertyToChange(propertyToChange);
    }

    @Override
    public void setPropertyToChange(ReadOnlyObjectWrapper<BasicState> propertyToChange) {
        log.debug("BasicStateModifierByEvent.setPropertyToChange with parameters " + "propertyToChange = [" + propertyToChange + "]");
        this.propertyToChange = propertyToChange;
    }

    public BasicStateModifierByEvent() {
    }

    @Override
    public void handle(BasicStateChangeEvent event) {
        if (propertyToChange == null) {
            throw new RuntimeException("BasicStateModifierByEvent. PropertyToChange can not be null");
        }
        log.debug("BasicStateModifierByEvent.handle with parameters " + "event = [" + event + "]");
        synchronized (propertyToChange) {
            if (propertyToChange.get() != event.getNewState()) {
                log.debug("BasicStateModifierByEvent.handle Changing value from [" + propertyToChange.get() + "] to  " +
                        "[" + event.getNewState() + "]");
                propertyToChange.setValue(event.getNewState());
            }
        }
    }
}
