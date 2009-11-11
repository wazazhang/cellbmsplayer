package com.cell.rpg.scene;

import com.cell.rpg.display.UnitNode;
import com.cell.rpg.scene.ability.NPCPathPoint;
import com.cell.rpg.scene.ability.Transport;
import com.cell.rpg.struct.TPosition;
import com.g2d.annotation.Property;

public class Actor extends SceneUnit
{
	final public String	template_unit_id;
	
	@Property("警戒范围")
	public int			look_range		= 300;
	
	@Property("触碰范围")
	public int			touch_range		= 30;
	
	public int			animate;
	
	public Actor(String id, String template_unit_id) 
	{	
		super(id);
		this.template_unit_id = template_unit_id;
	}

	public Class<?>[] getSubAbilityTypes()
	{
		return new Class<?>[]{
				Transport.class,	
				NPCPathPoint.class,
			};
	}
}
