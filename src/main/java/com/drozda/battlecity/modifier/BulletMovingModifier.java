package com.drozda.battlecity.modifier;

import com.drozda.battlecity.interfaces.HasGameUnits;
import com.drozda.battlecity.unit.BulletUnit;
import javafx.geometry.BoundingBox;

/**
 * Created by GFH on 30.09.2015.
 */
public class BulletMovingModifier extends MovingModifier<BulletUnit> {
    public BulletMovingModifier(BulletUnit gameUnit, HasGameUnits playground) {
        super(gameUnit, playground);
    }

    @Override
    protected void confirmNewPosition(BulletUnit gameUnit, double newX, double newY, HasGameUnits playground) {
        gameUnit.setBounds(new BoundingBox(newX, newY, gameUnit.getBounds().getWidth(),
                gameUnit.getBounds().getHeight()));
    }
}
