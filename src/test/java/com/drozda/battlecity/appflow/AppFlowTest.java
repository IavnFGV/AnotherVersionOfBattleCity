package com.drozda.battlecity.appflow;

import com.drozda.appflow.AppState;

/**
 * Created by GFH on 18.09.2015.
 */

public class AppFlowTest {


    //    @Test(expected = IllegalArgumentException.class)
    public void testEnumYabcState() {
        AppState appState = AppState.Designer;

        System.out.println(appState.getAllowedTransitions());
        System.out.println(appState.tryTransitionIgnoreWrong(AppState.HallOfFame));
        System.out.println(appState.canTransition(AppState.HallOfFame));
        System.out.println(appState.tryTransition(AppState.HallOfFame));

    }

    //    @Test
    public void testEnum() {
        TestEnum testEnum = TestEnum.en1;
        System.out.println(testEnum.getTest());
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

        {
            init();
        }

        public String getTest() {
            return testString;
        }

        abstract void init();
    }

}
