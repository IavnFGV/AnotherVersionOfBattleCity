package com.drozda.appflow.config;

import com.drozda.model.AppTeam;
import com.drozda.model.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by GFH on 05.10.2015.
 */
@XmlRootElement
public class FileAppData implements AppData {
    @XmlTransient
    private static final Logger log = LoggerFactory.getLogger(FileAppData.class);

    private boolean rememberMe;
    private AppUser lastUser;
    @XmlTransient
    private List<AppUser> appUsers;
    @XmlTransient
    private List<AppTeam> appTeams;

    public static FileAppData loadConfig(String path) {
        File file = checkPath(path, getConfigFilename());
        if (file == null) {
            return null;
        }
        try {
            JAXBContext context = JAXBContext.newInstance(FileAppData.class);
            Unmarshaller um = context.createUnmarshaller();
            FileAppData appData = (FileAppData) um.unmarshal(file);
            appData.appTeams = loadAppTeams(path);
            appData.appUsers = loadAppUsers(path);
            return appData;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private static File checkPath(String path, String filename) {
        if (path == null) {
            path = getConfigPath() + filename;
        }
        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        return file;
    }

    public static List<AppUser> loadAppUsers(String path) {
        File file = checkPath(path, getUsersFilename());
        if (file == null) {
            return null;
        }
        try {
            JAXBContext context = JAXBContext.newInstance(AppUserWithoutTeamListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();
            AppUserWithoutTeamListWrapper wrapper = (AppUserWithoutTeamListWrapper) um.unmarshal(file);
            return wrapper.getPersons();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static void saveConfig(AppData config) {
        log.debug("FileAppData.saveConfig with parameters " + "config = [" + config + "]");
        try {
            if (config == null) {
                throw new NullPointerException("FileAppData is null");
            }

            File file = new File(getConfigPath());
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(getConfigPath() + getConfigFilename());

            JAXBContext context = JAXBContext.newInstance(FileAppData.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(config, file);
            if (config.getAppTeams() != null) saveAppTeams(config.getAppTeams());
            if (config.getAppUsers() != null) saveAppUsers(config.getAppUsers());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private static String getConfigPath() {
        return System.getProperty("user.home") + File.separator + ".Tanks" + File.separator;
    }

    private static String getConfigFilename() {
        return "settings.xml";
    }

    public static void saveAppTeams(List<AppTeam> appTeams) {
        log.debug("FileAppData.saveAppTeams with parameters " + "appTeams = [" + appTeams + "]");
        AppTeamWrapper wrapper = new AppTeamWrapper(appTeams);
        try {
            if (appTeams == null) {
                throw new NullPointerException("appTeams is null");
            }

            File file = new File(getConfigPath());
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(getConfigPath() + getTeamsFilename());

            JAXBContext context = JAXBContext.newInstance(AppTeamWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(wrapper, file);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static void saveAppUsers(List<AppUser> appUsers) {
        log.debug("FileAppData.saveAppUsers with parameters " + "appUsers = [" + appUsers + "]");
        AppUserWithoutTeamListWrapper wrapper = new AppUserWithoutTeamListWrapper(appUsers);
        try {
            if (appUsers == null) {
                throw new NullPointerException("appUsers is null");
            }

            File file = new File(getConfigPath());
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(getConfigPath() + getUsersFilename());

            JAXBContext context = JAXBContext.newInstance(AppUserWithoutTeamListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(wrapper, file);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private static String getTeamsFilename() {
        return "teams.xml";
    }

    private static String getUsersFilename() {
        return "users.xml";
    }

    public static List<AppTeam> loadAppTeams(String path) {
        File file = checkPath(path, getTeamsFilename());
        if (file == null) {
            return null;
        }
        try {
            JAXBContext context = JAXBContext.newInstance(AppTeamWrapper.class);
            Unmarshaller um = context.createUnmarshaller();
            AppTeamWrapper wrapper = (AppTeamWrapper) um.unmarshal(file);
            return wrapper.getTeams();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public String toString() {
        return "FileAppData{" +
                "rememberMe=" + rememberMe +
                ", lastUser=" + lastUser +
                ", appUsers=" + appUsers +
                ", appTeams=" + appTeams +
                '}';
    }

    @Override
    public CheckLoginStatus checkLoginAndPassword(String login, int passwordHash) {
        if (!isLoginExists(login)) {
            return CheckLoginStatus.UNKNOWN_LOGIN;
        }
        if (!getAppUsers().stream().filter(appUser1 -> appUser1.getLogin().equals(login)).findFirst().get()
                .getPasswordHash().equals(passwordHash)) {
            return CheckLoginStatus.WRONG_PASSWORD;
        }
        return CheckLoginStatus.OK;
    }

    @Override
    public boolean isLoginExists(String login) {
        return getAppUsers().stream().
                map(appUser1 -> appUser1.getLogin()).
                collect(Collectors.toSet()).contains(login);
    }

    public List<AppUser> getAppUsers() {
        return appUsers;
    }

    @Override
    public boolean isTeamExists(String teamName) {
        return (getAppTeams().stream().anyMatch(appTeam -> appTeam.getName().equals(teamName)));
    }

    public List<AppTeam> getAppTeams() {
        return appTeams;
    }


    @Override
    public AppUser getLastUser() {
        return lastUser;
    }

    public void setLastUser(AppUser lastUser) {
        this.lastUser = lastUser;
    }

    @Override
    public boolean addUser(AppUser appUser) {
        if (isLoginExists(appUser.getLogin()))
            return false;
        getAppUsers().add(appUser);
        return getAppUsers().contains(appUser);//TODO
    }

    @Override
    public boolean addTeam(AppTeam appTeam) {
        if (isTeamExists(appTeam.getName())) return false;
        getAppTeams().add(appTeam);
        return getAppTeams().contains(appTeam);
    }

    @Override
    public boolean isRememberMe() {
        return rememberMe;
    }

    @Override
    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }


}
