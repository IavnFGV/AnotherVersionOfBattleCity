package com.drozda.battlecity.appflow;

import com.drozda.appflow.config.FileAppData;
import com.drozda.model.AppTeam;
import com.drozda.model.AppUser;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GFH on 05.10.2015.
 */
public class AppDataTest {
    @Test
    public void saveConfig() {
        FileAppData appData = new FileAppData();
        appData.setRememberMe(false);
        appData.setLastUser(new AppUser("Test", "madHead", 56544));
        FileAppData.saveConfig(appData);
    }

    @Test
    public void loadConfig() {
        FileAppData appData = FileAppData.loadConfig(null);
        System.out.println(appData);
        appData = FileAppData.loadConfig("C:/");
        System.out.println(appData);

    }

    @Test
    public void saveUsers() {
        List<AppUser> appUsers = new ArrayList<>();
        appUsers.add(new AppUser("left1", "middle1", 1));
        appUsers.add(new AppUser("left2", "middle2", 1));
        appUsers.add(new AppUser("left3", "middle3", 1));

        FileAppData.saveAppUsers(appUsers);
    }

    @Test
    public void loadUsers() {
        List<AppUser> appUsers = FileAppData.loadAppUsers(null);
        System.out.println(appUsers);
        appUsers = FileAppData.loadAppUsers("C:/");
        System.out.println(appUsers);

    }


    @Test
    public void saveTeams() {
        List<AppTeam> appTeams = new ArrayList<>();
        appTeams.add(new AppTeam("PeaceHogs"));
        appTeams.add(new AppTeam("WarHogs"));
        appTeams.add(new AppTeam("Dummies"));
        appTeams.add(new AppTeam("Nuts"));

        FileAppData.saveAppTeams(appTeams);
    }

    @Test
    public void loadTeams() {
        List<AppTeam> appTeams = FileAppData.loadAppTeams(null);
        System.out.println(appTeams);
        appTeams = FileAppData.loadAppTeams("C:/");
        System.out.println(appTeams);

    }

}
