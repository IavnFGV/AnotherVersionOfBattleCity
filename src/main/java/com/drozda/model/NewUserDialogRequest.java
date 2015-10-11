package com.drozda.model;

/**
 * Created by GFH on 11.10.2015.
 */
public class NewUserDialogRequest {
    final String initialLogin;

    public NewUserDialogRequest(String initialLogin) {
        this.initialLogin = initialLogin;
    }

    public String getInitialLogin() {
        return initialLogin;
    }
}
