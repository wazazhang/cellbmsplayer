package com.cell.rpg.ability;

import java.util.ArrayList;

/**
 * 适用于，从多种Ability类型中选择一个的组合
 * @author WAZA
 *
 */
public abstract class AbilitiesSingle implements Abilities
{
	AbstractAbility single;

	public AbstractAbility getSingle() {
		return single;
	}
	
	@Override
	public void addAbility(AbstractAbility element) {
		this.single = element;
	}
	
	@Override
	public void removeAbility(AbstractAbility element) {
		if (element.equals(single)) {
			single = null;
		}
	}
	
	@Override
	public void clearAbilities() {
		this.single = null;
	}

	@Override
	public AbstractAbility[] getAbilities() {
		if (single!=null) {
			return new AbstractAbility[]{single};
		} else {
			return new AbstractAbility[0];
		}
	}

	@Override
	public <T> ArrayList<T> getAbilities(Class<T> type) {
		if (single!=null && type.isInstance(single)) {
			ArrayList<T> ret = new ArrayList<T>(1);
			ret.add(type.cast(single));
			return ret;
		} else {
			return new ArrayList<T>();
		}
	}

	@Override
	public int getAbilitiesCount() {
		return single!=null ? 1 : 0;
	}

	@Override
	public <T> T getAbility(Class<T> type) {
	if (single!=null && type.isInstance(single)) {
			return type.cast(single);
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		return getSingle() + "";
	}
}
