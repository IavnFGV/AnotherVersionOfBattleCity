package com.drozda.battlecity.modifier;

import com.drozda.battlecity.CanChangeState;
import com.drozda.battlecity.CanPause;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 27.09.2015.
 */
public class StateModifier<T extends CanPause & CanChangeState> extends NumberListenerModifier {
    private static final Logger log = LoggerFactory.getLogger(StateModifier.class);

    private long timeInCurrentState;

    public StateModifier(T gameUnit) {
        super(gameUnit);
    }

    @Override
    public void perform(long deltaTime) {
        log.info("StateModifier.perform");
        log.info("deltaTime = [" + deltaTime + "]");
        timeInCurrentState += deltaTime;
        if (timeIsUp()) {
            goToNextState();
        }
    }

    private boolean timeIsUp() {
        log.debug("StateModifier.timeIsUp");
        long timeInState = getTimeInState();
        if ((timeInState > 0) && (timeInState <= timeInCurrentState)) {
            return true;
        }
        return false;
    }

    private void goToNextState() {
        gameUnit().goToNextState();
    }

    private long getTimeInState() {
        return gameUnit().getTimeInState(gameUnit().getCurrentState());
    }

    private T gameUnit() {
        return (T) gameUnit;
    }

}
