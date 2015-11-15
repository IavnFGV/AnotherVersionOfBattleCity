package com.drozda.fx.sprite;

import com.drozda.battlecity.unit.EnemyTankUnit;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Created by GFH on 13.11.2015.
 */
public class SimpleEnemyTankFxSprite extends TankFxSprite<EnemyTankUnit> {
    protected Rectangle2D[] viewports;

    public SimpleEnemyTankFxSprite(EnemyTankUnit gameUnit) {
        super(gameUnit);
    }

    @Override
    protected void initSprite() {
        switch (gameUnit.getTankType()) {

            case TANK_SIMPLE_ENEMY:
                viewports = YabcSprite.TANK_SIMPLE_ENEMY.viewports;
                break;
            case TANK_FAST_ENEMY:
                viewports = YabcSprite.TANK_FAST_ENEMY.viewports;
                break;
            case TANK_POWER_ENEMY:
                viewports = YabcSprite.TANK_POWER_ENEMY.viewports;
                break;
            case TANK_SIMPLE_ENEMY_X:
                viewports = YabcSprite.TANK_SIMPLE_ENEMY_X.viewports;
                break;
            case TANK_FAST_ENEMY_X:
                viewports = YabcSprite.TANK_FAST_ENEMY_X.viewports;
                break;
            case TANK_POWER_ENEMY_X:
                viewports = YabcSprite.TANK_POWER_ENEMY_X.viewports;
                break;
            default:
                throw new RuntimeException("BAD PARAMETER: it is not simple sprite :" + gameUnit.getTankType());
        }

        ImageView basicImageView = new ImageView(baseImage);
        BasicTankMoveAnimation basicTankMoveAnimation =
                new BasicTankMoveAnimation(Duration.millis(200), basicImageView, AnimationType.ANIMATION_ACTIVE);
        bindImageViewToGameUnit(basicImageView, 0, 0);
        basicImageView.setViewport(nextViewport(basicTankMoveAnimation.getAnimationType(), 0));
        animationSet.add(basicTankMoveAnimation);
        super.initSprite();
    }

    @Override
    protected Rectangle2D nextViewport(AnimationType animationType, int index) {
        if (animationType == AnimationType.ANIMATION_ACTIVE) {
            return viewports[index];
        }
        return super.nextViewport(animationType, index);
    }
}
