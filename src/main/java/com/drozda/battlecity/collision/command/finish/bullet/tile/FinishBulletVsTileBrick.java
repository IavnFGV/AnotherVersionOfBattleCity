package com.drozda.battlecity.collision.command.finish.bullet.tile;

import com.drozda.battlecity.collision.command.finish.FinishCollisionCommand;
import com.drozda.battlecity.interfaces.Collideable;
import com.drozda.battlecity.unit.BulletUnit;
import com.drozda.battlecity.unit.GameUnit;
import com.drozda.battlecity.unit.TileUnit;
import org.apache.commons.chain.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 18.11.2015.
 */
public class FinishBulletVsTileBrick extends FinishCollisionCommand<BulletUnit, TileUnit> {
    private static final Logger log = LoggerFactory.getLogger(FinishBulletVsTileBrick.class);

    @Override
    public boolean execute(Context context) throws Exception {
        super.execute(context);
        if (right.getTileType() != TileUnit.TileType.BRICK) {
            return CONTINUE_PROCESSING;
        }
        TileUnit.TileState newState = right.getTileStateAfterBullet(left);
        if (newState == TileUnit.TileState.STATE_EMPTY) {
            log.debug("sadsadsads");
            finalTransition(Collideable.CollisionProcessState.PARTIALLY
                    , Collideable.CollisionProcessState.COMPLETED,
                    Collideable.CollisionResult.INNER_STATE_CHANGE,
                    Collideable.CollisionResult.STATE_CHANGE,
                    bulletUnit -> {
                    },
                    tileUnit -> tileUnit.setCurrentState(GameUnit.State.DEAD));
            setSummary("Bullet is waiting other collision. Brick is dead", context);
            log.debug("Left = " + left);
            log.debug("Right = " + right);
            return PROCESSING_COMPLETE;
        }
        finalTransition(Collideable.CollisionProcessState.PARTIALLY,
                Collideable.CollisionProcessState.PARTIALLY,
                Collideable.CollisionResult.INNER_STATE_CHANGE,
                Collideable.CollisionResult.INNER_STATE_CHANGE,
                bulletUnit -> {
                },
                tileUnit -> tileUnit.setTileState(newState));
        setSummary("Bullet is waiting other collision. Brick is waiting other collision", context);
        log.debug("Left = " + left);
        log.debug("Right = " + right);
        return PROCESSING_COMPLETE;
    }
}
