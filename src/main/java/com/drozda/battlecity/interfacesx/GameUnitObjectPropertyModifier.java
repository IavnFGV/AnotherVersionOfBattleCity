package com.drozda.battlecity.interfacesx;


import javafx.beans.property.ReadOnlyObjectWrapper;

/**
 * Created by GFH on 19.11.2015.
 */
public interface GameUnitObjectPropertyModifier<S> extends GameUnitPropertyModifier {
    void setPropertyToChange(ReadOnlyObjectWrapper<S> propertyToChange);
}
