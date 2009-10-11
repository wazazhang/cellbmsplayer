package com.cell.rpg.entity;

import com.cell.rpg.ability.AbilitySceneNPCSpawnRegion;
import com.cell.rpg.ability.AbilitySceneStartRegion;
public class Region extends Unit
{
	public int width;
	public int height;

	public int 		color;
	public float 	alpha;
	
	public Region(int width, int height) 
	{
		this.width = width;
		this.height = height;
	}

	public Class<?>[] getSubAbilityTypes()
	{
		return new Class<?>[]{
				AbilitySceneStartRegion.class,
				AbilitySceneNPCSpawnRegion.class,
			};
	}
}
