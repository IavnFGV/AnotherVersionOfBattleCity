package com.drozda.fx.controller;

import com.drozda.appflow.AppModel;
import com.drozda.battlecity.manager.PlaygroundManager;
import com.drozda.battlecity.playground.YabcBattleGround;
import com.drozda.battlecity.unit.*;
import com.drozda.fx.sprite.YabcSprite;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

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
    private Map<GameUnit, Node> nodeMap = new HashMap<>();

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
        YabcBattleGround yabcBattleGround = playgroundManager.getPlayground(AppModel.stageNumberForLoading, 2, 2,
                AppModel.battleType);
        loadPlayground(yabcBattleGround);
        curYabcPlayground = yabcBattleGround;
    }

    public void loadPlayground(YabcBattleGround battleGround) {

        centerPane.getChildren().clear();
        this.curYabcPlayground = battleGround;

        Pane waterPane = new Pane();
        Pane tankPane = new Pane();
        Pane bulletPane = new Pane();
        Pane tilePane = new Pane();
        Pane grassPane = new Pane();
        Pane bonusPane = new Pane();

        Map<Object, Pane> paneMap = new HashMap<>();

        paneMap.put(TileUnit.TileType.FOREST, grassPane);
        paneMap.put(TileUnit.TileType.WATER, waterPane);
        paneMap.put(TankUnit.class, tankPane);
        paneMap.put(BulletUnit.class, bulletPane);
        paneMap.put(BonusUnit.class, bonusPane);
        class GameUnitProcessor {
            Predicate<GameUnit> skipGameUnit = gameUnit1 ->
                    !((gameUnit1 instanceof BonusUnit)
                            && (((BonusUnit) gameUnit1).isAux()));
            Consumer<GameUnit> processGameUnit = gameUnit1 -> {
                Object key = extractKeyObject(gameUnit1);
                Node node = getSprite(gameUnit1);
//                node.setCache(true);
                node.setCacheHint(CacheHint.ROTATE);
                getPane(key).getChildren().add(node);
            };

            Pane getPane(Object o) {
                return paneMap.getOrDefault(o, tilePane);
            }

            Node getSprite(GameUnit gameUnit) {
                return YabcSprite.getFullSprite(gameUnit);
            }

            Object extractKeyObject(GameUnit gameUnit) {
                if (gameUnit instanceof TileUnit) {
                    return ((TileUnit) gameUnit).getTileType();
                }
                return gameUnit.getClass();
            }

            void processUnitList() {
                battleGround.getUnitList().stream()
                        .filter(skipGameUnit)
                        .forEach(processGameUnit);
            }
        }

        new GameUnitProcessor().processUnitList();

        centerPane.getChildren().addAll(waterPane, tankPane, tilePane, bulletPane, bonusPane, grassPane);

        setSingleOrDouble
                (battleGround.getBattleType() == YabcBattleGround.BattleType.SINGLE_PLAYER ? 1 : 2);

    }

}
