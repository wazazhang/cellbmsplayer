package com.cell.rpg;

import java.util.ArrayList;
import java.util.Vector;

import com.cell.DObject;
import com.cell.rpg.ability.Abilities;
import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.io.RPGSerializationListener;
import com.cell.util.zip.ZipNode;

public abstract class RPGObject extends DObject implements Abilities, ZipNode, RPGSerializationListener
{
//	------------------------------------------------------------------------------------------------------------------
	
	final public String id;
	
	/**将显示在单位属性的Ability面板*/
	private Vector<AbstractAbility> 			abilities;

	transient AbstractAbility[]					static_abilities;
	
	transient Vector<RPGSerializationListener> 	seriListeners;
	
//	------------------------------------------------------------------------------------------------------------------
	
	public RPGObject(String id) {
		this.id = id;
	}
	
	@Override
	protected void init_transient() {
		super.init_transient();
		if (abilities == null) {
			this.abilities = new Vector<AbstractAbility>();
		}
		for (int i = abilities.size() - 1; i >= 0; --i) {
			if (abilities.get(i)==null) {
				abilities.remove(i);
			}
		}
	}
	
	@Override
	final public String getEntryName() {
		return id+".xml";
	}

//	------------------------------------------------------------------------------------------------------------------
	
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
		if (!RPGConfig.IS_EDIT_MODE) {
			if (static_abilities == null) {
				static_abilities = abilities.toArray(new AbstractAbility[abilities.size()]);
			}
			return static_abilities;
		}
		return abilities.toArray(new AbstractAbility[abilities.size()]);
	}

	public <T> T getAbility(Class<T> type) {
		for (AbstractAbility a : abilities) {
			if (type.isInstance(a)) {
				return type.cast(a);
			}
		}
		return null;
	}
	
	public <T> ArrayList<T> getAbilities(Class<T> type) {
		ArrayList<T> ret = new ArrayList<T>();
		for (AbstractAbility a : abilities) {
			if (type.isInstance(a)) {
				ret.add(type.cast(a));
			}
		}
		return ret;
	}
	
//	------------------------------------------------------------------------------------------------------------------
	
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
	
	@Override
	public void onReadComplete(RPGObject object, String xmlFile) {}
	@Override
	public void onWriteBefore(RPGObject object, String xmlFile) {}

//	------------------------------------------------------------------------------------------------------------------
	
}
