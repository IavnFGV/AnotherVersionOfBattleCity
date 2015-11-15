package com.drozda.battlecity.unit;

import com.drozda.battlecity.interfaces.Collideable;
import javafx.geometry.Bounds;
import org.apache.commons.lang3.tuple.ImmutablePair;

import static java.util.Arrays.asList;

/**
 * Created by GFH on 12.11.2015.
 */
public class EagleBaseUnit extends GameUnit implements Collideable {
    public EagleBaseUnit(Bounds bounds) {
        super(bounds, asList(State.ACTIVE, State.EXPLODING, State.DEAD), null);
    }

    @Override
    public ImmutablePair<CollideResult, CollideResult> collide(GameUnit gameUnit) {
        return null;
    }

    @Override
    public boolean isActive() {
        return false;
    }
}
