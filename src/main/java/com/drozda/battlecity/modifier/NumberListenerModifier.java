package com.drozda.battlecity.modifier;

import com.drozda.battlecity.CanPause;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 27.09.2015.
 */
public abstract class NumberListenerModifier extends UnitModifier implements ChangeListener<Number> {
    private static final Logger log = LoggerFactory.getLogger(NumberListenerModifier.class);

    public <T extends CanPause> NumberListenerModifier(T gameUnit) {
        super(gameUnit);
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

    public abstract void perform(long deltaTime);
}
