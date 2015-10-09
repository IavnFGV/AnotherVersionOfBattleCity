package com.drozda.fx.dialog;

import com.drozda.YabcLocalization;
import com.drozda.appflow.AppModel;
import com.drozda.appflow.AppState;
import com.drozda.model.AppTeam;
import com.drozda.model.AppUser;
import com.drozda.model.LoginDialogRequest;
import com.drozda.model.LoginDialogResponse;
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
public class Dialog {
    private static final Logger log = LoggerFactory.getLogger(Dialog.class);

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
}
