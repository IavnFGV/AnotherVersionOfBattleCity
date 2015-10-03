package com.drozda.battlecity.loader;

import com.drozda.battlecity.interfaces.LoadableCells;
import com.drozda.battlecity.unit.TileUnit;
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
        log.debug("FileLevelLoader.loadlevel with parameters " + "level = [" + level + "], world = [" + world + "]");
        InputStream is = LevelLoader.class.getResourceAsStream("/com/drozda/battlecity/level/" + level);
        if (is != null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String sCurrentLine;
            int y = 0;
            while ((sCurrentLine = bufferedReader.readLine()) != null) {
                char[] chars = sCurrentLine.toCharArray();
                int x = 0;
                for (char c : chars) {
                    if (c != '.') {
                        world.addCell(x, y, map.get(c));
                    }
                    log.info(String.valueOf(c));
                    x++;
                }
                y++;
            }
        }
        return true;
    }
}
