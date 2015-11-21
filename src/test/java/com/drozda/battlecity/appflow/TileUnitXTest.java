package com.drozda.battlecity.appflow;

import com.drozda.battlecity.manager.PlaygroundManager;
import com.drozda.battlecity.playground.YabcBattleGround;
import com.drozda.battlecity.unitx.TileUnitX;
import com.drozda.battlecity.unitx.enumeration.TileType;

/**
 * Created by GFH on 21.11.2015.
 */
public class TileUnitXTest {
    public static void main(String[] args) {
        PlaygroundManager playgroundManager = new PlaygroundManager();
        YabcBattleGround yabcBattleGround = new YabcBattleGround();
        TileUnitX tileUnitX = new TileUnitX(yabcBattleGround, TileType.BRICK);

        System.out.println(tileUnitX);
    }
}
