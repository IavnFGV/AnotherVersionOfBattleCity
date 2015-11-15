package com.drozda.fx.sprite;

import com.drozda.battlecity.unit.BonusUnit;
import com.drozda.battlecity.unit.PlayerTankUnit;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.collections.ListChangeListener;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GFH on 11.11.2015.
 */
public class PlayerTankFxSprite extends TankFxSprite<PlayerTankUnit> {
    private static final Logger log = LoggerFactory.getLogger(PlayerTankFxSprite.class);
    static Rectangle2D[] helmetViewports = YabcSprite.BONUS_HELMET_ON_TANK.viewports;
    static Map<Integer, Rectangle2D[]> firstPlayerviewportsMap = new HashMap<>();
    static Map<Integer, Rectangle2D[]> secondPlayerviewportsMap = new HashMap<>();

    static {
        firstPlayerviewportsMap.put(0, YabcSprite.TANK_FIRST_PLAYER_0_STAR.viewports);
        firstPlayerviewportsMap.put(1, YabcSprite.TANK_FIRST_PLAYER_1_STAR.viewports);
        firstPlayerviewportsMap.put(2, YabcSprite.TANK_FIRST_PLAYER_2_STAR.viewports);
        firstPlayerviewportsMap.put(3, YabcSprite.TANK_FIRST_PLAYER_3_STAR.viewports);

        secondPlayerviewportsMap.put(0, YabcSprite.TANK_SECOND_PLAYER_0_STAR.viewports);
        secondPlayerviewportsMap.put(1, YabcSprite.TANK_SECOND_PLAYER_1_STAR.viewports);
        secondPlayerviewportsMap.put(2, YabcSprite.TANK_SECOND_PLAYER_2_STAR.viewports);
        secondPlayerviewportsMap.put(3, YabcSprite.TANK_SECOND_PLAYER_3_STAR.viewports);

    }

    protected Map<Integer, Rectangle2D[]> viewportsMap;

    private FadeTransition fadeTransition; // for friendlyfire

    public PlayerTankFxSprite(PlayerTankUnit gameUnit) {
        super(gameUnit);
    }

    @Override
    protected void initSprite() {
        viewportsMap = new HashMap<>();
        if (gameUnit.getTankType() == PlayerTankUnit.PlayerTankType.TANK_FIRST_PLAYER) {
            viewportsMap = firstPlayerviewportsMap;
        }
        if (gameUnit.getTankType() == PlayerTankUnit.PlayerTankType.TANK_SECOND_PLAYER) {
            viewportsMap = secondPlayerviewportsMap;
        }

        ImageView basicImageView = new ImageView(baseImage);
        BasicTankMoveAnimation basicTankMoveAnimation =
                new BasicTankMoveAnimation(Duration.millis(200), basicImageView, AnimationType.ANIMATION_ACTIVE);
        bindImageViewToGameUnit(basicImageView, 0, 0);
        basicImageView.setViewport(nextViewport(basicTankMoveAnimation.getAnimationType(), 0));

        fadeTransition = new FadeTransition(Duration.millis(300), basicImageView);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.);
        fadeTransition.setCycleCount(Timeline.INDEFINITE);
        fadeTransition.setAutoReverse(true);


        gameUnit.starsProperty().addListener((observable, oldValue, newValue) -> {
            basicImageView.setViewport(nextViewport(basicTankMoveAnimation.getAnimationType(), 0));
        });
        ImageView helmetImageView = new ImageView(baseImage);
        helmetImageView.setVisible(false);
        HelmetAnimation helmetAnimation =
                new HelmetAnimation(Duration.millis(100), helmetImageView, AnimationType.ANIMATION_HELMET);
        bindImageViewToGameUnit(helmetImageView, 0, 0);
        helmetImageView.setViewport(nextViewport(helmetAnimation.getAnimationType(), 0));

        gameUnit.getBonusList().addListener(
                (ListChangeListener<BonusUnit>) c -> {
                    while (c.next()) {
                        if (c.wasRemoved()) {
                            for (BonusUnit remitem : c.getRemoved()) {
                                if ((remitem.getBonusType() == BonusUnit.BonusType.HELMET) ||
                                        (remitem.getBonusType() == BonusUnit.BonusType.START_GAME_HELMET)) {
                                    hideAnimation(AnimationType.ANIMATION_HELMET);
                                }
                                if (remitem.getBonusType() == BonusUnit.BonusType.FRIENDLYFIRE_GIFT) {
                                    fadeTransition.pause();
                                }
                            }
                        } else if (c.wasAdded())
                            for (BonusUnit additem : c.getAddedSubList()) {
                                if ((additem.getBonusType() == BonusUnit.BonusType.HELMET) ||
                                        (additem.getBonusType() == BonusUnit.BonusType.START_GAME_HELMET)) {
                                    showAnimation(AnimationType.ANIMATION_HELMET);
                                }
                                if (additem.getBonusType() == BonusUnit.BonusType.FRIENDLYFIRE_GIFT) {
                                    fadeTransition.play();
                                }
                            }
                    }
                }
        );

        animationSet.add(helmetAnimation);
        animationSet.add(basicTankMoveAnimation);
        super.initSprite();
    }

    @Override
    protected Rectangle2D nextViewport(AnimationType animationType, int index) {
        if (animationType.equals(AnimationType.ANIMATION_ACTIVE)) {
            return viewportsMap.get(gameUnit.getStars())[index];
        }
        if (animationType.equals(AnimationType.ANIMATION_HELMET)) {
            return helmetViewports[index];
        }
        return super.nextViewport(animationType, index);
    }

    @Override
    protected void toActiveState() {
        super.toActiveState();
        gameUnit.getBonusList().stream()
                .filter(bonusUnit ->
                        (((bonusUnit.getBonusType() == BonusUnit.BonusType.HELMET)) ||
                                (bonusUnit.getBonusType() == BonusUnit.BonusType.START_GAME_HELMET)))
                .findFirst()
                .ifPresent(bonusUnit1 -> showAnimation(AnimationType.ANIMATION_HELMET));
    }

    protected class HelmetAnimation extends SpriteAnimation<PlayerTankUnit> {

        private int count = 2;
        private int lastIndex;

        public HelmetAnimation(Duration duration, ImageView imageView, AnimationType animationType) {
            super(duration, imageView, animationType);
            setCycleCount(INDEFINITE);
        }

        @Override
        protected void interpolate(double k) {
            if (imageView.isVisible()) {
                final int index = Math.min((int) Math.floor(k * count), count - 1);
                if (index != lastIndex) {
                    imageView.setViewport(nextViewport(getAnimationType(), index));
                    lastIndex = index;
                }

            }
        }
    }

}
