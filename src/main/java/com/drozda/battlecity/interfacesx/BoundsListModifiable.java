package com.drozda.battlecity.interfacesx;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.geometry.Bounds;

/**
 * Created by GFH on 21.11.2015.
 */
public interface BoundsListModifiable /*extends ListPropertyModifiable<Bounds> */ {
    //    @Override
    ReadOnlyListProperty<Bounds> getBoundsListProperty();
}
