package com.drozda.battlecity.eventx;

import java.util.EventObject;

/**
 * Created by GFH on 20.11.2015.
 */
public class ChangeEvent extends EventObject {
    public ChangeEvent(Object source) {
        super(source);
    }

    @Override
    public String toString() {
        return "ChangeEvent{} " + super.toString();
    }
}
