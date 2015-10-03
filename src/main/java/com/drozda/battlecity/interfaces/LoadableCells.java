package com.drozda.battlecity.interfaces;

import com.drozda.battlecity.unit.TileUnit;

/**
 * Created by GFH on 01.10.2015.
 */
public interface LoadableCells {
    boolean addCell(int x, int y, TileUnit.TileType tileType);
}