package com.drozda.battlecity.unit;

import com.drozda.battlecity.interfaces.Destroyable;
import com.drozda.battlecity.interfaces.HasGameUnits;

import java.util.List;
import java.util.Map;

/**
 * Created by GFH on 12.11.2015.
 */
public class EagleBaseUnit extends GameUnit implements Destroyable {
    public EagleBaseUnit(double minX, double minY, double width, double height, List<State> stateFlow, Map<State, Long> timeInState, HasGameUnits playground) {
        super(minX, minY, width, height, stateFlow, timeInState, playground);
    }

    @Override
    public void collide(BulletUnit bulletUnit) {

    }

    @Override
    public boolean canBeDestroyed(BulletUnit.Type type) {
        return false;
    }
}
