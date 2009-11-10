package com.cell.rpg.scene;


import com.cell.rpg.scene.ability.NPCSpawnRegion;
import com.cell.rpg.scene.ability.StartRegion;



public class Region extends SceneUnit
{
	public int 		width;
	public int 		height;
	public int 		color;
	public float 	alpha;
	
	public Region(int x, int y, int width, int height) 
	{
		this.x		= x;
		this.y		= y;
		this.width	= width;
		this.height	= height;
	}

	public Class<?>[] getSubAbilityTypes()
	{
		return new Class<?>[]{
				StartRegion.class,
				NPCSpawnRegion.class,
			};
	}
}
