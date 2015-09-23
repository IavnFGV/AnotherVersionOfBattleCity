package com.drozda.appflow;

import com.drozda.model.AppUser;

/**
 * Created by GFH on 23.09.2015.
 */
public interface CustomFeatures {
    AppUser DEFAULT_USER = new AppUser("UNKNOWN", "UNKNOWN", 0);

    default AppUser getLastUser() { // get info from windows registy.. for example
        return DEFAULT_USER;
    }
}
