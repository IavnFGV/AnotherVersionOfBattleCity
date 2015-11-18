package com.drozda.battlecity.unit;

//import com.sun.javafx.geom.Shape;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static java.util.Arrays.asList;
import static javafx.scene.shape.Shape.union;

/**
 * Created by GFH on 27.09.2015.
 */
public class TileUnit extends GameUnit {
    protected static EnumSet<TileType> typesForCollisionProcess = EnumSet.of(TileType.BRICK, TileType.STEEL);
    private static Map<TileState, Consumer<Void>> rebuiltMap = new HashMap();

    static {
//        rebuiltMap.put()
    }

    private TileType tileType;
    private ObjectProperty<TileState> tileState = new SimpleObjectProperty<>(TileState.STATE_1111);
    private Shape complexBounds;
    private Rectangle[] tileParts;


    public TileUnit(Bounds bounds, TileType tileType) {
        super(bounds, asList(GameUnit.State.ACTIVE, GameUnit.State.DEAD), null);
        this.tileType = tileType;
        if (tileType == TileType.BRICK) {
            createComplexBoundHandler();
        }
        this.setCurrentState(State.ACTIVE);
    }

    private void createComplexBoundHandler() {
        tileParts = new Rectangle[]{
                new Rectangle(getBounds().getMinX(),
                        getBounds().getMinY(), 8, 8),
                new Rectangle(getBounds().getMinX() + 8,
                        getBounds().getMinY(), 8, 8),
                new Rectangle(getBounds().getMinX(),
                        getBounds().getMinY() + 8, 8, 8),
                new Rectangle(getBounds().getMinX() + 8,
                        getBounds().getMinY() + 8, 8, 8)};
        tileStateProperty().addListener((observable, oldValue, newValue) -> {
            rebuildBounds(newValue);
        });
        complexBounds = union(
                union(tileParts[0], tileParts[1]), union(tileParts[2], tileParts[3]));

    }

    public ObjectProperty<TileState> tileStateProperty() {
        return tileState;
    }

    private void rebuildBounds(TileState tileState) {
        tileState.boundsRebuilder.accept(this);
    }

    public TileUnit(Bounds bounds, List<State> stateFlow, Map<State, Long>
            timeInState) {
        super(bounds, stateFlow, timeInState);
        this.setCurrentState(State.ACTIVE);
        createComplexBoundHandler();
    }

    public TileState getTileStateAfterBullet(BulletUnit bulletUnit) {
        if (bulletUnit.getType() == BulletUnit.Type.POWERFUL) {
            return TileState.STATE_EMPTY;
        }
        if (getTileType() == TileType.STEEL) {
            return getTileState();
        }
        if (getTileType() == TileType.BRICK) {
            return getTileState().getTileStateAfterBullet(bulletUnit);
        }
        return getTileState();
    }

    public TileType getTileType() {
        return tileType;
    }

    public TileState getTileState() {
        return tileState.get();
    }

    public void setTileState(TileState tileState) {
        this.tileState.set(tileState);
    }

    @Override
    public boolean intersects(GameUnit gameUnit) {
        if (getTileType() != TileType.BRICK) {
            return super.intersects(gameUnit);
        }
        Bounds b = gameUnit.getBounds();
        return (complexBounds.contains(b.getMinX(), b.getMinY()) ||
                complexBounds.contains(b.getMaxX(), b.getMaxY()) ||
                complexBounds.contains(b.getMinX() + b.getWidth(), b.getMinY()) ||
                complexBounds.contains(b.getMinX(), b.getMinY() + b.getHeight()));

    }

    @Override
    public boolean isTakingPartInCollisionProcess() {
        return (super.isTakingPartInCollisionProcess() &&
                (typesForCollisionProcess.contains(getTileType())));

    }

    public enum TileType {
        BRICK, FOREST, ICE, STEEL, WATER
    }


    public enum TileState { // TODO implement transitions???
        STATE_EMPTY,
        STATE_0001,
        STATE_0010,
        STATE_0011(tileUnit -> tileUnit.complexBounds =
                union(tileUnit.tileParts[2], tileUnit.tileParts[3]),
                null, STATE_0010, null, STATE_0001),
        STATE_0100,
        STATE_0101,
        STATE_0110(tileUnit -> tileUnit.complexBounds =
                union(tileUnit.tileParts[1], tileUnit.tileParts[2]),
                STATE_0100, null, STATE_0010, null),
        STATE_0111,
        STATE_1000,
        STATE_1001(tileUnit -> tileUnit.complexBounds =
                union(tileUnit.tileParts[0], tileUnit.tileParts[3]),
                STATE_1000, null, STATE_0001, null),
        STATE_1010,
        STATE_1011,
        STATE_1100(tileUnit -> tileUnit.complexBounds =
                union(tileUnit.tileParts[0], tileUnit.tileParts[1]),
                null, STATE_0100, null, STATE_1000),
        STATE_1101,
        STATE_1110,
        STATE_1111(tileUnit -> tileUnit.complexBounds = union(
                union(tileUnit.tileParts[0], tileUnit.tileParts[1]),
                union(tileUnit.tileParts[2], tileUnit.tileParts[3])),
                STATE_1100, STATE_0110, STATE_0011, STATE_1001);

        protected Map<MoveableUnit.Direction, TileState> transitionMap = new HashMap<>();
        protected Consumer<TileUnit> boundsRebuilder;

        TileState(Consumer<TileUnit> boundsRebuilder, TileState... state) {
            this(state);
            this.boundsRebuilder = boundsRebuilder;
        }

        /**
         * Convention (UP,RIGHT,DOWN,LEFT) as a result {@code MoveableUnit.Direction.values()}.
         * If something absent or {@code null}, {@code getTileStateAfterBullet(BulletUnit bulletUnit)}
         * will return  {@code  STATE_EMPTY}
         */
        TileState(TileState... state) {
            MoveableUnit.Direction[] directions = MoveableUnit.Direction.values();
            for (int i = 0; i < state.length; i++) {
                if (state[i] == null) {
                    continue;
                }
                transitionMap.put(directions[i], state[i]);
            }
        }

        public TileState getTileStateAfterBullet(BulletUnit bulletUnit) {
            return transitionMap.getOrDefault(bulletUnit.getDirection(), STATE_EMPTY);
        }
    }
}
