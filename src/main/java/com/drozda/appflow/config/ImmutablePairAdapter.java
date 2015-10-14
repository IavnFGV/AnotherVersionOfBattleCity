package com.drozda.appflow.config;

import org.apache.commons.lang3.tuple.ImmutablePair;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by GFH on 14.10.2015.
 */
public class ImmutablePairAdapter extends XmlAdapter<String, ImmutablePair<String, Integer>> {
    public static String DELIMITER = "-dELIMITEr-";

    @Override
    public ImmutablePair<String, Integer> unmarshal(String v) throws Exception {
        String[] strings = v.split(DELIMITER);
        return new ImmutablePair<>(strings[0], Integer.valueOf(strings[1]));
    }

    @Override
    public String marshal(ImmutablePair<String, Integer> v) throws Exception {
        return v.getLeft() + DELIMITER + v.getRight();
    }
}

