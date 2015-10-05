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

/**
 * Created by GFH on 05.10.2015.
 */
@XmlRootElement
public class AppConfig {
    @XmlTransient
    private static final Logger log = LoggerFactory.getLogger(AppConfig.class);

    private boolean rememberMe;

    public static AppConfig loadConfig(String path) {
        File file = checkPath(path, getConfigFilename());
        if (file == null) {
            return null;
        }
        try {
            JAXBContext context = JAXBContext.newInstance(AppConfig.class);
            Unmarshaller um = context.createUnmarshaller();
            AppConfig appConfig = (AppConfig) um.unmarshal(file);
            return appConfig;
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

    private static String getConfigFilename() {
        return "settings.xml";
    }

    private static String getConfigPath() {
        return System.getProperty("user.home") + File.separator + ".Tanks" + File.separator;
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

    private static String getUsersFilename() {
        return "users.xml";
    }

    public static void saveAppUsers(List<AppUser> appUsers) {
        log.debug("AppConfig.saveAppUsers with parameters " + "appUsers = [" + appUsers + "]");
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

    public static void saveConfig(AppConfig config) {
        log.debug("AppConfig.saveConfig with parameters " + "config = [" + config + "]");
        try {
            if (config == null) {
                throw new NullPointerException("AppConfig is null");
            }

            File file = new File(getConfigPath());
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(getConfigPath() + getConfigFilename());

            JAXBContext context = JAXBContext.newInstance(AppConfig.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(config, file);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
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

    private static String getTeamsFilename() {
        return "users.xml";
    }

    public static void saveAppTeams(List<AppTeam> appTeams) {
        log.debug("AppConfig.saveAppTeams with parameters " + "appTeams = [" + appTeams + "]");
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

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    @Override
    public String toString() {
        return "AppConfig{" +
                "rememberMe=" + rememberMe +
                '}';
    }


}
