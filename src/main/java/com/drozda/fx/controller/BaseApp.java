package com.drozda.fx.controller;

import com.drozda.YabcLocalization;
import com.drozda.appflow.AppModel;
import com.drozda.fx.dialog.Dialog;
import com.drozda.model.LoginDialogResponse;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.BorderPane;
import org.controlsfx.control.HyperlinkLabel;
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
    private HyperlinkLabel helloLabel = new HyperlinkLabel();

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
            }
        });

        statusBar.getLeftItems().addAll(helloLabel);
    }

    public void setSubScene(Parent parent) {
        subScene.setRoot(parent);
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }
}
