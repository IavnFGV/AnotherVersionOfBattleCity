package com.drozda.battlecity.interfacesx;

import com.drozda.battlecity.unitx.enumeration.BasicState;
import javafx.beans.property.ReadOnlyObjectProperty;

/**
 * Created by GFH on 19.11.2015.
 */
public interface BasicStatePropertyModifiable /*extends ObjectPropertyModifiable<BasicState>*/ {
    //    @Override
    ReadOnlyObjectProperty<BasicState> getBasicStateProperty();

}
