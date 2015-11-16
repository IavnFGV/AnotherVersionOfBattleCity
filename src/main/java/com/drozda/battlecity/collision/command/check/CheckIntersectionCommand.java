package com.drozda.battlecity.collision.command.check;

import com.drozda.battlecity.collision.command.CollisionCommand;
import com.drozda.battlecity.unit.GameUnit;
import org.apache.commons.chain.Context;

/**
 * Created by GFH on 16.11.2015.
 */
public class CheckIntersectionCommand extends CollisionCommand<GameUnit, GameUnit> {
    @Override
    public boolean execute(Context context) throws Exception {
        GameUnit left = getLeftUnit(context);
        GameUnit right = getRightUnit(context);
        if (left.getBounds().intersects(right.getBounds())) {
            return CONTINUE_PROCESSING;
        }
        setSummary("No Intersection between objects", context);
        return PROCESSING_COMPLETE;
    }
}
