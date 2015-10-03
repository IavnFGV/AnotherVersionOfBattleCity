package com.drozda.appflow;

import com.drozda.fx.dialog.Dialog;
import com.drozda.model.LoginDialogResponse;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 19.09.2015.
 */
public class StateChangeListener implements ChangeListener<AppState> {

    private static final Logger log = LoggerFactory.getLogger(StateChangeListener.class);

    private boolean reverseState;

    @Override
    public void changed(ObservableValue<? extends AppState> observable, AppState oldValue, AppState newValue) {
        if (oldValue == null) { // application initialization
            AppModel.getBaseApp().setSubScene(AppState.MainMenu.getYabcFrame().getRoot());
            AppModel.getBaseApp().setSubScene(AppState.Battle.getYabcFrame().getRoot());

            return;
        }
        if (oldValue.equals(AppState.MainMenu) && newValue.equals(AppState.Battle)) {
//            while (AppModel.isLoginRequired()) {
//                LoginDialogResponse loginDialogResponse = loginRequest(oldValue);
//                if (loginDialogResponse == null) {
//                    toggleReverseState();
//                    AppModel.setState(oldValue);
//                    break;
//                }
//            }
//            if (!AppModel.isLoginRequired()) {
            AppModel.getBaseApp().setSubScene(AppState.Battle.getYabcFrame().getRoot());
            AppModel.getMainStage().getScene().setFill(new Color(0, 0, 0, 0));

            log.debug("FUCK111sadsadsadsadsad1!!!");
            return;
//            }

        }
        if (oldValue.equals(AppState.Battle) && newValue.equals(AppState.MainMenu)) {
            if (toggleReverseState()) {
                log.debug("FUCK1111!!!");
                return;
            }
            log.debug("FUCK!!!");
        }
//      }
    }

    private boolean toggleReverseState() {// if we have to go to prev state without checking old and new values
        reverseState = !reverseState;
        return !reverseState;
    }

    private LoginDialogResponse loginRequest(AppState state) {
        return Dialog.ShowLoginDialog(AppModel::changeUserPredicate, state, AppModel::changeUser);
    }
}
