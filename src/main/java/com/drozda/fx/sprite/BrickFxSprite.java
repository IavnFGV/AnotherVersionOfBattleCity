package com.drozda.fx.sprite;

import com.drozda.battlecity.unit.TileUnit;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

/**
 * Created by GFH on 09.11.2015.
 */
public class BrickFxSprite extends FxSprite<TileUnit> {
    Rectangle2D[] viewPorts = YabcSprite.TILE_BRICK.viewports;

    public BrickFxSprite(TileUnit gameUnit) {
        super(gameUnit);
        gameUnit.boundsProperty().addListener((observable, oldValue, newValue) -> {
            xProperty().setValue(newValue.getMinX());
            yProperty().setValue(newValue.getMinY());
        });
        xProperty().setValue(gameUnit.getBounds().getMinX());
        yProperty().setValue(gameUnit.getBounds().getMinY());

        animation.play();
    }

    @Override
    protected SpriteAnimation<TileUnit> createAnimation() {
        return new BrickAnimation(Duration.seconds(6), 15,
                Animation.INDEFINITE);
    }

    @Override
    protected Rectangle2D nextSprite(int index) {
        return viewPorts[index];
    }

    protected class BrickAnimation extends SpriteAnimation<TileUnit> {

        public BrickAnimation(Duration duration, int count, int cycleCount) {
            super(duration, count, cycleCount);
        }
    }

}
