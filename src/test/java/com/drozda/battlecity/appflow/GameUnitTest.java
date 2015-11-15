package com.drozda.battlecity.appflow;

import com.drozda.battlecity.unit.GameUnit;
import com.drozda.battlecity.unit.SpadeZoneTileUnit;
import javafx.geometry.BoundingBox;
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
                super(new BoundingBox(minX, minY, width, height), stateFlow, timeInState);
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

    @Test
    public void testChangeableTileUnit() {
        SpadeZoneTileUnit testUnit = new SpadeZoneTileUnit(
                new BoundingBox(0, 0, 0, 0));
        testUnit.initialize(0l);

        testUnit.currentStateProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("TileState newvalue is :" + newValue);
        });
        testUnit.tileTypeProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("TileType newvalue is :" + newValue);
        });
        testUnit.setCurrentState(GameUnit.State.ARMOR);
        for (long i = 0; i < 22 * ONE_SECOND; i += ONE_SECOND / 5) {
            System.out.println(i / 1000_000_000.);
            testUnit.heartBeat(i);

        }
        System.out.println("Last TileType is :" + testUnit.getTileType());
    }

}

