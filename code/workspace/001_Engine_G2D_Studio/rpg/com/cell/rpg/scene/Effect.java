package com.cell.rpg.scene;

public class Effect extends SceneUnit
{
	final public int	template_effect_id;
	
	public Effect(String id, int template_effect_id) 
	{	
		super(id);
		this.template_effect_id = template_effect_id;
	}

	public Class<?>[] getSubAbilityTypes()
	{
		return new Class<?>[]{};
	}	
	
	@Override
	public Class<? extends com.cell.rpg.scene.script.entity.SceneUnit> getEventType() {
		return null;
	}

}
