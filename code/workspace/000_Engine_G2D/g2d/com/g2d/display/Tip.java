package com.g2d.display;


public abstract class Tip extends DisplayObject 
{	
	public static int tip_offset_x 		= 20;
	public static int tip_offset_y 		= 20;
	public static int tip_opposite_x 	= -4;
	public static int tip_opposite_y 	= -4;
	
	final public void setLocation(DisplayObjectContainer parent, int x, int y) {
		
		if ((x + getWidth() + tip_offset_x) > parent.getWidth()) {
			x = x - getWidth() + tip_opposite_x;
		} else {
			x = x + tip_offset_x;
		}
		if ((y + getHeight() + tip_offset_y) > parent.getHeight()) {
			y = y - getHeight() + tip_opposite_y;
		} else {
			y = y + tip_offset_y;
		}
		if (x < 0) x = 0;
		if (y < 0) y = 0;
		super.setLocation(x, y);
	}
	
}
