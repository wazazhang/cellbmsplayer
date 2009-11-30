package com.cell.rpg;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import com.cell.DObject;
import com.cell.rpg.ability.Abilities;
import com.cell.rpg.ability.AbilitiesVector;
import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.io.RPGSerializationListener;
import com.cell.util.MarkedHashtable;
import com.cell.util.zip.ZipNode;

public abstract class RPGObject extends DObject implements Abilities, ZipNode
{
	final public String id;
	
	/**将显示在单位属性的Ability面板*/
	final private Vector<AbstractAbility> abilities = new Vector<AbstractAbility>();
	
	transient Vector<RPGSerializationListener> seriListeners;
	
	public RPGObject(String id) {
		this.id = id;
	}
	
	@Override
	final public String getEntryName() {
		return id+".xml";
	}

	public void clearAbilities() {
		abilities.clear();
	}
	public void addAbility(AbstractAbility element) {
		abilities.add(element);
	}
	public void removeAbility(AbstractAbility element) {
		abilities.remove(element);
	}
	public AbstractAbility[] getAbilities() {
		return abilities.toArray(new AbstractAbility[abilities.size()]);
	}

	@SuppressWarnings("unchecked")
	public <T extends AbstractAbility> T getAbility(Class<T> type) {
		for (AbstractAbility a : abilities) {
			if (type.isInstance(a)) {
				return (T) a;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends AbstractAbility> ArrayList<T> getAbilities(Class<T> type) {
		ArrayList<T> ret = new ArrayList<T>();
		for (AbstractAbility a : abilities) {
			if (type.isInstance(a)) {
				ret.add((T)a);
			}
		}
		return ret;
	}
	
	public int getAbilitiesCount() {
		return abilities.size();
	}
	
	public void addRPGSerializationListener(RPGSerializationListener listener) {
		if (seriListeners == null) {
			seriListeners = new Vector<RPGSerializationListener>();
		}
		seriListeners.add(listener);
	}
	
	public Vector<RPGSerializationListener> getRPGSerializationListeners() {
		return seriListeners;
	}
}
