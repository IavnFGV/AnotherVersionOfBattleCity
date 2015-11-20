package com.drozda.battlecity.interfacesx;

import java.util.EventObject;
import java.util.List;

/**
 * Created by GFH on 19.11.2015.
 */
public interface ObjectPropertyModifierManager {
    <I extends ObjectPropertyModifier> boolean addObjectPropertyModifier(I propertyModifier);

    List<ObjectPropertyModifierByProperty>
    getObjectPropertyModifiersByProperty();

    <E extends EventObject> List<ObjectPropertyModifierByEvent>
    getObjectPropertyModifiersByEventClass(Class<E> eventObjectType);

    <E extends EventObject> List<ObjectPropertyModifierByEvent>
    getObjectPropertyModifiersByEventObject(E eventObjectType);

}
