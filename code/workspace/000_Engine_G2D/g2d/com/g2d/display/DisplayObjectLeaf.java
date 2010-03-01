package com.g2d.display;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * 用于快速渲染的根节点
 * 该节点不会恢复Clip,Composite
 */
public abstract class DisplayObjectLeaf extends DisplayObject
{
	void onUpdate(DisplayObjectContainer parent) 
	{
		this.parent 			= parent;
		this.root 				= parent.root;
		this.timer ++;
		this.interval_ms 		= (int)(System.currentTimeMillis() - last_update_time);
		this.last_update_time 	= System.currentTimeMillis();
		this.refreshScreen(parent);
		this.update();
	}
	
	final void updateBefore(DisplayObjectContainer parent) {}
	final void updateAfter(DisplayObjectContainer parent) {}
	
	void onRender(Graphics2D g) 
	{
		if (visible) 
		{
			AffineTransform	transfrom = g.getTransform();
			try {
				g.translate(x, y);
				this.render(g);
			} finally {
				g.setTransform(transfrom);
			}
		}
	}
	
	final protected void renderBefore(Graphics2D g){}
	final protected void renderAfter(Graphics2D g) {}
	final protected void renderInteractive(Graphics2D g){}
	final protected void renderDebug(Graphics2D g) {}
	

}
