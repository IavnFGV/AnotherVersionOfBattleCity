package com.drozda.model;

import org.apache.commons.lang3.tuple.ImmutableTriple;

/**
 * Created by GFH on 20.09.2015.
 */
public class AppUser {

    private final ImmutableTriple<String, String, Integer> triple;

    public AppUser(String left, String middle, Integer right) {
        this.triple = new ImmutableTriple(left, middle, right);
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
        return triple.equals(((AppUser) (obj)).triple);
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "triple=" + triple +
                '}';
    }
}
