package com.drozda.fx.controller;

import com.drozda.battlecity.appflow.YabcAppModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Created by GFH on 19.09.2015.
 */
public class MainMenu {

    @FXML
    private Button buttonBattle;
    @FXML
    private Button buttonDesigner;
    @FXML
    private Button buttonLevelPicker;
    @FXML
    private Button buttonHallOfFame;
    @FXML
    private Button buttonSettings;

    public void buttonBattleHandle(){
        YabcAppModel.startBattle();
    }
}
