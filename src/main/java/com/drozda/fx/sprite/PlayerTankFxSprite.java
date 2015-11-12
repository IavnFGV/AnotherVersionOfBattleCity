package com.drozda.fx.sprite;

import com.drozda.battlecity.unit.TankUnit;
import javafx.animation.Transition;
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
public class PlayerTankFxSprite extends MovingFxSprite<TankUnit> {
    private static final Logger log = LoggerFactory.getLogger(PlayerTankFxSprite.class);
    public static String HELMET_TANK_ANIMATION = "HELMET_TANK_ANIMATION";
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
        animationSet.forEach(Transition::play);
        //allAnimations.play();
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
        basicImageView.setViewport(nextViewport(basicTankMoveAnimation.getAnimationId(), 0));


        gameUnit.starsProperty().addListener((observable, oldValue, newValue) -> {
            basicImageView.setViewport(nextViewport(basicTankMoveAnimation.getAnimationId(), 0));
        });
        ImageView helmetImageView = new ImageView(baseImage);
        HelmetAnimation helmetAnimation = new HelmetAnimation(Duration.millis(100), helmetImageView);
        bindImageViewToGameUnit(helmetImageView, 0, 0);
        helmetImageView.setViewport(nextViewport(helmetAnimation.getAnimationId(), 0));
        animationSet.add(helmetAnimation);
        animationSet.add(basicTankMoveAnimation);
        super.initSprite();
    }

    @Override
    protected Rectangle2D nextViewport(String animationId, int index) {
        if (animationId.equals(BASIC_MOVING_ANIMATION)) {
            return viewportsMap.get(gameUnit.getStars())[index];
        }
        if (animationId.equals(HELMET_TANK_ANIMATION)) {
            return helmetViewports[index];
        }
        return super.nextViewport(animationId, index);
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
                    imageView.setViewport(nextViewport(getAnimationId(), index));
                    lastIndex = index;
                }

            }
        }

        @Override
        protected String getAnimationId() {
            return HELMET_TANK_ANIMATION;
        }
    }

    protected class BasicTankMoveAnimation extends SpriteAnimation<TankUnit> {
        private int count = 2;

        @Override
        protected String getAnimationId() {
            return BASIC_MOVING_ANIMATION;
        }

        private int lastIndex;

        public BasicTankMoveAnimation(
                Duration duration,
                ImageView imageView
        ) {
            super(duration, imageView);
            setCycleDuration(duration);
            setCycleCount(INDEFINITE);
        }


        @Override
        protected void interpolate(double k) {
            final int index = Math.min((int) Math.floor(k * count), count - 1);
            if ((index != lastIndex) && (gameUnit.getEngineOn())) {
                imageView.setViewport(nextViewport(getAnimationId(), index));
                lastIndex = index;
            }
        }
    }
}
