package com.drozda.fx.dialog;

import com.drozda.YabcLocalization;
import com.drozda.battlecity.appflow.YabcAppModel;
import com.drozda.battlecity.appflow.YabcState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Predicate;

import static java.lang.String.format;

/**
 * Created by GFH on 21.09.2015.
 */
public class Dialog {
    private static final Logger log = LoggerFactory.getLogger(Dialog.class);


    public static void ShowLoginDialog(Predicate<YabcState> canShow, YabcState state) {
        if (canShow != null && state != null) {
            if (!canShow.test(state)) {
                YabcAppModel.setMessageString(format(YabcLocalization.getString("statusbar.cant.change.user"),
                        YabcAppModel.getState()));
                return;
            }
        }
        LoginDialog dlg = new LoginDialog(YabcAppModel.getCurrentUser(), YabcAppModel.isUnknownNormal());
        dlg.showAndWait().ifPresent(result -> log.info("Result is " + result));
        YabcAppModel.changeUser();
    }
}
