package com.drozda.fx.sprite;

import com.drozda.battlecity.unit.TileUnit;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

/**
 * Created by GFH on 09.11.2015.
 */
public class BrickFxSprite<T extends TileUnit> extends FxSprite<T> {
    private static Rectangle2D[] viewPorts = YabcSprite.TILE_BRICK.viewports;

    public BrickFxSprite(T gameUnit) {
        super(gameUnit);
    }

    @Override
    protected void initSprite() {
        if (animationSet.stream()
                .filter(tileUnitSpriteAnimation -> tileUnitSpriteAnimation.getAnimationType() == AnimationType.ANIMATION_ACTIVE)
                .count() < 1) {
            ImageView imageView = new ImageView(baseImage);
            gameUnit.tileStateProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == TileUnit.TileState.STATE_EMPTY) {
                    return;
                }
                imageView.setViewport(viewPorts[(tileStateToViewportIndex(newValue))]);
            });
            imageView.setViewport(viewPorts[tileStateToViewportIndex(gameUnit.getTileState())]);
            bindImageViewToGameUnit(imageView, 0, 0);
            SpriteAnimation brickAnimation = new BlankSpriteAnimation(imageView, AnimationType.ANIMATION_ACTIVE);
            animationSet.add(brickAnimation);
        }
        super.initSprite();
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
            default:
                return 0;
        }
    }
}
