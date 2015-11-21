package com.drozda.battlecity.eventx;

import com.drozda.battlecity.unitx.enumeration.PauseState;

/**
 * Created by GFH on 21.11.2015.
 */
public interface IPauseStateChangeEvent extends IChangeEvent {
    PauseState getNewPauseState();
}
