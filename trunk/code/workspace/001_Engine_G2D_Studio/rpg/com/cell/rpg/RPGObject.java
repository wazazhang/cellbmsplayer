package com.cell.rpg;

import java.io.ObjectStreamException;
import java.io.Serializable;

import com.cell.DObject;
import com.cell.rpg.ability.AbilitiesVector;
import com.cell.util.MarkedHashtable;

public abstract class RPGObject extends DObject implements Serializable
{
	/**将显示在单位属性的Ability面板*/
	final protected AbilitiesVector abilities;
	
	public RPGObject() {
		abilities = new AbilitiesVector(getSubAbilityTypes());
	}
	
	public AbilitiesVector getAbilities() {
		return abilities;
	}
	
	abstract public Class<?>[] getSubAbilityTypes();

}
