package com.drozda.battlecity.collision.command.finish.bullet.bullet;

import com.drozda.battlecity.collision.command.finish.FinishCollisionCommand;
import com.drozda.battlecity.interfaces.Collideable;
import com.drozda.battlecity.unit.BulletUnit;
import com.drozda.battlecity.unit.GameUnit;
import org.apache.commons.chain.Context;

/**
 * Created by GFH on 16.11.2015.
 */
public class FinishBulletVsBulletCommand extends FinishCollisionCommand<BulletUnit, BulletUnit> {
    @Override
    public boolean execute(Context context) throws Exception {
        super.execute(context);

        //todo this checking is redundant because all variants are Checked in previous commands ???

//            if (left.getParent() instanceof PlayerTankUnit) {
//                if (right.getParent() instanceof PlayerTankUnit) {
        finalTransition(Collideable.CollisionProcessState.COMPLETED,
                Collideable.CollisionProcessState.COMPLETED,
                Collideable.CollisionResult.STATE_CHANGE,
                Collideable.CollisionResult.STATE_CHANGE,
                bulletUnit -> bulletUnit.setCurrentState(GameUnit.State.DEAD),
                bulletUnit1 -> bulletUnit1.setCurrentState(GameUnit.State.DEAD),
                context);
        setSummary("Bullets are dead", context);
        return PROCESSING_COMPLETE;
//                }
//            }
    }
}
