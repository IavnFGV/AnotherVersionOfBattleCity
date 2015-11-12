package com.drozda.fx.sprite;

import com.drozda.battlecity.unit.MoveableUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 11.11.2015.
 */
public abstract class MovingFxSprite<T extends MoveableUnit> extends ExplodingFxSprite<T> {
    private static final Logger log = LoggerFactory.getLogger(MovingFxSprite.class);
    public static String BASIC_MOVING_ANIMATION = "BASIC_MOVING_ANIMATION";

    public MovingFxSprite(T gameUnit) {
        super(gameUnit);
    }

    @Override
    protected void initSprite() {
        SpriteAnimation basicAnimation =
                animationSet.stream().
                        filter(moveableUnitSpriteAnimation ->
                                moveableUnitSpriteAnimation.getAnimationId().
                                        equals(BASIC_MOVING_ANIMATION)).findAny().orElse(null);
        if (basicAnimation == null) {
            throw new RuntimeException("Something wrong! BASIC_MOVING_ANIMATION MUST be in set till this moment!");
        }

        gameUnit.directionProperty().addListener((observable1, oldValue1, newValue1) -> {
            switch (newValue1) {
                case UP:
                    basicAnimation.imageView.rotateProperty().setValue(0);
                    break;
                case LEFT:
                    basicAnimation.imageView.rotateProperty().setValue(270);
                    break;
                case DOWN:
                    basicAnimation.imageView.rotateProperty().setValue(180);
                    break;
                case RIGHT:
                    basicAnimation.imageView.rotateProperty().setValue(90);
                    break;
            }
        });

        super.initSprite();
    }

}
