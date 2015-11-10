package com.drozda.fx.controller;

import com.drozda.appflow.AppModel;
import com.drozda.appflow.AppState;
import com.drozda.battlecity.loader.LevelLoader;
import com.drozda.battlecity.loader.TestLoader;
import com.drozda.battlecity.playground.YabcBattleGround;
import com.drozda.battlecity.unit.TileUnit;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by GFH on 10.11.2015.
 */
public class SpriteTest extends Application {

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
                2, levelLoader);
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

        brickUnit.setTileState(TileUnit.TileState.STATE_1001);
//        waterUnit.setPause(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        //      Platform.runLater(() -> waterUnit.setPause(true));
//        Platform.runLater(() -> waterUnit.setCurrentState(GameUnit.State.DEAD));

    }
}
