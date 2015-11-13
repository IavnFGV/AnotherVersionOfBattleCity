package com.drozda.fx.sprite;

import com.drozda.battlecity.unit.TileUnit;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Created by GFH on 18.10.2015.
 */
public class WaterFxSprite extends FxSprite<TileUnit> {

    static Rectangle2D[] viewPorts = YabcSprite.TILE_WATER.viewports;


    public WaterFxSprite(TileUnit gameUnit) {
        super(gameUnit);
    }

    @Override
    protected void initSprite() {
        ImageView basicImageView = new ImageView(baseImage);
        WaterAnimation waterAnimation = new WaterAnimation(Duration.millis(500), basicImageView);
        bindImageViewToGameUnit(basicImageView, 0, 0);
        basicImageView.setViewport(nextViewport(waterAnimation.getAnimationType(), 0));
        animationSet.add(waterAnimation);
        super.initSprite();
    }

    @Override
    protected Rectangle2D nextViewport(AnimationType animationType, int index) {
        if (animationType.equals(AnimationType.ANIMATION_ACTIVE)) {
            return viewPorts[index];
        }
        return super.nextViewport(animationType, index);
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
            if (!imageView.isVisible()) return;
            final int index = Math.min((int) Math.floor(k * count), count - 1);
            if (index != lastIndex) {
                imageView.setViewport(nextViewport(getAnimationType(), index));
                lastIndex = index;
            }
        }

        @Override
        protected AnimationType getAnimationType() {
            return AnimationType.ANIMATION_ACTIVE;
        }

        @Override
        protected boolean isBackGroundAnimation() {
            return true;
        }
    }
}
