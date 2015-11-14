package com.drozda.fx.sprite;

import com.drozda.battlecity.unit.TileUnit;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

/**
 * Created by GFH on 10.11.2015.
 */
public class IceFxSprite extends FxSprite<TileUnit> {
    static Rectangle2D[] viewPorts = YabcSprite.TILE_ICE.viewports;

    public IceFxSprite(TileUnit gameUnit) {
        super(gameUnit);
    }

    @Override
    protected void initSprite() {
        ImageView basicImageView = new ImageView(baseImage);
        basicImageView.setViewport(viewPorts[0]);
        SpriteAnimation spriteAnimation = new SpriteAnimation(basicImageView, AnimationType.ANIMATION_ACTIVE) {

            @Override
            protected void interpolate(double frac) {

            }
        };
        bindImageViewToGameUnit(basicImageView, 0, 0);

        animationSet.add(spriteAnimation);
        super.initSprite();
    }
}
