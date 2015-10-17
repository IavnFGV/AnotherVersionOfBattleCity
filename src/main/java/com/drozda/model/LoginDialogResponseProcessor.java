package com.drozda.model;


import com.drozda.YabcLocalization;
import com.drozda.appflow.AppModel;
import com.drozda.appflow.config.AppData;
import com.drozda.appflow.config.FileAppData;
import com.drozda.fx.dialog.ConfirmationDialog;
import org.apache.commons.chain.Chain;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ChainBase;
import org.apache.commons.chain.impl.ContextBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by GFH on 11.10.2015.
 */
public class LoginDialogResponseProcessor {
    private static final Logger log = LoggerFactory.getLogger(LoginDialogResponseProcessor.class);

    private Command createUserCommand;
    private Command createTeamCommand;
    private Command setCurrentUserCommand;
    private LoginDialogResponseContext context;
    private Chain chain;

    public LoginDialogResponseProcessor() {
    }

    public static LoginDialogResponseProcessor newLoginDialogResponseProcessor(LoginDialogResponse dialogResponse, FileAppData.CheckLoginStatus checkLoginStatus, boolean isTeamExists) {
        LoginDialogResponseProcessor ldrp = new LoginDialogResponseProcessor();
        ldrp.context = new LoginDialogResponseContext(dialogResponse, checkLoginStatus, isTeamExists);

        ldrp.chain = new ChainBase();
        ldrp.setCurrentUserCommand = new SetCurrentUserCommand();
        ldrp.createUserCommand = new CreateUserCommand();
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

    static class LoginDialogResponseContext extends ContextBase {
        protected LoginDialogResponse loginDialogResponse;
        protected AppData.CheckLoginStatus checkLoginStatus;
        protected boolean teamExists;

        public LoginDialogResponseContext(LoginDialogResponse loginDialogResponse, AppData.CheckLoginStatus
                checkLoginStatus, boolean teamExists) {
            super();
            this.loginDialogResponse = loginDialogResponse;
            this.checkLoginStatus = checkLoginStatus;
            this.teamExists = teamExists;
        }
    }

    private abstract static class LoginDialogResponseCommand implements Command {

        @Override
        public boolean execute(Context context) throws Exception {
            if (!needExecution((LoginDialogResponseContext) context))
                return false;
            return fullExecute((LoginDialogResponseContext) context);
        }

        protected abstract boolean needExecution(LoginDialogResponseContext context);

        protected abstract boolean fullExecute(LoginDialogResponseContext context);
    }

    private static class SetCurrentUserCommand extends LoginDialogResponseCommand {

        @Override
        protected boolean needExecution(LoginDialogResponseContext context) {
            return context.checkLoginStatus == AppData.CheckLoginStatus.OK;
        }

        @Override
        protected boolean fullExecute(LoginDialogResponseContext context) {
            AppModel.setCurrentUser(context.loginDialogResponse.getUserInfo());
            return true;
        }


    }

//    private static class CreateTeamCommand extends LoginDialogResponseCommand {
//
//
//    }


    private static class CreateUserCommand extends LoginDialogResponseCommand {

        @Override
        protected boolean fullExecute(LoginDialogResponseContext context) {
            if (!ConfirmationDialog.userCreation()) // and dialog
            {
                AppModel.setMessageString(YabcLocalization.getString("statusbar.change.user.to.new.fail"));
            }
            LoginDialogResponseContext loginDialogResponseContext = context;
            AppModel.createNewUser(loginDialogResponseContext.loginDialogResponse.getUserInfo().getLogin(),
                    loginDialogResponseContext.loginDialogResponse.getUserInfo().getPasswordHash());
            return false;
        }

        @Override
        protected boolean needExecution(LoginDialogResponseContext context) {
            return context.checkLoginStatus == AppData.CheckLoginStatus.UNKNOWN_LOGIN;
        }
    }
}
