package com.drozda.battlecity.collision.command.check;

import com.drozda.battlecity.collision.command.CollisionCommand;
import com.drozda.battlecity.unit.GameUnit;
import org.apache.commons.chain.Context;

/**
 * Created by GFH on 16.11.2015.
 */
public class CheckNotItselfCommand extends CollisionCommand<GameUnit, GameUnit> {
    @Override
    public boolean execute(Context context) throws Exception {
        GameUnit left = getLeftUnit(context);
        GameUnit right = getRightUnit(context);
        if (left == right) {
            setSummary("Objects are identical", context);
            return PROCESSING_COMPLETE;
        }
        return CONTINUE_PROCESSING;
    }
}
