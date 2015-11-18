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
    protected Context context;

    @Override
    public boolean execute(Context context) throws Exception {
        initFields(context);
        return false;
    }

    protected void initFields(Context context) {
        this.left = getLeftUnit(context);
        this.right = getRightUnit(context);
        this.context = context;
    }

    protected void finalTransition(Collideable.CollisionProcessState leftCPS,
                                   Collideable.CollisionProcessState rightCPS,
                                   Collideable.CollisionResult leftCR,
                                   Collideable.CollisionResult rightCR,
                                   Consumer<L> leftConsumer,
                                   Consumer<R> rightConsumer) {
        setLeftCollisionProcessState(leftCPS, this.context);
        setRightCollisionProcessState(rightCPS, this.context);
        setLeftCollisionResult(leftCR, this.context);
        setRightCollisionResult(rightCR, this.context);
        leftConsumer.accept(this.left);
        rightConsumer.accept(this.right);
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
