package com.drozda.battlecity;

import java.util.List;
import java.util.Map;

/**
 * Created by GFH on 27.09.2015.
 */
public class BulletUnit extends MoveableUnit {

    private GameUnit parent;

    private Type type;

    public BulletUnit(double minX, double minY, double width, double height, List<State> stateFlow, Map<State, Long> timeInState) {
        super(minX, minY, width, height, stateFlow, timeInState);
    }

    public GameUnit getParent() {
        return parent;
    }

    public void setParent(GameUnit parent) {
        this.parent = parent;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        SIMPLE,
        POWERFUL;
    }

}
