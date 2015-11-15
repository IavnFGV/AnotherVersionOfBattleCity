package com.drozda.fx.sprite;

import com.drozda.battlecity.unit.EnemyTankUnit;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GFH on 13.11.2015.
 */
public class ArmorEnemyTankFxSprite extends TankFxSprite<EnemyTankUnit> {
    private static final Logger log = LoggerFactory.getLogger(ArmorEnemyTankFxSprite.class);
    static Map<Integer, Rectangle2D[]> armorViewportsMap = new HashMap<>();
    static Map<Integer, Rectangle2D[]> armorXViewportsMap = new HashMap<>();

    static {
        armorViewportsMap.put(1, YabcSprite.TANK_ARMOR_ENEMY_1_LIFES.viewports);
        armorViewportsMap.put(2, YabcSprite.TANK_ARMOR_ENEMY_2_LIFES.viewports);
        armorViewportsMap.put(3, YabcSprite.TANK_ARMOR_ENEMY_3_LIFES.viewports);
        armorViewportsMap.put(4, YabcSprite.TANK_ARMOR_ENEMY_4_LIFES.viewports);

        armorXViewportsMap.put(1, YabcSprite.TANK_ARMOR_ENEMY_1_LIFES.viewports);
        armorXViewportsMap.put(2, YabcSprite.TANK_ARMOR_ENEMY_2_LIFES.viewports);
        armorXViewportsMap.put(3, YabcSprite.TANK_ARMOR_ENEMY_3_LIFES.viewports);
        armorXViewportsMap.put(4, YabcSprite.TANK_ARMOR_ENEMY_4_LIFES_X.viewports);

    }

    protected Map<Integer, Rectangle2D[]> viewportsMap;

    public ArmorEnemyTankFxSprite(EnemyTankUnit gameUnit) {
        super(gameUnit);
    }

    @Override
    protected void initSprite() {
        viewportsMap = new HashMap<>();
        if (gameUnit.getTankType() == EnemyTankUnit.EnemyTankType.TANK_ARMOR_ENEMY) {
            viewportsMap = armorViewportsMap;
        } else if (gameUnit.getTankType() == EnemyTankUnit.EnemyTankType.TANK_ARMOR_ENEMY_X) {
            viewportsMap = armorXViewportsMap;
        } else {
            throw new RuntimeException("BAD PARAMETER: it is armorEnemy sprite :" + gameUnit.getTankType());
        }

        ImageView basicImageView = new ImageView(baseImage);
        BasicTankMoveAnimation basicTankMoveAnimation =
                new BasicTankMoveAnimation(Duration.millis(200), basicImageView, AnimationType.ANIMATION_ACTIVE);
        bindImageViewToGameUnit(basicImageView, 0, 0);
        basicImageView.setViewport(nextViewport(basicTankMoveAnimation.getAnimationType(), 0));

        gameUnit.lifesProperty().addListener((observable, oldValue, newValue) -> {
            basicImageView.setViewport(nextViewport(basicTankMoveAnimation.getAnimationType(), 0));
        });
        animationSet.add(basicTankMoveAnimation);
        super.initSprite();
    }

    @Override
    protected Rectangle2D nextViewport(AnimationType animationType, int index) {
        if (animationType == AnimationType.ANIMATION_ACTIVE) {
            return viewportsMap.get(gameUnit.getLifes())[index];
        }
        return super.nextViewport(animationType, index);
    }
}
