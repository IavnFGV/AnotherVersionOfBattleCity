package com.drozda.battlecity.interfacesx.manager;

import com.drozda.battlecity.interfacesx.modifier.list.ListPropertyModifier;
import com.drozda.battlecity.interfacesx.modifier.list.ListPropertyModifierByEvent;
import com.drozda.battlecity.interfacesx.modifier.list.ListPropertyModifierByListProperty;
import com.drozda.battlecity.interfacesx.modifier.list.ListPropertyModifierByProperty;

import java.util.EventObject;
import java.util.List;

/**
 * Created by GFH on 21.11.2015.
 */
public interface ListPropertyModifierManager extends PropertyModifierManager {

    <E extends EventObject> List<ListPropertyModifierByEvent>
    getListPropertyModifiersByEventClass(Class<E> eventObjectType);

    <E extends EventObject> List<ListPropertyModifierByEvent>
    getListPropertyModifiersByEventObject(E eventObject);

    <I extends ListPropertyModifier> boolean addListPropertyModifier(I propertyModifier);

    List<ListPropertyModifierByProperty> getListPropertyModifiersByProperty();

    List<ListPropertyModifierByListProperty> getListPropertyModifiersByListProperty();

}
