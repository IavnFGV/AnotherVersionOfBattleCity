package com.drozda.battlecity.collision.command;

import com.drozda.battlecity.collision.context.ContextEntries;
import com.drozda.battlecity.interfaces.Collideable;
import com.drozda.battlecity.unit.GameUnit;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

/**
 * Created by GFH on 16.11.2015.
 */
public abstract class CollisionCommand<L extends GameUnit, R extends GameUnit> implements Command {

    protected L getLeftUnit(Context context) {
        return (L) (context.get(ContextEntries.LEFT_GAMEUNIT));
    }

    protected R getRightUnit(Context context) {
        return (R) (context.get(ContextEntries.RIGHT_GAMEUNIT));
    }

    protected void setLeftCollisionProcessState(Collideable.CollisionProcessState state, Context context) {
        context.put(ContextEntries.LEFT_COLLISION_STATE, state);
    }

    protected void setRightCollisionProcessState(Collideable.CollisionProcessState state, Context context) {
        context.put(ContextEntries.RIGHT_COLLISION_STATE, state);
    }

    protected void setRightCollisionResult(Collideable.CollisionResult collisionResult, Context context) {
        context.put(ContextEntries.LEFT_COLLISION_RESULT, collisionResult);
    }

    protected void setLeftCollisionResult(Collideable.CollisionResult collisionResult, Context context) {
        context.put(ContextEntries.RIGHT_COLLISION_RESULT, collisionResult);
    }

    protected void setSummary(String summary, Context context) {
        context.put(ContextEntries.SUMMARY, summary);
    }

}
