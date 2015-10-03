package com.drozda.battlecity.interfaces;

/**
 * Created by GFH on 02.10.2015.
 */
public interface CanReceiveMail {

    String getAddress();

    boolean processMessage(Object o);
}
