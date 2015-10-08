package com.drozda.fx.controller;

import com.drozda.battlecity.manager.PlaygroundManager;
import com.drozda.battlecity.playground.YabcPlayground;
import com.drozda.battlecity.unit.GameUnit;
import com.drozda.battlecity.unit.TileUnit;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

/**
 * Created by GFH on 21.09.2015.
 */
public class Battle {
    PlaygroundManager playgroundManager = new PlaygroundManager();
    YabcPlayground curYabcPlayground;

    @FXML
    private BorderPane borderPane;

    @FXML
    private void initialize() {
        curYabcPlayground = playgroundManager.getPlayground(12, 1, 1);
        String s = "";
        for (GameUnit g : curYabcPlayground.getUnitList()) {
            if (g instanceof TileUnit) {
                s += ((TileUnit) g).getTileType() + " ";
            }
        }
        borderPane.getStylesheets().add("/com/drozda/fx/style/battle.css");
    }

}
