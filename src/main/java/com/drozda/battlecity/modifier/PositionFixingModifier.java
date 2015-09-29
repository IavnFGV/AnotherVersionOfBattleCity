package com.drozda.battlecity.modifier;

import com.drozda.battlecity.*;
import com.drozda.battlecity.manager.MoveManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.function.Consumer;

/**
 * Created by GFH on 29.09.2015.
 */
public class PositionFixingModifier<T extends CanPause & CanMove> extends UnitModifier<T> implements
        ChangeListener<MoveableUnit.Direction> {
    private Consumer<MoveManager.ConsumerFixPositionRequest> fixPositionConsumer;
    private HasGameUnits playground = StaticServices.getPlayground();

    public PositionFixingModifier(T gameUnit, Consumer<MoveManager.ConsumerFixPositionRequest> fixPositionConsumer) {
        super(gameUnit);
        this.fixPositionConsumer = fixPositionConsumer;
    }

    @Override
    public void changed(ObservableValue<? extends MoveableUnit.Direction> observable, MoveableUnit.Direction oldValue, MoveableUnit.Direction newValue) {
        fixPositionConsumer.accept(new MoveManager.ConsumerFixPositionRequest(getUnit(), playground));
    }

    private T getUnit() {
        return (T) gameUnit;
    }
}
