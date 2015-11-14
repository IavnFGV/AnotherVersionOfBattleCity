package com.drozda.battlecity.unit;

import com.drozda.battlecity.interfaces.HasGameUnits;
import com.drozda.battlecity.modifier.BulletMovingModifier;
import com.drozda.battlecity.modifier.MovingModifier;

import static java.util.Arrays.asList;

/**
 * Created by GFH on 27.09.2015.
 */
public class BulletUnit extends MoveableUnit {

    private GameUnit parent;

    private Type type;

    public BulletUnit(double minX, double minY, double width, double height, HasGameUnits playground, long velocity,
                      GameUnit parent, Type type) {
        super(minX, minY, width, height, asList(GameUnit.State.ACTIVE, State.EXPLODING, GameUnit.State.DEAD), null,
                playground, velocity);
        this.parent = parent;
        this.type = type;
    }

    @Override
    protected MovingModifier<BulletUnit> createMovingModifier(HasGameUnits playground) {
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

    @Override
    public IsMoveAllowedResult isMoveAllowed(boolean isInWorldBounds) {
        if (!isInWorldBounds) {
            setCurrentState(State.EXPLODING);
        }
        return isInWorldBounds ? IsMoveAllowedResult.ALLOW : IsMoveAllowedResult.DESTROY;
    }

    public enum Type {
        SIMPLE,
        POWERFUL
    }

}
