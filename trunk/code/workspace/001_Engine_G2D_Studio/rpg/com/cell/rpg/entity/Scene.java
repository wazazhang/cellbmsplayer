package com.cell.rpg.entity;

import java.util.ArrayList;
import java.util.Vector;

import com.cell.rpg.RPGObject;
import com.cell.rpg.ability.Abilities;
import com.cell.rpg.ability.AbstractAbility;

public class Scene extends RPGObject implements Abilities
{
	ArrayList<Unit>	units = new ArrayList<Unit>();
	
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

	public <T extends AbstractAbility> T getAbility(Class<T> type) {
		for (AbstractAbility a : abilities) {
			if (type.isInstance(a)) {
				return type.cast(a);
			}
		}
		return null;
	}
	
	public <T extends AbstractAbility> ArrayList<T> getAbilities(Class<T> type) {
		ArrayList<T> ret = new ArrayList<T>();
		for (AbstractAbility a : abilities) {
			if (type.isInstance(a)) {
				ret.add(type.cast(a));
			}
		}
		return ret;
	}

	public int getAbilitiesCount() {
		return abilities.size();
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return null;
	}
	
	
}
