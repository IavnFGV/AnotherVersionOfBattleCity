package com.drozda.battlecity.unit;

import com.drozda.battlecity.StaticServices;
import com.drozda.battlecity.interfaces.HasGameUnits;
import com.drozda.battlecity.modifier.StateFlowModifier;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

import static java.util.Arrays.asList;

/**
 * Created by GFH on 13.11.2015.
 */
public class SpadeZoneTileUnit extends TileUnit {
    private static final Logger log = LoggerFactory.getLogger(SpadeZoneTileUnit.class);
    private ObjectProperty<TileType> tileType = new SimpleObjectProperty<>(TileType.STEEL);

    {
        StateFlowModifier stateFlowModifier1 = new ChangeableTileUnitStateFlowModifier(this);
        stateFlowModifier = stateFlowModifier1;
    }

    public SpadeZoneTileUnit(double minX, double minY, double width, double height, HasGameUnits playground) {
        super(minX, minY, width, height, asList(State.CREATING, State.ACTIVE, State.ARMOR, State.BLINK, State.ACTIVE, State.DEAD),
                new HashMap<State, Long>() {{
                    put(State.CREATING, 0l);
                    put(State.ARMOR, StaticServices.ONE_SECOND * 16);
                    put(State.BLINK, StaticServices.ONE_SECOND * 4);
                }},
                playground);
    }


    @Override
    public TileType getTileType() {
        return tileType.get();
    }

    public void setTileType(TileType tileType) {
        this.tileType.set(tileType);
    }

    public ObjectProperty<TileType> tileTypeProperty() {
        return tileType;
    }

    private class ChangeableTileUnitStateFlowModifier extends StateFlowModifier<SpadeZoneTileUnit> {
        private long timeInSubState; //forBlinking

        public ChangeableTileUnitStateFlowModifier(SpadeZoneTileUnit gameUnit) {
            super(gameUnit);
            currentStateProperty().addListener((observable, oldValue, newValue) -> {
                if ((oldValue == State.ACTIVE) &&
                        (newValue == State.ARMOR)) {
                    setTileType(TileType.STEEL);
                }
                if ((oldValue == State.BLINK) &&
                        (newValue == State.ACTIVE)) {
                    setTileType(TileType.BRICK);
                }
            });
        }

        @Override
        protected void perform(long deltaTime) {
//            log.debug("ChangeableTileUnitStateFlowModifier.perform with parameters " + "deltaTime = [" + deltaTime + "]");
            if (getCurrentState() == State.BLINK) {
                timeInSubState += deltaTime;
                if (timeIsUp()) {
                    goToNextState();
                }
            }
            super.perform(deltaTime);
        }

        private boolean timeIsUp() {
            log.debug("StateFlowModifier.timeIsUp");
            long timeInState = StaticServices.ONE_SECOND;
            return (timeInState > 0) && (timeInState <= timeInSubState);
        }

        private void goToNextState() {
            setTileType(getTileType() == TileType.STEEL ? TileType.BRICK : TileType.STEEL);
            timeInSubState = 0;
        }
    }

}
