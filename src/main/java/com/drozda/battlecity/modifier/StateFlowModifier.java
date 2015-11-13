package com.drozda.battlecity.modifier;

import com.drozda.battlecity.interfaces.CanChangeState;
import com.drozda.battlecity.interfaces.CanPause;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 27.09.2015.
 */
public class StateFlowModifier<T extends CanPause & CanChangeState> extends NumberListenerModifier {
    private static final Logger log = LoggerFactory.getLogger(StateFlowModifier.class);

    private long timeInCurrentState;

    public StateFlowModifier(T gameUnit) {
        super(gameUnit, null);
    }

    @Override
    protected void perform(long deltaTime) {
        log.info("StateFlowModifier.perform");
        log.info("deltaTime = [" + deltaTime + "]");
        timeInCurrentState += deltaTime;
        if (timeIsUp()) {
            goToNextState();
        }
    }

    private boolean timeIsUp() {
        log.debug("StateFlowModifier.timeIsUp");
        long timeInState = getTimeInState();
        return (timeInState > 0) && (timeInState <= timeInCurrentState);
    }

    private void goToNextState() {
        log.debug("StateFlowModifier.goToNextState");
        gameUnit().goToNextState();
        timeInCurrentState = 0;
    }

    private long getTimeInState() {
        return gameUnit().getTimeInState(gameUnit().getCurrentState());
    }

    private T gameUnit() {
        return (T) gameUnit;
    }

}
