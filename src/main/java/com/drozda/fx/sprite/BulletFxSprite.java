package com.drozda.fx.sprite;

import com.drozda.battlecity.unit.BulletUnit;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 13.11.2015.
 */
public class BulletFxSprite extends MovingFxSprite<BulletUnit> {
    private static final Logger log = LoggerFactory.getLogger(PlayerTankFxSprite.class);
    static Rectangle2D[] bulletViewports = YabcSprite.BULLET.viewports;

    public BulletFxSprite(BulletUnit gameUnit) {
        super(gameUnit);
    }

    @Override
    protected void initSprite() {
        ImageView imageView = new ImageView(baseImage);
        imageView.setViewport(bulletViewports[0]);
        SpriteAnimation spriteAnimation = new BlankSpriteAnimation(imageView, AnimationType.ANIMATION_ACTIVE);
        bindImageViewToGameUnit(imageView, 0, 0);
        // because in Ancestor we set direction ChangeListener, not an InvalidationListener
        imageView.setRotate(directionToRotation(gameUnit.getDirection()));
        animationSet.add(spriteAnimation);
        super.initSprite();
    }
}
