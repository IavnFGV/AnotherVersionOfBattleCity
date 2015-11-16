package com.drozda.battlecity.collision.command.finish;

import com.drozda.battlecity.collision.command.CollisionCommand;
import com.drozda.battlecity.interfaces.Collideable;
import com.drozda.battlecity.unit.GameUnit;
import org.apache.commons.chain.Context;

import java.util.function.Consumer;

/**
 * Created by GFH on 16.11.2015.
 */
public abstract class FinishCollisionCommand<L extends GameUnit, R extends GameUnit> extends CollisionCommand<L, R> {
    protected L left;
    protected R right;

    @Override
    public boolean execute(Context context) throws Exception {
        initUnits(context);
        return false;
    }

    protected void initUnits(Context context) {
        this.left = getLeftUnit(context);
        this.right = getRightUnit(context);
    }

    protected void finalTransition(Collideable.CollisionProcessState leftCPS,
                                   Collideable.CollisionProcessState rightCPS,
                                   Collideable.CollisionResult leftCR,
                                   Collideable.CollisionResult rightCR,
                                   Consumer<L> leftConsumer,
                                   Consumer<R> rightConsumer,
                                   Context context) {
        setLeftCollisionProcessState(leftCPS, context);
        setRightCollisionProcessState(rightCPS, context);
        setLeftCollisionResult(leftCR, context);
        setRightCollisionResult(rightCR, context);
        leftConsumer.accept(left);
        rightConsumer.accept(right);
    }

    @Override
    protected void setLeftCollisionProcessState(Collideable.CollisionProcessState state, Context context) {
        super.setLeftCollisionProcessState(state, context);
        left.setCollisionProcessState(state);
    }

    @Override
    protected void setRightCollisionProcessState(Collideable.CollisionProcessState state, Context context) {
        super.setRightCollisionProcessState(state, context);
        right.setCollisionProcessState(state);
    }

}
