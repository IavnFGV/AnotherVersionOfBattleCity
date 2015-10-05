package com.drozda.model;

/**
 * Created by GFH on 05.10.2015.
 */
public class AppTeam {
    private String name;

    public AppTeam(String name) {
        this.name = name;
    }

    public AppTeam() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AppTeam{" +
                "name='" + name + '\'' +
                '}';
    }
}
