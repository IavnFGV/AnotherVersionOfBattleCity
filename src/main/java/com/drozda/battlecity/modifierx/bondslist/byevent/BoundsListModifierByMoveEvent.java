package com.drozda.battlecity.modifierx.bondslist.byevent;

import com.drozda.battlecity.eventx.IMoveEvent;
import com.drozda.battlecity.modifierx.ListPropertyModifierByEvent;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.geometry.Bounds;

/**
 * Created by GFH on 21.11.2015.
 */
public class BoundsListModifierByMoveEvent extends ListPropertyModifierByEvent<Bounds, IMoveEvent> {


    public BoundsListModifierByMoveEvent(ReadOnlyListWrapper<Bounds> listPropertyToChange,
                                         Class<IMoveEvent> eventObjectType,
                                         ) {
        super(listPropertyToChange, eventObjectType);
    }

    @Override
    public void handle(IMoveEvent event) {
        super.handle(event);
        if ((direction.get() == null) ||
                (engineState.get() == null) ||
                (speed.get() == null)) {
            throw new RuntimeException("BoundsListModifierByMoveEvent.handle. Not all fields are correct");
        }


    }
}

