package com.drozda.fx.dialog;

import com.drozda.YabcLocalization;
import com.drozda.model.NewUserDialogRequest;
import com.drozda.model.NewUserDialogResponse;
import impl.org.controlsfx.i18n.Localization;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.decoration.CompoundValidationDecoration;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;
import org.controlsfx.validation.decoration.StyleClassValidationDecoration;
import org.controlsfx.validation.decoration.ValidationDecoration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 11.10.2015.
 */
public class NewUserDialog extends Dialog<NewUserDialogResponse> {
    private static final Logger log = LoggerFactory.getLogger(NewUserDialog.class);

    private final ButtonType acceptUserButtonType;
    private final CustomTextField txUserName;
    private final CustomPasswordField txPassword;
    private final CustomPasswordField txConfirmPassword;
    private final ValidationSupport validationSupport = new ValidationSupport();
    private BooleanBinding fullBinding;

    NewUserDialog(NewUserDialogRequest newUserDialogRequest) {
        DialogPane dialogPane = this.getDialogPane();
        this.setTitle(YabcLocalization.getString("newuser.dlg.title"));
        dialogPane.setHeaderText(YabcLocalization.getString("newuser.dlg.header"));
        dialogPane.getStyleClass().add("command-links-dialog");
        dialogPane.getStylesheets().add(org.controlsfx.dialog.LoginDialog.class.getResource("dialogs.css")
                .toExternalForm());
        dialogPane.getButtonTypes().addAll(ButtonType.CANCEL);
        this.txUserName = (CustomTextField) TextFields.createClearableTextField();
        this.txUserName.setLeft(new ImageView(org.controlsfx.dialog.LoginDialog.class.getResource("/org/controlsfx/dialog/user.png").toExternalForm()));
        this.txPassword = (CustomPasswordField) TextFields.createClearablePasswordField();
        this.txPassword.setLeft(new ImageView(org.controlsfx.dialog.LoginDialog.class.getResource("/org/controlsfx/dialog/lock.png").toExternalForm()));
        this.txConfirmPassword = (CustomPasswordField) TextFields.createClearablePasswordField();
        this.txConfirmPassword.setLeft(new ImageView(org.controlsfx.dialog.LoginDialog.class.getResource("/org/controlsfx/dialog/lock.png").toExternalForm()));

        VBox content = new VBox(10.0D);
        content.getChildren().add(this.txUserName);
        content.getChildren().add(this.txPassword);
        content.getChildren().add(this.txConfirmPassword);
        dialogPane.setContent(content);
        this.acceptUserButtonType = new ButtonType(YabcLocalization.getString("newuser.dlg.accept.button"),
                ButtonBar.ButtonData.APPLY);
        dialogPane.getButtonTypes().addAll(this.acceptUserButtonType);
        //   Button loginButton = (Button) dialogPane.lookupButton(this.acceptUserButtonType);


//        BooleanBinding enoughDataInTextFields = Bindings.and(txUserName.textProperty().isNotEmpty(), txTeam
//                .textProperty().isNotEmpty());
//        enoughDataInTextFields = Bindings.and(enoughDataInTextFields, txPassword.textProperty().isNotEmpty());
//
//        BooleanBinding enoughAndNotCheckbox = Bindings.and(enoughDataInTextFields, cbUnknowIsNormal.selectedProperty()
//                .not());
//        fullBinding = Bindings.or(cbUnknowIsNormal.selectedProperty(), enoughAndNotCheckbox);
//
//        loginButton.setDisable(!fullBinding.get());

//        fullBinding.addListener((observable, oldValue, newValue) -> loginButton.setDisable(!newValue));

        String userNameCaption = Localization.getString("login.dlg.user.caption");
        String passwordCaption = Localization.getString("login.dlg.pswd.caption");
        String confirmPasswordCaption = YabcLocalization.getString("newuser.dlg.pswd.caption");


        if (newUserDialogRequest.getInitialLogin() != null) {
            this.txUserName.setText(newUserDialogRequest.getInitialLogin());
        }
        this.txUserName.setPromptText(userNameCaption);
        this.txPassword.setPromptText(passwordCaption);
        this.txConfirmPassword.setPromptText(confirmPasswordCaption);

        validationSupport.registerValidator(txConfirmPassword, (control, o) -> ValidationResult.fromErrorIf
                (txConfirmPassword, YabcLocalization.getString("newuser.dlg.pswd.passwords.are.not.identical"),
                        !txConfirmPassword.getText().equals(txPassword.getText())));

        ValidationDecoration iconDecorator = new GraphicValidationDecoration();
        ValidationDecoration cssDecorator = new StyleClassValidationDecoration();
        ValidationDecoration compoundDecorator = new CompoundValidationDecoration(cssDecorator, iconDecorator);
        validationSupport.setValidationDecorator(compoundDecorator);

        this.setResultConverter((dialogButton) -> {
            if (dialogButton == this.acceptUserButtonType) {
                return new NewUserDialogResponse();
            }
            return null;
        });
    }
}
