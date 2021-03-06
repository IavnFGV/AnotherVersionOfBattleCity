package com.drozda.fx.dialog;

import com.drozda.YabcLocalization;
import com.drozda.appflow.AppModel;
import com.drozda.appflow.AppState;
import com.drozda.appflow.config.AppData;
import com.drozda.model.*;
import javafx.application.Platform;
import javafx.scene.control.Control;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.ValidationSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * Created by GFH on 21.09.2015.
 */
public class Dialog<T> extends javafx.scene.control.Dialog<T> {
    private static final Logger log = LoggerFactory.getLogger(Dialog.class);

    static protected Effect invalidControlEffect;

    static {
        int depth = 70; //Setting the uniform variable for the glow width and height
        DropShadow borderGlow = new DropShadow();
        borderGlow.setOffsetY(0f);
        borderGlow.setOffsetX(0f);
        borderGlow.setColor(Color.RED);
        borderGlow.setWidth(depth);
        borderGlow.setHeight(depth);
        invalidControlEffect = borderGlow;
    }

    protected final ValidationSupport validationSupport = new ValidationSupport();

    public static NewUserDialogResponse showNewUserDialog(Consumer<NewUserDialogResponse> consumer, String
            initialLogin, AppData appData) {
        NewUserDialog dlg = new NewUserDialog(new NewUserDialogRequest(initialLogin), appData);
        dlg.showAndWait();
        consumer.accept(dlg.getResult());
        log.info("Result is " + dlg.getResult());
        return dlg.getResult();
    }

    public static LoginDialogResponse showLoginDialog(Predicate<AppState> canShow, AppState state,
                                                      Consumer<LoginDialogResponse> consumer,
                                                      List<AppUser> possibleUsers,
                                                      List<AppTeam> possibleTeams) {
        return showLoginDialog(canShow,
                state,
                consumer,
                possibleUsers != null ?
                        new HashSet<>(possibleUsers.stream().map(appUser -> appUser.getLogin()).collect(Collectors.toSet())) :
                        null,
                possibleTeams != null ?
                        new HashSet<>(possibleTeams.stream().map(appTeam -> appTeam.getName()).collect(Collectors.toSet())) :
                        null);
    }

    public static LoginDialogResponse showLoginDialog(Predicate<AppState> canShow, AppState state,
                                                      Consumer<LoginDialogResponse> consumer,
                                                      Set<String> possibleUsers,
                                                      Set<String> possibleTeams) {
        if (canShow != null && state != null) {
            if (!canShow.test(state)) {
                AppModel.setMessageString(format(YabcLocalization.getString("statusbar.cant.change.user"),
                        AppModel.getState()));
                return null;
            }
        }
        LoginDialog dlg = new LoginDialog(
                new LoginDialogRequest(AppModel.getCurrentUser(),
                        AppModel.isUnknownNormal() && AppModel.getCurrentUser() == null,
                        possibleUsers,
                        possibleTeams));
        dlg.showAndWait();
        consumer.accept(dlg.getResult());
        log.info("Result is " + dlg.getResult());
        return dlg.getResult();
    }

    protected List<Control> getInvalidControls() {
        Platform.runLater(() -> validationSupport.redecorate());
        return validationSupport.getValidationResult().getErrors().stream().map(ValidationMessage::getTarget).collect
                (Collectors.toList());
    }

    protected void markControls(List<Control> controls, Effect effect) {
        log.debug("Dialog.markControls with parameters " + "controls = [" + controls + "], effect = [" + effect + "]");
        Platform.runLater(() -> controls.forEach(control -> control.setEffect(effect)));
    }

    protected void markAsInvalid(List<Control> controls) {
        log.debug("Dialog.markAsInvalid with parameters " + "controls = [" + controls + "]");
        Platform.runLater(() -> controls.forEach(control -> control.setEffect(invalidControlEffect)));
    }
}
