package com.drozda.fx.dialog;

import com.drozda.YabcLocalization;
import com.drozda.model.LoginDialogRequest;
import com.drozda.model.LoginDialogResponse;
import impl.org.controlsfx.i18n.Localization;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.drozda.fx.dialog.CleanStyleHelper.cleanStyle;


/**
 * Created by GFH on 20.09.2015.
 */
public class LoginDialog extends Dialog<LoginDialogResponse> {
    private static final Logger log = LoggerFactory.getLogger(LoginDialog.class);
    private final ButtonType loginButtonType;
    private final CustomTextField txUserName;
    private final CustomTextField txTeam;
    private final CustomPasswordField txPassword;
    private final CheckBox cbUnknowIsNormal;
    private BooleanBinding fullBinding;

    // private ChoiceBox choiceBox;
    //ChoiceDialog
    LoginDialog(LoginDialogRequest loginDialogRequest) {
        DialogPane dialogPane = this.getDialogPane();
        this.setTitle(Localization.getString("login.dlg.title"));
        dialogPane.setHeaderText(Localization.getString("login.dlg.header"));
        dialogPane.getStyleClass().add("login-dialog");
        dialogPane.getStylesheets().add(org.controlsfx.dialog.LoginDialog.class.getResource("dialogs.css").toExternalForm());
        dialogPane.getButtonTypes().addAll(ButtonType.CANCEL);
        this.txUserName = (CustomTextField) TextFields.createClearableTextField();
        this.txUserName.setLeft(new ImageView(org.controlsfx.dialog.LoginDialog.class.getResource("/org/controlsfx/dialog/user.png").toExternalForm()));
        this.txTeam = (CustomTextField) TextFields.createClearableTextField();
        this.txTeam.setLeft(new ImageView(LoginDialog.class.getResource("/com/drozda/fx/dialog/team.png")
                .toExternalForm()));
        // this.choiceBox = new ChoiceBox();
        ////  this.choiceBox.getItems().add("s1");
        //  this.choiceBox.getItems().add("s2");
        this.txPassword = (CustomPasswordField) TextFields.createClearablePasswordField();
        this.txPassword.setLeft(new ImageView(org.controlsfx.dialog.LoginDialog.class.getResource("/org/controlsfx/dialog/lock.png").toExternalForm()));
        this.cbUnknowIsNormal = new CheckBox(YabcLocalization.getString("login.dlg.cbunknowisnormal.text"));
        this.cbUnknowIsNormal.selectedProperty().addListener((observable, oldValue, newValue) -> {
            this.txPassword.setDisable(newValue);
            this.txTeam.setDisable(newValue);
            this.txUserName.setDisable(newValue);
        });
        this.cbUnknowIsNormal.setSelected(loginDialogRequest.isInitialUnknowNormal());

        if (loginDialogRequest.getPossibleTeams() != null) {
            cleanStyle(TextFields.bindAutoCompletion(txTeam,
                    loginDialogRequest.getPossibleTeams()));
        }
        if (loginDialogRequest.getPossibleUsers() != null) {
            cleanStyle(TextFields.bindAutoCompletion(txUserName,
                    loginDialogRequest.getPossibleUsers()));
        }
        VBox content = new VBox(10.0D);
        content.getChildren().add(this.txUserName);
        content.getChildren().add(this.txTeam);
        //  content.getChildren().add(this.choiceBox);
        content.getChildren().add(this.txPassword);
        content.getChildren().add(this.cbUnknowIsNormal);
        dialogPane.setContent(content);
        this.loginButtonType = new ButtonType(Localization.getString("login.dlg.login.button"),
                ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().addAll(this.loginButtonType);
        Button loginButton = (Button) dialogPane.lookupButton(this.loginButtonType);


        BooleanBinding enoughDataInTextFields = Bindings.and(txUserName.textProperty().isNotEmpty(), txTeam
                .textProperty().isNotEmpty());
        enoughDataInTextFields = Bindings.and(enoughDataInTextFields, txPassword.textProperty().isNotEmpty());

        BooleanBinding enoughAndNotCheckbox = Bindings.and(enoughDataInTextFields, cbUnknowIsNormal.selectedProperty()
                .not());
        fullBinding = Bindings.or(cbUnknowIsNormal.selectedProperty(), enoughAndNotCheckbox);

        loginButton.setDisable(!fullBinding.get());

        fullBinding.addListener((observable, oldValue, newValue) -> loginButton.setDisable(!newValue));
//        notEnoughDataInTextFields.
//                and(cbUnknowIsNormal.selectedProperty()).
//                or(notEnoughDataInTextFields.not()).addListener((observable, oldValue, newValue) -> {
//            loginButton.setDisable(newValue);
//        });

//        BooleanBinding booleanBinding = new

        String userNameCation = Localization.getString("login.dlg.user.caption");
        String teamCation = YabcLocalization.getString("login.dlg.team.caption");
        String passwordCaption = Localization.getString("login.dlg.pswd.caption");

        if (loginDialogRequest.getInitialUserInfo() != null) {
            this.txUserName.setText(loginDialogRequest.getInitialUserInfo().getLogin());
            this.txTeam.setText(loginDialogRequest.getInitialUserInfo().getTeam());
            this.txPassword.setText("pass");
        }
        this.txUserName.setPromptText(userNameCation);
        this.txTeam.setPromptText(teamCation);
        this.txPassword.setPromptText(passwordCaption);

        this.setResultConverter((dialogButton) -> {
            if (dialogButton == this.loginButtonType) {
                return new LoginDialogResponse(this.txUserName.getText(), this.txTeam.getText(), this.txPassword
                        .getText().hashCode(), this.cbUnknowIsNormal.isSelected());
            }
            return null;
        });
    }
}
