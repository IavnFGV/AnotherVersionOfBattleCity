package com.drozda.battlecity.appflow;

import com.benjiweber.statemachine.BiTransitionTo;
import com.benjiweber.statemachine.PentaTransition;
import com.benjiweber.statemachine.TransitionTo;
import com.benjiweber.statemachine.TriTransitionTo;

/**
 * Created by GFH on 18.09.2015.
 */
public class YabcState implements AppState {

    protected int hash = 0;

    @Override
    public boolean equals(Object obj) {
//        if (super.equals(obj))
//            return true;
        if (obj == this) return true;
        if (obj instanceof AppState) {
            return this.getClass() == obj.getClass();
        } else return false;
    }

    @Override
    public int hashCode() {
        if (hash == 0) {
            long bits = 7L;
            bits = 31L * bits + this.getClass().getName().hashCode();
            hash = (int) (bits ^ (bits >> 32));
        }
        return hash;
    }

    public static class MainMenu extends YabcState implements PentaTransition<Battle, Designer, LevelPicker, HallOfFame,
            Settings> {
    }

    public static class Battle extends YabcState implements PentaTransition<MainMenu, Battle, Designer, LevelPicker,
            Settings> {
    }

    public static class Designer extends YabcState implements BiTransitionTo<MainMenu, Battle> {
    }

    public static class LevelPicker extends YabcState implements TriTransitionTo<MainMenu, Battle, Designer> {
    }

    public static class HallOfFame extends YabcState implements TransitionTo<MainMenu> {
    }

    public static class Settings extends YabcState implements BiTransitionTo<MainMenu, Battle> {
    }
}
