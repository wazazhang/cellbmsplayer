package com.cell.rpg.entity.scene;

import com.cell.rpg.entity.Unit;
import com.cell.rpg.entity.struct.TPosition;

public abstract class SceneUnit extends Unit
{
	/** scene graph 结构的视图 */
	public String 		name		= "no name";
	
	public TPosition	pos			= new TPosition();

}
