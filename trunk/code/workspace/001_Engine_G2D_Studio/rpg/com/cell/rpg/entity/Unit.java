package com.cell.rpg.entity;

import java.util.ArrayList;
import java.util.Vector;

import com.cell.rpg.RPGObject;
import com.cell.rpg.ability.Abilities;
import com.cell.rpg.ability.AbilitiesVector;
import com.cell.rpg.ability.AbilitySceneNPC;
import com.cell.rpg.ability.AbilitySceneNPCPathPoint;
import com.cell.rpg.ability.AbilitySceneTransport;
import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.display.UnitNode;
import com.cell.rpg.entity.struct.TMotion;
import com.cell.rpg.entity.struct.TPosition;
import com.cell.rpg.entity.struct.TState;
import com.g2d.annotation.Property;

/**
 * G2DStudio编辑器存储的单位
 * @author WAZA
 */
public abstract class Unit extends RPGObject
{
	/**将显示在单位属性的Ability面板*/
	AbilitiesVector abilities;
	
//	--------------------------------------------------------------------------------------------------------------------

	public Unit()
	{
		abilities = new AbilitiesVector(getSubAbilityTypes());
	}
	
	public AbilitiesVector getAbilities() {
		return abilities;
	}
	
	abstract public Class<?>[] getSubAbilityTypes();

}
