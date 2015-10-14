package com.drozda.model;

import com.drozda.appflow.config.ImmutablePairAdapter;
import org.apache.commons.lang3.tuple.ImmutablePair;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Created by GFH on 20.09.2015.
 */
public class AppUser {
    @XmlJavaTypeAdapter(ImmutablePairAdapter.class)
    private ImmutablePair<String, Integer> pair;
    private String teamName;

    public AppUser(String left, String middle, Integer right) {
        this.pair = new ImmutablePair<>(left, right);
        this.teamName = middle;
    }

    public AppUser() {
    }

    public String getLogin() {
        return pair.getLeft();
    }

    public String getTeam() {
        return teamName;
    }

    public Integer getPasswordHash() {
        return pair.getRight();
    }

    @Override
    public int hashCode() {
        return pair.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if (!(obj instanceof AppUser)) return false;
        return pair.equals(((AppUser) (obj)).pair);
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "pair=" + pair +
                ", teamName='" + teamName + '\'' +
                '}';
    }
}
