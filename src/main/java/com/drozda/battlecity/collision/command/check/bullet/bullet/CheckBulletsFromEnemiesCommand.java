package com.drozda.battlecity.collision.command.check.bullet.bullet;

import com.drozda.battlecity.collision.command.CollisionCommand;
import com.drozda.battlecity.unit.BulletUnit;
import com.drozda.battlecity.unit.EnemyTankUnit;
import org.apache.commons.chain.Context;

/**
 * Created by GFH on 16.11.2015.
 */
public class CheckBulletsFromEnemiesCommand extends CollisionCommand<BulletUnit, BulletUnit> {
    @Override
    public boolean execute(Context context) throws Exception {
        BulletUnit left = getLeftUnit(context);
        BulletUnit right = getRightUnit(context);
        if ((left.getParent() instanceof EnemyTankUnit) &&
                (right.getParent() instanceof EnemyTankUnit)) {
            setSummary("Bullets are from enemies", context);
            return PROCESSING_COMPLETE;
        }
        return CONTINUE_PROCESSING;
    }
}
