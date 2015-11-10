package com.drozda.battlecity.loader;

import com.drozda.battlecity.interfaces.LoadableCells;
import com.drozda.battlecity.unit.TileUnit;

/**
 * Created by GFH on 10.11.2015.
 */
public class TestLoader implements LevelLoader {
    @Override
    public boolean loadlevel(String level, LoadableCells world) throws Exception {
        int x = 0, y = 0;
        for (TileUnit.TileType tileType : TileUnit.TileType.values()) {
            world.addCell(x++, y++, tileType);
        }
        return true;
    }
}
