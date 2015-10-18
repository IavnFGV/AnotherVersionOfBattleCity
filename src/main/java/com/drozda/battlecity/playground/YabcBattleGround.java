package com.drozda.battlecity.playground;

import com.drozda.battlecity.interfaces.BattleGround;
import com.drozda.battlecity.unit.GameUnit;
import com.drozda.battlecity.unit.TileUnit;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
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
    //    private ObservableListBase observableListBase;
//    {
//
//    }
//    private ListProperty listProperty = new SimpleListProperty<>(observableListBase);
//    {
//        listProperty.s
//    }
    //private MailManager
    private ListProperty<GameUnit> unitList = new SimpleListProperty<>(FXCollections.observableArrayList());

    public YabcBattleGround(int multX, int multY) {

        gamePixel = new Point2D(multX, multY);
        //      unitList.get().add((GameUnit)(new Object()));
        setState(PlaygroundState.CREATED);
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

    public double getCellWidth() {
        return 8 * gamePixel.getX();
    }

    public double getCellHeight() {
        return 8 * gamePixel.getY();
    }
}
