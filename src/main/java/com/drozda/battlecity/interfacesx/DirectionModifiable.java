package com.drozda.battlecity.interfacesx;

import com.drozda.battlecity.unitx.enumeration.Direction;
import javafx.beans.property.ReadOnlyObjectProperty;

/**
 * Created by GFH on 21.11.2015.
 */
public interface DirectionModifiable /*extends ObjectPropertyModifiable<Direction> */ {
    //@Override
    ReadOnlyObjectProperty<Direction> getDirectionProperty();
}
