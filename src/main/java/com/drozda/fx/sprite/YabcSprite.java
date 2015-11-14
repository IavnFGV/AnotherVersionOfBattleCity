package com.drozda.fx.sprite;

import com.drozda.battlecity.StaticServices;
import com.drozda.battlecity.unit.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 24.09.2015.
 */
public enum YabcSprite {
    //tiles
    TILE_BRICK(new Rectangle2D[]{
            new Rectangle2D(0, 816, 16, 16),//1111
            new Rectangle2D(16, 816, 16, 16),//0111
            new Rectangle2D(32, 816, 16, 16),//1011
            new Rectangle2D(48, 816, 16, 16),//1101
            new Rectangle2D(64, 816, 16, 16),//1110
            new Rectangle2D(80, 816, 16, 16),//0011
            new Rectangle2D(96, 816, 16, 16),//1001
            new Rectangle2D(112, 816, 16, 16),//1100
            new Rectangle2D(128, 816, 16, 16),//0110
            new Rectangle2D(144, 816, 16, 16),//1000
            new Rectangle2D(160, 816, 16, 16),//0100
            new Rectangle2D(176, 816, 16, 16),//0010
            new Rectangle2D(192, 816, 16, 16),//0001
            new Rectangle2D(208, 816, 16, 16),//0101
            new Rectangle2D(224, 816, 16, 16),//1010
    }),
    TILE_FOREST(new Rectangle2D[]{new Rectangle2D(0, 864, 16, 16)}),
    TILE_ICE(new Rectangle2D[]{new Rectangle2D(0, 880, 16, 16)}),
    TILE_STEEL(new Rectangle2D[]{new Rectangle2D(0, 832, 16, 16)}),
    TILE_WATER(new Rectangle2D[]{
            new Rectangle2D(0, 848, 16, 16),
            new Rectangle2D(16, 848, 16, 16),
            new Rectangle2D(32, 848, 16, 16)}),
    EAGLE_BASE(new Rectangle2D[]{
            new Rectangle2D(0, 912, 32, 32),
            new Rectangle2D(32, 912, 32, 32)}),
    //players
    TANK_FIRST_PLAYER_0_STAR(new Rectangle2D[]{
            new Rectangle2D(0, 255, 32, 32),
            new Rectangle2D(32, 255, 32, 32)}),
    TANK_FIRST_PLAYER_1_STAR(new Rectangle2D[]{
            new Rectangle2D(64, 255, 32, 32),
            new Rectangle2D(96, 255, 32, 32)}),
    TANK_FIRST_PLAYER_2_STAR(new Rectangle2D[]{
            new Rectangle2D(128, 255, 32, 32),
            new Rectangle2D(160, 255, 32, 32)}),
    TANK_FIRST_PLAYER_3_STAR(new Rectangle2D[]{
            new Rectangle2D(192, 255, 32, 32),
            new Rectangle2D(224, 255, 32, 32)}),
    TANK_SECOND_PLAYER_0_STAR(new Rectangle2D[]{
            new Rectangle2D(0, 287, 32, 32),
            new Rectangle2D(32, 287, 32, 32)}),
    TANK_SECOND_PLAYER_1_STAR(new Rectangle2D[]{
            new Rectangle2D(64, 287, 32, 32),
            new Rectangle2D(96, 287, 32, 32)}),
    TANK_SECOND_PLAYER_2_STAR(new Rectangle2D[]{
            new Rectangle2D(128, 287, 32, 32),
            new Rectangle2D(160, 287, 32, 32)}),
    TANK_SECOND_PLAYER_3_STAR(new Rectangle2D[]{
            new Rectangle2D(192, 287, 32, 32),
            new Rectangle2D(224, 287, 32, 32)}),
    //enemies
    TANK_SIMPLE_ENEMY(new Rectangle2D[]{
            new Rectangle2D(0, 160, 32, 32),
            new Rectangle2D(32, 160, 32, 32)}),
    TANK_FAST_ENEMY(new Rectangle2D[]{
            new Rectangle2D(0, 192, 32, 32),
            new Rectangle2D(32, 192, 32, 32)}),
    TANK_POWER_ENEMY(new Rectangle2D[]{
            new Rectangle2D(0, 224, 32, 32),
            new Rectangle2D(32, 224, 32, 32)}),
    TANK_ARMOR_ENEMY_4_LIFES_X(new Rectangle2D[]{
            new Rectangle2D(0, 96, 32, 32),
            new Rectangle2D(32, 96, 32, 32)}),
    TANK_ARMOR_ENEMY_4_LIFES(new Rectangle2D[]{
            new Rectangle2D(0, 128, 32, 32),
            new Rectangle2D(32, 128, 32, 32)}),
    TANK_ARMOR_ENEMY_3_LIFES(new Rectangle2D[]{
            new Rectangle2D(64, 128, 32, 32),
            new Rectangle2D(96, 128, 32, 32)}),
    TANK_ARMOR_ENEMY_2_LIFES(new Rectangle2D[]{
            new Rectangle2D(128, 128, 32, 32),
            new Rectangle2D(160, 128, 32, 32)}),
    TANK_ARMOR_ENEMY_1_LIFES(new Rectangle2D[]{
            new Rectangle2D(192, 128, 32, 32),
            new Rectangle2D(224, 128, 32, 32)}),
    TANK_SIMPLE_ENEMY_X(new Rectangle2D[]{
            new Rectangle2D(0, 0, 32, 32),
            new Rectangle2D(32, 0, 32, 32)}),
    TANK_FAST_ENEMY_X(new Rectangle2D[]{
            new Rectangle2D(0, 32, 32, 32),
            new Rectangle2D(32, 32, 32, 32)}),
    TANK_POWER_ENEMY_X(new Rectangle2D[]{
            new Rectangle2D(0, 64, 32, 32),
            new Rectangle2D(32, 64, 32, 32)}),
    BONUS_HELMET_ON_TANK(new Rectangle2D[]{
            new Rectangle2D(0, 320, 32, 32),
            new Rectangle2D(32, 320, 32, 32)}),
    //bonuses on playground
    BONUS_HELMET(new Rectangle2D[]{
            new Rectangle2D(0, 449, 32, 32)}),
    BONUS_CLOCK(new Rectangle2D[]{
            new Rectangle2D(0, 481, 32, 32)}),
    BONUS_SPADE(new Rectangle2D[]{
            new Rectangle2D(0, 513, 32, 32)}),
    BONUS_STAR(new Rectangle2D[]{
            new Rectangle2D(0, 577, 32, 32)}),
    BONUS_GRENADE(new Rectangle2D[]{
            new Rectangle2D(0, 417, 32, 32)}),
    BONUS_TANK(new Rectangle2D[]{
            new Rectangle2D(0, 545, 32, 32)}),
    BONUS_GUN(new Rectangle2D[]{
            new Rectangle2D(0, 609, 32, 32)}),
    BULLET(new Rectangle2D[]{
            new Rectangle2D(64, 47, 8, 8)}),
    EXPLOSION_SMALL(new Rectangle2D[]{
            new Rectangle2D(269, 687, 32, 32),
            new Rectangle2D(207, 687, 32, 32),
            new Rectangle2D(144, 687, 32, 32),}),
    EXPLOSION_BIG(new Rectangle2D[]{
            new Rectangle2D(256, 672, 64, 64),
            new Rectangle2D(192, 672, 64, 64),
            new Rectangle2D(128, 672, 64, 64),
            new Rectangle2D(64, 672, 64, 64),
            new Rectangle2D(0, 672, 64, 64)}),
    CREATING(new Rectangle2D[]{
            new Rectangle2D(0, 384, 32, 32),
            new Rectangle2D(32, 384, 32, 32),
            new Rectangle2D(64, 384, 32, 32),
            new Rectangle2D(96, 384, 32, 32),
            new Rectangle2D(128, 384, 32, 32),
            new Rectangle2D(160, 384, 32, 32),
            new Rectangle2D(192, 384, 32, 32),
            new Rectangle2D(224, 384, 32, 32),
            new Rectangle2D(256, 384, 32, 32),
            new Rectangle2D(288, 384, 32, 32)}),
    //special for UI
    SMALL_ENEMY_TANK_FOR_COUNTER(new Rectangle2D[]{
            new Rectangle2D(0, 800, 16, 16)}),
    DIGIT_0(new Rectangle2D[]{
            new Rectangle2D(0, 768, 16, 16)}),
    DIGIT_1(new Rectangle2D[]{
            new Rectangle2D(16, 768, 16, 16)}),
    DIGIT_2(new Rectangle2D[]{
            new Rectangle2D(32, 768, 16, 16)}),
    DIGIT_3(new Rectangle2D[]{
            new Rectangle2D(48, 768, 16, 16)}),
    DIGIT_4(new Rectangle2D[]{
            new Rectangle2D(64, 768, 16, 16)}),
    DIGIT_5(new Rectangle2D[]{
            new Rectangle2D(0, 784, 16, 16)}),
    DIGIT_6(new Rectangle2D[]{
            new Rectangle2D(16, 784, 16, 16)}),
    DIGIT_7(new Rectangle2D[]{
            new Rectangle2D(32, 784, 16, 16)}),
    DIGIT_8(new Rectangle2D[]{
            new Rectangle2D(48, 784, 16, 16)}),
    DIGIT_9(new Rectangle2D[]{
            new Rectangle2D(64, 784, 16, 16)});
    protected static final Logger log = LoggerFactory.getLogger(YabcSprite.class);
    public static Image baseImage = new Image(YabcSprite.class.getResource("BattleCity.png").toExternalForm());
    protected Rectangle2D[] viewports;
    protected long toggleTime = (long) (StaticServices.ONE_SECOND * 0.125);

    YabcSprite() {
    }

    YabcSprite(Rectangle2D[] viewports, long toggleTime) {
//        log.debug("YabcSprite.YabcSprite with parameters " + "viewports = [" + viewports + "]");
        this(viewports);
        this.toggleTime = toggleTime;
    }

    YabcSprite(Rectangle2D[] viewports) {
//        log.debug("YabcSprite.YabcSprite with parameters " + "viewports = [" + viewports + "]");
        this.viewports = viewports;
    }

    public static ImageView getSimpleSprite(YabcSprite spriteType) {
        ImageView imageView = new ImageView(baseImage);
        imageView.setViewport(spriteType.viewports[0]);
        return imageView;
    }

    public static Node getFullSprite(GameUnit gameUnit) {
        if (gameUnit instanceof TileUnit) {
            TileUnit tileUnit = (TileUnit) gameUnit;
            log.debug(tileUnit.getTileType().toString());
            switch (tileUnit.getTileType()) {
                case BRICK:
                    return new BrickFxSprite(tileUnit);
                case FOREST:
                    return new ForestFxSprite(tileUnit);
                case ICE:
                    return new IceFxSprite(tileUnit);
                case STEEL:
                    return new SteelFxSprite(tileUnit);
                case WATER:
                    return new WaterFxSprite(tileUnit);
            }
        }
        if (gameUnit instanceof TankUnit) {
            TankUnit tankUnit = (TankUnit) gameUnit;
            log.debug(tankUnit.toString());
            switch (tankUnit.getTankType()) {

                case TANK_FIRST_PLAYER:
                    return new PlayerTankFxSprite(tankUnit);
                case TANK_SECOND_PLAYER:
                    return new PlayerTankFxSprite(tankUnit);
                case TANK_SIMPLE_ENEMY:
                    return new SimpleEnemyTankFxSprite(tankUnit);
                case TANK_FAST_ENEMY:
                    return new SimpleEnemyTankFxSprite(tankUnit);
                case TANK_POWER_ENEMY:
                    return new SimpleEnemyTankFxSprite(tankUnit);
                case TANK_ARMOR_ENEMY:
                    return new ArmorEnemyTankFxSprite(tankUnit);
                case TANK_SIMPLE_ENEMY_X:
                    return new SimpleEnemyTankFxSprite(tankUnit);
                case TANK_FAST_ENEMY_X:
                    return new SimpleEnemyTankFxSprite(tankUnit);
                case TANK_POWER_ENEMY_X:
                    return new SimpleEnemyTankFxSprite(tankUnit);
                case TANK_ARMOR_ENEMY_X:
                    return new ArmorEnemyTankFxSprite(tankUnit);
            }
        }
        if (gameUnit instanceof BulletUnit) {
            BulletUnit bulletUnit = (BulletUnit) gameUnit;
            log.debug(bulletUnit.toString());
            return new BulletFxSprite(bulletUnit);
        }
        if (gameUnit instanceof BonusUnit) {
            BonusUnit bonusUnit = (BonusUnit) gameUnit;
            return new BonusFxSprite(bonusUnit);
        }
        if (gameUnit instanceof EagleBaseUnit) {
            EagleBaseUnit eagleBaseUnit = (EagleBaseUnit) gameUnit;
            return new EagleBaseFxSprite(eagleBaseUnit);
        }
        return getDigit(0); //  STUB
    }

    public static ImageView getDigit(int digit) {
        ImageView imageView = new ImageView(baseImage);
        YabcSprite sprite;
        switch (digit) {
            case 0:
                sprite = DIGIT_0;
                break;
            case 1:
                sprite = DIGIT_1;
                break;
            case 2:
                sprite = DIGIT_2;
                break;
            case 3:
                sprite = DIGIT_3;
                break;
            case 4:
                sprite = DIGIT_4;
                break;
            case 5:
                sprite = DIGIT_5;
                break;
            case 6:
                sprite = DIGIT_6;
                break;
            case 7:
                sprite = DIGIT_7;
                break;
            case 8:
                sprite = DIGIT_8;
                break;
            case 9:
                sprite = DIGIT_9;
                break;
            default:
                sprite = DIGIT_0;
                break;
        }

        imageView.setViewport(sprite.viewports[0]);
        return imageView;
    }
}
