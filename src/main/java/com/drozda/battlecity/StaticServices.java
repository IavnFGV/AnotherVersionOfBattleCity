package com.drozda.battlecity;

import com.drozda.battlecity.interfaces.HasGameUnits;
import com.drozda.battlecity.playground.YabcPlayground;

import java.util.Locale;

/**
 * Created by GFH on 06.07.2015.
 */
public class StaticServices {
    public static Locale DEFAULT_LOCALE = Locale.getDefault();
    //    public static ResourceBundle MESSAGES = ResourceBundle.getBundle("messages/messages", DEFAULT_LOCALE);
    public static Long ONE_SECOND = 1000_000_000l;
    private static YabcPlayground yabcPlayground;

    public static HasGameUnits getYabcPlayground() {
        return yabcPlayground;
    }

    public static void initPlayground() { //TODO STUB/ REPLACE
        //     yabcPlayground=
        //  yabcPlayground = new YabcPlayground();
    }
}
