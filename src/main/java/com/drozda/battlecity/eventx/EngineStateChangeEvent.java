package com.drozda.battlecity.eventx;

import com.drozda.battlecity.unitx.enumeration.EngineState;

/**
 * Created by GFH on 22.11.2015.
 */
public class EngineStateChangeEvent extends ChangeEvent implements IEngineStateChangeEvent {

    final EngineState newEngineState;

    public EngineStateChangeEvent(Object source, EngineState newEngineState) {
        super(source);
        this.newEngineState = newEngineState;
    }

    @Override
    public EngineState getNewEngineState() {
        return newEngineState;
    }
}
