package com.drozda.fx.controller;

import com.drozda.appflow.AppModel;
import com.drozda.appflow.AppState;
import com.drozda.battlecity.controller.KeyboardActionCommandGenerator;
import com.drozda.battlecity.interfaces.ActionCommandGenerator;
import com.drozda.battlecity.loader.LevelLoader;
import com.drozda.battlecity.loader.TestLoader;
import com.drozda.battlecity.playground.PlaygroundState;
import com.drozda.battlecity.playground.YabcBattleGround;
import com.drozda.battlecity.unit.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by GFH on 10.11.2015.
 */
public class SpriteTest extends Application {

    static Stage propertiesStage;
    static PropertiesEditorController propertiesEditorController;
    private static Stage mainStage;
    AppState appState = AppState.Battle;
    LevelLoader levelLoader = new TestLoader();

    ActionCommandGenerator actionCommandGenerator = new KeyboardActionCommandGenerator(KeyCode.W, KeyCode.S, KeyCode.A,
            KeyCode.D, KeyCode.SPACE);

    public static void main(String[] args) {
        launch(args);
    }

    public void prepareScene() {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

//        Scene scene = new Scene(new Pane(),200,300);
        Scene scene = new Scene(appState.getYabcFrame().getRoot());
        Battle battle = (Battle) appState.getController();

//        YabcBattleGround yabcBattleGround = battle.playgroundManager.getPlayground(AppModel.stageNumberForLoading, 2,
//                2, levelLoader, YabcBattleGround.BattleType.DOUBLE_PLAYER);
        YabcBattleGround yabcBattleGround = battle.playgroundManager.getPlayground(AppModel.stageNumberForLoading, 2,
                2, YabcBattleGround.BattleType.DOUBLE_PLAYER);
        battle.loadPlayground(yabcBattleGround);
        //       yabcBattleGround.initialize(0l);
//        yabcBattleGround.setState(PlaygroundState.ACTIVE);
        TileUnit brickUnit = yabcBattleGround.getUnitList().stream()
                .filter(gameUnit -> (gameUnit instanceof TileUnit))
                .map(gameUnit1 -> (TileUnit) gameUnit1)
                .filter(tileUnit -> tileUnit.getTileType() == TileUnit.TileType.BRICK)
                .findFirst().get();
        TileUnit waterUnit = yabcBattleGround.getUnitList().stream()
                .filter(gameUnit -> (gameUnit instanceof TileUnit))
                .map(gameUnit1 -> (TileUnit) gameUnit1)
                .filter(tileUnit -> tileUnit.getTileType() == TileUnit.TileType.WATER)
                .findFirst().get();

        TankUnit firstTank = yabcBattleGround.getUnitList().stream()
                .filter(gameUnit -> (gameUnit instanceof PlayerTankUnit))
                .map(gameUnit1 -> (PlayerTankUnit) gameUnit1)
                .filter(tankUnit -> tankUnit.getTankType() == PlayerTankUnit.PlayerTankType.TANK_FIRST_PLAYER)
                .findFirst().get();


        TankUnit secondTank = yabcBattleGround.getUnitList().stream()
                .filter(gameUnit -> (gameUnit instanceof PlayerTankUnit))
                .map(gameUnit1 -> (PlayerTankUnit) gameUnit1)
                .filter(tankUnit -> tankUnit.getTankType() == PlayerTankUnit.PlayerTankType.TANK_SECOND_PLAYER)
                .findFirst().get();

//        yabcBattleGround.getUnitList().forEach(gameUnit -> gameUnit.setCurrentState(GameUnit.State.ACTIVE));
        yabcBattleGround.getUnitList().stream()
                .filter(gameUnit -> gameUnit instanceof TankUnit)
                .forEach(gameUnit1 -> {
//                    gameUnit1.setPause(false);
                    ((TankUnit) gameUnit1).setEngineOn(true);
                });
//        yabcBattleGround.setState(PlaygroundState.ACTIVE);

        BonusUnit bonusStar = yabcBattleGround.getUnitList().stream()
                .filter(gameUnit -> (gameUnit instanceof BonusUnit))
                .map(gameUnit1 -> (BonusUnit) gameUnit1)
                .filter(bonusUnit -> bonusUnit.getBonusType() == BonusUnit.BonusType.STAR)
                .findFirst().get();

        EagleBaseUnit eagleBase = yabcBattleGround.getUnitList().stream()
                .filter(gameUnit -> (gameUnit instanceof EagleBaseUnit))
                .map(gameUnit1 -> (EagleBaseUnit) gameUnit1)
                        //.filter(bonusUnit -> bonusUnit.getBonusType() == BonusUnit.BonusType.STAR)
                .findFirst().get();

        SpadeZoneTileUnit spadeZoneTileUnit = yabcBattleGround.getUnitList().stream()
                .filter(gameUnit -> (gameUnit instanceof SpadeZoneTileUnit))
                .map(gameUnit1 -> (SpadeZoneTileUnit) gameUnit1)
                        //.filter(bonusUnit -> bonusUnit.getBonusType() == BonusUnit.BonusType.STAR)
                .findFirst().get();


        //   BonusUnit bonusUnit = new BonusUnit(yabcBattleGround, BonusUnit.BonusType.FRIENDLYFIRE_GIFT);
        //    bonusUnit.setCurrentState(GameUnit.State.IN_POCKET);
//        firstTank.getBonusList().add(bonusUnit);
        readyForTest();
        spadeZoneTileUnit.setCurrentState(GameUnit.State.ARMOR);
        spadeZoneTileUnit.setPause(false);
        firstTank.setActionCommandGenerator(actionCommandGenerator);

        scene.setOnKeyReleased((KeyboardActionCommandGenerator) actionCommandGenerator);
        scene.setOnKeyPressed((KeyboardActionCommandGenerator) actionCommandGenerator);

        AnimationTimer mainLoop = new AnimationTimer() {
            private Boolean init = false;

            @Override
            public void handle(long now) {
                if (!init) {
                    yabcBattleGround.initialize(now);
                    yabcBattleGround.setState(PlaygroundState.ACTIVE);
                    init = true;
                }
                yabcBattleGround.heartBeat(now);
            }
        };
        mainLoop.start();

//        firstTank.getBonusList().add(new BonusUnit(0, 0, 0, 0, yabcBattleGround, BonusUnit.BonusType.HELMET));
//        firstTank.getBonusList().add(new BonusUnit(0, 0, 0, 0, yabcBattleGround, BonusUnit.BonusType
//                .START_GAME_HELMET));
        propertiesEditorController.initPropertyShit(firstTank);
//        propertiesEditorController.initPropertyShit(bonusStar);
//        propertiesEditorController.initPropertyShit(brickUnit);
//        propertiesEditorController.initPropertyShit(spadeZoneTileUnit);
//        propertiesEditorController.initPropertyShit(eagleBase);
        //     propertiesEditorController.initPropertyShit(secondTank);
        //  firstTank.setEngineOn(true);
        //    firstTank.setStars();
//        brickUnit.setTileState(TileUnit.TileState.STATE_1001);
//        waterUnit.setPause(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        //      Platform.runLater(() -> waterUnit.setPause(true));
//        Platform.runLater(() -> waterUnit.setCurrentState(GameUnit.State.DEAD));

    }

    public static void readyForTest() {
        propertiesStage = new Stage();
        propertiesStage.setScene(new Scene(createPropertiesEditorWorld()));
        propertiesStage.setTitle("Properties Editor");
        propertiesStage.show();
    }

    private static Parent createPropertiesEditorWorld() {
        FXMLLoader loader = new FXMLLoader();
        Parent root = null;
        try {
            root = loader.load(AppModel.class.getResourceAsStream("/com/drozda/fx/fxml/PropertiesEditorController" +
                    ".fxml"));
            propertiesEditorController = loader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }


}
