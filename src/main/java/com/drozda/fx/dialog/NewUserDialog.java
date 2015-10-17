package com.drozda.fx.dialog;

import com.drozda.YabcLocalization;
import com.drozda.appflow.config.AppData;
import com.drozda.model.AppUser;
import com.drozda.model.NewUserDialogRequest;
import com.drozda.model.NewUserDialogResponse;
import impl.org.controlsfx.i18n.Localization;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;
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
    private BooleanBinding acceptButtonActivationBinding;

    NewUserDialog(NewUserDialogRequest newUserDialogRequest, AppData appData) {
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
        Button acceptButton = (Button) dialogPane.lookupButton(this.acceptUserButtonType);

        String userNameCaption = Localization.getString("login.dlg.user.caption");
        String passwordCaption = Localization.getString("login.dlg.pswd.caption");
        String confirmPasswordCaption = YabcLocalization.getString("newuser.dlg.pswd.caption");


        if (newUserDialogRequest.getInitialLogin() != null) {
            this.txUserName.setText(newUserDialogRequest.getInitialLogin());
        }
        this.txUserName.setPromptText(userNameCaption);
        this.txPassword.setPromptText(passwordCaption);
        this.txConfirmPassword.setPromptText(confirmPasswordCaption);
        this.txUserName.focusedProperty().addListener((observable, oldValue,
                                                       newValue) ->
                this.txUserName.setEffect(null));
        validationSupport.registerValidator(txUserName, Validator.createEmptyValidator(YabcLocalization.getString
                ("dlg.field.cannot.be.empty")));
        validationSupport.registerValidator(txUserName, (control1, o1) -> ValidationResult.fromErrorIf
                        (txUserName, YabcLocalization.getString("dlg.field.already.exists"), appData.isLoginExists(txUserName
                                .getText()))
        );
        validationSupport.registerValidator(txPassword,
                Validator.createEmptyValidator(YabcLocalization.getString("dlg.field.cannot.be.empty")));
        validationSupport.registerValidator(txConfirmPassword,
                Validator.createEmptyValidator(YabcLocalization.getString("dlg.field.cannot.be.empty")));
        validationSupport.registerValidator(txConfirmPassword, (control, o) -> ValidationResult.fromErrorIf
                (txConfirmPassword, YabcLocalization.getString("newuser.dlg.pswd.passwords.are.not.identical"),
                        !txConfirmPassword.getText().equals(txPassword.getText())));

        ValidationDecoration iconDecorator = new GraphicValidationDecoration();
        validationSupport.setValidationDecorator(iconDecorator);

        BooleanBinding passwordsEmpty = Bindings.or(txPassword.textProperty().isEmpty(), txConfirmPassword
                .textProperty().isEmpty());
        BooleanBinding fieldEmpty = Bindings.or(txUserName.textProperty().isEmpty(), passwordsEmpty);
        BooleanBinding passworsAreEqual = txConfirmPassword.textProperty().isEqualTo(txPassword.textProperty());

        acceptButtonActivationBinding = Bindings.and(passworsAreEqual, fieldEmpty.not());
        acceptButtonActivationBinding.addListener((observable, oldValue, newValue) -> acceptButton.setDisable
                (!newValue));
        acceptButton.addEventFilter(ActionEvent.ACTION, event -> {
            if (!validateInput()) {
                event.consume();
            }
        });
        acceptButton.setDisable(!acceptButtonActivationBinding.get());
        acceptButton.setDefaultButton(true);
        this.setResultConverter((dialogButton) -> {
            if (dialogButton == this.acceptUserButtonType) {
                return new NewUserDialogResponse(new AppUser(txUserName.getText(), null, txPassword.getText().hashCode()));
            }
            return null;
        });
    }

    private boolean validateInput() {
        validationSupport.getValidationResult().getErrors().stream().findFirst().ifPresent(validationMessage -> {

            int depth = 70; //Setting the uniform variable for the glow width and height

            DropShadow borderGlow = new DropShadow();
            borderGlow.setOffsetY(0f);
            borderGlow.setOffsetX(0f);
            borderGlow.setColor(Color.RED);
            borderGlow.setWidth(depth);
            borderGlow.setHeight(depth);
            validationMessage.getTarget().setEffect(borderGlow); //
        });

        return validationSupport.getValidationResult().getErrors().isEmpty();
    }

}
