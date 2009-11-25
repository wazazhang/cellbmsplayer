package com.g2d.studio.scene.effect;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.g2d.cell.game.SceneSprite;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Sprite;

public class EditEffectInfo extends Sprite
{
	double 			time_offset = 0;
	
	BufferedImage 	image ;
	
	public EditEffectInfo(BufferedImage image, double time_offset) 
	{
		this.image 			= image;
		this.time_offset 	= time_offset;
		this.setSize(image.getWidth(), image.getHeight());
		this.setLocalBounds(
				-image.getWidth()/2, 
				-image.getHeight()/2,
				image.getWidth(), 
				image.getHeight());
	}
	
	@Override
	public void render(Graphics2D g)
	{
		super.render(g);
		
		if (getParent() instanceof SceneSprite) {
			setLocation(0, getParent().local_bounds.y);
		}
		alpha	= (float)Math.max(Math.sin(timer/10f + time_offset), 0);
		g.drawImage(image, local_bounds.x, local_bounds.y, this);
	}
	
}
