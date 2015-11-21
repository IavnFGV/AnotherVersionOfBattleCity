package com.drozda.battlecity.eventx;

import com.drozda.battlecity.unitx.enumeration.Direction;

/**
 * Created by GFH on 21.11.2015.
 */
public interface IDirectionChangeEvent extends IChangeEvent {

    Direction getNewDirection();
}
