package com.drozda.battlecity.interfacesx;

import javafx.beans.property.ReadOnlyListWrapper;

/**
 * Created by GFH on 19.11.2015.
 */
public interface ListPropertyModifier<S> extends PropertyModifier {

    void setListPropertyToChange(ReadOnlyListWrapper<S> listPropertyToChange);
}
