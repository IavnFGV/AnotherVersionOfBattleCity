package com.drozda.battlecity.collision.command.check.bullet.bullet;

import com.drozda.battlecity.collision.command.CollisionCommand;
import com.drozda.battlecity.unit.BulletUnit;
import org.apache.commons.chain.Context;

/**
 * Created by GFH on 16.11.2015.
 */
public class CheckBulletsOneParentCommand extends CollisionCommand<BulletUnit, BulletUnit> {
    @Override
    public boolean execute(Context context) throws Exception {
        BulletUnit left = getLeftUnit(context);
        BulletUnit right = getRightUnit(context);
        if (left.getParent() == right.getParent()) {
            setSummary("Bullets have one parent", context);
            return PROCESSING_COMPLETE;
        }
        return CONTINUE_PROCESSING;
    }
}
