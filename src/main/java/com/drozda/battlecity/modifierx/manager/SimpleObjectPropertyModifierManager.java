package com.drozda.battlecity.modifierx.manager;

import com.drozda.battlecity.interfacesx.manager.ObjectPropertyModifierManager;
import com.drozda.battlecity.interfacesx.modifiable.ObjectPropertyModifiable;
import com.drozda.battlecity.interfacesx.modifier.object.ObjectPropertyModifier;
import com.drozda.battlecity.interfacesx.modifier.object.ObjectPropertyModifierByEvent;
import com.drozda.battlecity.interfacesx.modifier.object.ObjectPropertyModifierByListProperty;
import com.drozda.battlecity.interfacesx.modifier.object.ObjectPropertyModifierByProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by GFH on 19.11.2015.
 */
public class SimpleObjectPropertyModifierManager<T> implements ObjectPropertyModifiable<T>, ObjectPropertyModifierManager {

    private static final Logger log = LoggerFactory.getLogger(SimpleObjectPropertyModifierManager.class);
    protected ReadOnlyObjectWrapper<T> readOnlyObjectWrapper = new ReadOnlyObjectWrapper<>();

    protected Map<Class<? extends EventObject>, List<ObjectPropertyModifierByEvent>> modifierByEventMap = new HashMap<>();
    protected List<ObjectPropertyModifierByProperty> modifiersByProperty = new ArrayList<>();
    protected List<ObjectPropertyModifierByListProperty> modifiersByListProperty = new ArrayList<>();

    public SimpleObjectPropertyModifierManager() {
        super();
        log.debug("SimpleObjectPropertyModifierManager.SimpleObjectPropertyModifierManager with parameters " + "");
    }

    @Override
    public ReadOnlyObjectProperty<T> getObjectProperty() {
        return readOnlyObjectWrapper.getReadOnlyProperty();
    }

    @Override
    public <E extends EventObject> List<ObjectPropertyModifierByEvent> getObjectPropertyModifiersByEventClass(Class<E> eventObjectType) {
        return modifierByEventMap.get(eventObjectType);
    }

    @Override
    public <E extends EventObject> List<ObjectPropertyModifierByEvent> getObjectPropertyModifiersByEventObject(E eventObjectType) {
        log.debug("SimpleObjectPropertyModifierManager.getObjectPropertyModifiersByEventObject with parameters " + "eventObjectType = [" + eventObjectType + "]");
        List<ObjectPropertyModifierByEvent> list = new ArrayList<>();
        for (Class<? extends EventObject> aClass : modifierByEventMap.keySet()) {
            if (eventObjectType.getClass().isInstance(aClass)) {
                log.info(eventObjectType.getClass() + " is equals to " + aClass);
                list.addAll(modifierByEventMap.get(aClass));
            }
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <I extends ObjectPropertyModifier> boolean addObjectPropertyModifier(I propertyModifier) {
        log.debug("SimpleObjectPropertyModifierManager.addObjectPropertyModifier with parameters " + "propertyModifier = [" + propertyModifier + "]");
        if (propertyModifier instanceof ObjectPropertyModifierByProperty) {
            if (modifiersByProperty.contains(propertyModifier)) {
                log.info("SimpleObjectPropertyModifierManager.addObjectPropertyModifier PropertyModifierByProperty is" +
                        " already in list");
                return false;
            }
            log.debug("SimpleObjectPropertyModifierManager.addObjectPropertyModifier Adding PropertyModifierByProperty to list");
            return modifiersByProperty.add(
                    (ObjectPropertyModifierByProperty)
                            propertyModifier);
        }
        if (propertyModifier instanceof ObjectPropertyModifierByEvent) {
            ObjectPropertyModifierByEvent modifierByEvent =
                    (ObjectPropertyModifierByEvent) propertyModifier;
            List<ObjectPropertyModifierByEvent> list;
            if (!modifierByEventMap.containsKey(modifierByEvent.getEventObjectType())) {
                log.debug("SimpleObjectPropertyModifierManager.addObjectPropertyModifier. Creating new list of " +
                        "PropertyModifierByEvent");
                list = new ArrayList<>();
                modifierByEventMap.put(modifierByEvent.getEventObjectType(), list);
            } else {
                list = modifierByEventMap.get(modifierByEvent.getEventObjectType());
            }
            if (list.contains(modifierByEvent)) {
                log.info("SimpleObjectPropertyModifierManager.addObjectPropertyModifier PropertyModifierByEvent is " +
                        "already in list");
                return false;
            }
            log.debug("SimpleObjectPropertyModifierManager.addObjectPropertyModifier. Adding PropertyModifierByEvent to list");
            return list.add(modifierByEvent);
        }
        if (propertyModifier instanceof ObjectPropertyModifierByListProperty) {
            if (modifiersByListProperty.contains(propertyModifier)) {
                log.info("SimpleObjectPropertyModifierManager.addObjectPropertyModifier " +
                        "PropertyModifierByListProperty is already in list");
                return false;
            }
            log.debug("SimpleObjectPropertyModifierManager.addObjectPropertyModifier Adding " +
                    "PropertyModifierByListProperty to list");
            return modifiersByListProperty.add(
                    (ObjectPropertyModifierByListProperty)
                            propertyModifier);
        }
        throw new RuntimeException("Trying to add unsupported Modifier");
    }

    @Override
    public List<ObjectPropertyModifierByProperty> getObjectPropertyModifiersByProperty() {
        return modifiersByProperty;
    }

    @Override
    public List<ObjectPropertyModifierByListProperty> getObjectPropertyModifiersByListProperty() {
        return modifiersByListProperty;
    }

    protected ReadOnlyObjectWrapper<T> getObjectPropertyWrapper() {
        return readOnlyObjectWrapper;
    }
}
