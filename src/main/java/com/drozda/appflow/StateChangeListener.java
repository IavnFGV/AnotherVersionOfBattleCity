package com.drozda.appflow;

import com.drozda.fx.dialog.Dialog;
import com.drozda.model.LoginDialogResponse;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 19.09.2015.
 */
public class StateChangeListener implements ChangeListener<AppState> {

    private static final Logger log = LoggerFactory.getLogger(StateChangeListener.class);

    private boolean disableBattleToMainMenu;

    @Override
    public void changed(ObservableValue<? extends AppState> observable, AppState oldValue, AppState newValue) {
        if (oldValue == null) { // application initialization
            AppModel.getBaseApp().setSubScene(AppState.MainMenu.getYabcFrame().getRoot());
            return;
        }
        if (oldValue.equals(AppState.MainMenu) && newValue.equals(AppState.Battle)) {
            while (AppModel.isLoginRequired()) {
                LoginDialogResponse loginDialogResponse = loginRequest(oldValue);
                if (loginDialogResponse == null) {
                    setDisableBattleToMainMenu(true);
                    AppModel.setState(oldValue);
                    break;
                }
            }
            if (!AppModel.isLoginRequired()) {
                AppModel.getBaseApp().setSubScene(AppState.Battle.getYabcFrame().getRoot());

                log.debug("FUCK111sadsadsadsadsad1!!!");
                return;
            }

        }
        if (oldValue.equals(AppState.Battle) && newValue.equals(AppState.MainMenu)) {
            if (isDisableBattleToMainMenu()) {
                log.debug("FUCK1111!!!");
                setDisableBattleToMainMenu(false);
                return;
            }
            log.debug("FUCK!!!");
        }
//      }
    }

    private LoginDialogResponse loginRequest(AppState state) {
        return Dialog.showLoginDialog(AppModel::changeUserPredicate,
                state,
                AppModel::processLoginDialogResponse,
                AppModel.appData.getAppUsers(),
                AppModel.appData.getAppTeams());
    }

    private boolean isDisableBattleToMainMenu() {
        return disableBattleToMainMenu;
    }

    private void setDisableBattleToMainMenu(boolean disableBattleToMainMenu) {
        this.disableBattleToMainMenu = disableBattleToMainMenu;
    }
}
