package com.drozda.battlecity.unit;

import com.drozda.battlecity.interfaces.HasGameUnits;
import com.drozda.battlecity.modifier.BulletMovingModifier;
import com.drozda.battlecity.modifier.MovingModifier;

import java.util.List;
import java.util.Map;

/**
 * Created by GFH on 27.09.2015.
 */
public class BulletUnit extends MoveableUnit {

    private GameUnit parent;

    private Type type;

    public BulletUnit(double minX, double minY, double width, double height, List<State> stateFlow,
                      Map<State, Long> timeInState, HasGameUnits playground, long velocity, GameUnit parent, Type type) {
        super(minX, minY, width, height, stateFlow, timeInState, playground, velocity);
        this.parent = parent;
        this.type = type;
    }

    @Override
    protected MovingModifier<MoveableUnit> createMovingModifier(HasGameUnits playground) {
        return new BulletMovingModifier(this, playground);
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
