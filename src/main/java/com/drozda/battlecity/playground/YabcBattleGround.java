package com.drozda.battlecity.playground;

import com.drozda.battlecity.interfaces.BattleGround;
import com.drozda.battlecity.interfaces.CanMove;
import com.drozda.battlecity.unit.*;
import com.drozda.fx.sprite.FxSprite;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

/**
 * Created by GFH on 27.09.2015.
 */
public class YabcBattleGround implements BattleGround<TileUnit> {
    private static final Logger log = LoggerFactory.getLogger(YabcBattleGround.class);
    private static final EnumSet<PlaygroundState> notPauseStates = EnumSet.of(PlaygroundState.ACTIVE);
    private static final EnumSet<PlaygroundState> pauseStates = EnumSet.complementOf(notPauseStates);
    //    private PlaygroundState state;
    private Point2D gamePixel;
    private int worldWiddthCells = 26;
    private int worldHeightCells = 26;
    private int tankHeightCells = 2;
    private int tankWidthCells = 2;
    private int bonusHeightCells = 2;
    private int bonusWidthCells = 2;
    private int eagleBaseHeightCells = 2;
    private int eagleBaseWidthCells = 2;
    private int worldSizeCells = worldWiddthCells * worldHeightCells;
    private ListProperty<GameUnit> unitList = new SimpleListProperty<>(FXCollections.observableArrayList());
    private ObjectProperty<BattleType> battleType = new SimpleObjectProperty<>(BattleType.SINGLE_PLAYER);
    private ObjectProperty<PlaygroundState> state = new SimpleObjectProperty<>(PlaygroundState.INITIALIZING);
    private ReadOnlyBooleanWrapper pause = new ReadOnlyBooleanWrapper(true);
    private Point2D firstPlayerRespawn;
    private Point2D secondPlayerRespawn;
    private Point2D eagleBaseRespawn;
    private List<Point2D> spadeZone;
    private List<TileUnit> wereInSpadeZone = new LinkedList<>();
    private List<GameUnit> wereNotInPauseState;
    private TankUnit firstPlayer;
    private TankUnit secondPlayer;

    {
        state.addListener((observable, oldValue, newValue) ->
        {
            if (notPauseStates.contains(newValue)) {
                pause.setValue(false);
            }
            if (pauseStates.contains(newValue)) {
                pause.setValue(true);
            }
        });
        pauseProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        FxSprite.pauseBackgroundAnimation();
                        wereNotInPauseState = unitList.stream()
                                .filter(gameUnit1 -> !gameUnit1.isPause())
                                .collect(Collectors.toList());
                        wereNotInPauseState.forEach(gameUnit -> gameUnit.setPause(true));
                    } else {
                        FxSprite.startBackgroundAnimation();
                        if ((wereNotInPauseState == null) || (wereNotInPauseState.isEmpty())) {
                            unitList.forEach(gameUnit -> gameUnit.setPause(false));
                        } else {
                            wereNotInPauseState.forEach(gameUnit -> gameUnit.setPause(false));
                        }
                    }
                }
        );

    }

    public YabcBattleGround(int multX, int multY, BattleType battleType) {
        gamePixel = new Point2D(multX, multY);
        setState(PlaygroundState.CREATED);
        setBattleType(battleType);
        firstPlayerRespawn = new Point2D(8 * getCellWidth(), 24 * getCellHeight());
        secondPlayerRespawn = new Point2D(16 * getCellWidth(), 24 * getCellHeight());
        eagleBaseRespawn = new Point2D(12 * getCellWidth(), 24 * getCellHeight());
        spadeZone = asList(
                new Point2D(11 * getCellWidth(), 25 * getCellHeight()),
                new Point2D(11 * getCellWidth(), 24 * getCellHeight()),
                new Point2D(11 * getCellWidth(), 23 * getCellHeight()),
                new Point2D(12 * getCellWidth(), 23 * getCellHeight()),
                new Point2D(13 * getCellWidth(), 23 * getCellHeight()),
                new Point2D(14 * getCellWidth(), 23 * getCellHeight()),
                new Point2D(14 * getCellWidth(), 24 * getCellHeight()),
                new Point2D(14 * getCellWidth(), 25 * getCellHeight())
        );
    }

    @Override
    public boolean addCell(TileUnit gameUnit) {
        try {
            unitList.add(gameUnit);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    @Override
    public double getCellHeight() {
        return 8 * gamePixel.getY();
    }

    @Override
    public double getCellWidth() {
        return 8 * gamePixel.getX();
    }

    public void heartBeat(long now) {
        unitList.forEach(gameUnit -> gameUnit.heartBeat(now));
    }

    public TankUnit getFirstPlayer() {
        return firstPlayer;
    }

    public TankUnit getSecondPlayer() {
        if (getBattleType() == BattleType.DOUBLE_PLAYER) {
            return secondPlayer;
        }
        return null;
    }

    public BattleType getBattleType() {
        return battleType.get();
    }

    public void setBattleType(BattleType battleType) {
        this.battleType.set(battleType);
    }

    public ReadOnlyBooleanProperty pauseProperty() {
        return pause.getReadOnlyProperty();
    }

    public PlaygroundState getState() {
        return state.get();
    }

    public void setState(PlaygroundState state) {
        this.state.set(state);
    }

    public ObjectProperty<PlaygroundState> stateProperty() {
        return state;
    }

    public void initialize(long tickTime) {
        setState(PlaygroundState.INITIALIZING);
        unitList.stream().forEach(gameUnit -> {
            gameUnit.initialize(tickTime);
            gameUnit.setPause(this.isPause());
        });
        if (this.isPause()) {
            FxSprite.pauseBackgroundAnimation();
        } else {
            FxSprite.startBackgroundAnimation();
        }
        setState(PlaygroundState.PAUSED);
    }

    public boolean isPause() {
        return pause.get();
    }

    @Override
    public ObservableList<GameUnit> getUnitList() {
        return unitList.get();
    }

    @Override
    public boolean isInWorldBounds(double newX, double newY, CanMove moveUnit) {
        return !(newX < 0 ||
                newX > worldHeightCells * getCellHeight() - moveUnit.getBounds().getHeight() ||
                newY < 0 ||
                newY > worldWiddthCells * getCellWidth() - moveUnit.getBounds().getWidth());
    }

    public void setUnitList(ObservableList<GameUnit> unitList) {
        this.unitList.set(unitList);
    }

    public ListProperty<GameUnit> unitListProperty() {
        return unitList;
    }

    public ObjectProperty<BattleType> battleTypeProperty() {
        return battleType;
    }

    @Override
    public void createActiveUnits() {
        createEagleBase();
        createFirstPlayer();
        markAndCreateSpadeZone();
        activateSpadeZone(); // TODO REMOVE AFTER TESTING
        if (getBattleType() == BattleType.DOUBLE_PLAYER) {
            createSecondPlayer();
        }
    }

    @Override
    public boolean setVitalPoints(List<Point2D> enemiesPespawn, Point2D firstPlayerRespawn, Point2D secondPlayerRespawn, Point2D eagleBaseRespawn) {
        return false;
    }

    private void activateSpadeZone() {
        wereInSpadeZone.forEach(tileUnit -> tileUnit.setCurrentState(GameUnit.State.DEAD));
        unitList.stream()
                .filter(gameUnit -> gameUnit instanceof SpadeZoneTileUnit)
                .forEach(gameUnit1 -> gameUnit1.setCurrentState(GameUnit.State.ACTIVE));
    }

    private void markAndCreateSpadeZone() {
        markSpadeZone();
        createSpadeZone();
    }

    private void createSpadeZone() {
        for (Point2D p : spadeZone) {
            SpadeZoneTileUnit spadeZoneTileUnit =
                    new SpadeZoneTileUnit(
                            new BoundingBox(p.getX(),
                                    p.getY(),
                                    getCellWidth(),
                                    getCellHeight()));
            unitList.add(spadeZoneTileUnit);
        }
    }

    private void markSpadeZone() {
        Function<Point2D, List<TileUnit>> marker = new Function<Point2D, List<TileUnit>>() {
            @Override
            public List<TileUnit> apply(Point2D point2D) {
                return unitList.stream()
                        .filter(gameUnit -> gameUnit instanceof TileUnit)
                        .filter(gameUnit1 -> gameUnit1.getBounds().contains(point2D))
                        .map(gameUnit2 -> (TileUnit) gameUnit2)
                        .collect(Collectors.toList());
            }
        };
        spadeZone.stream()
                .map(marker)
                .forEach(tileUnits -> wereInSpadeZone.addAll(tileUnits));
    }

    private void createEagleBase() {
        EagleBaseUnit eagleBaseUnit =
                new EagleBaseUnit(
                        new BoundingBox(eagleBaseRespawn.getX(),
                                eagleBaseRespawn.getY(),
                                getEagleBaseWidth(),
                                getEagleBaseHeight()));
        unitList.add(eagleBaseUnit);
    }

    private double getEagleBaseHeight() {
        return eagleBaseHeightCells * getCellHeight();
    }

    private void createSecondPlayer() {
        secondPlayer = new PlayerTankUnit(
                new BoundingBox(secondPlayerRespawn.getX(),
                        secondPlayerRespawn.getY(),
                        getTankWidth(),
                        getTankHeight()),
                this,
                PlayerTankUnit.PlayerTankType.TANK_SECOND_PLAYER);
        unitList.add(secondPlayer);
    }

    private void createFirstPlayer() {
        firstPlayer = new PlayerTankUnit(
                new BoundingBox(firstPlayerRespawn.getX(),
                        firstPlayerRespawn.getY(),
                        getTankWidth(),
                        getTankHeight()),
                this, PlayerTankUnit.PlayerTankType.TANK_FIRST_PLAYER);
        unitList.add(firstPlayer);
    }

    public void testCreateAllEnemies() {
        List<EnemyTankUnit.EnemyTankType> typelist = asList(EnemyTankUnit.EnemyTankType.values());
        double x = 98;
        double y = 98;
        for (EnemyTankUnit.EnemyTankType tankType : typelist) {
            unitList.add(
                    new EnemyTankUnit(
                            new BoundingBox(x, y, getTankWidth(), getTankHeight()),
                            this, tankType)
            );
            x += 32;
        }
    }

    public double getTankWidth() {
        return tankWidthCells * getCellWidth();
    }

    public double getTankHeight() {
        return tankHeightCells * getCellHeight();
    }

    public void testCreateAllBonuses() {
        List<BonusUnit.BonusType> typeList = asList(BonusUnit.BonusType.values());

        double x = 98;
        double y = 98 + 32;
        for (BonusUnit.BonusType bonusType : typeList) {
            if (bonusType == BonusUnit.BonusType.START_GAME_HELMET) continue;
            if (bonusType == BonusUnit.BonusType.FRIENDLYFIRE_GIFT) continue;
            unitList.add(
                    new BonusUnit(
                            new BoundingBox(x, y, getBonusWidth(), getBonusHeight()),
                            bonusType)
            );
            x += 32;
        }
    }

    public double getBonusWidth() {
        return bonusWidthCells * getCellWidth();
    }

    public double getBonusHeight() {
        return bonusHeightCells * getCellHeight();
    }

    public double getEagleBaseWidth() {
        return eagleBaseWidthCells * getCellWidth();
    }

    public enum BattleType {
        SINGLE_PLAYER,
        DOUBLE_PLAYER
    }
}
