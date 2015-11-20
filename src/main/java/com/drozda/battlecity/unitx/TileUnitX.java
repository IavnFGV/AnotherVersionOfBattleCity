package com.drozda.battlecity.unitx;

import com.drozda.battlecity.interfaces.BattleGround;
import com.drozda.battlecity.unitx.enumeration.TileType;

/**
 * Created by GFH on 20.11.2015.
 */
public class TileUnitX extends GameUnitX {
    private TileType tileType;

    public TileUnitX(BattleGround playground, TileType tileType) {
        super(playground);
        this.tileType = tileType;
    }

    public TileType getTileType() {
        return tileType;
    }
}
