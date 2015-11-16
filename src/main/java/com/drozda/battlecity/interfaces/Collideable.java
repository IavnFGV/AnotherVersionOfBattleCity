package com.drozda.battlecity.interfaces;

import org.apache.commons.lang3.tuple.ImmutablePair;


/**
 * Created by GFH on 30.09.2015.
 */
public interface Collideable {

    ImmutablePair<CollisionResult, CollisionResult> NOTHING_PAIR = new ImmutablePair<>(CollisionResult
            .NOTHING, CollisionResult.NOTHING);


    /**
     * For example bullet and player tanks are active;
     * bonuses, tiles, enemies tanks are submissive))
     */
    default boolean isActive() {
        return true;
    }

    /**
     * true if unit wants to participate in collision process
     *
     * @return
     */
    default boolean isTakingPartInCollisionProcess() {
        return true;
    }

    default void startCollisionProcessing() {
        if (getCollisionProcessState() != CollisionProcessState.READY) {
            throw new InvalidCollisionProcessState(this.getClass().getName() +
                    " is in invalid state for starting CollisionProcessing");
        }
        setCollisionProcessState(CollisionProcessState.STARTED);
    }

    CollisionProcessState getCollisionProcessState();

    void setCollisionProcessState(CollisionProcessState collisionProcessState);

    default void finishCollisionProcessing() {
        if (getCollisionProcessState() == CollisionProcessState.READY) {
            throw new InvalidCollisionProcessState(this.getClass().getName() +
                    " is in invalid state for finishing CollisionProcessing");
        }
        if (getCollisionProcessState() == CollisionProcessState.PARTIALLY) {
            handlePartialCollisionState();
        }
        if ((getCollisionProcessState() == CollisionProcessState.STARTED) ||
                (getCollisionProcessState() == CollisionProcessState.COMPLETED)) {
            setCollisionProcessState(CollisionProcessState.READY);
        }
        if (getCollisionProcessState() != CollisionProcessState.READY) {
            throw new InvalidCollisionProcessState(this.getClass().getName() +
                    "cant finish CollisionProcessing. Maybe handlePartialCollisionState() is bad implemented");
        }
    }

    default void handlePartialCollisionState() {
        setCollisionProcessState(CollisionProcessState.COMPLETED);
    }

    enum CollisionProcessState {
        STARTED,
        PARTIALLY,
        COMPLETED,
        READY
    }

    enum CollisionResult {
        NOTHING,
        STATE_CHANGE,
        INNER_STATE_CHANGE,
        LIFE_DECREMENT
    }

    class InvalidCollisionProcessState extends RuntimeException {
        public InvalidCollisionProcessState(String message) {
            super(message);
        }
    }

}
