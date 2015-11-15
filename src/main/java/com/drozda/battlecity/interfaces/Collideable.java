package com.drozda.battlecity.interfaces;

import com.drozda.battlecity.unit.GameUnit;
import org.apache.commons.lang3.tuple.ImmutablePair;


/**
 * Created by GFH on 30.09.2015.
 */
public interface Collideable<T extends GameUnit> {
    ImmutablePair<CollideResult, CollideResult> collide(T gameUnit);

    /**
     * For example bullet and player tanks are active;
     * bonuses, tiles, enemies tanks are submissive))
     */
    boolean isActive();

    enum CollideResult {
        NOTHING,
        STATE_CHANGE,
        LIFE_DECREMENT
    }

}
