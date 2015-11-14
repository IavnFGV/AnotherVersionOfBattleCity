package com.drozda.fx.sprite;

import com.drozda.battlecity.unit.BonusUnit;
import com.drozda.battlecity.unit.GameUnit;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Created by GFH on 14.11.2015.
 */
public class BonusFxSprite extends FxSprite<BonusUnit> {

    protected Rectangle2D[] viewports;

    public BonusFxSprite(BonusUnit gameUnit) {
        super(gameUnit);
    }

    @Override
    protected void initSprite() {
        switch (gameUnit.getBonusType()) {

            case START_GAME_HELMET:
                throw new RuntimeException(gameUnit.getBonusType() + " is not visual sprite");
            case HELMET:
                viewports = YabcSprite.BONUS_HELMET.viewports;
                break;
            case CLOCK:
                viewports = YabcSprite.BONUS_CLOCK.viewports;
                break;
            case SPADE:
                viewports = YabcSprite.BONUS_SPADE.viewports;
                break;
            case STAR:
                viewports = YabcSprite.BONUS_STAR.viewports;
                break;
            case GRENADE:
                viewports = YabcSprite.BONUS_GRENADE.viewports;
                break;
            case TANK:
                viewports = YabcSprite.BONUS_TANK.viewports;
                break;
            case GUN:
                viewports = YabcSprite.BONUS_GUN.viewports;
                break;
        }
        ImageView imageView = new ImageView(baseImage);
        BonusAnimation bonusAnimation = new BonusAnimation(
                Duration.millis(gameUnit.getTimeInState(GameUnit.State.BLINK) / 1000_000 / 5)
                , imageView);
        bindImageViewToGameUnit(imageView, 0, 0);
        imageView.setViewport(viewports[0]);
        animationSet.add(bonusAnimation);

        super.initSprite();
    }

    @Override
    protected void toInPocketState() {
        hideAnimation(AnimationType.ANIMATION_ACTIVE);
    }

    @Override
    protected void toBlinkState() {
        showAnimation(AnimationType.ANIMATION_ACTIVE);
    }

    protected class BonusAnimation extends SpriteAnimation<BonusUnit> {

        private int count = 2;
        private int lastIndex;

        public BonusAnimation(Duration duration, ImageView imageView) {
            super(duration, imageView);
            setCycleCount(INDEFINITE);
        }

        @Override
        protected void interpolate(double k) {
            if (gameUnit.getCurrentState() != GameUnit.State.BLINK) return;
            final int index = Math.min((int) Math.floor(k * count), count - 1);
            if (index != lastIndex) {
                imageView.setVisible(!imageView.isVisible());
                lastIndex = index;
            }
        }

        @Override
        protected AnimationType getAnimationType() {
            return AnimationType.ANIMATION_ACTIVE;
        }
    }

}
