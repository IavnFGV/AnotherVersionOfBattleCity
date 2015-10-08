package com.drozda.fx.controller;

import com.drozda.YabcLocalization;
import com.drozda.appflow.AppModel;
import com.drozda.fx.dialog.Dialog;
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

    private HyperlinkLabel helloLabel = new HyperlinkLabel();

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
                Dialog.showLoginDialog(AppModel::changeUserPredicate,
                        AppModel.getState(),
                        AppModel::changeUser,
                        AppModel.appData.getAppUsers(),
                        AppModel.appData.getAppTeams());
            }
        });

        statusBar.getLeftItems().addAll(helloLabel);
        log.info(statusBar.getPrefHeight() + "");
        borderPane.getStylesheets().add("/com/drozda/fx/style/baseapp.css");
    }

    public void setSubScene(Parent parent) {
        centerAnchorPane.getChildren().clear();
        centerAnchorPane.getChildren().add(parent);
        Pane pane = (Pane) parent;
        centerAnchorPane.setPrefSize(pane.getPrefWidth(), pane.getPrefHeight());

    }

    public BorderPane getBorderPane() {
        return borderPane;
    }
}
