package com.g2d.display;

import com.g2d.Graphics2D;



public abstract class Tip extends DisplayObjectContainer
{	
	final static public int ANCHOR_LEFT 	= 0x01;
	final static public int ANCHOR_RIGHT 	= 0x02;
	final static public int ANCHOR_HCENTER 	= 0x04;
	final static public int ANCHOR_TOP 		= 0x10;
	final static public int ANCHOR_BOTTON 	= 0x20;
	final static public int ANCHOR_VCENTER 	= 0x40;
	
	static public int DEFAULT_ANCHOR = ANCHOR_RIGHT | ANCHOR_BOTTON;
	
	public Tip() {
		setSize(10, 10);
	}
	
	/**
	 * 
	 * 设置Tip在Stage里显示位置，重载此方法可以控制Tip显示位置
	 * @param parent
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	protected void setStageLocation(Stage parent, int x, int y, int w, int h) 
	{
		int anchor = DEFAULT_ANCHOR;
//		if (x > parent.getWidth()/2) {
//			anchor &= 0xf0;
//			anchor |= ANCHOR_LEFT;
//		}
//		x += 32;
//		y += 32;
		if (y > parent.getHeight()/2) {
			anchor &= 0x0f;
			anchor |= ANCHOR_TOP;
		} else {
			y += 16;
		}
		anchorTo(parent, x, y, w, h, anchor);
	}
	
	protected void anchorTo(Stage parent, int x, int y, int w, int h, int anchor) {
		int sx = x;
		int sy = y;
		// horizontal
		{
			if ((anchor & ANCHOR_HCENTER) != 0) {
				sx = x + w/2 - getWidth()/2;
			}
			if ((anchor & ANCHOR_RIGHT) != 0) {
				sx = x + w + 1;
			}
			if ((anchor & ANCHOR_LEFT) != 0) {
				sx = x - getWidth() - 1;
			}
			
			if (sx + getWidth() > parent.getWidth()) {
				sx = x - getWidth() - 1;
			}
			if (sx < 0) {
				sx = x + w + 1;
			}
			if (sx < 0) {
				sx = 0;
			}
		}
		// vertical
		{
			if ((anchor & ANCHOR_VCENTER) != 0) {
				sy = y + h/2 - getHeight()/2;
			}
			if ((anchor & ANCHOR_BOTTON) != 0) {
				sy = y + h + 1;
			}
			if ((anchor & ANCHOR_TOP) != 0) {
				sy = y - getHeight() - 1;
			}
			
			if (sy + getHeight() > parent.getHeight()) {
				sy = parent.getHeight() - getHeight();
			}
			if (sy < 0) {
				sy = y + h + 1;
			}
			if (sy < 0) {
				sy = 0;
			}
		}
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
