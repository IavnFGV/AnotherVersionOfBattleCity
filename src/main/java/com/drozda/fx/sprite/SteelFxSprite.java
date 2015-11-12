package com.drozda.fx.sprite;

import com.drozda.battlecity.unit.TileUnit;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

/**
 * Created by GFH on 10.11.2015.
 */
public class SteelFxSprite extends FxSprite<TileUnit> {
    static Rectangle2D[] viewPorts = YabcSprite.TILE_STEEL.viewports;

    public SteelFxSprite(TileUnit gameUnit) {
        super(gameUnit);
    }

    @Override
    protected void initSprite() {
        ImageView imageView = new ImageView(baseImage);
        imageView.setViewport(viewPorts[0]);
        SpriteAnimation spriteAnimation = new SpriteAnimation(imageView) {
            @Override
            protected String getAnimationId() {
                return NO_ANIMATION;
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
    protected Rectangle2D nextViewport(String animationId, int index) {
        return null;
    }
}
