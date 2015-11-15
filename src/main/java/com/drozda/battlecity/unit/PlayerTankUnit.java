package com.drozda.battlecity.unit;

import com.drozda.battlecity.StaticServices;
import com.drozda.battlecity.interfaces.Collideable;
import com.drozda.battlecity.interfaces.HasGameUnits;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import org.apache.commons.lang3.tuple.ImmutablePair;

/**
 * Created by GFH on 15.11.2015.
 */
public class PlayerTankUnit extends TankUnit<PlayerTankUnit.PlayerTankType> {

    protected ListProperty<BonusUnit> bonusList = new SimpleListProperty<>(FXCollections.observableArrayList());

    protected IntegerProperty stars = new SimpleIntegerProperty(0);
    protected BonusUnit startGameHelmet = new BonusUnit(BonusUnit.BonusType.START_GAME_HELMET);
    protected BonusUnit friendlyfireGift = new BonusUnit(BonusUnit.BonusType.FRIENDLYFIRE_GIFT);

    public PlayerTankUnit(Bounds bounds, HasGameUnits playground, PlayerTankType tankType) {
        super(bounds, null, null, playground, tankType, 0l);
        playground.getUnitList().addAll(startGameHelmet, friendlyfireGift);
        currentStateProperty().addListener((observable, oldValue, newValue) -> {
                    if ((oldValue == State.CREATING) &&
                            (newValue == State.ACTIVE)) {
                        Platform.runLater(() -> startGameHelmet.takeToPocket(this));
                    }
                }
        );
        setVelocity(StaticServices.NORMAL_SPEED);

    }

    public BonusUnit getStartGameHelmet() {
        return startGameHelmet;
    }

    public void setStartGameHelmet(BonusUnit startGameHelmet) {
        this.startGameHelmet = startGameHelmet;
    }

    public BonusUnit getFriendlyfireGift() {
        return friendlyfireGift;
    }

    public void setFriendlyfireGift(BonusUnit friendlyfireGift) {
        this.friendlyfireGift = friendlyfireGift;
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

    public ObservableList<BonusUnit> getBonusList() {
        return bonusList.get();
    }

    public void setBonusList(ObservableList<BonusUnit> bonusList) {
        this.bonusList.set(bonusList);
    }

    public ListProperty<BonusUnit> bonusListProperty() {
        return bonusList;
    }

    @Override
    public ImmutablePair<CollideResult, CollideResult> activeCollide(GameUnit other) {
        CollideResult selfCollideResult = CollideResult.NOTHING;
        if (!this.getBounds().intersects(other.getBounds())) {
            return NOTHING_PAIR;
        }
        colliding_block:
        {
            if (other instanceof BonusUnit) {
                BonusUnit bonusUnit = (BonusUnit) other;
                selfCollideResult = CollideResult.INNER_STATE_CHANGE;
                break colliding_block;
            }
            if (other instanceof BulletUnit) {
                BulletUnit bulletUnit = (BulletUnit) other;
                if (bulletUnit.getParent() == this) {
                    selfCollideResult = CollideResult.NOTHING;
                    break colliding_block;
                }
                if (bulletUnit.getParent() instanceof PlayerTankUnit) {
                    friendlyfireGift.takeToPocket(this);
                    selfCollideResult = CollideResult.INNER_STATE_CHANGE;
                    break colliding_block;
                }
                if (bulletUnit.getParent() instanceof EnemyTankUnit) {
                    setCurrentState(State.EXPLODING);
                    selfCollideResult = CollideResult.STATE_CHANGE;
                    break colliding_block;
                }
            }
        }
        if (selfCollideResult != CollideResult.NOTHING) {
            return new ImmutablePair<>(selfCollideResult, ((Collideable) other).passiveCollide(this));
        }
        return NOTHING_PAIR;
    }

    @Override
    public CollideResult passiveCollide(GameUnit other) {
        if (other instanceof BulletUnit) {
            BulletUnit bulletUnit = (BulletUnit) other;
            if (bulletUnit.getParent() == this) {
                return CollideResult.NOTHING;
            }
            if (bulletUnit.getParent() instanceof PlayerTankUnit) {
                friendlyfireGift.takeToPocket(this);
                return CollideResult.INNER_STATE_CHANGE;
            }
            if (bulletUnit.getParent() instanceof EnemyTankUnit) {
                setCurrentState(State.EXPLODING);
                return CollideResult.STATE_CHANGE;
            }
        }
        return CollideResult.NOTHING;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    public enum PlayerTankType {
        TANK_FIRST_PLAYER,
        TANK_SECOND_PLAYER
    }
}
