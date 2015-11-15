package com.drozda.battlecity.unit;

import com.drozda.battlecity.StaticServices;
import com.drozda.battlecity.interfaces.Collideable;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

import java.util.HashMap;

import static java.util.Arrays.asList;

/**
 * Created by GFH on 26.09.2015.
 */
public class BonusUnit extends GameUnit implements Collideable<PlayerTankUnit> {
    private final BonusType bonusType;
    public boolean processed;

    public BonusUnit(Bounds bounds, BonusType bonusType) {
        super(
                bounds, asList(State.ACTIVE, State.BLINK, State.DEAD, State.IN_POCKET, State.DEAD),
                new HashMap<State, Long>() {{
                    put(State.ACTIVE, StaticServices.ONE_SECOND * 10);
                    put(State.BLINK, StaticServices.ONE_SECOND / 2);
                    put(State.IN_POCKET, bonusType.timeInPocket);
                }});
        this.bonusType = bonusType;
    }

    public BonusUnit(BonusType bonusType) {
        super(
                new BoundingBox(0, 0, 0, 0), asList(State.DEAD, State.IN_POCKET, State.DEAD),
                new HashMap<State, Long>() {{
                    put(State.IN_POCKET, bonusType.timeInPocket);
                }});
        this.bonusType = bonusType;
        if (!bonusType.isAux) {
            throw new RuntimeException("Bonustype = " + bonusType + " cannot be created with this constructor");
        }
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
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
                "}";
    }

    @Override
    public CollideResult passiveCollide(PlayerTankUnit other) {
        this.takeToPocket(other);
        return CollideResult.STATE_CHANGE;
    }

    public void takeToPocket(PlayerTankUnit tankUnit) {
        if (!getBonusType().isOneTime) {
            BonusUnit bonusUnit = tankUnit.getBonusList().stream()
                    .filter(bonusUnit1 -> bonusUnit1.getBonusType() == getBonusType())
                    .findFirst().orElse(null);
            if (bonusUnit != null) {
                bonusUnit.resetTimeInCurrentState();
                if (!this.isAux()) {
                    this.setCurrentState(State.DEAD);
                }
                return;
            }
        }
        this.setCurrentState(State.IN_POCKET);
        tankUnit.getBonusList().add(this);
        this.currentStateProperty().addListener((observable, oldValue, newValue) -> {
            if ((oldValue == State.IN_POCKET) && (newValue == State.DEAD)) {
                tankUnit.getBonusList().remove(this);
            }
        });
    }

    public boolean isAux() {
        return getBonusType().isAux;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public boolean isTakingPartInCollisionProcess() {
        return !this.isAux();
    }

    public enum BonusType {
        START_GAME_HELMET(false, 3 * StaticServices.ONE_SECOND, true),
        FRIENDLYFIRE_GIFT(false, 3 * StaticServices.ONE_SECOND, true),
        HELMET(false, 10 * StaticServices.ONE_SECOND, false),
        CLOCK(false, 3 * StaticServices.ONE_SECOND, false),
        SPADE(true, 0l, false),
        STAR(true, 0l, false),
        GRENADE(true, 0l, false),
        TANK(true, 0l, false),
        GUN(true, 0l, false);

        public final boolean isOneTime;
        public final long timeInPocket;
        public final boolean isAux;

        BonusType(boolean isOneTime, long timeInPocket, boolean isAux) {
            this.isOneTime = isOneTime;
            this.timeInPocket = timeInPocket;
            this.isAux = isAux;
        }
    }
}

