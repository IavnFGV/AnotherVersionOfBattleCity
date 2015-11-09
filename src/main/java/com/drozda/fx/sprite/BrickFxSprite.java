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
        animation.lastIndex = 1;//instead of initialization  - change to 1 for reset viewport in first iteration
        animation.play();
    }

    @Override
    protected SpriteAnimation<TileUnit> createAnimation() {
        return new BrickAnimation();
    }

    @Override
    protected Rectangle2D nextSprite(int index) {
        return viewPorts[index];
    }

    protected class BrickAnimation extends SpriteAnimation<TileUnit> {

        public BrickAnimation() {
            super(Duration.seconds(6), 15,
                    Animation.INDEFINITE);

        }

        protected void interpolate(double k) {
            final int index = tileStateToViewportIndex(gameUnit.getTileState());

            if (index != lastIndex) {
                BrickFxSprite.this.setViewport(nextSprite(index));
                lastIndex = index;
            }
        }

        protected int tileStateToViewportIndex(TileUnit.TileState tileState) {
            switch (tileState) {
                case STATE_0001:
                    return 12;
                case STATE_0010:
                    return 11;
                case STATE_0011:
                    return 5;
                case STATE_0100:
                    return 10;
                case STATE_0101:
                    return 13;
                case STATE_0110:
                    return 8;
                case STATE_0111:
                    return 1;
                case STATE_1000:
                    return 9;
                case STATE_1001:
                    return 6;
                case STATE_1010:
                    return 14;
                case STATE_1011:
                    return 2;
                case STATE_1100:
                    return 7;
                case STATE_1101:
                    return 3;
                case STATE_1110:
                    return 4;
                case STATE_1111:
                    return 0;
            }
            return 0;
        }


    }

}
