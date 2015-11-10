package com.drozda.fx.sprite;

import com.drozda.battlecity.unit.TileUnit;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

/**
 * Created by GFH on 10.11.2015.
 */
public class IceFxSprite extends FxSprite<TileUnit> {
    Rectangle2D[] viewPorts = YabcSprite.TILE_ICE.viewports;

    public IceFxSprite(TileUnit gameUnit) {
        super(gameUnit);
        initSprite();
        baseImageView.setViewport(viewPorts[0]);
    }

    @Override
    protected SpriteAnimation<TileUnit> createAnimation(ImageView imageView) {
        return null;
    }

    @Override
    protected Rectangle2D nextSprite(int index) {
        return null;
    }
}
