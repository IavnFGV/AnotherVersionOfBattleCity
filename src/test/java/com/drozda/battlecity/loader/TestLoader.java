package com.drozda.battlecity.loader;

import com.drozda.battlecity.interfaces.LoadableCells;
import com.drozda.battlecity.unit.TileUnit;
import javafx.geometry.BoundingBox;

/**
 * Created by GFH on 10.11.2015.
 */
public class TestLoader implements LevelLoader {
    @Override
    public boolean loadlevel(String level, LoadableCells world) throws Exception {
        int x = 0, y = 0;
        double cellHeight = world.getCellHeight();
        double cellWidth = world.getCellWidth();
        for (TileUnit.TileType tileType : TileUnit.TileType.values()) {
            TileUnit tileUnit = new TileUnit(
                    new BoundingBox(x * cellWidth, y * cellHeight, cellWidth, cellHeight),
                    tileType
            );
            world.addCell(tileUnit);
        }
        return true;
    }
}
