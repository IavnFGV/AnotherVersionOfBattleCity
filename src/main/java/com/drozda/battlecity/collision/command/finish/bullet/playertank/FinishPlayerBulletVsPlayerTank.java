package com.drozda.battlecity.collision.command.finish.bullet.playertank;

import com.drozda.battlecity.collision.command.finish.FinishCollisionCommand;
import com.drozda.battlecity.interfaces.Collideable;
import com.drozda.battlecity.unit.BulletUnit;
import com.drozda.battlecity.unit.GameUnit;
import com.drozda.battlecity.unit.PlayerTankUnit;
import org.apache.commons.chain.Context;

/**
 * Created by GFH on 18.11.2015.
 */
public class FinishPlayerBulletVsPlayerTank extends FinishCollisionCommand<BulletUnit, PlayerTankUnit> {
    @Override
    public boolean execute(Context context) throws Exception {
        super.execute(context);
        if (left.getParent() instanceof PlayerTankUnit) {
            finalTransition(Collideable.CollisionProcessState.COMPLETED,
                    Collideable.CollisionProcessState.PARTIALLY,
                    Collideable.CollisionResult.STATE_CHANGE,
                    Collideable.CollisionResult.INNER_STATE_CHANGE,
                    bulletUnit -> bulletUnit.setCurrentState(GameUnit.State.DEAD),
                    tankUnit -> {
                        tankUnit.getFriendlyfireGift().takeToPocket(tankUnit);
                        tankUnit.setCollisionProcessState(Collideable.CollisionProcessState.PARTIALLY);
                    });
            setSummary("Bullet is DEAD. Playertank get friendlyfiregift", context);
            return PROCESSING_COMPLETE;
        }
        return CONTINUE_PROCESSING;
    }
}
