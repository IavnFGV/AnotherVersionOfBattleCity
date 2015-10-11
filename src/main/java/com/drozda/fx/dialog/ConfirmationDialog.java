package com.drozda.fx.dialog;

import com.drozda.YabcLocalization;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Created by GFH on 11.10.2015.
 */
public class ConfirmationDialog {

    public static boolean userCreation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(YabcLocalization.getString("confirm.newuser.dlg.title"));
        alert.setHeaderText(YabcLocalization.getString("confirm.newuser.dlg.header"));
        alert.setContentText(YabcLocalization.getString("confirm.newuser.dlg.content"));

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

}
