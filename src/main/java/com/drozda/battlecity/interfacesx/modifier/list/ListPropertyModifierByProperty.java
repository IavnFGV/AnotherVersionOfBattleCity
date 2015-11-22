package com.drozda.battlecity.interfacesx.modifier.list;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;

/**
 * Created by GFH on 21.11.2015.
 */
public interface ListPropertyModifierByProperty<T, S>
        extends ListPropertyModifier<S>, ChangeListener<T> {

    <P extends ReadOnlyObjectProperty<T>> void setPropertyToListen(P propertyToListen);

}
