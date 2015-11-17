package com.drozda.battlecity.unit;

import com.drozda.battlecity.StaticServices;
import com.drozda.battlecity.interfaces.BattleGround;
import com.drozda.battlecity.interfaces.HasGameUnits;
import com.drozda.battlecity.modifier.BulletMovingModifier;
import com.drozda.battlecity.modifier.MovingModifier;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * Created by GFH on 27.09.2015.
 */
public class BulletUnit extends MoveableUnit {

    private static Map<State, Long> timeInState = new HashMap<>();

    static {
        timeInState.put(State.EXPLODING, StaticServices.BULLET_EXPLODING_TIME);
    }

    private TankUnit parent;
    private Type type;

    public BulletUnit(Bounds bounds, BattleGround playground, long velocity,
                      TankUnit parent, Type type) {
        super(bounds, asList(GameUnit.State.ACTIVE, State.EXPLODING, GameUnit.State.DEAD), timeInState,
                playground, velocity);
        this.parent = parent;
        this.type = type;
        this.setEngineOn(true);
        this.setDirection(parent.getDirection());
        parent.setActiveBullets(parent.getActiveBullets() + 1);

        this.currentStateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == State.DEAD) {
                parent.setActiveBullets(parent.getActiveBullets() - 1);
            }
        });
    }

    public BulletUnit(Bounds bounds, BattleGround playground) {
        super(bounds, asList(GameUnit.State.DEAD, GameUnit.State.ACTIVE, State.EXPLODING, GameUnit.State.DEAD), timeInState,
                playground, 0l);
        this.setEngineOn(true);
        this.currentStateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == State.DEAD) {
                parent.setActiveBullets(parent.getActiveBullets() - 1);
            }
        });
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void handlePartialCollisionState() {
        setCurrentState(State.EXPLODING);
        setCollisionProcessState(CollisionProcessState.COMPLETED);
    }

    @Override
    protected MovingModifier<BulletUnit> createMovingModifier(HasGameUnits playground) {
        return new BulletMovingModifier(this, playground);
    }

    public TankUnit getParent() {
        return parent;
    }

    public void setParent(TankUnit parent) {
        this.parent = parent;
    }

    public void setParentFromPlayground(TankUnit parent) {
        Point2D startPosition = parent.getBulletStartPosition();
        double bulletWidth = playground.getBulletWidth();
        double bulletHeight = playground.getBulletHeight();

        Bounds newBounds = new BoundingBox(startPosition.getX(), startPosition.getY(), bulletWidth, bulletHeight);
        setBounds(newBounds);
        this.parent = parent;
        this.type = parent.getBulletType();
        setVelocity(parent.getBulletSpeed());
        this.setEngineOn(true);
        this.setDirection(parent.getDirection());
        parent.setActiveBullets(parent.getActiveBullets() + 1);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public IsMoveAllowedResult isMoveAllowed(boolean isInWorldBounds) {
        if (!isInWorldBounds) {
            setCurrentState(State.EXPLODING);
        }
        return isInWorldBounds ? IsMoveAllowedResult.ALLOW : IsMoveAllowedResult.DESTROY;
    }


    public enum Type {
        SIMPLE,
        POWERFUL
    }

}
