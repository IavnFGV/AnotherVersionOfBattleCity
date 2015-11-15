package com.drozda.battlecity.unit;

import com.drozda.battlecity.StaticServices;
import com.drozda.battlecity.interfaces.Collideable;
import com.drozda.battlecity.interfaces.HasGameUnits;
import javafx.geometry.Bounds;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.EnumSet;

/**
 * Created by GFH on 15.11.2015.
 */
public class EnemyTankUnit extends TankUnit<EnemyTankUnit.EnemyTankType> {
    private static EnumSet<EnemyTankType> fastTanks = EnumSet.of(EnemyTankType.TANK_FAST_ENEMY, EnemyTankType
            .TANK_FAST_ENEMY_X);
    private static EnumSet<EnemyTankType> armorTanks = EnumSet.of(EnemyTankType.TANK_ARMOR_ENEMY, EnemyTankType
            .TANK_ARMOR_ENEMY_X);

    public EnemyTankUnit(Bounds bounds,
                         HasGameUnits playground, EnemyTankType tankType) {
        super(bounds, null, null, playground, tankType, 0l);
        if (fastTanks.contains(tankType)) {
            setVelocity(StaticServices.FAST_SPEED);
        } else {
            setVelocity(StaticServices.NORMAL_SPEED);
        }
        if (armorTanks.contains(tankType)) {
            setLifes(4);
        } else {
            setLifes(1);
        }
    }

    @Override
    public ImmutablePair<CollideResult, CollideResult> activeCollide(Collideable other) {
        return null;
    }

    @Override
    public CollideResult passiveCollide(Collideable other) {
        if (other instanceof BulletUnit) {
            BulletUnit bulletUnit = (BulletUnit) other;
            if (bulletUnit.getParent() instanceof PlayerTankUnit) {
                return decLifes(bulletUnit.getType());
            }
        }
        return CollideResult.NOTHING;
    }

    private CollideResult decLifes(BulletUnit.Type bulletType) {
        int curLifes = getLifes();
        if (bulletType == BulletUnit.Type.SIMPLE) {
            setLifes(curLifes - 1);
        } else {
            setLifes(curLifes - 1);
        }
        if (getLifes() > 0) {
            return CollideResult.INNER_STATE_CHANGE;
        } else {
            return CollideResult.STATE_CHANGE;
        }
    }

    @Override
    public boolean isActive() {
        return false;
    }

    public enum EnemyTankType {
        TANK_SIMPLE_ENEMY,
        TANK_FAST_ENEMY,
        TANK_POWER_ENEMY,
        TANK_ARMOR_ENEMY,
        TANK_SIMPLE_ENEMY_X,
        TANK_FAST_ENEMY_X,
        TANK_POWER_ENEMY_X,
        TANK_ARMOR_ENEMY_X,
    }
}
