package com.drozda.fx.controller;

import com.drozda.appflow.AppModel;
import com.drozda.battlecity.manager.PlaygroundManager;
import com.drozda.battlecity.playground.YabcBattleGround;
import com.drozda.battlecity.unit.GameUnit;
import com.drozda.battlecity.unit.TileUnit;
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
    PlaygroundManager playgroundManager = new PlaygroundManager();
    YabcBattleGround curYabcPlayground;
    IntegerProperty enemyCount = new SimpleIntegerProperty(0);
    IntegerProperty singleOrDouble = new SimpleIntegerProperty(0);
    IntegerProperty stageNumber = new SimpleIntegerProperty(0);
    IntegerProperty firstPlayersLifes = new SimpleIntegerProperty(0);
    IntegerProperty secondPlayersLifes = new SimpleIntegerProperty(0);
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
    @FXML
    private Pane centerPane;

    public int getFirstPlayersLifes() {
        return firstPlayersLifes.get();
    }

    public void setFirstPlayersLifes(int firstPlayersLifes) {
        this.firstPlayersLifes.set(firstPlayersLifes);
    }

    public int getSecondPlayersLifes() {
        return secondPlayersLifes.get();
    }

    public void setSecondPlayersLifes(int secondPlayersLifes) {
        this.secondPlayersLifes.set(secondPlayersLifes);
    }

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

        firstPlayersLifesProperty().addListener((observable, oldValue, newValue) -> {
            firstPlayerLifes.getChildren().clear();
            firstPlayerLifes.getChildren().add(YabcSprite.getDigit(newValue.intValue()));
        });


        secondPlayersLifesProperty().addListener((observable, oldValue, newValue) -> {
            secondPlayerLifes.getChildren().clear();
            secondPlayerLifes.getChildren().add(YabcSprite.getDigit(newValue.intValue()));
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

    public IntegerProperty firstPlayersLifesProperty() {
        return firstPlayersLifes;
    }

    public IntegerProperty secondPlayersLifesProperty() {
        return secondPlayersLifes;
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

    public void startGame() {
        YabcBattleGround yabcBattleGround = playgroundManager.getPlayground(AppModel.stageNumberForLoading, 2, 2);
        loadPlayground(yabcBattleGround);
    }

    public void loadPlayground(YabcBattleGround battleGround) {
        //Pane pane = new Pane();
        for (GameUnit gameUnit : battleGround.getUnitList()) {
            if (gameUnit instanceof TileUnit) {
                TileUnit tileUnit =
                        ((TileUnit) gameUnit);
                if ((tileUnit.getTileType() == TileUnit.TileType.WATER) || //TODO REMOVE AFTER IMPLEMENTATION OF ALL  TYPES
                        (tileUnit.getTileType() == TileUnit.TileType
                                .BRICK)) {
                    ImageView imageView = YabcSprite.getFullSprite(gameUnit);
                    centerPane.getChildren().add(imageView);
                }
            }
            // ImageView imageView = YabcSprite.getFullSprite(gameUnit);
            //  centerPane.getChildren().add(imageView);
        }
        // borderPane.getCenter().
    }

}
