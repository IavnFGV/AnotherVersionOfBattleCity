package com.drozda.battlecity.interfacesx;

import java.util.EventObject;
import java.util.List;

/**
 * Created by GFH on 19.11.2015.
 */
public interface ObjectPropertyModifierManager {
    <I extends GameUnitObjectPropertyModifier> boolean addObjectPropertyModifier(I propertyModifier);

    List<GameUnitObjectPropertyModifierByProperty>
    getObjectPropertyModifiersByProperty();

    <E extends EventObject> List<GameUnitObjectPropertyModifierByEvent>
    getObjectPropertyModifiersByEventClass(Class<E> eventObjectType);

    <E extends EventObject> List<GameUnitObjectPropertyModifierByEvent>
    getObjectPropertyModifiersByEventObject(E eventObjectType);

}
