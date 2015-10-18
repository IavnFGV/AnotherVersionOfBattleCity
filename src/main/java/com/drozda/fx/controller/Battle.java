package com.drozda.fx.controller;

import com.drozda.battlecity.playground.YabcBattleGround;
import com.drozda.fx.sprite.YabcSprite;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

import java.lang.reflect.Field;

/**
 * Created by GFH on 21.09.2015.
 */
public class Battle {
    //    PlaygroundManager playgroundManager = new PlaygroundManager();
    YabcBattleGround curYabcPlayground;
    IntegerProperty enemyCount = new SimpleIntegerProperty(0);
    IntegerProperty singleOrDouble = new SimpleIntegerProperty(0);

    IntegerProperty stageNumber = new SimpleIntegerProperty(0);
    @FXML
    private BorderPane borderPane;
    @FXML
    private TilePane enemyCounter;
    @FXML
    private ImageView firstPlayerBanner;
    @FXML
    private ImageView secondPlayerBanner;
    @FXML
    private Pane firstPlayerLifes;
    @FXML
    private Pane secondPlayerLifes;
    @FXML
    private Pane firstPlayerBannerPane;
    @FXML
    private Pane secondPlayerBannerPane;
    @FXML
    private Pane stageNumberBannerPane;
    @FXML
    private ImageView stageNumberBanner;
    @FXML
    private Pane stageNumberSecondDigit;
    @FXML
    private Pane stageNumberFirstDigit;

    public int getStageNumber() {
        return stageNumber.get();
    }

    public void setStageNumber(int stageNumber) {
        this.stageNumber.set(stageNumber);
    }

    public int getSingleOrDouble() {
        return singleOrDouble.get();
    }

    public void setSingleOrDouble(int singleOrDouble) {
        this.singleOrDouble.set(singleOrDouble);
    }

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
                enemyCounter.getChildren().add(YabcSprite.getSimpleSprite(YabcSprite.SMALL_ENEMY_TANK_FOR_COUNTER));
                delta--;
            }
        });

        singleOrDoubleProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue.intValue()) {
                case 1: {
                    secondPlayerBannerPane.setVisible(false);
                    firstPlayerBannerPane.setVisible(true);
                }
                break;
                case 2: {
                    secondPlayerBannerPane.setVisible(true);
                    firstPlayerBannerPane.setVisible(true);
                }
                break;
                default: {
                    secondPlayerBannerPane.setVisible(false);
                    firstPlayerBannerPane.setVisible(false);
                }
            }
        });
//        curYabcPlayground = playgroundManager.getPlayground(12, 1, 1);
//        String s = "";
//        for (GameUnit g : curYabcPlayground.getUnitList()) {
//            if (g instanceof TileUnit) {
//                s += ((TileUnit) g).getTileType() + " ";
//            }
//        }
        stageNumberProperty().addListener((observable, oldValue, newValue) -> {
            int firstDigit = newValue.intValue() / 10;
            int secondDigit = newValue.intValue() % 10;

            stageNumberFirstDigit.getChildren().clear();
            stageNumberFirstDigit.getChildren().add(YabcSprite.getDigit(firstDigit));

            stageNumberSecondDigit.getChildren().clear();
            stageNumberSecondDigit.getChildren().add(YabcSprite.getDigit(secondDigit));


        });

        initResources();
    }

    public IntegerProperty enemyCountProperty() {
        return enemyCount;
    }

    public IntegerProperty singleOrDoubleProperty() {
        return singleOrDouble;
    }

    public IntegerProperty stageNumberProperty() {
        return stageNumber;
    }

    private void initResources() {
        borderPane.getStylesheets().add("/com/drozda/fx/style/battle.css");
        Field[] fields = getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.getType() == ImageView.class) {
                try {
                    ImageView imageView = (ImageView) field.get(this);
                    imageView.setImage(YabcSprite.baseImage);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
