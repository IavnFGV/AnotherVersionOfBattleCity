package com.drozda.model;

import java.util.Set;

public class LoginDialogRequest {
    private final AppUser initialUserInfo;
    private final boolean initialUnknowNormal;
    private final Set<String> possibleUsers;
    private final Set<String> possibleTeams;

    public LoginDialogRequest(AppUser initialUserInfo, boolean initialUnknowNormal,
                              Set<String> possibleUsers, Set<String> possibleTeams) {
        this.initialUserInfo = initialUserInfo;
        this.initialUnknowNormal = initialUnknowNormal;
        this.possibleUsers = possibleUsers;
        this.possibleTeams = possibleTeams;
    }

    public Set<String> getPossibleUsers() {
        return possibleUsers;
    }

    public Set<String> getPossibleTeams() {
        return possibleTeams;
    }

    public AppUser getInitialUserInfo() {
        return initialUserInfo;
    }

    public boolean isInitialUnknowNormal() {
        return initialUnknowNormal;
    }
}
