package com.drozda.battlecity.modifierx.pausestate.byproperty;

import com.drozda.battlecity.modifierx.ObjectPropertyModifierByProperty;
import com.drozda.battlecity.playground.PlaygroundState;
import com.drozda.battlecity.unitx.enumeration.PauseState;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;

/**
 * Created by GFH on 20.11.2015.
 */
public class PauseStateModifierByProperty extends ObjectPropertyModifierByProperty<PlaygroundState, PauseState> {
    public PauseStateModifierByProperty(ObjectProperty<PlaygroundState> propertyToListen) {
        super(propertyToListen);
    }

    @Override
    public void changed(ObservableValue<? extends PlaygroundState> observable, PlaygroundState oldValue, PlaygroundState newValue) {
        throw new RuntimeException("not implemented yet");
    }
}
