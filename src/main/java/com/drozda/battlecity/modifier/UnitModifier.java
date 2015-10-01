package com.drozda.battlecity.modifier;

import com.drozda.battlecity.CanPause;
import com.drozda.battlecity.HasGameUnits;

/**
 * Created by GFH on 27.09.2015.
 */
public abstract class UnitModifier<T extends CanPause> {
    protected T gameUnit;

    public UnitModifier(T gameUnit, HasGameUnits playground) {
        this.gameUnit = gameUnit;
    }
}
