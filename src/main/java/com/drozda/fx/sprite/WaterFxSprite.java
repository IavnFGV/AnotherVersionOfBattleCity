package com.drozda.fx.sprite;

import com.drozda.battlecity.unit.TileUnit;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Created by GFH on 18.10.2015.
 */
public class WaterFxSprite extends FxSprite<TileUnit> {

    public static String BASIC_WATER_ANIMATION = "BASIC_WATER_ANIMATION";
    static Rectangle2D[] viewPorts = YabcSprite.TILE_WATER.viewports;


    public WaterFxSprite(TileUnit gameUnit) {
        super(gameUnit);

        animationSet.forEach(Transition::play);
        //allAnimations.play();
    }

    @Override
    protected void initSprite() {
        ImageView basicImageView = new ImageView(baseImage);
        WaterAnimation waterAnimation = new WaterAnimation(Duration.millis(500), basicImageView);
        bindImageViewToGameUnit(basicImageView, 0, 0);
        basicImageView.setViewport(nextViewport(waterAnimation.getAnimationId(), 0));
        animationSet.add(waterAnimation);
        super.initSprite();
    }

    @Override
    protected Rectangle2D nextViewport(String animationId, int index) {
        if (animationId.equals(BASIC_WATER_ANIMATION)) {
            return viewPorts[index];
        }
        return super.nextViewport(animationId, index);
    }

    protected class WaterAnimation extends SpriteAnimation<TileUnit> {

        private int count = 3;
        private int lastIndex;

        public WaterAnimation(Duration duration, ImageView imageView) {
            super(duration, imageView);
            setCycleCount(INDEFINITE);
        }

        @Override
        protected void interpolate(double k) {
            final int index = Math.min((int) Math.floor(k * count), count - 1);
            if (index != lastIndex) {
                imageView.setViewport(nextViewport(getAnimationId(), index));
                lastIndex = index;
            }
        }

        @Override
        protected String getAnimationId() {
            return BASIC_WATER_ANIMATION;
        }
    }
}
