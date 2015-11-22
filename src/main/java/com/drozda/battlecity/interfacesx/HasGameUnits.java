package com.drozda.battlecity.interfacesx;

import com.drozda.battlecity.unitx.GameUnitX;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;

import java.util.List;

/**
 * Created by GFH on 22.11.2015.
 */
public interface HasGameUnits {

    ObservableList<GameUnitX> getUnitList();

    boolean isInWorldBounds(List<Bounds> boundsList);
}
