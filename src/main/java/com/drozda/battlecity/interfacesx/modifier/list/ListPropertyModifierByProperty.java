package com.drozda.battlecity.interfacesx.modifier.list;

import com.drozda.battlecity.interfacesx.modifier.object.ObjectPropertyModifier;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;

/**
 * Created by GFH on 21.11.2015.
 */
public interface ListPropertyModifierByProperty<T, S>
        extends ObjectPropertyModifier<S>, ChangeListener<T> {

    <P extends ObjectProperty<T>> void setPropertyToListen(P propertyToListen);

}
