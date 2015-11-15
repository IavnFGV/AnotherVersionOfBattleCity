package com.drozda.battlecity.interfaces;

import org.apache.commons.lang3.tuple.ImmutablePair;


/**
 * Created by GFH on 30.09.2015.
 */
public interface Collideable<T> {

    ImmutablePair<CollideResult, CollideResult> NOTHING_PAIR = new ImmutablePair<>(CollideResult
            .NOTHING, CollideResult.NOTHING);

    default ImmutablePair<CollideResult, CollideResult> activeCollide(T other) {
        return null;
    }

    CollideResult passiveCollide(T other);

    /**
     * For example bullet and player tanks are active;
     * bonuses, tiles, enemies tanks are submissive))
     */
    boolean isActive();

    /**
     * true if unit wants to participate in collision process
     *
     * @return
     */
    default boolean isTakingPartInCollisionProcess() {
        return true;
    }

    enum CollideResult {
        NOTHING,
        STATE_CHANGE,
        INNER_STATE_CHANGE,
        LIFE_DECREMENT
    }

}
