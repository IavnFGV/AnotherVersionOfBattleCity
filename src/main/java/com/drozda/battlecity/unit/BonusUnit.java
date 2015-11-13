package com.drozda.battlecity.unit;

import com.drozda.battlecity.StaticServices;
import com.drozda.battlecity.interfaces.HasGameUnits;

import java.util.HashMap;

import static java.util.Arrays.asList;

/**
 * Created by GFH on 26.09.2015.
 */
public class BonusUnit extends GameUnit {
    private final BonusType bonusType;
    public boolean processed;

    public BonusUnit(double minX, double minY, double width, double height, HasGameUnits playground, BonusType bonusType) {
        super(minX, minY, width, height,
                asList(State.ACTIVE, State.BLINK, State.DEAD, State.IN_POCKET, State.DEAD),
                new HashMap<State, Long>() {{
                    put(State.ACTIVE, StaticServices.ONE_SECOND * 10);
                    put(State.BLINK, StaticServices.ONE_SECOND / 2);
                    put(State.IN_POCKET, bonusType.timeInPocket);
                }}, playground);
        this.bonusType = bonusType;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public void takeToPocket(TankUnit tankUnit) {
        this.setCurrentState(State.IN_POCKET);
        tankUnit.getBonusList().add(this);
    }

    public boolean isOneTime() {
        return getBonusType().isOneTime;
    }

    public BonusType getBonusType() {
        return bonusType;
    }

    @Override
    public String toString() {
        return "BonusUnit{" +
                "bonusType=" + bonusType +
                "} " + super.toString();
    }

    public enum BonusType {
        START_GAME_HELMET(false, 3 * StaticServices.ONE_SECOND),
        HELMET(false, 10 * StaticServices.ONE_SECOND),
        CLOCK(false, 3 * StaticServices.ONE_SECOND),
        SPADE(true, 0l),
        STAR(true, 0l),
        GRENADE(true, 0l),
        TANK(true, 0l),
        GUN(true, 0l);

        public final boolean isOneTime;
        public final long timeInPocket;

        BonusType(boolean isOneTime, long timeInPocket) {
            this.isOneTime = isOneTime;
            this.timeInPocket = timeInPocket;
        }
    }
}

