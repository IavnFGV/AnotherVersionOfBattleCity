package com.drozda.appflow.config;

import com.drozda.model.AppTeam;
import com.drozda.model.AppUser;

import java.util.List;

/**
 * Created by GFH on 17.10.2015.
 */
public interface AppData {
    static AppData getAppData(DataStorage dataStorage) {
        switch (dataStorage) {
            case LOCAL_STORAGE:
                return FileAppData.loadConfig(null);
            default:
                throw new RuntimeException("Unknown DataStorage");
        }
    }

    boolean isTeamExists(String teamName);

    CheckLoginStatus checkLoginAndPassword(String login, int passwordHash);

    boolean isLoginExists(String login);

    AppUser getLastUser();

    void setLastUser(AppUser lastUser);

    boolean addUser(AppUser appUser);

    boolean addTeam(AppTeam appTeam);

    boolean isRememberMe();

    void setRememberMe(boolean rememberMe);

    List<AppTeam> getAppTeams();

    List<AppUser> getAppUsers();

    enum CheckLoginStatus {
        OK,
        WRONG_PASSWORD,
        UNKNOWN_LOGIN
    }

    enum DataStorage {
        LOCAL_STORAGE
    }
}
