package com.drozda.fx.sprite;

import com.drozda.battlecity.StaticServices;
import com.drozda.battlecity.unit.GameUnit;
import com.drozda.battlecity.unit.TileUnit;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by GFH on 24.09.2015.
 */
public enum YabcSprite {
    //tiles
    TILE_BRICK, TILE_EMPTY, TILE_FOREST, TILE_ICE, TILE_STEEL,
    TILE_WATER(new Rectangle2D[]{
            new Rectangle2D(0, 848, 16, 16),
            new Rectangle2D(16, 848, 16, 16),
            new Rectangle2D(32, 848, 16, 16)}),//TILE_WATER_1,TILE_WATER_2,TILE_WATER_3,
    //players
    TANK_FIRST_PLAYER, TANK_FIRST_PLAYER_1_STAR, TANK_FIRST_PLAYER_2_STAR, TANK_FIRST_PLAYER_3_STAR, TANK_FIRST_PLAYER_4_STAR,
    TANK_SECOND_PLAYER, TANK_SECOND_PLAYER_1_STAR, TANK_SECOND_PLAYER_2_STAR, TANK_SECOND_PLAYER_3_STAR, TANK_SECOND_PLAYER_4_STAR,
    //enemies
    TANK_SIMPLE_ENEMY, TANK_FAST_ENEMY, TANK_POWER_ENEMY,
    TANK_ARMOR_ENEMY, TANK_ARMOR_ENEMY_4_LIFES_X, TANK_ARMOR_ENEMY_4_LIFES, TANK_ARMOR_ENEMY_3_LIFES,
    TANK_ARMOR_ENEMY_2_LIFES, TANK_ARMOR_ENEMY_1_LIFES,
    TANK_SIMPLE_ENEMY_X, TANK_FAST_ENEMY_X, TANK_POWER_ENEMY_X, TANK_ARMOR_ENEMY_X,
    //bonuses
    BONUS_HELMET, BONUS_CLOCK, BONUS_SPADE, BONUS_STAR, BONUS_GRENADE, BONUS_TANK, BONUS_GUN,
    //bullets
    BULLET_PLAYER, BULLET_ENEMY,
    //special for UI
    SMALL_ENEMY_TANK_FOR_COUNTER(new Rectangle2D[]{new Rectangle2D(0, 800, 16, 16)}),
    DIGIT_0(new Rectangle2D[]{new Rectangle2D(0, 768, 16, 16)}),
    DIGIT_1(new Rectangle2D[]{new Rectangle2D(16, 768, 16, 16)}),
    DIGIT_2(new Rectangle2D[]{new Rectangle2D(32, 768, 16, 16)}),
    DIGIT_3(new Rectangle2D[]{new Rectangle2D(48, 768, 16, 16)}),
    DIGIT_4(new Rectangle2D[]{new Rectangle2D(64, 768, 16, 16)}),
    DIGIT_5(new Rectangle2D[]{new Rectangle2D(0, 784, 16, 16)}),
    DIGIT_6(new Rectangle2D[]{new Rectangle2D(16, 784, 16, 16)}),
    DIGIT_7(new Rectangle2D[]{new Rectangle2D(32, 784, 16, 16)}),
    DIGIT_8(new Rectangle2D[]{new Rectangle2D(48, 784, 16, 16)}),
    DIGIT_9(new Rectangle2D[]{new Rectangle2D(64, 784, 16, 16)});
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

    public static ImageView getFullSprite(GameUnit gameUnit) {
        if (gameUnit instanceof TileUnit) {
            TileUnit tileUnit = (TileUnit) gameUnit;
            if (tileUnit.getTileType() == TileUnit.TileType.WATER) {
                return new WaterFxSprite(tileUnit);
            }
        }
        return getDigit(0);
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
