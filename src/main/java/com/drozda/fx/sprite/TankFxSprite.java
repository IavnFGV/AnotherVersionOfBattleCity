package com.drozda.fx.sprite;

import com.drozda.battlecity.unit.TankUnit;
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
public class TankFxSprite extends FxSprite<TankUnit> {
    private static final Logger log = LoggerFactory.getLogger(TankFxSprite.class);
    Map<Integer, Rectangle2D[]> viewPorsMap = new HashMap<>();

    public TankFxSprite(TankUnit gameUnit) {
        super(gameUnit);
        viewPorsMap.put(0, YabcSprite.TANK_FIRST_PLAYER_0_STAR.viewports);
        viewPorsMap.put(1, YabcSprite.TANK_FIRST_PLAYER_1_STAR.viewports);
        viewPorsMap.put(2, YabcSprite.TANK_FIRST_PLAYER_2_STAR.viewports);
        viewPorsMap.put(3, YabcSprite.TANK_FIRST_PLAYER_3_STAR.viewports);
        initSprite();
        baseImageView.setViewport(nextSprite(0));
        gameUnit.starsProperty().addListener((observable, oldValue, newValue) -> {
            baseImageView.setViewport(nextSprite(0));
        });

        allAnimations.play();
    }

    @Override
    protected SpriteAnimation<TankUnit> createAnimation(ImageView imageView) {
        return new BasicTankAnimation(Duration.millis(200), baseImageView);
    }

    @Override
    protected Rectangle2D nextSprite(int index) {
        return viewPorsMap.get(gameUnit.getStars())[index];
    }

    protected class BasicTankAnimation extends SpriteAnimation<TankUnit> {

        private int count = 2;
        private int lastIndex;

        public BasicTankAnimation(
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
                imageView.setViewport(nextSprite(index));
                lastIndex = index;
            }
        }
    }
}
