package com.drozda.fx.sprite;

import com.drozda.battlecity.unit.BonusUnit;
import com.drozda.battlecity.unit.GameUnit;
import com.drozda.battlecity.unit.TankUnit;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Created by GFH on 11.11.2015.
 */
public class PlayerTankFxSprite extends TankFxSprite {
    Rectangle2D[] helmetViewports = YabcSprite.BONUS_HELMET.viewports;
    ImageView helmetImageView = new ImageView(sprite);

    public PlayerTankFxSprite(TankUnit gameUnit) {
        super(gameUnit);
        if (gameUnit.getTankType() == TankUnit.TankType.TANK_FIRST_PLAYER) {
            viewportsMap.put(0, YabcSprite.TANK_FIRST_PLAYER_0_STAR.viewports);
            viewportsMap.put(1, YabcSprite.TANK_FIRST_PLAYER_1_STAR.viewports);
            viewportsMap.put(2, YabcSprite.TANK_FIRST_PLAYER_2_STAR.viewports);
            viewportsMap.put(3, YabcSprite.TANK_FIRST_PLAYER_3_STAR.viewports);
        }
        if (gameUnit.getTankType() == TankUnit.TankType.TANK_SECOND_PLAYER) {
            viewportsMap.put(0, YabcSprite.TANK_SECOND_PLAYER_0_STAR.viewports);
            viewportsMap.put(1, YabcSprite.TANK_SECOND_PLAYER_1_STAR.viewports);
            viewportsMap.put(2, YabcSprite.TANK_SECOND_PLAYER_2_STAR.viewports);
            viewportsMap.put(3, YabcSprite.TANK_SECOND_PLAYER_3_STAR.viewports);
        }
        baseImageView.setViewport(nextSprite(0));
        gameUnit.starsProperty().addListener((observable, oldValue, newValue) -> {
            baseImageView.setViewport(nextSprite(0));
        });
        helmetImageView.setViewport(nextHelmetSprite(0));
        animations.add(new HelmetAnimation(Duration.millis(100), helmetImageView));

        gameUnit.boundsProperty().addListener((observable, oldValue, newValue) -> {
            helmetImageView.xProperty().setValue(newValue.getMinX());
            helmetImageView.yProperty().setValue(newValue.getMinY());
        });
        helmetImageView.xProperty().setValue(gameUnit.getBounds().getMinX());
        helmetImageView.yProperty().setValue(gameUnit.getBounds().getMinY());

        imageViews.add(helmetImageView);

        initSprite();
        allAnimations.play();
    }

    @Override
    protected Rectangle2D nextSprite(int index) {
        return viewportsMap.get(gameUnit.getStars())[index];
    }

    private Rectangle2D nextHelmetSprite(int index) {
        return helmetViewports[index];
    }

    protected class HelmetAnimation extends SpriteAnimation<TankUnit> {

        private int count = 2;
        private int lastIndex;

        public HelmetAnimation(Duration duration, ImageView imageView) {
            super(duration, imageView);
            setCycleCount(INDEFINITE);
        }

        @Override
        protected void interpolate(double k) {
            if ((gameUnit.getBonusList().contains(BonusUnit.BonusType.HELMET) ||
                    (gameUnit.getCurrentState() == GameUnit.State.CREATING))) {
                imageView.setVisible(true);
                final int index = Math.min((int) Math.floor(k * count), count - 1);
                if (index != lastIndex) {
                    imageView.setViewport(nextHelmetSprite(index));
                    lastIndex = index;
                }
            } else {
                imageView.setVisible(false);
            }

        }
    }
}
