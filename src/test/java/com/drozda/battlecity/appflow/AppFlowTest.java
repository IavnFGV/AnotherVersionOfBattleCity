package com.drozda.battlecity.appflow;

import org.junit.Test;

/**
 * Created by GFH on 18.09.2015.
 */

public class AppFlowTest {
    @Test
    public void testFlow() {
        YabcState state = new YabcState.Battle();
        System.out.println(state.tryTransition(YabcState.MainMenu::new).ignoreIfInvalid().getClass());
        System.out.println(state.tryTransition(YabcState.MainMenu::new).ignoreIfInvalid().getClass());
//        System.out.println(state.transition(AppState.Battle::new));
    }

}
