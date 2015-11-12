package com.drozda.fx.sprite;

import com.drozda.battlecity.unit.BulletUnit;
import com.drozda.battlecity.unit.EagleBaseUnit;
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
public class ExplodingFxSprite<T extends GameUnit> extends FxSprite<T> {
    private static final Logger log = LoggerFactory.getLogger(ExplodingFxSprite.class);
    public static String EXPLODING_ANIMATION = "EXPLODING_ANIMATION";
    static Rectangle2D[] explosionSmallViewports = YabcSprite.EXPLOSION_SMALL.viewports;
    static Rectangle2D[] explosionBigViewports = YabcSprite.EXPLOSION_BIG.viewports;

    public ExplodingFxSprite(T gameUnit) {
        super(gameUnit);
    }

    @Override
    protected void initSprite() {
        SpriteAnimation basicAnimation = animationSet.stream().
                filter(moveableUnitSpriteAnimation ->
                        moveableUnitSpriteAnimation.getAnimationType().
                                equals(AnimationType.ANIMATION_EXPLODING)).findAny().orElse(null);
        if (basicAnimation == null) {
            if (gameUnit instanceof BulletUnit) {
                initSmallExplosionAnimation();
            }
            if ((gameUnit instanceof TankUnit) || (gameUnit instanceof EagleBaseUnit)) {
                initBigExplosionAnimation();
            }

//            throw new RuntimeException("Something wrong! EXPLODING_ANIMATION MUST be in set till this moment!");
        }
        super.initSprite();
    }

    protected void initSmallExplosionAnimation() {
        ImageView imageView = new ImageView(baseImage);
        ExplosionAnimation smallExplosionAnimation = new ExplosionAnimation(Duration.millis(gameUnit
                .getTimeInState(GameUnit.State.EXPLODING) / 1000_000), explosionSmallViewports.length, imageView);
        bindImageViewToGameUnit(imageView, -12, -12);
        imageView.setViewport(nextViewport(smallExplosionAnimation.getAnimationType(), 0));
        imageView.setVisible(false);
        animationSet.add(smallExplosionAnimation);
    }

    protected void initBigExplosionAnimation() {
        ImageView imageView = new ImageView(baseImage);
        ExplosionAnimation bigExplosionAnimation = new ExplosionAnimation(Duration.millis(gameUnit
                .getTimeInState(GameUnit.State.EXPLODING) / 1000_000), explosionBigViewports.length, imageView);
        bindImageViewToGameUnit(imageView, -16, -16);
        imageView.setViewport(nextViewport(bigExplosionAnimation.getAnimationType(), 0));
        imageView.setVisible(false);
        animationSet.add(bigExplosionAnimation);
    }

    @Override
    protected Rectangle2D nextViewport(AnimationType animationType, int index) {
        if (gameUnit instanceof BulletUnit) {
            return explosionSmallViewports[index];
        }
        if ((gameUnit instanceof TankUnit) || (gameUnit instanceof EagleBaseUnit)) {
            return explosionBigViewports[index];
        }
        return super.nextViewport(animationType, index);
    }

    protected class ExplosionAnimation extends SpriteAnimation<T> {

        private int count;
        private int lastIndex;

        public ExplosionAnimation(Duration duration, int count, ImageView imageView) {
            super(duration, imageView);
            setCycleCount(1);
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
            return AnimationType.ANIMATION_EXPLODING;
        }
    }

}
