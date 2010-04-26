package com.cell.rpg.scene;

import com.cell.rpg.RPGObject;
import com.cell.rpg.anno.PropertyAdapter;
import com.cell.rpg.anno.PropertyType;
import com.cell.rpg.quest.QuestState;
import com.g2d.annotation.Property;

public abstract class SceneUnit extends RPGObject
{
	/** scene graph 结构的视图 */
	public String 		name	= "no name";
	public float		x;
	public float		y;
	public float		z;

	@Property("只显示在接了某任务(覆盖TUnit)")
	public QuestState 	show_in_quest_state		= QuestState.NA;
	
	@Property("只显示在接了某任务ID(覆盖TUnit)")@PropertyAdapter(PropertyType.QUEST_ID)
	public int 			show_in_quest_id		= -1;
	
	public SceneUnit(String id) {
		super(id);
	}
	
}
