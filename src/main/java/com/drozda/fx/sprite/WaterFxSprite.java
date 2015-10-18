package com.drozda.fx.sprite;

import com.drozda.battlecity.unit.TileUnit;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

/**
 * Created by GFH on 18.10.2015.
 */
public class WaterFxSprite extends FxSprite<TileUnit> {

    Rectangle2D[] viewPorts = YabcSprite.TILE_WATER.viewports;

    public WaterFxSprite(TileUnit gameUnit) {
        super(gameUnit);
        animation.play();
    }

    @Override
    protected SpriteAnimation<TileUnit> createAnimation() {
        return new WaterAnimation(Duration.seconds(1), 3,
                Animation.INDEFINITE);
    }

    @Override
    protected Rectangle2D nextSprite(int index) {

        return viewPorts[index];
    }

    protected class WaterAnimation extends SpriteAnimation<TileUnit> {

        public WaterAnimation(Duration duration, int count, int cycleCount) {
            super(duration, count, cycleCount);
        }
    }
}
