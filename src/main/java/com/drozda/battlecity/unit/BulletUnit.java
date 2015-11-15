package com.drozda.battlecity.unit;

import com.drozda.battlecity.interfaces.Collideable;
import com.drozda.battlecity.interfaces.HasGameUnits;
import com.drozda.battlecity.modifier.BulletMovingModifier;
import com.drozda.battlecity.modifier.MovingModifier;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import org.apache.commons.lang3.tuple.ImmutablePair;

import static java.util.Arrays.asList;

/**
 * Created by GFH on 27.09.2015.
 */
public class BulletUnit extends MoveableUnit implements Collideable<GameUnit> {

    private TankUnit parent;

    private Type type;

    public BulletUnit(Bounds bounds, HasGameUnits playground, long velocity,
                      TankUnit parent, Type type) {
        super(bounds, asList(GameUnit.State.ACTIVE, State.EXPLODING, GameUnit.State.DEAD), null,
                playground, velocity);
        this.parent = parent;
        this.type = type;
        this.setEngineOn(true);
        this.setDirection(parent.getDirection());
        this.currentStateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == State.DEAD) {
                Platform.runLater(() -> this.playground.getUnitList().remove(this));
            }
        });
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

    @Override
    public ImmutablePair<CollideResult, CollideResult> activeCollide(GameUnit other) {
        CollideResult selfCollideResult = CollideResult.NOTHING;

        colliding_block:
        // only for experiment  always want to try  labels!!))
        // TODO make this in hash or something
        {
            if (this.parent == other) {
                selfCollideResult = CollideResult.NOTHING;
                break colliding_block;
            }
            if (!this.getBounds().intersects(other.getBounds())) {
                selfCollideResult = CollideResult.NOTHING;
                break colliding_block;
            }
            if (other instanceof BonusUnit) {
                selfCollideResult = CollideResult.NOTHING;
                break colliding_block;
            }
            if (other instanceof BulletUnit) {
                this.setCurrentState(State.DEAD);
                selfCollideResult = CollideResult.STATE_CHANGE;
                break colliding_block;
            }
            if (other instanceof PlayerTankUnit) {
                if (this.parent instanceof PlayerTankUnit) {
                    this.setCurrentState(State.DEAD);
                    selfCollideResult = CollideResult.STATE_CHANGE;
                    break colliding_block;
                }
                if (this.parent instanceof EnemyTankUnit) {
                    this.setCurrentState(State.EXPLODING);
                    selfCollideResult = CollideResult.STATE_CHANGE;
                    break colliding_block;
                }
            }
            if (other instanceof EnemyTankUnit) {
                if (this.parent instanceof EnemyTankUnit) {
                    selfCollideResult = CollideResult.NOTHING;
                    break colliding_block;
                }
                if (this.parent instanceof PlayerTankUnit) {
                    this.setCurrentState(State.EXPLODING);
                    break colliding_block;
                }
            }
            if (other instanceof TileUnit) {
                this.setCurrentState(State.EXPLODING);
                selfCollideResult = CollideResult.STATE_CHANGE;
                break colliding_block;
            }
        }
        if (selfCollideResult != CollideResult.NOTHING) {
            return new ImmutablePair<>(selfCollideResult, ((Collideable) other).passiveCollide(this));
        }
        return NOTHING_PAIR;
    }

    @Override
    public CollideResult passiveCollide(GameUnit other) {
        if (parent == other) {
            return CollideResult.NOTHING;
        }
        if (other instanceof BulletUnit) {
            this.setCurrentState(State.DEAD);
            return CollideResult.STATE_CHANGE;
        }
        if (other instanceof PlayerTankUnit) {
            if (this.parent instanceof PlayerTankUnit) {
                this.setCurrentState(State.DEAD);
                return CollideResult.STATE_CHANGE;
            }
            if (this.parent instanceof EnemyTankUnit) {
                this.setCurrentState(State.EXPLODING);
                return CollideResult.STATE_CHANGE;
            }
        }
        if (other instanceof EnemyTankUnit) {
            if (this.parent instanceof EnemyTankUnit) {
                return CollideResult.NOTHING;
            }
            if (this.parent instanceof PlayerTankUnit) {
                this.setCurrentState(State.EXPLODING);
                return CollideResult.STATE_CHANGE;
            }
        }
        return CollideResult.NOTHING;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    public enum Type {
        SIMPLE,
        POWERFUL
    }

}
