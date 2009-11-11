package com.cell.rpg.scene;

import com.cell.rpg.RPGObject;
import com.cell.rpg.struct.TPosition;

public abstract class SceneUnit extends RPGObject
{
	/** scene graph 结构的视图 */
	public String 		name	= "no name";
	public float		x;
	public float		y;
	public float		z;
	
	public SceneUnit(String id) {
		super(id);
	}
	
}
