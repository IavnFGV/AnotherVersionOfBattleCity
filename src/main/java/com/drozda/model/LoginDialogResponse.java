package com.drozda.model;

/**
 * Created by GFH on 22.09.2015.
 */
public class LoginDialogResponse {
    private final AppUser userInfo;
    private final boolean unknownNormal;

    public LoginDialogResponse(String left, String middle, Integer right, boolean unknownNormal) {
        this.userInfo = new AppUser(left, middle, right);
        this.unknownNormal = unknownNormal;
    }

    public AppUser getUserInfo() {
        return userInfo;
    }

    public boolean isUnknownNormal() {
        return unknownNormal;
    }

    @Override
    public String toString() {
        return "LoginDialogResponse{" +
                "userInfo=" + userInfo +
                ", unknownNormal=" + unknownNormal +
                '}';
    }
}
