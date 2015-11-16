package com.drozda.battlecity.collision.command.finish.bullet.enemytank;

import com.drozda.battlecity.collision.command.finish.FinishCollisionCommand;
import com.drozda.battlecity.interfaces.Collideable;
import com.drozda.battlecity.unit.BulletUnit;
import com.drozda.battlecity.unit.EnemyTankUnit;
import com.drozda.battlecity.unit.GameUnit;
import org.apache.commons.chain.Context;

/**
 * Created by GFH on 16.11.2015.
 */
public class FinishBulletVsEnemyTank extends FinishCollisionCommand<BulletUnit, EnemyTankUnit> {
    @Override
    public boolean execute(Context context) throws Exception {
        super.execute(context);
        int newLifes = right.getLifesAfterBullet(left.getType());
        if (newLifes > 0) {
            finalTransition(Collideable.CollisionProcessState.COMPLETED,
                    Collideable.CollisionProcessState.PARTIALLY,
                    Collideable.CollisionResult.STATE_CHANGE,
                    Collideable.CollisionResult.LIFE_DECREMENT,
                    bulletUnit -> bulletUnit.setCurrentState(GameUnit.State.EXPLODING),
                    enemyTankUnit -> enemyTankUnit.setLifes(newLifes),
                    context);
            setSummary("Bullet is exploding. EnemyTank lifes decremented", context);
//            return PROCESSING_COMPLETE;
        } else {
            finalTransition(Collideable.CollisionProcessState.COMPLETED,
                    Collideable.CollisionProcessState.COMPLETED,
                    Collideable.CollisionResult.STATE_CHANGE,
                    Collideable.CollisionResult.STATE_CHANGE,
                    bulletUnit -> bulletUnit.setCurrentState(GameUnit.State.EXPLODING),
                    enemyTankUnit -> {
                        enemyTankUnit.setLifes(0);
                        enemyTankUnit.setCurrentState(GameUnit.State.EXPLODING);
                    },
                    context);
            setSummary("Bullet is exploding. EnemyTank is exploding", context);
        }
        return PROCESSING_COMPLETE;
    }
}
