package com.drozda.battlecity.appflow;

import com.drozda.fx.dialog.Dialog;
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
        if (oldValue == null) { // application initialization
            YabcAppModel.getBaseApp().setSubScene(YabcState.MainMenu.getYabcFrame().getRoot());
            return;
        }

        if (oldValue.equals(YabcState.MainMenu)) {
            if (newValue.equals(YabcState.Battle)) {
                if (!YabcAppModel.isUnknownNormal()) {
                    loginRequest(oldValue);
                }
            }
        }
    }

    private void loginRequest(YabcState state) {
        Dialog.ShowLoginDialog(YabcAppModel::changeUserPredicate, state);
    }
}
