package com.drozda.battlecity;

import java.util.Locale;

/**
 * Created by GFH on 06.07.2015.
 */
public class StaticServices {
    public static Locale DEFAULT_LOCALE = Locale.getDefault();
    //    public static ResourceBundle MESSAGES = ResourceBundle.getBundle("messages/messages", DEFAULT_LOCALE);
    public static Long ONE_SECOND = 1000_000_000l;
    public static long FAST_SPEED = 8l;
    public static long NORMAL_SPEED = 6l;
    public static long BULLET_CASH_SIZE = 6l;
    public static long NORMAL_BULLET_SPEED = 10l;
    public static long FAST_BULLET_SPEED = 14l;
    public static Long BULLET_EXPLODING_TIME = ONE_SECOND / 24;
    public static Long TANK_EXPLODING_TIME = ONE_SECOND / 16;

    public static int EXTRACT_COLLIDING_LIST = 1;
}
