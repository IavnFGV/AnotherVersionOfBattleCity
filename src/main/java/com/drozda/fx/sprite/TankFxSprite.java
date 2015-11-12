package com.drozda.fx.sprite;

import com.drozda.battlecity.unit.GameUnit;
import com.drozda.battlecity.unit.TankUnit;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 12.11.2015.
 */
public class TankFxSprite extends MovingFxSprite<TankUnit> {
    private static final Logger log = LoggerFactory.getLogger(TankFxSprite.class);
    static Rectangle2D[] creatingViewports = YabcSprite.CREATING.viewports;

    public TankFxSprite(TankUnit gameUnit) {
        super(gameUnit);
    }

    @Override
    protected void initSprite() {
        ImageView basicImageView = new ImageView(baseImage);
        CreatingAnimation creatingAnimation = new CreatingAnimation(
                Duration.millis(gameUnit.getTimeInState(GameUnit.State.CREATING)
                        / 1000_000), creatingViewports.length, basicImageView);
        bindImageViewToGameUnit(basicImageView, 0, 0);
        basicImageView.setViewport(nextViewport(creatingAnimation.getAnimationType(), 0));
        animationSet.add(creatingAnimation);
        super.initSprite();
    }

    @Override
    protected Rectangle2D nextViewport(AnimationType animationType, int index) {
        if (animationType == AnimationType.ANIMATION_CREATING) {
            return creatingViewports[index];
        }
        return super.nextViewport(animationType, index);
    }

    protected class CreatingAnimation extends SpriteAnimation<TankUnit> {

        private int count;
        private int lastIndex;

        public CreatingAnimation(Duration duration, int count, ImageView imageView) {
            super(duration, imageView);
            this.count = count;
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
            return AnimationType.ANIMATION_CREATING;
        }
    }


}
