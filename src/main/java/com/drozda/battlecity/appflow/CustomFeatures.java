package com.drozda.battlecity.appflow;

import com.drozda.battlecity.model.YabcUser;

/**
 * Created by GFH on 23.09.2015.
 */
public interface CustomFeatures {
    YabcUser DEFAULT_USER = new YabcUser("UNKNOWN", "UNKNOWN", 0);

    default YabcUser getLastUser() { // get info from windows registy.. for example
        return DEFAULT_USER;
    }
}
