package com.drozda.battlecity.appflow;

import com.drozda.fx.controller.MainMenu;
import com.drozda.fx.dialog.LoginRequestDialog;
import com.drozda.fx.skeleton.MainApp;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 19.09.2015.
 */
public class StateChangeListener implements ChangeListener<YabcState> {

    private static final Logger log = LoggerFactory.getLogger(StateChangeListener.class);

    @Override
    public void changed(ObservableValue<? extends YabcState> observable, YabcState oldValue, YabcState newValue) {
        if(oldValue==null){ // application initialization
            YabcAppModel.getBaseApp().setSubScene(YabcState.MainMenu.getYabcFrame().getRoot());
            return;
        }

        if (oldValue.equals(YabcState.MainMenu)) {
            if (newValue.equals(YabcState.Battle)) {
                if (YabcAppModel.needLogin()) {
                    loginRequest();
                }
            }
        }
    }

    private void loginRequest() {

        LoginRequestDialog dlg = new LoginRequestDialog(null);

        // LoginDialog dlg = new LoginDialog(null, null);
        // configureSampleDialog(dlg, "");
        dlg.showAndWait().ifPresent(result -> log.info("Result is " + result));
        YabcAppModel.changeUser();
    }
}
