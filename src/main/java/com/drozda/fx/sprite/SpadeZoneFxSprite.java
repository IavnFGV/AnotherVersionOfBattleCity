package com.drozda.fx.sprite;

import com.drozda.battlecity.unit.SpadeZoneTileUnit;
import com.drozda.battlecity.unit.TileUnit;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

/**
 * Created by GFH on 14.11.2015.
 */
public class SpadeZoneFxSprite extends BrickFxSprite<SpadeZoneTileUnit> {
    static Rectangle2D[] steelViewports = YabcSprite.TILE_STEEL.viewports;
    static Rectangle2D[] brickViewports = YabcSprite.TILE_BRICK.viewports;

    public SpadeZoneFxSprite(SpadeZoneTileUnit gameUnit) {
        super(gameUnit);
    }

    @Override
    protected void toArmorState() {
        hideAnimation(AnimationType.ANIMATION_ACTIVE, AnimationType.ANIMATION_CREATING, AnimationType.ANIMATION_EXPLODING);
        showAnimation(AnimationType.ANIMATION_BLINK);
    }

    @Override
    protected void initSprite() {
        ImageView imageView = new ImageView(baseImage);
        gameUnit.tileTypeProperty().addListener((observable1, oldValue1, newValue1) -> {
            if (newValue1 == TileUnit.TileType.BRICK) {
                imageView.setViewport(brickViewports[tileStateToViewportIndex(gameUnit.getTileState())]);
            }
            if (newValue1 == TileUnit.TileType.STEEL) {
                imageView.setViewport(steelViewports[0]);
            }
        });
        imageView.setViewport(steelViewports[0]);
        bindImageViewToGameUnit(imageView, 0, 0);
        SpriteAnimation blinkAnimation = new BlankSpriteAnimation(imageView, AnimationType.ANIMATION_BLINK);
        animationSet.add(blinkAnimation);

        super.initSprite();
    }

}
