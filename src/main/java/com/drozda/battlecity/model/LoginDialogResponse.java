package com.drozda.battlecity.model;

/**
 * Created by GFH on 22.09.2015.
 */
public class LoginDialogResponse {
    private final YabcUser userInfo;
    private final boolean unknownNormal;

    public LoginDialogResponse(String left, String middle, Integer right, boolean unknownNormal) {
        this.userInfo = new YabcUser(left, middle, right);
        this.unknownNormal = unknownNormal;
    }

    public YabcUser getUserInfo() {
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
