package com.drozda.battlecity.appflow;

import com.drozda.battlecity.eventx.*;
import com.drozda.battlecity.manager.PlaygroundManager;
import com.drozda.battlecity.playground.YabcBattleGround;
import com.drozda.battlecity.unitx.MoveableUnitX;
import com.drozda.battlecity.unitx.TileUnitX;
import com.drozda.battlecity.unitx.enumeration.*;
import javafx.geometry.BoundingBox;

import static java.util.Arrays.asList;

/**
 * Created by GFH on 21.11.2015.
 */
public class TileUnitXTest {
    public static void main(String[] args) {
        PlaygroundManager playgroundManager = new PlaygroundManager();
        YabcBattleGround yabcBattleGround = new YabcBattleGround();
        TileUnitX tileUnitX = new TileUnitX(yabcBattleGround, TileType.BRICK);


        tileUnitX.handle(new BasicStateChangeEvent(tileUnitX, BasicState.ACTIVE));
        tileUnitX.handle(new PauseStateChangeEvent(tileUnitX, PauseState.PLAY));

        System.out.println(tileUnitX);

        tileUnitX.handle(new WakeUpChangeEvent(tileUnitX, PauseState.PAUSE, BasicState.SLEEP, asList(new BoundingBox(1, 2, 3, 4)
        )));

        System.out.println(tileUnitX);

        MoveableUnitX moveableUnitX = new MoveableUnitX(yabcBattleGround, Direction.UP);

        System.out.println(moveableUnitX);

        moveableUnitX.handle(new DirectionChangeEvent(moveableUnitX, Direction.DOWN));

        System.out.println(moveableUnitX);

        moveableUnitX.handle(new SpeedChangeEvent(moveableUnitX, 45));

        System.out.println(moveableUnitX);

        moveableUnitX.handle(new EngineStateChangeEvent(moveableUnitX, EngineState.ENABLED));

        System.out.println(moveableUnitX);
    }
}
