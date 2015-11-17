package com.drozda.battlecity.unit;

import com.drozda.battlecity.StaticServices;
import com.drozda.battlecity.interfaces.BattleGround;
import javafx.geometry.Bounds;

import java.util.EnumSet;

/**
 * Created by GFH on 15.11.2015.
 */
public class EnemyTankUnit extends TankUnit<EnemyTankUnit.EnemyTankType> {
    private static EnumSet<EnemyTankType> fastTanks = EnumSet.of(EnemyTankType.TANK_FAST_ENEMY, EnemyTankType
            .TANK_FAST_ENEMY_X);
    private static EnumSet<EnemyTankType> armorTanks = EnumSet.of(EnemyTankType.TANK_ARMOR_ENEMY, EnemyTankType
            .TANK_ARMOR_ENEMY_X);
    private static EnumSet<EnemyTankType> tanksWithSpeedBullets = EnumSet.of(EnemyTankType.TANK_POWER_ENEMY,
            EnemyTankType.TANK_POWER_ENEMY_X);

    public EnemyTankUnit(Bounds bounds,
                         BattleGround playground, EnemyTankType tankType) {
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

    public int getLifesAfterBullet(BulletUnit.Type type) {
        int newLifes = getLifes();
        switch (type) {
            case POWERFUL:
                newLifes--;
            case SIMPLE:
                newLifes--;
                break;
        }
        return newLifes;
    }

    @Override
    public long getBulletSpeed() {
        if (tanksWithSpeedBullets.contains(getTankType())) {
            return StaticServices.FAST_BULLET_SPEED;
        }
        return StaticServices.NORMAL_BULLET_SPEED;
    }

    @Override
    public BulletUnit.Type getBulletType() {
        return BulletUnit.Type.SIMPLE;
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
