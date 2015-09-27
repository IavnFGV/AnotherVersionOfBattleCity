package com.drozda.battlecity;

/**
 * Created by GFH on 27.09.2015.
 */
public interface CanChangeState {
    GameUnit.State getCurrentState();

    void setCurrentState(GameUnit.State state);

    Long getTimeInState(GameUnit.State state);

    void goToNextState();
}
