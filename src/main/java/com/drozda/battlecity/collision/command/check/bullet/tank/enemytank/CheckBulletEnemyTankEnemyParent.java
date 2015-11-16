package com.drozda.battlecity.collision.command.check.bullet.tank.enemytank;

import com.drozda.battlecity.collision.command.CollisionCommand;
import com.drozda.battlecity.unit.BulletUnit;
import com.drozda.battlecity.unit.EnemyTankUnit;
import org.apache.commons.chain.Context;

/**
 * Created by GFH on 16.11.2015.
 */
public class CheckBulletEnemyTankEnemyParent extends CollisionCommand<BulletUnit, EnemyTankUnit> {
    @Override
    public boolean execute(Context context) throws Exception {
        BulletUnit left = getLeftUnit(context);
        EnemyTankUnit right = getRightUnit(context);
        if (left.getParent() instanceof EnemyTankUnit) {
            setSummary("Bullet from another enemy", context);
            return PROCESSING_COMPLETE;
        }
        return CONTINUE_PROCESSING;
    }
}
