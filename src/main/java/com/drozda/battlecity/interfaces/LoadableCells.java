package com.drozda.battlecity.interfaces;

/**
 * Created by GFH on 01.10.2015.
 */
public interface LoadableCells<T extends Enum> {
    boolean addCell(int x, int y, T tileType);
}