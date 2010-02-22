package com.cell.rpg.template;

import com.cell.rpg.particle.ParticleSystem;


public class TEffect extends TemplateNode
{	
	final public ParticleSystem particles = new ParticleSystem();
	
	public TEffect(int id, String name) {
		super(id, name);
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return new Class<?>[] {};
	}
}
