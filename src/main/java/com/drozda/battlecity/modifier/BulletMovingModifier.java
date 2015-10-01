package com.drozda.battlecity.modifier;

import com.drozda.battlecity.BulletUnit;
import com.drozda.battlecity.CanPause;
import com.drozda.battlecity.HasGameUnits;

/**
 * Created by GFH on 30.09.2015.
 */
public class BulletMovingModifier extends MovingModifier<BulletUnit> {
    public BulletMovingModifier(CanPause gameUnit, HasGameUnits playground) {
        super(gameUnit, playground);
    }

    @Override
    protected void confirmNewPosition(BulletUnit gameUnit, double newX, double newY, HasGameUnits playground) {

    }
}
