package com.drozda.battlecity.unit;

import com.drozda.battlecity.interfaces.Destroyable;
import com.drozda.battlecity.interfaces.HasGameUnits;

import static java.util.Arrays.asList;

/**
 * Created by GFH on 12.11.2015.
 */
public class EagleBaseUnit extends GameUnit implements Destroyable {
    public EagleBaseUnit(double minX, double minY, double width, double height, HasGameUnits playground) {
        super(minX, minY, width, height, asList(State.ACTIVE, State.EXPLODING, State.DEAD), null, playground);
    }

    @Override
    public void collide(BulletUnit bulletUnit) {

    }

    @Override
    public boolean canBeDestroyed(BulletUnit.Type type) {
        return true;
    }
}
