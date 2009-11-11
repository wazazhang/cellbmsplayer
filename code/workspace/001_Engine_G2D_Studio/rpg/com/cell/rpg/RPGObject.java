package com.cell.rpg;

import java.io.ObjectStreamException;
import java.io.Serializable;

import com.cell.DObject;
import com.cell.rpg.ability.AbilitiesVector;
import com.cell.util.MarkedHashtable;
import com.cell.util.zip.ZipNode;

public abstract class RPGObject extends DObject implements Serializable, ZipNode
{
	final public String id;
	
	/**将显示在单位属性的Ability面板*/
	final protected AbilitiesVector abilities;
	
	public RPGObject(String id) {
		this.id = id;
		this.abilities = new AbilitiesVector(getSubAbilityTypes());
	}
	
	@Override
	final public String getEntryName() {
		return getClass().getSimpleName().toLowerCase() + "/" + id+".xml";
	}
	
	public AbilitiesVector getAbilities() {
		return abilities;
	}
	
	abstract public Class<?>[] getSubAbilityTypes();

}
