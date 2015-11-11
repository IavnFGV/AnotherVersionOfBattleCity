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
public abstract class TankFxSprite extends FxSprite<TankUnit> {
    private static final Logger log = LoggerFactory.getLogger(TankFxSprite.class);
    protected Map<Integer, Rectangle2D[]> viewportsMap = new HashMap<>();


    public TankFxSprite(TankUnit gameUnit) {
        super(gameUnit);
    }

    @Override
    protected SpriteAnimation<TankUnit> createAnimation(ImageView imageView) {
        return new BasicTankAnimation(Duration.millis(200), baseImageView);
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
