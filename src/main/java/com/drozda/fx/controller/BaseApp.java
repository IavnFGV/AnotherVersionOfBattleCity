package com.drozda.fx.controller;

import com.drozda.YabcLocalization;
import com.drozda.appflow.AppModel;
import com.drozda.fx.dialog.Dialog;
import com.drozda.model.LoginDialogResponse;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.HyperlinkLabel;
import org.controlsfx.control.StatusBar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by GFH on 21.09.2015.
 */
public class BaseApp implements Initializable {

    private static final Logger log = LoggerFactory.getLogger(BaseApp.class);

    @FXML
    private BorderPane borderPane;
    @FXML
    private AnchorPane centerAnchorPane;
    @FXML
    private Pane leftPane;
    private HyperlinkLabel helloLabel = new HyperlinkLabel();
    //@FXML
    //private SubScene subScene;
    private StatusBar statusBar = new StatusBar();

    {
        statusBar.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        borderPane.setBottom(statusBar);
        helloLabel.textProperty().bind(AppModel.messageStringProperty());
        helloLabel.setOnAction(event -> {
            Hyperlink link = (Hyperlink) event.getSource();
            if (link != null && link.getText().equals(YabcLocalization.getString("statusbar.relogin"))) {
                LoginDialogResponse loginDialogResponse = Dialog.ShowLoginDialog(AppModel::changeUserPredicate,
                        AppModel.getState(), AppModel::changeUser);
                log.info(statusBar.getHeight() + "");
            }
        });

        statusBar.getLeftItems().addAll(helloLabel);
        log.info(statusBar.getPrefHeight() + "");
        borderPane.getStylesheets().add("/com/drozda/fx/style/baseapp.css");
//        borderPane.centerProperty().addListener((observable, oldValue, newValue) -> {
//            leftPane.setMaxSize(0, 0);
//            leftPane.setMinSize(0, 0);
//            leftPane.setPrefSize(0,0);
//borderPane.autosize();
//        });
//        borderPane.setLeft(null);
    }

    public void setSubScene(Parent parent) {
        centerAnchorPane.getChildren().clear();
        centerAnchorPane.getChildren().add(parent);
        Pane pane = (Pane) parent;
        centerAnchorPane.setPrefSize(pane.getPrefWidth(), pane.getPrefHeight());
        //   borderPane.setCenter(parent);
        //subScene.setRoot(parent);
        // subPane.setNodeOrientation();
        // subPane.getChildren().add(parent);
        //    anchorPane.getChildren().clear();
        //   anchorPane.getChildren().add(parent);
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }
}
