package com.drozda.battlecity;

import java.util.List;
import java.util.Map;

/**
 * Created by GFH on 27.09.2015.
 */
public class BulletUnit extends MoveableUnit {
    public BulletUnit(double minX, double minY, double width, double height, List<State> stateFlow, Map<State, Long> timeInState) {
        super(minX, minY, width, height, stateFlow, timeInState);
    }
}
