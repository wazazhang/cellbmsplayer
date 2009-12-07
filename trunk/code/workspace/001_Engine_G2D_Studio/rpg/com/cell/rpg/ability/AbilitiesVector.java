package com.cell.rpg.ability;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

public class AbilitiesVector implements Abilities, Serializable
{
	private static final long serialVersionUID = 1L;
	
	/**将显示在单位属性的Ability面板*/
	private Vector<AbstractAbility> abilities = new Vector<AbstractAbility>();
	private Class<?>[] sub_types;
	
	public AbilitiesVector(Class<?> ... sub_types) {
		setTypes(sub_types);
	}
	
	protected void setTypes(Class<?> ... sub_types) {
		this.sub_types = sub_types;
	}
	
	final public void clearAbilities() {
		abilities.clear();
	}
	final public void addAbility(AbstractAbility element) {
		abilities.add(element);
	}
	final public void removeAbility(AbstractAbility element) {
		abilities.remove(element);
	}
	final public AbstractAbility[] getAbilities() {
		return abilities.toArray(new AbstractAbility[abilities.size()]);
	}

	@SuppressWarnings("unchecked")
	final public <T> T getAbility(Class<T> type) {
		for (AbstractAbility a : abilities) {
			if (type.isInstance(a)) {
				return (T) a;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	final public <T> ArrayList<T> getAbilities(Class<T> type) {
		ArrayList<T> ret = new ArrayList<T>();
		for (AbstractAbility a : abilities) {
			if (type.isInstance(a)) {
				ret.add((T)a);
			}
		}
		return ret;
	}
	
	final public int getAbilitiesCount() {
		return abilities.size();
	}
	
	@Override
	final public Class<?>[] getSubAbilityTypes() {
		return sub_types;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer(getAbilitiesCount() + "个单位 : ");
		for (AbstractAbility a : abilities) {
			buffer.append(a.toString() + ";");
		}
		return buffer.toString();
	}
	
	
	
	
}
