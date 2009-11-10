package com.cell.rpg.entity.scene;

import com.cell.rpg.ability.AbilitySceneNPCSpawnRegion;

import com.cell.rpg.ability.AbilitySceneStartRegion;



public class Region extends SceneUnit
{
	public int 		width;
	public int 		height;
	public int 		color;
	public float 	alpha;
	
	public Region(int x, int y, int width, int height) 
	{
		this.pos.x	= x;
		this.pos.y	= y;
		this.width	= width;
		this.height	= height;
	}

	public Class<?>[] getSubAbilityTypes()
	{
		return new Class<?>[]{
				AbilitySceneStartRegion.class,
				AbilitySceneNPCSpawnRegion.class,
			};
	}
}
