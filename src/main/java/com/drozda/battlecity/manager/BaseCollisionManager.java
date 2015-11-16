package com.drozda.battlecity.manager;

import com.drozda.battlecity.collision.command.check.CheckIntersectionCommand;
import com.drozda.battlecity.collision.command.check.CheckNotItselfCommand;
import com.drozda.battlecity.collision.command.check.CheckTypesCommand;
import com.drozda.battlecity.collision.command.check.bullet.bullet.CheckBulletsFromEnemiesCommand;
import com.drozda.battlecity.collision.command.check.bullet.bullet.CheckBulletsOneParentCommand;
import com.drozda.battlecity.collision.command.check.bullet.tank.CheckBulletTankIsParent;
import com.drozda.battlecity.collision.command.check.bullet.tank.enemytank.CheckBulletEnemyTankEnemyParent;
import com.drozda.battlecity.collision.command.finish.bullet.bullet.FinishBulletVsBulletCommand;
import com.drozda.battlecity.collision.command.finish.bullet.enemytank.FinishBulletVsEnemyTank;
import com.drozda.battlecity.collision.context.ContextEntries;
import com.drozda.battlecity.interfaces.Collideable;
import com.drozda.battlecity.interfaces.CollisionManager;
import com.drozda.battlecity.interfaces.HasGameUnits;
import com.drozda.battlecity.unit.*;
import org.apache.commons.chain.Chain;
import org.apache.commons.chain.impl.ChainBase;
import org.apache.commons.chain.impl.ContextBase;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by GFH on 16.11.2015.
 */
public class BaseCollisionManager implements CollisionManager {
    private static final Logger log = LoggerFactory.getLogger(BaseCollisionManager.class);

    final HasGameUnits playground;
    Map<Pair<Class<? extends GameUnit>, Class<? extends GameUnit>>, Chain>
            collisionChains = new HashMap<>();
    Predicate<GameUnit> isTakingPartInCollisionProcess = gameUnit -> gameUnit.isTakingPartInCollisionProcess();
    Predicate<GameUnit> onlyBullets = gameUnit -> gameUnit instanceof BulletUnit;
    Predicate<GameUnit> onlyTanks = gameUnit -> gameUnit instanceof TankUnit;

    {
        collisionChains.put(new ImmutablePair<>(BulletUnit.class, BulletUnit.class), createBulletVsBulletChain());
        collisionChains.put(new ImmutablePair<>(BulletUnit.class, PlayerTankUnit.class), new ChainBase());
        collisionChains.put(new ImmutablePair<>(BulletUnit.class, EnemyTankUnit.class), createBulletWithEnemyTankChain());
        collisionChains.put(new ImmutablePair<>(BulletUnit.class, TileUnit.class), new ChainBase());
        collisionChains.put(new ImmutablePair<>(BulletUnit.class, EagleBaseUnit.class), new ChainBase());
        collisionChains.put(new ImmutablePair<>(PlayerTankUnit.class, BonusUnit.class), new ChainBase());
    }

    public BaseCollisionManager(HasGameUnits playground) {
        this.playground = playground;
    }

    private Chain createBulletWithEnemyTankChain() {
        Chain chain = createBaseCollisionChain();
        chain.addCommand(new CheckTypesCommand(BulletUnit.class, EnemyTankUnit.class));
        chain.addCommand(new CheckBulletTankIsParent());
        chain.addCommand(new CheckBulletEnemyTankEnemyParent());
        chain.addCommand(new FinishBulletVsEnemyTank());
        return chain;
    }

    private Chain createBaseCollisionChain() {
        Chain chain = new ChainBase();
        chain.addCommand(new CheckNotItselfCommand());
        chain.addCommand(new CheckIntersectionCommand());
        return chain;
    }

    private Chain createBulletVsBulletChain() {
        Chain chain = createBaseCollisionChain();
        chain.addCommand(new CheckTypesCommand(BulletUnit.class, BulletUnit.class));
        chain.addCommand(new CheckBulletsOneParentCommand());
        chain.addCommand(new CheckBulletsFromEnemiesCommand());
        chain.addCommand(new FinishBulletVsBulletCommand());
        return chain;
    }

    @Override
    public void collisionCycle() {
        List<GameUnit> wantToCollideList = extractWantToCollideList();
        wantToCollideList.stream().forEach(gameUnit -> gameUnit.startCollisionProcessing());

        List<BulletUnit> bulletUnits = wantToCollideList.stream()
                .filter(onlyBullets)
                .map(gameUnit -> (BulletUnit) gameUnit)
                .collect(Collectors.toList());

        List<BulletUnit> aliveBullets = new LinkedList<>();

        while (bulletUnits.size() > 1) {
            BulletUnit bulletUnit = bulletUnits.remove(0);
            if (processUnitAndList(bulletUnit, (List) bulletUnits) != Collideable.CollisionProcessState.COMPLETED) {
                aliveBullets.add(bulletUnit);
            }
        }
        aliveBullets.addAll(bulletUnits);

        List<TankUnit> tankUnits = wantToCollideList.stream()
                .filter(onlyTanks)
                .map(gameUnit -> (TankUnit) gameUnit)
                .collect(Collectors.toList());

        List<TankUnit> aliveTanks = new LinkedList<>();


        while (aliveBullets.size() > 0) {
            BulletUnit bulletUnit = aliveBullets.remove(0);
            processUnitAndList(bulletUnit, (List) tankUnits);
        }
//        aliveTanks.addAll(tankUnits);


        List<GameUnit> other = wantToCollideList.stream()
                .filter(onlyTanks.negate())
                .filter(onlyBullets.negate())
                .collect(Collectors.toList());

        wantToCollideList.forEach(gameUnit -> gameUnit.finishCollisionProcessing());
    }

    private List<GameUnit> extractWantToCollideList() {
        return playground.getUnitList().stream()
                .filter(isTakingPartInCollisionProcess)
                .collect(Collectors.toList());
    }

    private Collideable.CollisionProcessState processUnitAndList(GameUnit gameUnit, List<GameUnit> gameUnits) {
        CollisionContext collisionContext = new CollisionContext();
        int curElement = 0;
        while (true) {
            GameUnit unit = gameUnits.get(curElement);
            collisionContext.invalidateContext();
            collisionContext.setLeftUnit(gameUnit);
            collisionContext.setRightUnit(unit);
            ImmutablePair immutablePair = new ImmutablePair(gameUnit.getClass(), unit.getClass());
            Chain chain = collisionChains.get(immutablePair);
            if (chain != null) {
                try {
                    chain.execute(collisionContext);
                    log.debug(collisionContext.getSummary());
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
                if (collisionContext.getRightCollisionProcessState() == Collideable.CollisionProcessState.COMPLETED) {
                    gameUnits.remove(curElement);
                } else {
                    curElement++;
                }
                if (collisionContext.getLeftCollisionProcessState() == Collideable.CollisionProcessState.COMPLETED) {
                    break;
                }
                if (gameUnits.size() < 2) {
                    break;
                }
                if (curElement > gameUnits.size() - 1) {
                    break;
                }
            }
        }
        return collisionContext.getLeftCollisionProcessState();
    }

    static class CollisionContext extends ContextBase {
        void invalidateContext() {
            clear();
        }

        protected void setLeftUnit(Object o) {
            put(ContextEntries.LEFT_GAMEUNIT, o);
        }

        protected void setRightUnit(Object o) {
            put(ContextEntries.RIGHT_GAMEUNIT, o);
        }

        protected Collideable.CollisionProcessState getLeftCollisionProcessState() {
            return (Collideable.CollisionProcessState) get(ContextEntries.LEFT_COLLISION_STATE);
        }

        protected Collideable.CollisionProcessState getRightCollisionProcessState() {
            return (Collideable.CollisionProcessState) get(ContextEntries.RIGHT_COLLISION_STATE);
        }

        protected Collideable.CollisionResult getRightCollisionResult() {
            return (Collideable.CollisionResult) get(ContextEntries.LEFT_COLLISION_RESULT);
        }

        protected Collideable.CollisionResult getLeftCollisionResult() {
            return (Collideable.CollisionResult) get(ContextEntries.RIGHT_COLLISION_RESULT);
        }

        protected String getSummary() {
            return (String) get(ContextEntries.SUMMARY);
        }
    }


}
