package com.drozda.battlecity.playground;

import com.drozda.battlecity.interfaces.BattleGround;
import com.drozda.battlecity.unit.GameUnit;
import com.drozda.battlecity.unit.TankUnit;
import com.drozda.battlecity.unit.TileUnit;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 27.09.2015.
 */
public class YabcBattleGround implements BattleGround<TileUnit.TileType> {
    private static final Logger log = LoggerFactory.getLogger(YabcBattleGround.class);

    private PlaygroundState state;
    private Point2D gamePixel;
    private int worldWiddthCells = 26;
    private int worldHeightCells = 26;
    private int tankHeightCells = 2;
    private int tankWidthCells = 2;
    private int worldSizeCells = worldWiddthCells * worldHeightCells;
    private ListProperty<GameUnit> unitList = new SimpleListProperty<>(FXCollections.observableArrayList());
    private ObjectProperty<BattleType> battleType = new SimpleObjectProperty<>(BattleType.SINGLE_PLAYER);

    private Point2D firstPlayerRespawn;
    private Point2D secondPlayerRespawn;

    public YabcBattleGround(int multX, int multY, BattleType battleType) {
        gamePixel = new Point2D(multX, multY);
        setState(PlaygroundState.CREATED);
        setBattleType(battleType);
        firstPlayerRespawn = new Point2D(8 * getCellWidth(), 24 * getCellHeight());
        secondPlayerRespawn = new Point2D(16 * getCellWidth(), 24 * getCellHeight());
    }

    public double getCellWidth() {
        return 8 * gamePixel.getX();
    }

    public double getCellHeight() {
        return 8 * gamePixel.getY();
    }

    public PlaygroundState getState() {
        return state;
    }

    public void setState(PlaygroundState state) {
        this.state = state;
    }

    public void initialize() {
        setState(PlaygroundState.INITIALIZING);
        setState(PlaygroundState.PAUSED);

    }

    @Override
    public ObservableList<GameUnit> getUnitList() {
        return unitList.get();
    }

    public void setUnitList(ObservableList<GameUnit> unitList) {
        this.unitList.set(unitList);
    }

    public ListProperty<GameUnit> unitListProperty() {
        return unitList;
    }

    @Override
    public boolean addCell(int x, int y, TileUnit.TileType tileType) {
        TileUnit tileUnit = new TileUnit(x * getCellWidth(), y * getCellHeight(), getCellWidth(), getCellHeight(),
                null, null, this, tileType);
        unitList.add(tileUnit);
        return true;
    }

    public BattleType getBattleType() {
        return battleType.get();
    }

    public void setBattleType(BattleType battleType) {
        this.battleType.set(battleType);
    }

    public ObjectProperty<BattleType> battleTypeProperty() {
        return battleType;
    }

    @Override
    public void start() {
        createFirstPlayer();

        //     if ()
    }

    private void createFirstPlayer() {
        TankUnit firstPlayer = new TankUnit(firstPlayerRespawn.getX(), firstPlayerRespawn.getY(), getTankWidth(),
                getTankHeight(), this, TankUnit.TankType.TANK_FIRST_PLAYER);
        unitList.add(firstPlayer);
    }

    public double getTankWidth() {
        return tankWidthCells * getCellWidth();
    }

    public double getTankHeight() {
        return tankHeightCells * getCellHeight();
    }

    public enum BattleType {
        SINGLE_PLAYER,
        DOUBLE_PLAYER
    }
}
