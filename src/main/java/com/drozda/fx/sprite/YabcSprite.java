package com.drozda.fx.sprite;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by GFH on 24.09.2015.
 */
public enum YabcSprite {

    TILE_BRICK, TILE_EMPTY, TILE_FOREST, TILE_ICE, TILE_STEEL, TILE_WATER,
    TANK_FIRST_PLAYER_1L, TANK_FIRST_PLAYER_2L, TANK_FIRST_PLAYER_3L, TANK_FIRST_PLAYER_4L,
    TANK_SECOND_PLAYER_1L, TANK_SECOND_PLAYER_2L, TANK_SECOND_PLAYER_3L, TANK_SECOND_PLAYER_4L,
    TANK_SIMPLE_ENEMY, TANK_FAST_ENEMY, TANK_POWER_ENEMY, TANK_ARMOR_ENEMY,
    TANK_SIMPLE_ENEMY_X, TANK_FAST_ENEMY_X, TANK_POWER_ENEMY_X, TANK_ARMOR_ENEMY_X,
    BONUS_HELMET, BONUS_CLOCK, BONUS_SPADE, BONUS_STAR, BONUS_GRENADE, BONUS_TANK, BONUS_GUN,
    BULLET_PLAYER, BULLET_ENEMY,
    SMALL_ENEMY_TANK_FOR_COUNTER;
    private static Image baseImage = new Image(YabcSprite.class.getResource("BattleCity.png").toExternalForm());


    public static ImageView getSprite(YabcSprite spriteType) {
        ImageView imageView = new ImageView(baseImage);

        switch (spriteType) {
            case TILE_BRICK:
                break;
            case TILE_EMPTY:
                break;
            case TILE_FOREST:
                break;
            case TILE_ICE:
                break;
            case TILE_STEEL:
                break;
            case TILE_WATER:
                break;
            case TANK_FIRST_PLAYER_1L:
                break;
            case TANK_FIRST_PLAYER_2L:
                break;
            case TANK_FIRST_PLAYER_3L:
                break;
            case TANK_FIRST_PLAYER_4L:
                break;
            case TANK_SECOND_PLAYER_1L:
                break;
            case TANK_SECOND_PLAYER_2L:
                break;
            case TANK_SECOND_PLAYER_3L:
                break;
            case TANK_SECOND_PLAYER_4L:
                break;
            case TANK_SIMPLE_ENEMY:
                break;
            case TANK_FAST_ENEMY:
                break;
            case TANK_POWER_ENEMY:
                break;
            case TANK_ARMOR_ENEMY:
                break;
            case TANK_SIMPLE_ENEMY_X:
                break;
            case TANK_FAST_ENEMY_X:
                break;
            case TANK_POWER_ENEMY_X:
                break;
            case TANK_ARMOR_ENEMY_X:
                break;
            case BONUS_HELMET:
                break;
            case BONUS_CLOCK:
                break;
            case BONUS_SPADE:
                break;
            case BONUS_STAR:
                break;
            case BONUS_GRENADE:
                break;
            case BONUS_TANK:
                break;
            case BONUS_GUN:
                break;
            case BULLET_PLAYER:
                break;
            case BULLET_ENEMY:
                break;
            case SMALL_ENEMY_TANK_FOR_COUNTER:
                Rectangle2D rectangle2D = new Rectangle2D(0, 800, 16, 16);
                imageView.setViewport(rectangle2D);
                break;
        }
        return imageView;
    }

}
