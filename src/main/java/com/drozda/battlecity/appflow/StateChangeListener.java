package com.drozda.battlecity.appflow;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.controlsfx.dialog.LoginDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 19.09.2015.
 */
public class StateChangeListener implements ChangeListener<YabcState> {

    private static final Logger log = LoggerFactory.getLogger(StateChangeListener.class);

    @Override
    public void changed(ObservableValue<? extends YabcState> observable, YabcState oldValue, YabcState newValue) {
        if (oldValue.equals(new YabcState.MainMenu())) {
            if (newValue.equals(new YabcState.Battle())) {
                if (YabcAppModel.needLogin()) {
                    loginRequest();
                }
            }
        }
    }

    private void loginRequest() {
        LoginDialog dlg = new LoginDialog(null, null);
        // configureSampleDialog(dlg, "");
        dlg.showAndWait().ifPresent(result -> log.info("Result is " + result));
        YabcAppModel.changeUser();
    }
}
