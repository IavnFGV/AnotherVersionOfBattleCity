package com.drozda.model;

import javax.xml.bind.annotation.XmlElement;


/**
 * Created by GFH on 20.09.2015.
 */
public class AppUser {
    //    @XmlJavaTypeAdapter(ImmutablePairAdapter.class)
    @XmlElement
    private String teamName;
    @XmlElement
    private String login;
    @XmlElement
    private int passwordHash;


    public AppUser(String left, String middle, int right) {
        this.teamName = middle;
        this.login = left;
        this.passwordHash = right;
    }

    public AppUser() {
    }

    public String getLogin() {
        return login;
    }

    public String getTeam() {
        return teamName;
    }

    public Integer getPasswordHash() {
        return passwordHash;
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if (!(obj instanceof AppUser)) return false;
        return login.equals(((AppUser) (obj)).login);
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "teamName='" + teamName + '\'' +
                ", login='" + login + '\'' +
                ", passwordHash=" + passwordHash +
                '}';
    }
}
