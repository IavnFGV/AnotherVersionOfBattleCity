package com.drozda.fx.sprite;

import com.drozda.battlecity.unit.MoveableUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 11.11.2015.
 */
public abstract class MovingFxSprite<T extends MoveableUnit> extends ExplodingFxSprite<T> {
    private static final Logger log = LoggerFactory.getLogger(MovingFxSprite.class);

    public MovingFxSprite(T gameUnit) {
        super(gameUnit);
    }

    @Override
    protected void initSprite() {
        SpriteAnimation basicAnimation =
                animationSet.stream().
                        filter(moveableUnitSpriteAnimation ->
                                moveableUnitSpriteAnimation.getAnimationType().
                                        equals(AnimationType.ANIMATION_ACTIVE)).findAny().orElse(null);
        if (basicAnimation == null) {
            throw new RuntimeException("Something wrong! BASIC_MOVING_ANIMATION MUST be in set till this moment!");
        }


        gameUnit.directionProperty().addListener((observable1, oldValue1, newValue1) -> {
            basicAnimation.imageView.setRotate(directionToRotation(newValue1));
        });

        super.initSprite();
    }

    protected double directionToRotation(MoveableUnit.Direction direction) {
        switch (direction) {
            case UP:
                return 0;
            case LEFT:
                return 270;
            case DOWN:
                return 180;
            case RIGHT:
                return 90;
        }
        return 0;
    }

}
