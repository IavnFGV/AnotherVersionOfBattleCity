package com.drozda.battlecity.interfacesx;

import com.drozda.battlecity.unitx.enumeration.MoveOnWorldBounds;

/**
 * Created by GFH on 22.11.2015.
 */
public interface Moveable extends BoundsListModifiable, EngineStateModifiable, DirectionModifiable, SpeedModifiable {
    MoveOnWorldBounds isMoveAllowed(boolean isInWorldBounds);

    boolean canMove();
}
