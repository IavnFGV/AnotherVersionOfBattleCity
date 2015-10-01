package com.drozda.battlecity;

import java.util.List;
import java.util.Map;

/**
 * Created by GFH on 27.09.2015.
 */
public class TileUnit extends GameUnit implements Destroyable {
    private TileType tileType;

    public TileUnit(double minX, double minY, double width, double height, List<State> stateFlow, Map<State, Long> timeInState, HasGameUnits playground, TileType tileType) {
        super(minX, minY, width, height, stateFlow, timeInState, playground);
        this.tileType = tileType;
    }

    @Override
    public void collide(BulletUnit bulletUnit) {
//todo stub replace
    }

    @Override
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
            if (getTileType() == TileType.STEEL) {
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
}
