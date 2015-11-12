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
                        moveableUnitSpriteAnimation.getAnimationId().
                                equals(EXPLODING_ANIMATION)).findAny().orElse(null);
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
        ImageView basicImageView = new ImageView(baseImage);
        SmallExplosionAnimation smallExplosionAnimation = new SmallExplosionAnimation(Duration.millis(gameUnit
                .getTimeInState(GameUnit.State.EXPLODING) / 1000), imageView);
        bindImageViewToGameUnit(basicImageView, 0, 0);
        basicImageView.setViewport(nextViewport(smallExplosionAnimation.getAnimationId(), 0));

    }

    protected void initBigExplosionAnimation() {

    }

    protected class SmallExplosionAnimation extends SpriteAnimation<T> {

        private int count = explosionSmallViewports.length;
        private int lastIndex;

        public SmallExplosionAnimation(Duration duration, ImageView imageView) {
            super(duration, imageView);
        }

        @Override
        protected void interpolate(double k) {
            final int index = Math.min((int) Math.floor(k * count), count - 1);
            if (gameUnit.getCurrentState() == GameUnit.State.EXPLODING) {
                imageView.setVisible(true);
                if (index != lastIndex) {
                    imageView.setViewport(nextViewport(getAnimationId(), index));
                    lastIndex = index;
                }
            } else {
                imageView.setVisible(false);
            }
        }

        @Override
        protected String getAnimationId() {
            return EXPLODING_ANIMATION;
        }
    }

}
