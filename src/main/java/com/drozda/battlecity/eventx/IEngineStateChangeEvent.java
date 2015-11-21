package com.drozda.battlecity.eventx;

import com.drozda.battlecity.unitx.enumeration.EngineState;

/**
 * Created by GFH on 22.11.2015.
 */
public interface IEngineStateChangeEvent extends IChangeEvent {
    EngineState getNewEngineState();
}
