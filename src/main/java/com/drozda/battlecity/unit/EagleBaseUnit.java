package com.drozda.battlecity.unit;

import javafx.geometry.Bounds;

import static java.util.Arrays.asList;

/**
 * Created by GFH on 12.11.2015.
 */
public class EagleBaseUnit extends GameUnit {
    public EagleBaseUnit(Bounds bounds) {
        super(bounds, asList(State.ACTIVE, State.EXPLODING, State.DEAD), null);
    }
}
