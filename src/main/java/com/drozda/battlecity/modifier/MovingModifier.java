package com.drozda.battlecity.modifier;

import com.drozda.battlecity.StaticServices;
import com.drozda.battlecity.interfaces.CanMove;
import com.drozda.battlecity.interfaces.CanPause;
import com.drozda.battlecity.interfaces.HasGameUnits;

/**
 * Created by GFH on 27.09.2015.
 */
public abstract class MovingModifier<T extends CanPause & CanMove> extends NumberListenerModifier {
    private long moveAccumulator = 0l;
    private HasGameUnits playground;


    public MovingModifier(CanPause gameUnit, HasGameUnits playground) {
        super(gameUnit, playground);
        this.playground = playground;
    }
    @Override
    protected void perform(long deltaTime) {
        moveAccumulator += deltaTime;
        if (gameUnit().canMove() &&
                (moveAccumulator >= StaticServices.ONE_SECOND / 64)) {
            moveAccumulator = 0l;
            double newX = gameUnit().getBounds().getMinX();
            double newY = gameUnit().getBounds().getMinY();
            double deltaPosition = (gameUnit().getVelocity());
            switch (gameUnit().getDirection()) {
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
            confirmNewPosition(gameUnit(), newX, newY, playground);
        }
    }

    protected T gameUnit() {
        return (T) gameUnit;
    }

    abstract protected void confirmNewPosition(T gameUnit, double newX, double newY, HasGameUnits playground);
}
