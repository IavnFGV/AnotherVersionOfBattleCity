package com.drozda.battlecity.collision.command.check.bullet.tank;

import com.drozda.battlecity.collision.command.CollisionCommand;
import com.drozda.battlecity.unit.BulletUnit;
import com.drozda.battlecity.unit.TankUnit;
import org.apache.commons.chain.Context;

/**
 * Created by GFH on 16.11.2015.
 */
public class CheckBulletTankIsParent extends CollisionCommand<BulletUnit, TankUnit> {
    @Override
    public boolean execute(Context context) throws Exception {
        BulletUnit left = getLeftUnit(context);
        TankUnit right = getRightUnit(context);
        if (left.getParent() == right) {
            setSummary("Bullet from this tank", context);
            return PROCESSING_COMPLETE;
        }
        return CONTINUE_PROCESSING;
    }
}
