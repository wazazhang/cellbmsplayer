package com.g2d.studio.particles;

import java.awt.Color;
import java.awt.event.MouseEvent;

import com.g2d.display.Canvas;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Stage;
import com.g2d.display.particle.ParticleData;
import com.g2d.display.particle.ParticleDisplay;
import com.g2d.studio.gameedit.EffectEditor;

public class ParticleStage extends Stage
{
	EffectEditor 	cur_edit;
	ParticleDisplay	particle;
	
	@Override
	public void inited(Canvas root, Object[] args) {
		if (args!=null && args.length>0 && args[0] instanceof EffectEditor) {
			cur_edit = (EffectEditor)args[0];
		}
		particle = new ParticleDisplay(cur_edit.getData().particles);
		this.addChild(particle);
	}
	
	
	public void added(DisplayObjectContainer parent) {
		particle.setLocation(getWidth()/2, getHeight()/2);
	}
	
	public void removed(DisplayObjectContainer parent) {
	}

	public void update() {
		cur_edit.getData();
		if (getRoot().isMouseHold(MouseEvent.BUTTON1)) {
			particle.setLocation(getMouseX(), getMouseY());
			particle.spawn();
		}
		if (getRoot().isMouseDown(MouseEvent.BUTTON3)) {
			particle.setLocation(getMouseX(), getMouseY());
			particle.spawn();
		}
	}

	public void render(java.awt.Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fill(local_bounds);
	}
	
}
