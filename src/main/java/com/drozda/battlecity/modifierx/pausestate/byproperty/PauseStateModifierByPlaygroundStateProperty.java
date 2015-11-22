package com.drozda.battlecity.modifierx.pausestate.byproperty;

import com.drozda.battlecity.modifierx.ObjectPropertyModifierByProperty;
import com.drozda.battlecity.unitx.enumeration.PauseState;
import com.drozda.battlecity.unitx.enumeration.PlaygroundState;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;

/**
 * Created by GFH on 20.11.2015.
 */
public class PauseStateModifierByPlaygroundStateProperty extends ObjectPropertyModifierByProperty<PlaygroundState, PauseState> {
    public PauseStateModifierByPlaygroundStateProperty(ObjectProperty<PlaygroundState> propertyToListen) {
        super(propertyToListen);
    }

    @Override
    public void changed(ObservableValue<? extends PlaygroundState> observable, PlaygroundState oldValue, PlaygroundState newValue) {
        throw new RuntimeException("not implemented yet");
    }
}
