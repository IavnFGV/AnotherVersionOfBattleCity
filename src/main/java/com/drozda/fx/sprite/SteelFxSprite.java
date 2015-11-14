package com.drozda.fx.sprite;

import com.drozda.battlecity.unit.TileUnit;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

/**
 * Created by GFH on 10.11.2015.
 */
public class SteelFxSprite extends FxSprite<TileUnit> {
    static Rectangle2D[] viewports = YabcSprite.TILE_STEEL.viewports;

    public SteelFxSprite(TileUnit gameUnit) {
        super(gameUnit);
    }

    @Override
    protected void initSprite() {
        ImageView imageView = new ImageView(baseImage);
        imageView.setViewport(viewports[0]);
        SpriteAnimation spriteAnimation = new SpriteAnimation(imageView) {
            @Override
            protected AnimationType getAnimationType() {
                return AnimationType.ANIMATION_ACTIVE;
            }

            @Override
            protected void interpolate(double frac) {

            }
        };
        bindImageViewToGameUnit(imageView, 0, 0);
        animationSet.add(spriteAnimation);
        super.initSprite();
    }

    @Override
    protected Rectangle2D nextViewport(AnimationType animationType, int index) {
        return null;
    }
}
