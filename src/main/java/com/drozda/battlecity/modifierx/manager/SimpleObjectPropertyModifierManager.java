package com.drozda.battlecity.modifierx.manager;

import com.drozda.battlecity.interfacesx.GameUnitObjectPropertyModifier;
import com.drozda.battlecity.interfacesx.ObjectPropertyModifiable;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GFH on 19.11.2015.
 */
public class SimpleObjectPropertyModifierManager<T> implements ObjectPropertyModifiable<T> {

    private static final Logger log = LoggerFactory.getLogger(SimpleObjectPropertyModifierManager.class);
    protected ReadOnlyObjectWrapper<T> basicStateReadOnlyObjectWrapper = new ReadOnlyObjectWrapper<>();
    protected Map<Class<? extends GameUnitObjectPropertyModifier>, List<GameUnitObjectPropertyModifier>>
            modifierBasicStateMap = new HashMap<>();

    public SimpleObjectPropertyModifierManager(Class<T> type) {
        super();
        log.debug("SimpleObjectPropertyModifierManager.SimpleObjectPropertyModifierManager with parameters " + "type = [" + type + "]");
    }

    @Override
    public ReadOnlyObjectProperty<T> getObjectProperty(T objectPropertyType) {
        return null;
    }

    @Override
    public <I extends GameUnitObjectPropertyModifier> List<GameUnitObjectPropertyModifier> getObjectPropertyModifiers(T objectPropertyType, Class<I> interfaceType) {
        return null;
    }
}
