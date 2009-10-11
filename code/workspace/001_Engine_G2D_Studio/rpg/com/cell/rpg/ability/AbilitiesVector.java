package com.cell.rpg.ability;

import java.util.ArrayList;
import java.util.Vector;

public abstract class AbilitiesVector implements Abilities
{
	private static final long serialVersionUID = 1L;
	
	/**将显示在单位属性的Ability面板*/
	public Vector<AbstractAbility> abilities = new Vector<AbstractAbility>();
	
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
	
	public int size() {
		return abilities.size();
	}
}
