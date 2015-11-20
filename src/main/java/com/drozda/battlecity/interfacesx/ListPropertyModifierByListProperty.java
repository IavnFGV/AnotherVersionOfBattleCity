package com.drozda.battlecity.interfacesx;

import javafx.beans.property.ListProperty;
import javafx.collections.ListChangeListener;

/**
 * Created by GFH on 21.11.2015.
 */
public interface ListPropertyModifierByListProperty<T, S>
        extends ListPropertyModifier<S>, ListChangeListener<T> {
    @Override
    void onChanged(Change<? extends T> c);

    <P extends ListProperty<T>> void setListPropertyToListen(P propertyToListen);
}
