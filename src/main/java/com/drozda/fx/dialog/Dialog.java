package com.drozda.fx.dialog;

import com.drozda.YabcLocalization;
import com.drozda.appflow.AppModel;
import com.drozda.appflow.AppState;
import com.drozda.model.LoginDialogResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.lang.String.format;

/**
 * Created by GFH on 21.09.2015.
 */
public class Dialog {
    private static final Logger log = LoggerFactory.getLogger(Dialog.class);


    public static LoginDialogResponse ShowLoginDialog(Predicate<AppState> canShow, AppState state,
                                                      Consumer<LoginDialogResponse> consumer) {
        if (canShow != null && state != null) {
            if (!canShow.test(state)) {
                AppModel.setMessageString(format(YabcLocalization.getString("statusbar.cant.change.user"),
                        AppModel.getState()));
                return null;
            }
        }
        LoginDialog dlg = new LoginDialog(AppModel.getCurrentUser(),
                AppModel.isUnknownNormal() && AppModel.getCurrentUser() == null);
        dlg.showAndWait();
        consumer.accept(dlg.getResult());
        log.info("Result is " + dlg.getResult());
        return dlg.getResult();
    }
}
