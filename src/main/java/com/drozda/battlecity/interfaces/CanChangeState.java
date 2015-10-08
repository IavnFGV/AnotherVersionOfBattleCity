package com.drozda.battlecity.interfaces;

/**
 * Created by GFH on 27.09.2015.
 */
public interface CanChangeState<T extends Enum> {
    T getCurrentState();

    void setCurrentState(T state);

    Long getTimeInState(T state);

    void goToNextState();
}
