package com.drozda.battlecity.unit;

import com.drozda.battlecity.interfaces.Collideable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Bounds;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * Created by GFH on 27.09.2015.
 */
public class TileUnit extends GameUnit implements Collideable {
    private TileType tileType;
    private ObjectProperty<TileState> tileState = new SimpleObjectProperty<>(TileState.STATE_1111);

    public TileUnit(Bounds bounds, TileType tileType) {
        super(bounds, asList(GameUnit.State.ACTIVE, GameUnit.State.DEAD), null);
        this.tileType = tileType;
        this.setCurrentState(State.ACTIVE);
    }

    public TileUnit(Bounds bounds, List<State> stateFlow, Map<State, Long>
            timeInState) {
        super(bounds, stateFlow, timeInState);
        this.setCurrentState(State.ACTIVE);
    }

    public TileState getTileState() {
        return tileState.get();
    }

    public void setTileState(TileState tileState) {
        this.tileState.set(tileState);
    }

    public ObjectProperty<TileState> tileStateProperty() {
        return tileState;
    }

    @Override
    public ImmutablePair<CollideResult, CollideResult> collide(GameUnit gameUnit) {
        return null;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    public boolean canBeDestroyed(BulletUnit.Type type) {
        if (getTileType() == null) {
            return false;
        }
        if (type == BulletUnit.Type.SIMPLE) {
            if (getTileType() == TileType.BRICK) {
                return true;
            }
        }
        if (type == BulletUnit.Type.POWERFUL) {
            if ((getTileType() == TileType.STEEL) ||
                    (getTileType() == TileType.BRICK)) {
                return true;
            }
        }
        return false;
    }

    public TileType getTileType() {
        return tileType;
    }

    public enum TileType {
        BRICK, FOREST, ICE, STEEL, WATER
    }

    public enum TileState { // TODO implement transitions???
        STATE_0001,
        STATE_0010,
        STATE_0011,
        STATE_0100,
        STATE_0101,
        STATE_0110,
        STATE_0111,
        STATE_1000,
        STATE_1001,
        STATE_1010,
        STATE_1011,
        STATE_1100,
        STATE_1101,
        STATE_1110,
        STATE_1111
    }
}
