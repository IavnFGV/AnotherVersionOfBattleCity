package com.drozda.battlecity.interfacesx;

/**
 * Created by GFH on 19.11.2015.
 */
public interface ObjectPropertyModifierManager {
    <I extends GameUnitObjectPropertyModifier> void addObjectPropertyModifier(I propertyModifier);
}
