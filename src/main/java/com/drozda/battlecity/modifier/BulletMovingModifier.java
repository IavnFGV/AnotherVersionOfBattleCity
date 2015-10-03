package com.drozda.battlecity.modifier;

import com.drozda.battlecity.interfaces.CanPause;
import com.drozda.battlecity.interfaces.HasGameUnits;
import com.drozda.battlecity.unit.MoveableUnit;

/**
 * Created by GFH on 30.09.2015.
 */
public class BulletMovingModifier extends MovingModifier<MoveableUnit> {
    public BulletMovingModifier(CanPause gameUnit, HasGameUnits playground) {
        super(gameUnit, playground);
    }

    @Override
    protected void confirmNewPosition(MoveableUnit gameUnit, double newX, double newY, HasGameUnits playground) {

    }
}
