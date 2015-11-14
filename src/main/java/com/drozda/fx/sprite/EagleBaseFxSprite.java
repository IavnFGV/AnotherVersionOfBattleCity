package com.drozda.fx.sprite;

import com.drozda.battlecity.unit.EagleBaseUnit;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

/**
 * Created by GFH on 14.11.2015.
 */
public class EagleBaseFxSprite extends ExplodingFxSprite<EagleBaseUnit> {
    static Rectangle2D[] viewports = YabcSprite.EAGLE_BASE.viewports;
    ImageView imageView;

    public EagleBaseFxSprite(EagleBaseUnit gameUnit) {
        super(gameUnit);
    }

    @Override
    protected void toActiveState() {
        super.toActiveState();
        imageView.setViewport(viewports[0]);
    }

    @Override
    protected void toDeadState() {
        super.toDeadState();
        showAnimation(AnimationType.ANIMATION_ACTIVE);
        imageView.setViewport(viewports[1]);
    }

    @Override
    protected void initSprite() {
        imageView = new ImageView(baseImage);
        SpriteAnimation spriteAnimation = new BlankSpriteAnimation(imageView, AnimationType.ANIMATION_ACTIVE);
        bindImageViewToGameUnit(imageView, 0, 0);
        imageView.setViewport(viewports[0]);
        animationSet.add(spriteAnimation);

        super.initSprite();
    }
}
