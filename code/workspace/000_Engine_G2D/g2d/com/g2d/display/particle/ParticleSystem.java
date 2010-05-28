package com.g2d.display.particle;

import java.awt.Graphics2D;

import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Sprite;

public abstract class ParticleSystem extends DisplayObjectContainer 
{
	@Override
	public void added(DisplayObjectContainer parent) {}

	@Override
	public void removed(DisplayObjectContainer parent) {}

	@Override
	public void render(Graphics2D g) {}

	@Override
	public void update() {}
	
	@Override
	protected boolean testCatchMouse(Graphics2D g) {
		return false;
	}
}
