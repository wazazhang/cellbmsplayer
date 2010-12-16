package com.g2d;

import com.g2d.geom.Rectangle;

public interface Font
{
	public String getName() ;
	
	public int getSize();
	
	public Rectangle getStringBounds(String src, Graphics2D g2d);
	
	public Font newSize(int size);
	
	public Font newStyle(int style);
	
	public Font newInstance(int style, int size);
}
