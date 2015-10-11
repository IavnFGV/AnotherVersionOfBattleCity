package com.drozda.model;

/**
 * Created by GFH on 11.10.2015.
 */
public class NewUserDialogResponse {
    private final AppUser user;

    public NewUserDialogResponse(AppUser user) {
        this.user = user;
    }

    public AppUser getUser() {
        return user;
    }
}
