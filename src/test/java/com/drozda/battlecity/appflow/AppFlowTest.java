package com.drozda.battlecity.appflow;

import org.junit.Test;

/**
 * Created by GFH on 18.09.2015.
 */

public class AppFlowTest {


    @Test(expected = IllegalArgumentException.class)
    public void testEnumYabcState() {
        YabcState yabcState = YabcState.Designer;

        System.out.println(yabcState.getAllowedTransitions());
        System.out.println(yabcState.tryTransitionIgnoreWrong(YabcState.HallOfFame));
        System.out.println(yabcState.canTransition(YabcState.HallOfFame));
        System.out.println(yabcState.tryTransition(YabcState.HallOfFame));

    }

}
