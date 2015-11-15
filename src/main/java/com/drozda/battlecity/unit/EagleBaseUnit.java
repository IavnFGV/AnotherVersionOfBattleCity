package com.drozda.battlecity.unit;

import com.drozda.battlecity.interfaces.Collideable;
import javafx.geometry.Bounds;
import org.apache.commons.lang3.tuple.ImmutablePair;

import static java.util.Arrays.asList;

/**
 * Created by GFH on 12.11.2015.
 */
public class EagleBaseUnit extends GameUnit implements Collideable<BulletUnit> {
    public EagleBaseUnit(Bounds bounds) {
        super(bounds, asList(State.ACTIVE, State.EXPLODING, State.DEAD), null);
    }

    @Override
    public ImmutablePair<CollideResult, CollideResult> activeCollide(BulletUnit other) {
        return null;
    }

    @Override
    public CollideResult passiveCollide(BulletUnit other) {
        this.setCurrentState(State.EXPLODING);
        return CollideResult.STATE_CHANGE;
    }

    @Override
    public boolean isActive() {
        return false;
    }
}
