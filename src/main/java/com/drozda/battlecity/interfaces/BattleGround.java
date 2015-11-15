package com.drozda.battlecity.interfaces;

import com.drozda.battlecity.unit.GameUnit;
import javafx.geometry.Point2D;

import java.util.List;

/**
 * Created by GFH on 07.10.2015.
 */
public interface BattleGround<T extends GameUnit> extends HasGameUnits, LoadableCells<T> {
    void createActiveUnits();

    boolean setVitalPoints(List<Point2D> enemiesPespawn,
                           Point2D firstPlayerRespawn,
                           Point2D secondPlayerRespawn,
                           Point2D eagleBaseRespawn);
}
