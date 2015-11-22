package com.drozda.battlecity.interfacesx;

import com.drozda.battlecity.unitx.enumeration.PlaygroundState;
import javafx.beans.property.ReadOnlyObjectProperty;

/**
 * Created by GFH on 22.11.2015.
 */
public interface BattleGround<E extends Enum> extends HasGameUnits, Loadable<E> {
    ReadOnlyObjectProperty<PlaygroundState> stateProperty();

    double getTileWidth();

    double getTileHeight();

}
