package com.drozda.battlecity.modifierx.speed.byevent;

import com.drozda.battlecity.eventx.ISpeedChangeEvent;
import com.drozda.battlecity.modifierx.ObjectPropertyModifierByEvent;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 21.11.2015.
 */
public class SpeedModifierBySpeedChangeEvent extends ObjectPropertyModifierByEvent<Integer,
        ISpeedChangeEvent> {
    private static Logger log = LoggerFactory.getLogger(SpeedModifierBySpeedChangeEvent.class);

    public SpeedModifierBySpeedChangeEvent(ReadOnlyObjectWrapper<Integer> propertyToChange, Class<ISpeedChangeEvent> eventObjectType) {
        super(propertyToChange, eventObjectType);

    }

    @Override
    public void handle(ISpeedChangeEvent event) {
        super.handle(event);
        log.debug("SpeedModifierBySpeedChangeEvent.handle with parameters " + "event = [" + event + "]");
        synchronized (propertyToChange) {
            if (propertyToChange.get() != event.getNewSpeed()) {
                log.debug("SpeedModifierBySpeedChangeEvent.handle  Changing value from [" + propertyToChange.get() + "] to  " +
                        "[" + event.getNewSpeed() + "]");
                propertyToChange.setValue(event.getNewSpeed());
            }
        }
    }
}
