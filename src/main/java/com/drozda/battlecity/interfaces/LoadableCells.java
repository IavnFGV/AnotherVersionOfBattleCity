package com.drozda.battlecity.interfaces;

import com.drozda.battlecity.unit.GameUnit;

/**
 * Created by GFH on 01.10.2015.
 */
public interface LoadableCells<T extends GameUnit> {
    boolean addCell(T gameUnit);
}