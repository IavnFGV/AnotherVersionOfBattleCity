package com.drozda.fx.dialog;

import com.drozda.YabcLocalization;
import com.drozda.model.AppUser;
import com.drozda.model.LoginDialogResponse;
import impl.org.controlsfx.i18n.Localization;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;

/**
 * Created by GFH on 20.09.2015.
 */
public class LoginDialog extends Dialog<LoginDialogResponse> {
    private final ButtonType loginButtonType;
    private final CustomTextField txUserName;
    private final CustomTextField txTeam;
    private final CustomPasswordField txPassword;
    private final CheckBox cbUnknowIsNormal;


    LoginDialog(AppUser initialUserInfo, boolean initialUnknowNormal) {
        DialogPane dialogPane = this.getDialogPane();
        this.setTitle(Localization.getString("login.dlg.title"));
        dialogPane.setHeaderText(Localization.getString("login.dlg.header"));
        dialogPane.getStyleClass().add("login-dialog");
        dialogPane.getStylesheets().add(org.controlsfx.dialog.LoginDialog.class.getResource("dialogs.css").toExternalForm());
        dialogPane.getButtonTypes().addAll(new ButtonType[]{ButtonType.CANCEL});
        this.txUserName = (CustomTextField) TextFields.createClearableTextField();
        this.txUserName.setLeft(new ImageView(org.controlsfx.dialog.LoginDialog.class.getResource("/org/controlsfx/dialog/user.png").toExternalForm()));
        this.txTeam = (CustomTextField) TextFields.createClearableTextField();
        this.txTeam.setLeft(new ImageView(LoginDialog.class.getResource("/com/drozda/fx/dialog/team.png")
                .toExternalForm()));

        this.txPassword = (CustomPasswordField) TextFields.createClearablePasswordField();
        this.txPassword.setLeft(new ImageView(org.controlsfx.dialog.LoginDialog.class.getResource("/org/controlsfx/dialog/lock.png").toExternalForm()));
        this.cbUnknowIsNormal = new CheckBox(YabcLocalization.getString("login.dlg.cbunknowisnormal.text"));
        this.cbUnknowIsNormal.selectedProperty().addListener((observable, oldValue, newValue) -> {
            this.txPassword.setDisable(newValue);
            this.txTeam.setDisable(newValue);
            this.txUserName.setDisable(newValue);
        });
        this.cbUnknowIsNormal.setSelected(initialUnknowNormal);
        Label lbMessage = new Label("");
        lbMessage.getStyleClass().addAll(new String[]{"message-banner"});
        lbMessage.setVisible(false);
        lbMessage.setManaged(false);
        VBox content = new VBox(10.0D);
        content.getChildren().add(lbMessage);
        content.getChildren().add(this.txUserName);
        content.getChildren().add(this.txTeam);
        content.getChildren().add(this.txPassword);
        content.getChildren().add(this.cbUnknowIsNormal);
        dialogPane.setContent(content);
        this.loginButtonType = new ButtonType(Localization.getString("login.dlg.login.button"), ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().addAll(new ButtonType[]{this.loginButtonType});
        Button loginButton = (Button) dialogPane.lookupButton(this.loginButtonType);
        loginButton.setOnAction((actionEvent) -> {
            try {
                lbMessage.setVisible(false);
                lbMessage.setManaged(false);
                this.hide();
            } catch (Throwable var5) {
                lbMessage.setVisible(true);
                lbMessage.setManaged(true);
                lbMessage.setText(var5.getMessage());
                var5.printStackTrace();
            }

        });
        String userNameCation = Localization.getString("login.dlg.user.caption");
        String teamCation = YabcLocalization.getString("login.dlg.team.caption");
        String passwordCaption = Localization.getString("login.dlg.pswd.caption");

        if (initialUserInfo != null) {
            this.txUserName.setText(initialUserInfo.getLogin());
            this.txTeam.setText(initialUserInfo.getTeam());
            this.txPassword.setText("pass");
        }
        this.txUserName.setPromptText(userNameCation);
        this.txTeam.setPromptText(teamCation);
        this.txPassword.setPromptText(passwordCaption);

        this.setResultConverter((dialogButton) ->
                dialogButton == this.loginButtonType ?
                        new LoginDialogResponse(this.txUserName.getText(), this.txTeam.getText(),
                                initialUserInfo != null ?
                                        initialUserInfo.getPasswordHash() :
                                        this.txPassword.getText().hashCode(), this.cbUnknowIsNormal.isSelected())
                        : null);
    }
}
