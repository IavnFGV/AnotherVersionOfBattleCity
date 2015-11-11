package com.drozda.battlecity.unit;

import com.drozda.battlecity.interfaces.Destroyable;
import com.drozda.battlecity.interfaces.HasGameUnits;
import com.drozda.battlecity.modifier.MovingModifier;
import com.drozda.battlecity.modifier.PositionFixingModifier;
import com.drozda.battlecity.modifier.TankMovingModifier;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Map;

/**
 * Created by GFH on 29.09.2015.
 */
public class TankUnit extends MoveableUnit implements Destroyable {

    protected final PositionFixingModifier<MoveableUnit> fixingModifier;
    protected ListProperty<BonusUnit.BonusType> bonusList = new SimpleListProperty<>();
    protected TankType tankType;
    protected IntegerProperty stars = new SimpleIntegerProperty(0);
    protected IntegerProperty lifes = new SimpleIntegerProperty(1);

    public TankUnit(double minX, double minY, double width, double height,
                    List<State> stateFlow, Map<State, Long> timeInState,
                    HasGameUnits playground, long velocity,
                    TankType tankType, int lifes) {
        super(minX, minY, width, height, stateFlow, timeInState, playground, velocity);
        fixingModifier = new PositionFixingModifier<>(this, playground);
        this.tankType = tankType;
        this.setLifes(lifes);
    }

    public TankUnit(double minX, double minY, double width, double height,
                    HasGameUnits playground, TankType tankType) {
        this(minX, minY, width, height, null, null, playground, tankType);
    }

    public TankUnit(double minX, double minY, double width, double height,
                    List<State> stateFlow, Map<State, Long> timeInState,
                    HasGameUnits playground, TankType tankType) {
        super(minX, minY, width, height, stateFlow, timeInState, playground, 0l);
        fixingModifier = new PositionFixingModifier<>(this, playground);
        this.tankType = tankType;
        long velocity;
        if ((tankType == TankType.TANK_FAST_ENEMY) ||
                (tankType == TankType.TANK_FAST_ENEMY_X)) {
            velocity = fastSpeed;
        } else {
            velocity = normalSpeed;
        }
        setVelocity(velocity);
        if ((tankType == TankType.TANK_ARMOR_ENEMY) ||
                (tankType == TankType.TANK_ARMOR_ENEMY_X)) {
            setLifes(3);
        }
    }

    public int getLifes() {
        return lifes.get();
    }

    public void setLifes(int lifes) {
        this.lifes.set(lifes);
    }

    public IntegerProperty lifesProperty() {
        return lifes;
    }

    public TankType getTankType() {
        return tankType;
    }

    public void setTankType(TankType tankType) {
        this.tankType = tankType;
    }

    public int getStars() {
        return stars.get();
    }

    public void setStars(int stars) {
        this.stars.set(stars);
    }

    public IntegerProperty starsProperty() {
        return stars;
    }

    public ObservableList<BonusUnit.BonusType> getBonusList() {
        return bonusList.get();
    }

    public void setBonusList(ObservableList<BonusUnit.BonusType> bonusList) {
        this.bonusList.set(bonusList);
    }

    public ListProperty<BonusUnit.BonusType> bonusListProperty() {
        return bonusList;
    }

    @Override
    protected MovingModifier<MoveableUnit> createMovingModifier(HasGameUnits playground) {
        return new TankMovingModifier(this, playground);
    }

    @Override
    public void collide(BulletUnit bulletUnit) {
//todo replace

    }

    @Override
    public boolean canBeDestroyed(BulletUnit.Type type) {
        return false;
    }

    @Override
    public String toString() {
        return "TankUnit{" +
                "fixingModifier=" + fixingModifier +
                ", bonusList=" + bonusList +
                ", tankType=" + tankType +
                ", stars=" + stars +
                ", lifes=" + lifes +
                "} " + super.toString();
    }

    public enum TankType {
        TANK_FIRST_PLAYER,
        TANK_SECOND_PLAYER,
        //enemies
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
