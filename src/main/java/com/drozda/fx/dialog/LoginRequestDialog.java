package com.drozda.fx.dialog;

import com.drozda.YabcLocalization;
import com.drozda.battlecity.model.Triple;
import impl.org.controlsfx.i18n.Localization;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.dialog.LoginDialog;
import org.controlsfx.validation.ValidationSupport;

/**
 * Created by GFH on 20.09.2015.
 */
public class LoginRequestDialog extends Dialog<Triple> {
    private final ButtonType loginButtonType;
    private final CustomTextField txUserName;
    private final CustomTextField txTeam;
    private final CustomPasswordField txPassword;


    public LoginRequestDialog(Triple initialUserInfo) {
        DialogPane dialogPane = this.getDialogPane();
        this.setTitle(Localization.getString("login.dlg.title"));
        dialogPane.setHeaderText(Localization.getString("login.dlg.header"));
        dialogPane.getStyleClass().add("login-dialog");
        dialogPane.getStylesheets().add(LoginDialog.class.getResource("dialogs.css").toExternalForm());
        dialogPane.getButtonTypes().addAll(new ButtonType[]{ButtonType.CANCEL});
        this.txUserName = (CustomTextField) TextFields.createClearableTextField();
        this.txUserName.setLeft(new ImageView(LoginDialog.class.getResource("/org/controlsfx/dialog/user.png").toExternalForm()));
        this.txTeam = (CustomTextField) TextFields.createClearableTextField();
        this.txTeam.setLeft(new ImageView(LoginRequestDialog.class.getResource("/com/drozda/fx/dialog/team.png")
                .toExternalForm()));

        this.txPassword = (CustomPasswordField) TextFields.createClearablePasswordField();
        this.txPassword.setLeft(new ImageView(LoginDialog.class.getResource("/org/controlsfx/dialog/lock.png").toExternalForm()));
        Label lbMessage = new Label("");
        lbMessage.getStyleClass().addAll(new String[]{"message-banner"});
        lbMessage.setVisible(false);
        lbMessage.setManaged(false);
        VBox content = new VBox(10.0D);
        content.getChildren().add(lbMessage);
        content.getChildren().add(this.txUserName);
        content.getChildren().add(this.txTeam);
        content.getChildren().add(this.txPassword);
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

        this.txUserName.setPromptText(userNameCation);
        this.txUserName.setText(initialUserInfo == null ? "" : (String) initialUserInfo.getLogin());
        this.txTeam.setPromptText(teamCation);
        this.txTeam.setText(initialUserInfo == null ? "" : (String) initialUserInfo.getTeam());
        this.txPassword.setPromptText(passwordCaption);
        this.txPassword.setText(new String(initialUserInfo == null ? "" : (String) initialUserInfo.getPassword()));
        ValidationSupport validationSupport = new ValidationSupport();
        this.setResultConverter((dialogButton) -> {
            return dialogButton == this.loginButtonType ? new Triple(this.txUserName.getText(), this.txTeam.getText(), this
                    .txPassword.getText())
                    : null;
        });
    }
}
