package com.drozda.battlecity.manager;

import com.drozda.battlecity.YabcPlayGround;
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

    public YabcPlayGround getPlayground(int levelNumber, int multX, int multY) {
        log.debug("PlaygroundManager.getYabcPlayGround with parameters " + "levelNumber = [" + levelNumber + "], multX = [" + multX + "], multY = [" + multY + "]");
        YabcPlayGround yabcPlayGround = new YabcPlayGround(multX, multY);

        try {
            levelLoader.loadlevel(levelNumber + "", yabcPlayGround);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
        return yabcPlayGround;
    }
}
