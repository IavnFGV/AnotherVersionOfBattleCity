package com.drozda.battlecity.interfacesx;

import com.drozda.battlecity.unitx.enumeration.EngineState;
import javafx.beans.property.ReadOnlyObjectProperty;

/**
 * Created by GFH on 22.11.2015.
 */
public interface EngineStateModifiable /*extends ObjectPropertyModifiable<EngineState> */ {
    //    @Override
    ReadOnlyObjectProperty<EngineState> getEngineStateProperty();
}
