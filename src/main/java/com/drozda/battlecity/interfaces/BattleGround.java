package com.drozda.battlecity.interfaces;

/**
 * Created by GFH on 07.10.2015.
 */
public interface BattleGround<T extends Enum> extends HasGameUnits, LoadableCells<T> {
    void start();
}
