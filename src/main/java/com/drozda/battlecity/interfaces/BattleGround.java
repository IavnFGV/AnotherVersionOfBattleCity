package com.drozda.battlecity.interfaces;

import com.drozda.battlecity.playground.PlaygroundState;
import com.drozda.battlecity.unit.BulletUnit;
import com.drozda.battlecity.unit.GameUnit;
import com.drozda.battlecity.unit.TankUnit;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Point2D;

import java.util.List;

/**
 * Created by GFH on 07.10.2015.
 */
public interface BattleGround<T extends GameUnit> extends HasGameUnits, LoadableCells<T> {
    void createActiveUnits();

    ObjectProperty<PlaygroundState> stateProperty();

    List<BulletUnit> getActiveBullets();

    void fire(TankUnit tankUnit);

    boolean setVitalPoints(List<Point2D> enemiesPespawn,
                           Point2D firstPlayerRespawn,
                           Point2D secondPlayerRespawn,
                           Point2D eagleBaseRespawn);
}
