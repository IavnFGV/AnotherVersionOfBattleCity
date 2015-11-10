package com.drozda.fx.sprite;

import com.drozda.battlecity.unit.TileUnit;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Created by GFH on 18.10.2015.
 */
public class WaterFxSprite extends FxSprite<TileUnit> {

    Rectangle2D[] viewPorts = YabcSprite.TILE_WATER.viewports;


    public WaterFxSprite(TileUnit gameUnit) {
        super(gameUnit);

        initSprite();
        allAnimations.play();
    }

    @Override
    protected SpriteAnimation<TileUnit> createAnimation(ImageView imageView) {
        return new WaterAnimation(Duration.seconds(1), 3,
                Animation.INDEFINITE, imageView);
    }

    @Override
    protected Rectangle2D nextSprite(int index) {

        return viewPorts[index];
    }

    protected class WaterAnimation extends SpriteAnimation<TileUnit> {

        private int count;
        private int lastIndex;

        public WaterAnimation(Duration duration, int count, int cycleCount, ImageView imageView) {
            super(duration, imageView);
            this.count = count;
            setCycleCount(cycleCount);
        }

        @Override
        protected void interpolate(double k) {
            final int index = Math.min((int) Math.floor(k * count), count - 1);
            if (index != lastIndex) {
                imageView.setViewport(nextSprite(index));
                lastIndex = index;
            }

        }
    }
}
