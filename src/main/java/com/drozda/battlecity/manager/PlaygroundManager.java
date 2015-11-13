package com.drozda.battlecity.manager;

import com.drozda.battlecity.loader.FileLevelLoader;
import com.drozda.battlecity.loader.LevelLoader;
import com.drozda.battlecity.playground.YabcBattleGround;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 30.09.2015.
 */
public class PlaygroundManager {
    private static final Logger log = LoggerFactory.getLogger(FileLevelLoader.class);

    LevelLoader levelLoader = new FileLevelLoader();

    public YabcBattleGround getPlayground(int levelNumber, int multX, int multY, YabcBattleGround.BattleType battleType) {
        return getPlayground(levelNumber, multX, multY, this.levelLoader, battleType);
    }

    public YabcBattleGround getPlayground(int levelNumber, int multX, int multY, LevelLoader levelLoader, YabcBattleGround.BattleType battleType) {
        log.debug("PlaygroundManager.getPlayground with parameters " + "levelNumber = [" + levelNumber + "], multX = [" + multX + "], multY = [" + multY + "], levelLoader = [" + levelLoader + "]");
        YabcBattleGround yabcPlayground = new YabcBattleGround(multX, multY, battleType);

        try {
            levelLoader.loadlevel(levelNumber + "", yabcPlayground);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
        yabcPlayground.createActiveUnits();
        yabcPlayground.testCreateAllEnemies(); //todo REMOVE it is for test!!
        yabcPlayground.testCreateAllBonuses(); //todo REMOVE it is for test!!

        return yabcPlayground;
    }
}
