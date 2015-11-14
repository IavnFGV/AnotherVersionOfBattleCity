package com.drozda.battlecity.modifier;

import com.drozda.battlecity.StaticServices;
import com.drozda.battlecity.interfaces.CanMove;
import com.drozda.battlecity.interfaces.CanPause;
import com.drozda.battlecity.interfaces.HasGameUnits;

/**
 * Created by GFH on 27.09.2015.
 */
public abstract class MovingModifier<T extends CanPause & CanMove> extends NumberListenerModifier<T> {
    private long moveAccumulator = 0l;
    private HasGameUnits playground;


    public MovingModifier(T gameUnit, HasGameUnits playground) {
        super(gameUnit, playground);
        this.playground = playground;
    }

    @Override
    protected void perform(long deltaTime) {
        moveAccumulator += deltaTime;
        if (gameUnit.canMove() &&
                (moveAccumulator >= StaticServices.ONE_SECOND / 40)) {
            moveAccumulator = 0l;
            double newX = gameUnit.getBounds().getMinX();
            double newY = gameUnit.getBounds().getMinY();
            double deltaPosition = (gameUnit.getVelocity());
            switch (gameUnit.getDirection()) {
                case UP:
                    newY = (newY - deltaPosition);
                    break;
                case LEFT:
                    newX = (newX - deltaPosition);
                    break;
                case DOWN:
                    newY = (newY + deltaPosition);
                    break;
                case RIGHT:
                    newX = (newX + deltaPosition);
                    break;
            }
            boolean isInWorldBounds = playground.isInWorldBounds(newX, newY, gameUnit);
            if (gameUnit.isMoveAllowed(isInWorldBounds) == CanMove.IsMoveAllowedResult.ALLOW) {
                confirmNewPosition(gameUnit, newX, newY, playground);
            }
        }
    }


    abstract protected void confirmNewPosition(T gameUnit, double newX, double newY, HasGameUnits playground);
}
