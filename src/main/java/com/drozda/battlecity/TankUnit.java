package com.drozda.battlecity;

import java.util.List;
import java.util.Map;

/**
 * Created by GFH on 29.09.2015.
 */
public class TankUnit extends MoveableUnit {
    public TankUnit(double minX, double minY, double width, double height, List<State> stateFlow, Map<State, Long> timeInState) {
        super(minX, minY, width, height, stateFlow, timeInState);
    }
}
