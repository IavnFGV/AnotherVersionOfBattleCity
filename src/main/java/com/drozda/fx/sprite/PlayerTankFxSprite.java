package com.drozda.fx.sprite;

import com.drozda.battlecity.unit.BonusUnit;
import com.drozda.battlecity.unit.TankUnit;
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
public class PlayerTankFxSprite extends TankFxSprite {
    private static final Logger log = LoggerFactory.getLogger(PlayerTankFxSprite.class);
    static Rectangle2D[] helmetViewports = YabcSprite.BONUS_HELMET.viewports;
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


    public PlayerTankFxSprite(TankUnit gameUnit) {
        super(gameUnit);
    }

    @Override
    protected void initSprite() {
        viewportsMap = new HashMap<>();
        if (gameUnit.getTankType() == TankUnit.TankType.TANK_FIRST_PLAYER) {
            viewportsMap = firstPlayerviewportsMap;
        }
        if (gameUnit.getTankType() == TankUnit.TankType.TANK_SECOND_PLAYER) {
            viewportsMap = secondPlayerviewportsMap;
        }

        ImageView basicImageView = new ImageView(baseImage);
        BasicTankMoveAnimation basicTankMoveAnimation = new BasicTankMoveAnimation(Duration.millis(200), basicImageView);
        bindImageViewToGameUnit(basicImageView, 0, 0);
        basicImageView.setViewport(nextViewport(basicTankMoveAnimation.getAnimationType(), 0));

        gameUnit.starsProperty().addListener((observable, oldValue, newValue) -> {
            basicImageView.setViewport(nextViewport(basicTankMoveAnimation.getAnimationType(), 0));
        });
        ImageView helmetImageView = new ImageView(baseImage);
        helmetImageView.setVisible(false);
        HelmetAnimation helmetAnimation = new HelmetAnimation(Duration.millis(100), helmetImageView);
        bindImageViewToGameUnit(helmetImageView, 0, 0);
        helmetImageView.setViewport(nextViewport(helmetAnimation.getAnimationType(), 0));

        gameUnit.getBonusList().addListener(
                new ListChangeListener<BonusUnit.BonusType>() {
                    @Override
                    public void onChanged(Change<? extends BonusUnit.BonusType> c) {
                        while (c.next()) {
                            if (c.wasPermutated()) {
                                for (int i = c.getFrom(); i < c.getTo(); ++i) {
                                    //permutate
                                }
                            } else if (c.wasUpdated()) {
                                //update item
                            } else {
                                for (BonusUnit.BonusType remitem : c.getRemoved()) {
                                    if (remitem == BonusUnit.BonusType.HELMET) {
                                        turnOffAnimation(AnimationType.ANIMATION_HELMET);
                                    }
                                }
                                for (BonusUnit.BonusType additem : c.getAddedSubList()) {
                                    if (additem == BonusUnit.BonusType.HELMET) {
                                        turnOnAnimation(AnimationType.ANIMATION_HELMET);
                                    }
                                }
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

    protected class HelmetAnimation extends SpriteAnimation<TankUnit> {

        private int count = 2;
        private int lastIndex;

        public HelmetAnimation(Duration duration, ImageView imageView) {
            super(duration, imageView);
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

        @Override
        protected AnimationType getAnimationType() {
            return AnimationType.ANIMATION_HELMET;
        }
    }

}