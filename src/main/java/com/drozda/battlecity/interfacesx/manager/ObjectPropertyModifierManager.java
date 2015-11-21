package com.drozda.battlecity.interfacesx.manager;

import com.drozda.battlecity.interfacesx.modifier.object.ObjectPropertyModifier;
import com.drozda.battlecity.interfacesx.modifier.object.ObjectPropertyModifierByEvent;
import com.drozda.battlecity.interfacesx.modifier.object.ObjectPropertyModifierByListProperty;
import com.drozda.battlecity.interfacesx.modifier.object.ObjectPropertyModifierByProperty;

import java.util.EventObject;
import java.util.List;

/**
 * Created by GFH on 19.11.2015.
 */
public interface ObjectPropertyModifierManager extends PropertyModifierManager {

    <E extends EventObject> List<ObjectPropertyModifierByEvent>
    getObjectPropertyModifiersByEventClass(Class<E> eventObjectType);

    <E extends EventObject> List<ObjectPropertyModifierByEvent>
    getObjectPropertyModifiersByEventObject(E eventObjectType);

    <I extends ObjectPropertyModifier> boolean addObjectPropertyModifier(I propertyModifier);

    List<ObjectPropertyModifierByProperty> getObjectPropertyModifiersByProperty();

    List<ObjectPropertyModifierByListProperty> getObjectPropertyModifiersByListProperty();
}
