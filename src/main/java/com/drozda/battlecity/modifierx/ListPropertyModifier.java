package com.drozda.battlecity.modifierx;

import javafx.beans.property.ReadOnlyListWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 21.11.2015.
 */
public abstract class ListPropertyModifier<S> implements com.drozda.battlecity.interfacesx.modifier.list
        .ListPropertyModifier<S> {
    private static Logger log = LoggerFactory.getLogger(ListPropertyModifier.class);

    protected ReadOnlyListWrapper<S> listPropertyToChange;

    public ListPropertyModifier(ReadOnlyListWrapper<S> listPropertyToChange) {
        setListPropertyToChange(listPropertyToChange);
    }

    @Override
    public void setListPropertyToChange(ReadOnlyListWrapper<S> listPropertyToChange) {
        this.listPropertyToChange = listPropertyToChange;
    }
}