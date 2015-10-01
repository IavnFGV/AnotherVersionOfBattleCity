package com.drozda.battlecity.appflow;

import com.drozda.battlecity.GameUnit;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.drozda.battlecity.StaticServices.ONE_SECOND;

/**
 * Created by GFH on 27.09.2015.
 */
public class GameUnitTest {
    @Test
    public void testGameUnit() {
        class TestUnit extends GameUnit {
            public TestUnit(double minX, double minY, double width, double height, List<State> stateFlow, Map<State, Long> timeInState) {
                super(minX, minY, width, height, stateFlow, timeInState, );
            }
        }

        TestUnit testUnit = new TestUnit(0, 0, 0, 0, null, null);
        testUnit.initialize(0l);
        GameUnit.State lastState = testUnit.getCurrentState();

        for (long i = 0; i < 5 * ONE_SECOND; i += ONE_SECOND / 10) {
            testUnit.heartBeat(i);
            GameUnit.State newState = testUnit.getCurrentState();
            if (newState != lastState) {
                System.out.println("newState is " + newState);
                lastState = newState;
                if (newState == GameUnit.State.ACTIVE) {
                    testUnit.setCurrentState(GameUnit.State.EXPLODING);
                }
            }
        }
    }
}
