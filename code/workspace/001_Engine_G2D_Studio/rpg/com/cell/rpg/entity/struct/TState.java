package com.cell.rpg.entity.struct;

import com.cell.rpg.RPGObject;

public class TState extends RPGObject
{
	private static final long serialVersionUID = 1L;

	/**hp和 hp最大值*/
	public int hit_point, hit_point_max;
	
	/**mp和 mp最大值*/
	public int mana_point, mana_point_max;

	/**力量*/
	public int st_strength;

	/**敏捷*/
	public int st_agility;

	/**智力*/
	public int st_intelligence;

	
}
