package com.cell.rpg.entity;

import java.awt.Rectangle;

import com.cell.CMath;
import com.cell.rpg.ability.AbilitySceneNPC;
import com.cell.rpg.ability.AbilitySceneNPCSpawnRegion;
import com.cell.rpg.ability.AbilitySceneStartRegion;
import com.cell.rpg.ability.AbilitySceneTransport;
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
