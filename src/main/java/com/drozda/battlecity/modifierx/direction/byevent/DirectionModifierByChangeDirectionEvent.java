package com.drozda.battlecity.modifierx.direction.byevent;

import com.drozda.battlecity.eventx.IDirectionChangeEvent;
import com.drozda.battlecity.modifierx.ObjectPropertyModifierByEvent;
import com.drozda.battlecity.unitx.enumeration.Direction;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 21.11.2015.
 */
public class DirectionModifierByChangeDirectionEvent extends ObjectPropertyModifierByEvent<Direction,
        IDirectionChangeEvent> {
    private static Logger log = LoggerFactory.getLogger(DirectionModifierByChangeDirectionEvent.class);

    public DirectionModifierByChangeDirectionEvent(ReadOnlyObjectWrapper<Direction> propertyToChange, Class<IDirectionChangeEvent> eventObjectType) {
        super(propertyToChange, eventObjectType);
    }

    @Override
    public void handle(IDirectionChangeEvent event) {
        super.handle(event);
        log.debug("DirectionModifierByChangeDirectionEvent.handle with parameters " + "event = [" + event + "]");
        synchronized (propertyToChange) {
            if (propertyToChange.get() != event.getNewDirection()) {
                log.debug("DirectionModifierByChangeDirectionEvent.handle Changing value from [" + propertyToChange.get() + "] to  " +
                        "[" + event.getNewDirection() + "]");
                propertyToChange.setValue(event.getNewDirection());
            }
        }
    }
}
