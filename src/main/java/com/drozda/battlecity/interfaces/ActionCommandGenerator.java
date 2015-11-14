package com.drozda.battlecity.interfaces;

import com.drozda.battlecity.unit.MoveableUnit;

/**
 * Created by GFH on 14.11.2015.
 */
public interface ActionCommandGenerator {
    MoveableUnit.ActionCommand extractActionCommand();
}
