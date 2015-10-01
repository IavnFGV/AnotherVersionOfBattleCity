package com.drozda.battlecity;

import javafx.geometry.Bounds;

/**
 * Created by GFH on 27.09.2015.
 */
public interface CanMove {

    //  boolean getEngineOn();

    boolean canMove();

    MoveableUnit.Direction getDirection();

    Bounds getBounds();

    void setBounds(Bounds bounds);

    long getVelocity();
}
