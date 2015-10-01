package com.drozda.battlecity;

import com.drozda.battlecity.modifier.MovingModifier;
import com.drozda.battlecity.modifier.PositionFixingModifier;
import com.drozda.battlecity.modifier.TankMovingModifier;

import java.util.List;
import java.util.Map;

/**
 * Created by GFH on 29.09.2015.
 */
public class TankUnit extends MoveableUnit implements Destroyable {
    protected final PositionFixingModifier<MoveableUnit> fixingModifier;

    public TankUnit(double minX, double minY, double width, double height, List<State> stateFlow, Map<State, Long> timeInState, HasGameUnits playground, long velocity) {
        super(minX, minY, width, height, stateFlow, timeInState, playground, velocity);
        fixingModifier = new PositionFixingModifier<>(this, playground);
    }

    @Override
    protected MovingModifier<MoveableUnit> createMovingModifier(HasGameUnits playground) {
        return new TankMovingModifier(this, playground);
    }

    @Override
    public void collide(BulletUnit bulletUnit) {
//todo replace

    }

    @Override
    public boolean canBeDestroyed(BulletUnit.Type type) {
        return false;
    }
}
