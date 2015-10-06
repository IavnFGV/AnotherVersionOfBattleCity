package com.drozda.appflow.config;

import org.apache.commons.lang3.tuple.ImmutableTriple;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by GFH on 05.10.2015.
 */
public class ImmutableTripleAdapter extends XmlAdapter<String, ImmutableTriple<String, String, Integer>> {
    @Override
    public ImmutableTriple<String, String, Integer> unmarshal(String v) throws Exception {
        String[] strings = v.split("dELIMITEr");
        return new ImmutableTriple<>(strings[0], null, Integer.valueOf(strings[1]));
    }

    @Override
    public String marshal(ImmutableTriple<String, String, Integer> v) throws Exception {
        return v.getLeft() + "dELIMITEr" + v.getRight();
    }
}
