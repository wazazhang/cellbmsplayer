package com.cell.rpg.entity;

import java.util.ArrayList;
import java.util.Vector;

import com.cell.rpg.RPGObject;
import com.cell.rpg.ability.Abilities;
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
public abstract class Unit extends RPGObject implements Abilities
{
// --------------------------------------------------------------------------------------------------------------------
//	transient
	
//	--------------------------------------------------------------------------------------------------------------------
//	persistent

	public UnitNode		display_node;
	
	
	/** scene graph 结构的视图 */
	public String 		name		= "no name";
	
	public TPosition	pos			= new TPosition();
	
//	public TMotion 		motion		= new TMotion();
//
//	public TState		state		= new TState();

//	--------------------------------------------------------------------------------------------------------------------
//	properties 
	
	@Property("警戒范围")
	public int			look_range		= 300;
	
	@Property("触碰范围")
	public int			touch_range		= 30;

//	--------------------------------------------------------------------------------------------------------------------
	
	/**将显示在单位属性的Ability面板*/
	public Vector<AbstractAbility> abilities = new Vector<AbstractAbility>();
	
	
	
//	--------------------------------------------------------------------------------------------------------------------

	public Unit()
	{
	}
	
	protected void init_transient() {
		super.init_transient();
		if (abilities == null) {
			abilities = new Vector<AbstractAbility>();
		}
	}
	
	public void set(Unit other) 
	{
		this.name	= other.name;
		this.pos	= other.pos;
//		this.motion	= other.motion;
	}
	
//	-------------------------------------------------------------------------------------------------------
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
	
//	--------------------------------------------------------------------------------------------------------------------
	
	
}
