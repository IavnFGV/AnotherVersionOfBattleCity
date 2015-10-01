package com.drozda.battlecity.modifier;

import com.drozda.battlecity.CanChangeState;
import com.drozda.battlecity.CanPause;
import com.drozda.battlecity.HasGameUnits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 27.09.2015.
 */
public class StateFlowModifier<T extends CanPause & CanChangeState> extends NumberListenerModifier {
    private static final Logger log = LoggerFactory.getLogger(StateFlowModifier.class);

    private long timeInCurrentState;

    public StateFlowModifier(CanPause gameUnit, HasGameUnits playground) {
        super(gameUnit, playground);
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
        if ((timeInState > 0) && (timeInState <= timeInCurrentState)) {
            return true;
        }
        return false;
    }

    private void goToNextState() {
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
