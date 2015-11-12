package com.drozda.fx.sprite;

import com.drozda.battlecity.unit.TileUnit;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

/**
 * Created by GFH on 10.11.2015.
 */
public class ForestFxSprite extends FxSprite<TileUnit> {

    static Rectangle2D[] viewPorts = YabcSprite.TILE_FOREST.viewports;

    public ForestFxSprite(TileUnit gameUnit) {
        super(gameUnit);
    }

    @Override
    protected void initSprite() {
        ImageView imageView = new ImageView(baseImage);
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
        imageView.setViewport(viewPorts[0]);
        animationSet.add(spriteAnimation);
        super.initSprite();
    }
}
