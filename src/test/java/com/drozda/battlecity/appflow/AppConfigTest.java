package com.drozda.battlecity.appflow;

import com.drozda.appflow.config.AppConfig;
import com.drozda.model.AppTeam;
import com.drozda.model.AppUser;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GFH on 05.10.2015.
 */
public class AppConfigTest {
    @Test
    public void saveConfig() {
        AppConfig appConfig = new AppConfig();
        appConfig.setRememberMe(false);
        AppConfig.saveConfig(appConfig);
    }

    @Test
    public void loadConfig() {
        AppConfig appConfig = AppConfig.loadConfig(null);
        System.out.println(appConfig);
        appConfig = AppConfig.loadConfig("C:/");
        System.out.println(appConfig);

    }

    @Test
    public void saveUsers() {
        List<AppUser> appUsers = new ArrayList<>();
        appUsers.add(new AppUser("left1", "middle1", 1));
        appUsers.add(new AppUser("left2", "middle2", 1));
        appUsers.add(new AppUser("left3", "middle3", 1));

        AppConfig.saveAppUsers(appUsers);
    }

    @Test
    public void loadUsers() {
        List<AppUser> appUsers = AppConfig.loadAppUsers(null);
        System.out.println(appUsers);
        appUsers = AppConfig.loadAppUsers("C:/");
        System.out.println(appUsers);

    }


    @Test
    public void saveTeams() {
        List<AppTeam> appTeams = new ArrayList<>();
        appTeams.add(new AppTeam("PeaceHogs"));
        appTeams.add(new AppTeam("WarHogs"));
        appTeams.add(new AppTeam("Dummies"));
        appTeams.add(new AppTeam("Nuts"));

        AppConfig.saveAppTeams(appTeams);
    }

    @Test
    public void loadTeams() {
        List<AppTeam> appTeams = AppConfig.loadAppTeams(null);
        System.out.println(appTeams);
        appTeams = AppConfig.loadAppTeams("C:/");
        System.out.println(appTeams);

    }

}
