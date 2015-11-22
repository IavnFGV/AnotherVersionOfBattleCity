package com.drozda.battlecity.unitx;

import com.drozda.battlecity.interfacesx.BattleGround;
import com.drozda.battlecity.unitx.enumeration.TileType;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

/**
 * Created by GFH on 20.11.2015.
 */
public class TileUnitX extends GameUnitX {
    private TileType tileType;
    private Bounds boundsFixedToGreed;

    public TileUnitX(BattleGround playground, TileType tileType) {
        super(playground);
        this.tileType = tileType;
    }

    @Override
    public Bounds getBoundsFixedToGreed() {
        if (boundsFixedToGreed == null) {
            if (getBoundsListProperty().get().size() > 0) {
                Bounds firstBounds = getBoundsListProperty().get(0);
                boundsFixedToGreed = new BoundingBox(firstBounds.getMinX(), firstBounds.getMinY(), playground
                        .getTileWidth(), playground.getTileHeight());
            }
        }
        return boundsFixedToGreed;
    }

    @Override
    public Class getEventObjectType() {
        throw new RuntimeException("getEventObjectType does not make sense for application");
    }

    @Override
    public String toString() {
        return "TileUnitX{" +
                "tileType=" + tileType +
                "} " + super.toString();
    }

    public TileType getTileType() {
        return tileType;
    }
}
