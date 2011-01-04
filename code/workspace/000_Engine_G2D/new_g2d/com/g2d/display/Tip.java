package com.g2d.display;

import com.g2d.Graphics2D;



public abstract class Tip extends DisplayObjectContainer
{	
	/**
	 * 设置Tip在Stage里显示位置
	 * @param parent
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	protected void setStageLocation(Stage parent, int x, int y, int w, int h) 
	{
		int sx = x + w + 1; 
		int sy = y;
		if (sx + getWidth() > parent.getWidth()) {
			sx = x - getWidth() - 1;
		}
		if (sy + getHeight() > parent.getHeight()) {
			sy = parent.getHeight() - getHeight();
		}
		if (sx < 0) sx = 0;
		if (sy < 0) sy = 0;
		super.setLocation(sx, sy);
	}
	
	@Override
	final protected boolean testCatchMouse(Graphics2D g) {
		return false;
	}
	
	@Override
	final public void added(DisplayObjectContainer parent) {
	}
	
	@Override
	final public void removed(DisplayObjectContainer parent) {
	}
}
