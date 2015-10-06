package com.drozda.model;

import com.drozda.appflow.config.ImmutableTripleAdapter;
import org.apache.commons.lang3.tuple.ImmutableTriple;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by GFH on 20.09.2015.
 */
public class AppUser {

    @XmlJavaTypeAdapter(ImmutableTripleAdapter.class)
    private ImmutableTriple<String, String, Integer> triple;

    public AppUser(String left, String middle, Integer right) {
        this.triple = new ImmutableTriple(left, middle, right);
    }

    public AppUser() {
    }

    public String getLogin() {
        return triple.getLeft();
    }

    public String getTeam() {
        return triple.getMiddle();
    }

    public Integer getPasswordHash() {
        return triple.getRight();
    }

    @Override
    public int hashCode() {
        return triple.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if (!(obj instanceof AppUser)) return false;
        return triple.equals(((AppUser) (obj)).triple);
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "triple=" + triple +
                '}';
    }
}
