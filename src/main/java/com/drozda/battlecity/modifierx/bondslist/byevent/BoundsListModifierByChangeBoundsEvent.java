package com.drozda.battlecity.modifierx.bondslist.byevent;

import com.drozda.battlecity.eventx.IBoundsChangeEvent;
import com.drozda.battlecity.modifierx.ListPropertyModifierByEvent;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.geometry.Bounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 21.11.2015.
 */
public class BoundsListModifierByChangeBoundsEvent extends ListPropertyModifierByEvent<Bounds, IBoundsChangeEvent> {
    private static Logger log = LoggerFactory.getLogger(BoundsListModifierByChangeBoundsEvent.class);

    public BoundsListModifierByChangeBoundsEvent(ReadOnlyListWrapper<Bounds> listPropertyToChange, Class<IBoundsChangeEvent> eventObjectType) {
        super(listPropertyToChange, eventObjectType);
    }

    @Override
    public void handle(IBoundsChangeEvent event) {
        super.handle(event);
        log.debug("BoundsListModifierByChangeBoundsEvent.handle with parameters " + "event = [" + event + "]");
        synchronized (listPropertyToChange) {
            if (listPropertyToChange.get().containsAll(event.getNewBounds()) &&
                    (listPropertyToChange.get().size() == event.getNewBounds().size())) {
                log.debug("BoundsListModifierByChangeBoundsEvent.handle lists are equal");
                return;
            }
            log.debug("BoundsListModifierByChangeBoundsEvent.handle replacing bounds");
            listPropertyToChange.get().clear();
            listPropertyToChange.get().addAll(event.getNewBounds());
        }
    }
}
