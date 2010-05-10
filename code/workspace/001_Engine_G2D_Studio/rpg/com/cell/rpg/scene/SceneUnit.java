package com.cell.rpg.scene;

import com.cell.rpg.RPGObject;
import com.cell.rpg.anno.PropertyAdapter;
import com.cell.rpg.anno.PropertyType;
import com.cell.rpg.quest.QuestState;
import com.cell.rpg.struct.QuestStateDisplayOR;
import com.g2d.annotation.Property;

public abstract class SceneUnit extends RPGObject
{
	/** scene graph 结构的视图 */
	public String 		name	= "no name";
	public float		x;
	public float		y;
	public float		z;

	@Property("任务依赖显示条件 (覆盖TUnit)")
	public QuestStateDisplayOR quest_display = null;
	
	public SceneUnit(String id) {
		super(id);
	}
	
}
