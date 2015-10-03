package com.drozda.battlecity.loader;

import com.drozda.battlecity.interfaces.LoadableCells;

/**
 * Created by GFH on 01.10.2015.
 */
public interface LevelLoader {
    boolean loadlevel(String level, LoadableCells world) throws Exception;
}
