package com.drozda.battlecity.eventx;

import com.drozda.battlecity.unitx.enumeration.BasicState;

/**
 * Created by GFH on 21.11.2015.
 */
public interface IBasicStateChangeEvent extends IChangeEvent {
    BasicState getNewBasicState();
}
