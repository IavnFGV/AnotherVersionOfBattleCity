package com.drozda.battlecity.controller;

import com.drozda.battlecity.interfaces.ActionCommandGenerator;
import com.drozda.battlecity.unit.MoveableUnit;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by GFH on 14.11.2015.
 */
public class KeyboardActionCommandGenerator implements ActionCommandGenerator, EventHandler<KeyEvent> {
    protected final KeyCode up;
    protected final KeyCode down;
    protected final KeyCode left;
    protected final KeyCode right;
    protected final KeyCode fire;

    Map<KeyCode, MoveableUnit.Direction> directionMap = new HashMap<>();

    Set<KeyCode> pressedKeys = EnumSet.noneOf(KeyCode.class);
    Set<KeyCode> monitoringKeys = EnumSet.noneOf(KeyCode.class);

    MoveableUnit.Direction directionCommand;
    boolean fireCommand;

    public KeyboardActionCommandGenerator(KeyCode up, KeyCode down, KeyCode left, KeyCode right, KeyCode fire) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.fire = fire;

        monitoringKeys.add(up);
        monitoringKeys.add(down);
        monitoringKeys.add(left);
        monitoringKeys.add(right);
        monitoringKeys.add(fire);

        directionMap.put(up, MoveableUnit.Direction.UP);
        directionMap.put(down, MoveableUnit.Direction.DOWN);
        directionMap.put(left, MoveableUnit.Direction.LEFT);
        directionMap.put(right, MoveableUnit.Direction.RIGHT);

    }

    @Override
    public MoveableUnit.ActionCommand extractActionCommand() {
        return new MoveableUnit.ActionCommand(directionCommand, fireCommand);
    }

    @Override
    public void handle(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            synchronized (pressedKeys) {
                if (monitoringKeys.contains(event.getCode())) {
                    if (event.getCode() == fire) {
                        fireCommand = true;
                        return;
                    }
                    directionCommand = directionMap.get(event.getCode());
                }
            }
        }
        if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            synchronized (pressedKeys) {
                if (monitoringKeys.contains(event.getCode())) {
                    if (event.getCode() == fire) {
                        fireCommand = false;
                        return;
                    }
                    directionCommand = null;
                }
            }
        }
    }
}
