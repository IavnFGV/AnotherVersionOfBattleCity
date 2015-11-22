package com.drozda.battlecity.interfacesx;

import javafx.geometry.Bounds;

import java.util.List;

/**
 * Created by GFH on 22.11.2015.
 */
public interface CanFire {
    boolean fire();

    List<Bounds> getBulletStartPosition();
}
