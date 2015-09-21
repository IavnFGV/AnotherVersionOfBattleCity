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


    enum TestEnum {
        en1 {
            @Override
            void init() {
                testString = "en1";
            }
        }, en2 {
            @Override
            void init() {
                testString = "en2";
            }
        };
        protected String testString;

        public String getTest() {
            return testString;
        }

        abstract void init();
        {init();}
    }

    @Test
    public void testEnum(){
        TestEnum testEnum = TestEnum.en1;
        System.out.println(testEnum.getTest());
    }

}
