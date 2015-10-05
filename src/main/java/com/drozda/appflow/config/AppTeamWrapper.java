package com.drozda.appflow.config;

import com.drozda.model.AppTeam;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by GFH on 06.10.2015.
 */
@XmlRootElement(name = "teams")
public class AppTeamWrapper {
    private List<AppTeam> teams;

    public AppTeamWrapper(List<AppTeam> teams) {
        this.teams = teams;
    }

    public AppTeamWrapper() {
    }

    public List<AppTeam> getTeams() {
        return teams;
    }

    @XmlElement(name = "team")
    public void setTeams(List<AppTeam> teams) {
        this.teams = teams;
    }
}
