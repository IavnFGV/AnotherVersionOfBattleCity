package com.drozda.appflow;

import com.drozda.fx.dialog.Dialog;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 19.09.2015.
 */
public class StateChangeListener implements ChangeListener<AppState> {

    private static final Logger log = LoggerFactory.getLogger(StateChangeListener.class);

    @Override
    public void changed(ObservableValue<? extends AppState> observable, AppState oldValue, AppState newValue) {
        if (oldValue == null) { // application initialization
            AppModel.getBaseApp().setSubScene(AppState.MainMenu.getYabcFrame().getRoot());
            return;
        }

        if (oldValue.equals(AppState.MainMenu)) {
            if (newValue.equals(AppState.Battle)) {
                if (!AppModel.isUnknownNormal()) {
                    loginRequest(oldValue);
                }
            }
        }
    }

    private void loginRequest(AppState state) {
        Dialog.ShowLoginDialog(AppModel::changeUserPredicate, state);
    }
}
