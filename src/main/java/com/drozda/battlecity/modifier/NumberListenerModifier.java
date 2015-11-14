package com.drozda.battlecity.modifier;

import com.drozda.battlecity.interfaces.CanPause;
import com.drozda.battlecity.interfaces.HasGameUnits;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 27.09.2015.
 */
public abstract class NumberListenerModifier<T extends CanPause> extends UnitModifier<T> implements ChangeListener<Number> {
    private static final Logger log = LoggerFactory.getLogger(NumberListenerModifier.class);

    public NumberListenerModifier(T gameUnit, HasGameUnits playground) {
        super(gameUnit, playground);
    }

    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        log.debug("NumberListenerModifier.changed");
        log.debug("observable = [" + observable + "], oldValue = [" + oldValue + "], newValue = [" + newValue + "]");
        if (isPause()) {
            return;
        }
        long deltaTime = newValue.longValue() - oldValue.longValue();
        perform(deltaTime);
    }

    protected boolean isPause() {
        return gameUnit.isPause();
    }

    protected abstract void perform(long deltaTime);
}
