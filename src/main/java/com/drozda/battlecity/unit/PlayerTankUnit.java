package com.drozda.battlecity.unit;

import com.drozda.battlecity.StaticServices;
import com.drozda.battlecity.interfaces.BattleGround;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;

/**
 * Created by GFH on 15.11.2015.
 */
public class PlayerTankUnit extends TankUnit<PlayerTankUnit.PlayerTankType> {

    protected ListProperty<BonusUnit> bonusList = new SimpleListProperty<>(FXCollections.observableArrayList());

    protected IntegerProperty stars = new SimpleIntegerProperty(0);
    protected BonusUnit startGameHelmet = new BonusUnit(BonusUnit.BonusType.START_GAME_HELMET);
    protected BonusUnit friendlyfireGift = new BonusUnit(BonusUnit.BonusType.FRIENDLYFIRE_GIFT);

    public PlayerTankUnit(Bounds bounds, BattleGround playground, PlayerTankType tankType) {
        super(bounds, null, null, playground, tankType, 0l);
        playground.getUnitList().addAll(startGameHelmet, friendlyfireGift);
        currentStateProperty().addListener((observable, oldValue, newValue) -> {
                    if ((oldValue == State.CREATING) &&
                            (newValue == State.ACTIVE)) {
                        Platform.runLater(() -> startGameHelmet.takeToPocket(this));
                    }
                    if (newValue == State.DEAD) {
                        setStars(0);
                    }
                }
        );
        setVelocity(StaticServices.NORMAL_SPEED);

    }

    @Override
    public boolean isActive() {
        return true;
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
    public long getBulletSpeed() {
        if (getStars() > 1) {
            return StaticServices.FAST_BULLET_SPEED;
        }
        return StaticServices.NORMAL_BULLET_SPEED;
    }

    public int getStars() {
        return stars.get();
    }

    public void setStars(int stars) {
        this.stars.set(stars);
    }

    @Override
    public BulletUnit.Type getBulletType() {
        if (getStars() > 2) {
            return BulletUnit.Type.POWERFUL;
        }
        return BulletUnit.Type.SIMPLE;
    }


    public enum PlayerTankType {
        TANK_FIRST_PLAYER,
        TANK_SECOND_PLAYER
    }
}
