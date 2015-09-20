package com.drozda.fx.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import org.controlsfx.control.StatusBar;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by GFH on 21.09.2015.
 */
public class BaseApp implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private SubScene subScene;
    private StatusBar statusBar = new StatusBar();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        borderPane.setBottom(statusBar);
        statusBar.getLeftItems().addAll(new Button("sd"));
    }

    public void setSubScene(Parent parent) {
        subScene.setRoot(parent);
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }
}
