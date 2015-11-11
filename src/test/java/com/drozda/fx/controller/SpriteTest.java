package com.drozda.fx.controller;

import com.drozda.appflow.AppModel;
import com.drozda.appflow.AppState;
import com.drozda.battlecity.loader.LevelLoader;
import com.drozda.battlecity.loader.TestLoader;
import com.drozda.battlecity.playground.YabcBattleGround;
import com.drozda.battlecity.unit.TankUnit;
import com.drozda.battlecity.unit.TileUnit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

        YabcBattleGround yabcBattleGround = battle.playgroundManager.getPlayground(AppModel.stageNumberForLoading, 2,
                2, levelLoader, YabcBattleGround.BattleType.DOUBLE_PLAYER);
//        YabcBattleGround yabcBattleGround = battle.playgroundManager.getPlayground(AppModel.stageNumberForLoading, 2,
//                2, YabcBattleGround.BattleType.SINGLE_PLAYER);
        battle.loadPlayground(yabcBattleGround);
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
                .filter(gameUnit -> (gameUnit instanceof TankUnit))
                .map(gameUnit1 -> (TankUnit) gameUnit1)
                .filter(tankUnit -> tankUnit.getTankType() == TankUnit.TankType.TANK_FIRST_PLAYER)
                .findFirst().get();


        TankUnit secondTank = yabcBattleGround.getUnitList().stream()
                .filter(gameUnit -> (gameUnit instanceof TankUnit))
                .map(gameUnit1 -> (TankUnit) gameUnit1)
                .filter(tankUnit -> tankUnit.getTankType() == TankUnit.TankType.TANK_SECOND_PLAYER)
                .findFirst().get();
        readyForTest();
        //     firstTank.getBonusList().add(BonusUnit.BonusType.HELMET);
//        propertiesEditorController.initPropertyShit(firstTank);
        propertiesEditorController.initPropertyShit(secondTank);
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
