package com.drozda.battlecity.unit;

import com.drozda.battlecity.interfaces.Collideable;
import com.drozda.battlecity.interfaces.HasGameUnits;
import com.drozda.battlecity.modifier.MovingModifier;
import com.drozda.battlecity.modifier.PositionFixingModifier;
import com.drozda.battlecity.modifier.TankMovingModifier;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Bounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by GFH on 15.11.2015.
 */
public abstract class TankUnit<E extends Enum> extends MoveableUnit implements Collideable {
    private static final Logger log = LoggerFactory.getLogger(TankUnit.class);
    protected final PositionFixingModifier<MoveableUnit> fixingModifier;
    protected IntegerProperty lifes = new SimpleIntegerProperty(1);
    private E tankType;

    public TankUnit(Bounds bounds, List<State> stateFlow, Map<State, Long> timeInState, HasGameUnits playground, E
            tankType, long velocity) {
        super(bounds, stateFlow, timeInState, playground, velocity);
        this.tankType = tankType;
        fixingModifier = new PositionFixingModifier<>(this, playground);
        this.directionProperty().addListener(fixingModifier);
    }

    @Override
    public IsMoveAllowedResult isMoveAllowed(boolean isInWorldBounds) {
        return isInWorldBounds ? IsMoveAllowedResult.ALLOW : IsMoveAllowedResult.STOP;
    }

    public int getLifes() {
        return lifes.get();
    }

    public void setLifes(int lifes) {
        this.lifes.set(lifes);
    }

    public IntegerProperty lifesProperty() {
        return lifes;
    }

    @Override
    protected MovingModifier<TankUnit> createMovingModifier(HasGameUnits playground) {
        return new TankMovingModifier(this, playground);
    }

    public E getTankType() {
        return tankType;
    }

    public void setTankType(E tankType) {
        this.tankType = tankType;
    }

}
