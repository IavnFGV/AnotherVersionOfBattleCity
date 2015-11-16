package com.drozda.battlecity.interfaces;

import com.drozda.battlecity.unit.GameUnit;

/**
 * Created by GFH on 16.11.2015.
 */
public interface CollisionHandler<T extends GameUnit> {

    boolean isColliding(T other);


}
