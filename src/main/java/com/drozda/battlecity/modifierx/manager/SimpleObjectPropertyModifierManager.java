package com.drozda.battlecity.modifierx.manager;

import com.drozda.battlecity.interfacesx.GameUnitObjectPropertyModifier;
import com.drozda.battlecity.interfacesx.ObjectPropertyModifiable;
import com.drozda.battlecity.interfacesx.ObjectPropertyModifierManager;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GFH on 19.11.2015.
 */
public class SimpleObjectPropertyModifierManager<T> implements ObjectPropertyModifiable<T>, ObjectPropertyModifierManager {

    private static final Logger log = LoggerFactory.getLogger(SimpleObjectPropertyModifierManager.class);
    protected ReadOnlyObjectWrapper<T> readOnlyObjectWrapper = new ReadOnlyObjectWrapper<>();
    protected Map<Class<? extends GameUnitObjectPropertyModifier>, List<GameUnitObjectPropertyModifier>>
            modifierObjectPropertyMap = new HashMap<>();

    public SimpleObjectPropertyModifierManager(Class<T> type) {
        super();
        log.debug("SimpleObjectPropertyModifierManager.SimpleObjectPropertyModifierManager with parameters " + "type = [" + type + "]");
    }

    @Override
    public ReadOnlyObjectProperty<T> getObjectProperty() {
        return readOnlyObjectWrapper.getReadOnlyProperty();
    }

    @Override
    public <I extends GameUnitObjectPropertyModifier> List<GameUnitObjectPropertyModifier> getObjectPropertyModifiers(Class<I> interfaceType) {
        return modifierObjectPropertyMap.get(interfaceType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <I extends GameUnitObjectPropertyModifier> void addObjectPropertyModifier(I propertyModifier) {
        log.debug("SimpleObjectPropertyModifierManager.addObjectPropertyModifier with parameters " + "propertyModifier = [" + propertyModifier + "]");
        List<GameUnitObjectPropertyModifier> list;
        if (!modifierObjectPropertyMap.containsKey(propertyModifier.getClass())) {
            log.debug("SimpleObjectPropertyModifierManager.addObjectPropertyModifier. Creating new list");
            list = new ArrayList<>();
            list.add(propertyModifier);
        } else {
            list = modifierObjectPropertyMap.get(propertyModifier.getClass());
        }
        log.debug("SimpleObjectPropertyModifierManager.addObjectPropertyModifier. Adding modifier to list");
        propertyModifier.setPropertyToChange(getObjectPropertyWrapper());
        list.add(propertyModifier);
    }

    protected ReadOnlyObjectWrapper<T> getObjectPropertyWrapper() {
        return readOnlyObjectWrapper;
    }
}
