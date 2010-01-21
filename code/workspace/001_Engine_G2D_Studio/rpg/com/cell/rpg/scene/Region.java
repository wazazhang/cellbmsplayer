package com.cell.rpg.scene;


import com.cell.rpg.quest.ability.QuestAccepter;
import com.cell.rpg.quest.ability.QuestPublisher;
import com.cell.rpg.scene.ability.RegionPlayerEnter;
import com.cell.rpg.scene.ability.RegionSpawnNPC;



public class Region extends SceneUnit
{
	public int 		width;
	public int 		height;
	public int 		color;
	public float 	alpha;
	
	public Region(String id, int x, int y, int width, int height) 
	{
		super(id);
		this.x		= x;
		this.y		= y;
		this.width	= width;
		this.height	= height;
	}

	public Class<?>[] getSubAbilityTypes()
	{
		return new Class<?>[]{
				RegionPlayerEnter.class,
				RegionSpawnNPC.class,
				QuestAccepter.class,
				QuestPublisher.class,
			};
	}
}
