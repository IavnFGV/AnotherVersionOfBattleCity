package com.drozda.battlecity.interfaces;

import com.drozda.battlecity.unit.GameUnit;
import javafx.collections.ObservableList;

/**
 * Created by GFH on 27.09.2015.
 */
public interface HasGameUnits {
    ObservableList<GameUnit> getUnitList();

    boolean isInWorldBounds(double newX, double newY, CanMove moveUnit);


}
