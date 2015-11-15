package com.drozda.battlecity.loader;

import com.drozda.battlecity.interfaces.LoadableCells;
import com.drozda.battlecity.unit.TileUnit;
import javafx.geometry.BoundingBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GFH on 01.10.2015.
 */
public class FileLevelLoader implements LevelLoader {
    private static final Logger log = LoggerFactory.getLogger(FileLevelLoader.class);

    private static Map<Character, TileUnit.TileType> map = new HashMap<>();

    static {
        map.put('#', TileUnit.TileType.BRICK);
        map.put('@', TileUnit.TileType.STEEL);
        map.put('~', TileUnit.TileType.WATER);
        map.put('%', TileUnit.TileType.FOREST);
        map.put('-', TileUnit.TileType.ICE);
        // map.put('.', TileUnit.TileType.EMPTY);
    }

    public boolean loadlevel(String level, LoadableCells world) throws Exception {
        log.info("FileLevelLoader.loadlevel with parameters " + "level = [" + level + "], world = [" + world + "]");
        InputStream is = LevelLoader.class.getResourceAsStream("/com/drozda/battlecity/level/" + level);
        if (is == null) {
            log.error("Input stream is null. Something with resource " + "'/com/drozda/battlecity/level/" + level + "'");
            return false;
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        String sCurrentLine;
        double cellHeight = world.getCellHeight();
        double cellWidth = world.getCellWidth();
        int y = 0;
        while ((sCurrentLine = bufferedReader.readLine()) != null) {
            char[] chars = sCurrentLine.toCharArray();
            int x = 0;
            for (char c : chars) {
                if (c != '.') {
                    TileUnit tileUnit = new TileUnit(
                            new BoundingBox(x * cellWidth, y * cellHeight, cellWidth, cellHeight),
                            map.get(c)
                    );
                    world.addCell(tileUnit);
                }
                log.info(String.valueOf(c));
                x++;
            }
            y++;
        }

        return true;
    }
}
