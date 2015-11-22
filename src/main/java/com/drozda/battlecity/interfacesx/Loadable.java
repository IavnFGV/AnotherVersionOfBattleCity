package com.drozda.battlecity.interfacesx;

/**
 * Created by GFH on 22.11.2015.
 */
public interface Loadable<E extends Enum> {
    boolean load(int x, int y, E gameUnit);
}
