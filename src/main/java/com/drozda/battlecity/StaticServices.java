package com.drozda.battlecity;

import com.drozda.battlecity.interfaces.HasGameUnits;

import java.util.Locale;

/**
 * Created by GFH on 06.07.2015.
 */
public class StaticServices {
    public static Locale DEFAULT_LOCALE = Locale.getDefault();
    //    public static ResourceBundle MESSAGES = ResourceBundle.getBundle("messages/messages", DEFAULT_LOCALE);
    public static Long ONE_SECOND = 1000_000_000l;
    private static YabcPlayGround yabcPlayGround;

    public static HasGameUnits getYabcPlayGround() {
        return yabcPlayGround;
    }

    public static void initPlayground() { //TODO STUB/ REPLACE
        //     yabcPlayGround=
        //  yabcPlayGround = new YabcPlayGround();
    }
}
