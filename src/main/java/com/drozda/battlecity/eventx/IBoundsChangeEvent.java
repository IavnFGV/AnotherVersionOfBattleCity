package com.drozda.battlecity.eventx;

import javafx.geometry.Bounds;

import java.util.List;

/**
 * Created by GFH on 21.11.2015.
 */
public interface IBoundsChangeEvent extends IChangeEvent {
    List<Bounds> getNewBounds();
}
