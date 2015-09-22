package com.drozda.battlecity.model;

import org.apache.commons.lang3.tuple.ImmutableTriple;

/**
 * Created by GFH on 20.09.2015.
 */
public class YabcUser {

    private final ImmutableTriple<String, String, Integer> triple;

    public YabcUser(String left, String middle, Integer right) {
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
        return triple.equals(((YabcUser) (obj)).triple);
    }

    @Override
    public String toString() {
        return "YabcUser{" +
                "triple=" + triple +
                '}';
    }
}
