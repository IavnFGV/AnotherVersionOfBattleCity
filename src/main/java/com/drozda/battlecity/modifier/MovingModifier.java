package com.drozda.battlecity.modifier;

import com.drozda.battlecity.*;
import com.drozda.battlecity.manager.MoveManager;

import java.util.function.Consumer;

/**
 * Created by GFH on 27.09.2015.
 */
public class MovingModifier<T extends CanPause & CanMove & CanChangeState> extends NumberListenerModifier {
    private long moveAccumulator = 0l;
    private Consumer<MoveManager.ConsumerRequest> boundsConsumer;

    private HasGameUnits playground = StaticServices.getPlayground();

    public MovingModifier(T gameUnit, Consumer<MoveManager.ConsumerRequest> boundsConsumer) {
        super(gameUnit);
        this.boundsConsumer = boundsConsumer;
    }

    @Override
    public void perform(long deltaTime) {
        moveAccumulator += deltaTime;
        if (gameUnit().getEngineOn() &&
                (moveAccumulator >= StaticServices.ONE_SECOND / 64) &&
                gameUnit().getCurrentState() == GameUnit.State.ACTIVE) {
            moveAccumulator = 0l;
            double newX = gameUnit().getBounds().getMinX();
            double newY = gameUnit().getBounds().getMinY();
            double deltaPosition = (Double.valueOf(gameUnit().getVelocity()));
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
            boundsConsumer.accept(new MoveManager.ConsumerRequest(gameUnit(), newX, newY, playground));
        }
    }

    private T gameUnit() {
        return (T) gameUnit;
    }
}
