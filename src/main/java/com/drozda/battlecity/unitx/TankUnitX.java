package com.drozda.battlecity.unitx;

import com.drozda.battlecity.eventx.IMoveEvent;
import com.drozda.battlecity.interfaces.CanFire;
import com.drozda.battlecity.interfacesx.BattleGround;
import com.drozda.battlecity.modifierx.boundslist.byevent.TankBoundsListModifierByMoveEvent;
import com.drozda.battlecity.modifierx.boundslist.byproperty.BoundsListModifierByDirectionProperty;
import com.drozda.battlecity.unitx.enumeration.Direction;

/**
 * Created by GFH on 22.11.2015.
 */
public abstract class TankUnitX extends MoveableUnitX implements CanFire {

    BoundsListModifierByDirectionProperty boundsListModifierByDirectionProperty =
            new BoundsListModifierByDirectionProperty(getReadOnlyBoundsListWrapper(),
                    getDirectionProperty(),
                    playground, this);
    TankBoundsListModifierByMoveEvent tankBoundsListModifierByMoveEvent =
            new TankBoundsListModifierByMoveEvent(getReadOnlyBoundsListWrapper(), IMoveEvent.class,
                    playground, this);

    public TankUnitX(BattleGround playground, Direction startDirection) {
        super(playground, startDirection);
        boundsListModifierManager.addListPropertyModifier(boundsListModifierByDirectionProperty);
        boundsListModifierManager.addListPropertyModifier(tankBoundsListModifierByMoveEvent);
    }
}
