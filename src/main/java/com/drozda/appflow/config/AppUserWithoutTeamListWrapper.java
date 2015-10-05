package com.drozda.appflow.config;

import com.drozda.model.AppUser;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by GFH on 05.10.2015.
 */
@XmlRootElement(name = "users")
public class AppUserWithoutTeamListWrapper {
    private List<AppUser> users;

    public AppUserWithoutTeamListWrapper(List<AppUser> users) {
        this.users = users;
    }

    public AppUserWithoutTeamListWrapper() {
    }

    @XmlElement(name = "user")
    public List<AppUser> getPersons() {
        return users;
    }

    public void setPersons(List<AppUser> users) {
        this.users = users;
    }
}