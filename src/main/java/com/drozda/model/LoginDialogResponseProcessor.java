package com.drozda.model;


import com.drozda.YabcLocalization;
import com.drozda.appflow.AppModel;
import com.drozda.appflow.config.AppData;
import com.drozda.fx.dialog.ConfirmationDialog;
import org.apache.commons.chain.Chain;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ChainBase;
import org.apache.commons.chain.impl.ContextBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;


/**
 * Created by GFH on 11.10.2015.
 */
public class LoginDialogResponseProcessor {
    private static final Logger log = LoggerFactory.getLogger(LoginDialogResponseProcessor.class);

    private Command createTeamCommand;
    private Command createUserCommand;
    //  private Command IsProcessComplete;
    private Command setCurrentUserCommand;
    //TODO Password checker
    private Context context;
    private Chain chain;

    public LoginDialogResponseProcessor() {
    }

    public static LoginDialogResponseProcessor newLoginDialogResponseProcessor(LoginDialogResponse dialogResponse, AppData.CheckLoginStatus checkLoginStatus, boolean isTeamExists) {
        LoginDialogResponseProcessor ldrp = new LoginDialogResponseProcessor();
        ldrp.context = new ContextBase();
        ldrp.context.put(LoginDialogResponse.class, dialogResponse);
        ldrp.context.put(AppData.CheckLoginStatus.class, checkLoginStatus);
        ldrp.context.put("isTeamExists", isTeamExists);

        ldrp.chain = new ChainBase();
        ldrp.setCurrentUserCommand = new SetCurrentUserCommand(ldrp.context);
        ldrp.createUserCommand = new CreateUserCommand(ldrp.context);
        ldrp.chain.addCommand(ldrp.setCurrentUserCommand);
        ldrp.chain.addCommand(ldrp.createUserCommand);
        return ldrp;

    }

    public boolean execute() {
        try {
            return chain.execute(context);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    private abstract static class LoginDialogResponseCommand implements Command {
        protected LoginDialogResponse loginDialogResponse;
        protected CheckUsernameAndPasswordResult checkUsernameAndPasswordResult;

        public LoginDialogResponseCommand(Context context) {
            if (context == null) {
                throw new NullPointerException("Context is null");
            }
            this.loginDialogResponse = (LoginDialogResponse) context.get(LoginDialogResponse.class);
            this.checkUsernameAndPasswordResult =
                    (CheckUsernameAndPasswordResult) context.get(CheckUsernameAndPasswordResult.class);
        }

        @Override
        public boolean execute(Context context) throws Exception {
            if (!needExecution(context))
                return false;
            return fullExecute(context);
        }

        protected abstract boolean needExecution(Context context);

        protected abstract boolean fullExecute(Context context);
    }

    private static class SetCurrentUserCommand extends LoginDialogResponseCommand {
        public SetCurrentUserCommand(Context context) {
            super(context);
        }

        @Override
        protected boolean needExecution(Context context) {
            return this.checkUsernameAndPasswordResult.bitSet.get(2)//password is correct;
                    && this.checkUsernameAndPasswordResult.bitSet.get(0);// and login is not new
        }

        @Override
        protected boolean fullExecute(Context context) {
            AppModel.setCurrentUser(loginDialogResponse.getUserInfo());
            return true;
        }


    }

    private static class CreateTeamCommand extends LoginDialogResponseCommand {
        public CreateTeamCommand(Context context) {
            super(context);
        }

        @Override
        protected boolean needExecution(Context context) {
            return !checkUsernameAndPasswordResult.bitSet.get(1);//new team
        }

        @Override
        protected boolean fullExecute(Context context) {


            return false;
        }
    }


    private static class CreateUserCommand extends LoginDialogResponseCommand {
        public CreateUserCommand(Context context) {
            super(context);
        }

        @Override
        protected boolean fullExecute(Context context) {
            if (!ConfirmationDialog.userCreation()) // and dialog
            {
                context.put("STOP", true);
                AppModel.setMessageString(YabcLocalization.getString("statusbar.change.user.to.new.fail"));
                return true;
            }
            AppModel.createNewUser(loginDialogResponse.getUserInfo().getLogin(), loginDialogResponse.getUserInfo().getPasswordHash());
            if (!AppModel.appData.getAppUsers().stream().map(AppUser::getLogin).collect(Collectors.toSet()).contains
                    (loginDialogResponse.getUserInfo().getLogin())) {
                context.put("STOP", true);
                return true;
            }
            return false;
        }

        @Override
        protected boolean needExecution(Context context) {
            return !this.checkUsernameAndPasswordResult.bitSet.get(2)//password is incorrect;
                    && !this.checkUsernameAndPasswordResult.bitSet.get(0)// and login is new
                    && !(boolean) context.getOrDefault("STOP", false);
        }
    }
}
