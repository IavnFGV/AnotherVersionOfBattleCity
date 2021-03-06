package com.drozda.battlecity.interfacesx.modifier.object;


import com.drozda.battlecity.interfacesx.modifier.PropertyModifier;
import javafx.beans.property.ReadOnlyObjectWrapper;

/**
 * Created by GFH on 19.11.2015.
 */
public interface ObjectPropertyModifier<S> extends PropertyModifier {
    void setPropertyToChange(ReadOnlyObjectWrapper<S> propertyToChange);
}
