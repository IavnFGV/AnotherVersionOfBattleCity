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
public class FinishEnemyBulletVsPlayerTank extends FinishCollisionCommand<BulletUnit, PlayerTankUnit> {

    @Override
    public boolean execute(Context context) throws Exception {
        super.execute(context);
        //no checking - it is last command in chain
        finalTransition(Collideable.CollisionProcessState.COMPLETED,
                Collideable.CollisionProcessState.COMPLETED,
                Collideable.CollisionResult.STATE_CHANGE,
                Collideable.CollisionResult.STATE_CHANGE,
                bulletUnit -> bulletUnit.setCurrentState(GameUnit.State.EXPLODING),
                tankUnit -> tankUnit.setCurrentState(GameUnit.State.EXPLODING));
        setSummary("Bullet is exploding. Playertank is Explodind", context);
        return PROCESSING_COMPLETE;
    }
}
