package com.drozda.battlecity.interfacesx;

import com.drozda.battlecity.unitx.enumeration.PauseState;
import javafx.beans.property.ReadOnlyObjectProperty;

/**
 * Created by GFH on 19.11.2015.
 */
public interface PauseStatePropertyModifiable/* extends ObjectPropertyModifiable<PauseState>*/ {
    //    @Override
    ReadOnlyObjectProperty<PauseState> getPauseStateProperty();
}
