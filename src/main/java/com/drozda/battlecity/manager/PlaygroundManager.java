package com.drozda.battlecity.manager;

import com.drozda.battlecity.Playground;
import com.drozda.battlecity.loader.FileLevelLoader;
import com.drozda.battlecity.loader.LevelLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 30.09.2015.
 */
public class PlaygroundManager {
    private static final Logger log = LoggerFactory.getLogger(FileLevelLoader.class);

    LevelLoader levelLoader = new FileLevelLoader();

    public Playground getPlayground(int levelNumber, int multX, int multY) {
        log.debug("PlaygroundManager.getPlayground with parameters " + "levelNumber = [" + levelNumber + "], multX = [" + multX + "], multY = [" + multY + "]");
        Playground playground = new Playground(multX, multY);

        try {
            levelLoader.loadlevel(levelNumber + "", playground);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
        return playground;
    }
}
