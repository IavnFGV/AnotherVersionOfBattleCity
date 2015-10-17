package com.drozda.fx.controller;

import com.drozda.battlecity.manager.PlaygroundManager;
import com.drozda.battlecity.playground.YabcBattleGround;
import com.drozda.battlecity.unit.GameUnit;
import com.drozda.battlecity.unit.TileUnit;
import com.drozda.fx.sprite.YabcSprite;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;

/**
 * Created by GFH on 21.09.2015.
 */
public class Battle {
    PlaygroundManager playgroundManager = new PlaygroundManager();
    YabcBattleGround curYabcPlayground;
    IntegerProperty enemyCount = new SimpleIntegerProperty(0);
    @FXML
    private BorderPane borderPane;
    @FXML
    private TilePane enemyCounter;

    public int getEnemyCount() {
        return enemyCount.get();
    }

    public void setEnemyCount(int enemyCount) {
        this.enemyCount.set(enemyCount);
    }

    @FXML
    private void initialize() {

        enemyCountProperty().addListener((observable, oldValue, newValue) -> {
            int delta = newValue.intValue() - oldValue.intValue();
            int currentNumber = enemyCounter.getChildren().size();
            if (delta < 0) {
                enemyCounter.getChildren().remove(currentNumber + delta, currentNumber);
            }
            while (delta > 0) {
                enemyCounter.getChildren().add(YabcSprite.getSprite(YabcSprite.SMALL_ENEMY_TANK_FOR_COUNTER));
                delta--;
            }
        });
        curYabcPlayground = playgroundManager.getPlayground(12, 1, 1);
        String s = "";
        for (GameUnit g : curYabcPlayground.getUnitList()) {
            if (g instanceof TileUnit) {
                s += ((TileUnit) g).getTileType() + " ";
            }
        }
        borderPane.getStylesheets().add("/com/drozda/fx/style/battle.css");


    }

    public IntegerProperty enemyCountProperty() {
        return enemyCount;
    }

}
