package com.g2d.java2d.impl;

import com.g2d.Font;
import com.g2d.Graphics2D;
import com.g2d.geom.Rectangle;

public class AwtFont implements com.g2d.Font
{
	final private java.awt.Font font;
	
	public AwtFont(java.awt.Font font) {
		this.font = font;
	}
	
	public java.awt.Font getFont() {
		return font;
	}
	@Override
	public int getSize() {
		return font.getSize();
	}
	@Override
	public String getName() {
		return font.getName();
	}
	
	@Override
	public Rectangle getStringBounds(String src, Graphics2D g) {
		java.awt.Graphics2D g2d = ((AwtGraphics2D)g).g2d;
		java.awt.geom.Rectangle2D rect = font.getStringBounds(src, g2d.getFontRenderContext());
		return new Rectangle(0, 0, (int)rect.getWidth(), (int)rect.getHeight());
	}
	
	@Override
	public Font newInstance(int style, int size) {
		return new AwtFont(new java.awt.Font(font.getName(), style, size));
	}
	
	@Override
	public Font newSize(int size) {
		return new AwtFont(new java.awt.Font(font.getName(), font.getStyle(), size));
	}
	
	@Override
	public Font newStyle(int style) {
		return new AwtFont(new java.awt.Font(font.getName(), style, font.getSize()));
	}
	
	
}
