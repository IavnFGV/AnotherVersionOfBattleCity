package com.drozda.fx.controller;

import com.drozda.battlecity.Playground;
import com.drozda.battlecity.manager.PlaygroundManager;
import com.drozda.battlecity.unit.GameUnit;
import com.drozda.battlecity.unit.TileUnit;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Created by GFH on 21.09.2015.
 */
public class Battle {
    PlaygroundManager playgroundManager = new PlaygroundManager();
    Playground curPlayground;
    @FXML
    private TextArea textArea;

    @FXML
    private void initialize() {
        curPlayground = playgroundManager.getPlayground(12, 1, 1);
        String s = "";
        for (GameUnit g : curPlayground.getUnitList()) {
            if (g instanceof TileUnit) {
                s += ((TileUnit) g).getTileType() + " ";
            }
        }
        textArea.setText(s);
    }

}
