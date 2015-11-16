package com.drozda.battlecity.collision.command.check;

import com.drozda.battlecity.collision.command.CollisionCommand;
import com.drozda.battlecity.unit.GameUnit;
import org.apache.commons.chain.Context;

/**
 * Created by GFH on 16.11.2015.
 */
public class CheckTypesCommand extends CollisionCommand<GameUnit, GameUnit> {
    final Class lClass;
    final Class rClass;

    public CheckTypesCommand(Class lClass, Class rClass) {
        this.lClass = lClass;
        this.rClass = rClass;
    }

    @Override
    public boolean execute(Context context) throws Exception {
        if ((getLeftUnit(context).getClass() != lClass) ||
                (getRightUnit(context).getClass() != rClass)) {
            setSummary("Types mismatch", context);
            return PROCESSING_COMPLETE;
        }
        return CONTINUE_PROCESSING;
    }
}
