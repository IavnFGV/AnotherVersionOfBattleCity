package com.drozda.battlecity.model;

import org.apache.commons.lang3.tuple.ImmutableTriple;

/**
 * Created by GFH on 20.09.2015.
 */
public class Triple {

    private final ImmutableTriple<String, String, String> triple;

    public Triple(String left, String middle, String right) {
        this.triple = new ImmutableTriple<String, String, String>(left, middle, right);
    }

    public Object getLogin() {
        return triple.getLeft();
    }

    public Object getTeam() {
        return triple.getMiddle();
    }

    public Object getPassword() {
        return triple.getRight();
    }

    @Override
    public String toString() {
        return "Triple{" +
                "triple=" + triple +
                '}';
    }
}
