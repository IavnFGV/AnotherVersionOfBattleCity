package com.drozda.battlecity.interfaces;

import com.drozda.battlecity.unit.MoveableUnit;
import javafx.geometry.Bounds;

/**
 * Created by GFH on 27.09.2015.
 */
public interface CanMove {

    IsMoveAllowedResult isMoveAllowed(boolean isInWorldBounds);

    boolean canMove();

    MoveableUnit.Direction getDirection();

    Bounds getBounds();

    void setBounds(Bounds bounds);

    long getVelocity();

    enum IsMoveAllowedResult {
        ALLOW,
        STOP,
        DESTROY
    }
}
