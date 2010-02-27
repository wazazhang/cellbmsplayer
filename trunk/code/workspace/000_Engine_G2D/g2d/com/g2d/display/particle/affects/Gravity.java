package com.g2d.display.particle.affects;

import com.g2d.annotation.Property;
import com.g2d.display.particle.ParticleAffect;
import com.g2d.display.particle.ParticleAffectNode;

@Property("引力")
public class Gravity implements ParticleAffect 
{
	@Property("X方向")
	public float x = 0;
	
	@Property("Y方向")
	public float y = 0;
	
	@Override
	public void update(float timelinePosition, ParticleAffectNode particle) {
		particle.addVectorX(x);
		particle.addVectorY(y);
	}

}
