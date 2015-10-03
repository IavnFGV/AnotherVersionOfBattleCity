package com.drozda.battlecity.interfaces;

import com.drozda.battlecity.unit.BulletUnit;

/**
 * Created by GFH on 30.09.2015.
 */
public interface Destroyable {
    void collide(BulletUnit bulletUnit);

    boolean canBeDestroyed(BulletUnit.Type type);
}
