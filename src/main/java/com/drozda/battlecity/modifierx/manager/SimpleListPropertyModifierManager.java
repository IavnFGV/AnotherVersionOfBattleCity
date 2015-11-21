package com.drozda.battlecity.modifierx.manager;

import com.drozda.battlecity.eventx.IChangeEvent;
import com.drozda.battlecity.interfacesx.manager.ListPropertyModifierManager;
import com.drozda.battlecity.interfacesx.modifiable.ListPropertyModifiable;
import com.drozda.battlecity.interfacesx.modifier.list.ListPropertyModifier;
import com.drozda.battlecity.interfacesx.modifier.list.ListPropertyModifierByEvent;
import com.drozda.battlecity.interfacesx.modifier.list.ListPropertyModifierByListProperty;
import com.drozda.battlecity.interfacesx.modifier.list.ListPropertyModifierByProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by GFH on 21.11.2015.
 */
public class SimpleListPropertyModifierManager<T> implements ListPropertyModifiable<T>, ListPropertyModifierManager {
    private static final Logger log = LoggerFactory.getLogger(SimpleListPropertyModifierManager.class);
    protected ReadOnlyListWrapper<T> readOnlyListWrapper = new ReadOnlyListWrapper<>(FXCollections.observableArrayList());

    protected Map<Class<? extends IChangeEvent>, List<ListPropertyModifierByEvent>> modifierByEventMap = new HashMap<>();
    protected List<ListPropertyModifierByProperty> modifiersByProperty = new ArrayList<>();
    protected List<ListPropertyModifierByListProperty> modifiersByListProperty = new ArrayList<>();


    public SimpleListPropertyModifierManager() {
        super();
        log.debug("SimpleListPropertyModifierManager.SimpleListPropertyModifierManager with parameters " + "");
    }

    protected ReadOnlyListWrapper<T> readOnlyListWrapperProperty() {
        return readOnlyListWrapper;
    }

    public void setReadOnlyListWrapper(ObservableList<T> readOnlyListWrapper) {
        this.readOnlyListWrapper.set(readOnlyListWrapper);
    }

    @Override
    public ReadOnlyListProperty<T> getListProperty() {
        return readOnlyListWrapper.getReadOnlyProperty();
    }

    @Override
    public <E extends EventObject> List<ListPropertyModifierByEvent> getListPropertyModifiersByEventClass(Class<E> eventObjectType) {
        return modifierByEventMap.get(eventObjectType);
    }

    @Override
    public <E extends EventObject> List<ListPropertyModifierByEvent> getListPropertyModifiersByEventObject(E eventObject) {
        log.debug("SimpleListPropertyModifierManager.getListPropertyModifiersByEventObject with parameters " + "eventObject = [" + eventObject + "]");
        List<ListPropertyModifierByEvent> list = new ArrayList<>();
        for (Class<? extends IChangeEvent> aClass : modifierByEventMap.keySet()) {
            if (aClass.isInstance(eventObject)) {
                log.info(eventObject.getClass() + " is equals to " + aClass);
                list.addAll(modifierByEventMap.get(aClass));
            }
        }
        return list;
    }

    @Override
    public <I extends ListPropertyModifier> boolean addListPropertyModifier(I propertyModifier) {
        log.debug("SimpleListPropertyModifierManager.addListPropertyModifier with parameters " + "propertyModifier = [" + propertyModifier + "]");
        if (propertyModifier instanceof ListPropertyModifierByProperty) {
            if (modifiersByProperty.contains(propertyModifier)) {
                log.info("SimpleListPropertyModifierManager.addListPropertyModifier PropertyModifierByProperty is" +
                        " already in list");
                return false;
            }
            log.debug("SimpleListPropertyModifierManager.addListPropertyModifier Adding PropertyModifierByProperty to list");
            return modifiersByProperty.add(
                    (ListPropertyModifierByProperty)
                            propertyModifier);
        }
        if (propertyModifier instanceof ListPropertyModifierByEvent) {
            ListPropertyModifierByEvent modifierByEvent =
                    (ListPropertyModifierByEvent) propertyModifier;
            List<ListPropertyModifierByEvent> list;
            if (!modifierByEventMap.containsKey(modifierByEvent.getEventObjectType())) {
                log.debug("SimpleListPropertyModifierManager.addListPropertyModifier. Creating new list of " +
                        "PropertyModifierByEvent");
                list = new ArrayList<>();
                modifierByEventMap.put(modifierByEvent.getEventObjectType(), list);
            } else {
                list = modifierByEventMap.get(modifierByEvent.getEventObjectType());
            }
            if (list.contains(modifierByEvent)) {
                log.info("SimpleListPropertyModifierManager.addListPropertyModifier PropertyModifierByEvent is " +
                        "already in list");
                return false;
            }
            log.debug("SimpleListPropertyModifierManager.addListPropertyModifier. Adding PropertyModifierByEvent to list");
            return list.add(modifierByEvent);
        }
        if (propertyModifier instanceof ListPropertyModifierByListProperty) {
            if (modifiersByListProperty.contains(propertyModifier)) {
                log.info("SimpleListPropertyModifierManager.addListPropertyModifier " +
                        "PropertyModifierByListProperty is already in list");
                return false;
            }
            log.debug("SimpleListPropertyModifierManager.addListPropertyModifier Adding " +
                    "PropertyModifierByListProperty to list");
            return modifiersByListProperty.add(
                    (ListPropertyModifierByListProperty)
                            propertyModifier);
        }
        throw new RuntimeException("Trying to add unsupported Modifier");
    }

    @Override
    public List<ListPropertyModifierByProperty> getListPropertyModifiersByProperty() {
        return modifiersByProperty;
    }

    @Override
    public List<ListPropertyModifierByListProperty> getListPropertyModifiersByListProperty() {
        return modifiersByListProperty;
    }
}
