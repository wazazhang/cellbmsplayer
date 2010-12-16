package com.g2d.java2d.impl;


import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.CharacterIterator;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.HashMap;
import java.util.Map;

import com.g2d.Color;
import com.g2d.Font;
import com.g2d.Graphics2D;
import com.g2d.font.GraphicAttribute;
import com.g2d.font.TextAttribute;
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
	
	public static AttributedString decode(AttributedString atext) 
	{
		AttributedCharacterIterator	it = atext.getIterator();
		int b = 0, e = 0;
		
		for (char c = it.first(); c != CharacterIterator.DONE; c = it.next()) 
		{
			b = it.getIndex();
			e = b + 1;
			
			Number size = (Number) it.getAttribute(TextAttribute.SIZE);
			if (size != null) {
				atext.addAttribute(java.awt.font.TextAttribute.SIZE, size, b, e);
			}
			
			Font font = (Font) it.getAttribute(TextAttribute.FONT);
			if (font != null) {
				atext.addAttribute(java.awt.font.TextAttribute.FONT, ((AwtFont)(font)).getFont(), b, e);
			}
			
			Color fcolor = (Color) it.getAttribute(TextAttribute.FOREGROUND);
			if (fcolor != null) {
				atext.addAttribute(java.awt.font.TextAttribute.FOREGROUND, new java.awt.Color(fcolor.getARGB(), true), b, e);
			}
			
			Color bcolor = (Color) it.getAttribute(TextAttribute.BACKGROUND);
			if (bcolor != null) {
				atext.addAttribute(java.awt.font.TextAttribute.BACKGROUND, new java.awt.Color(bcolor.getARGB(), true), b, e);
			}
			
			Object bold = it.getAttribute(TextAttribute.BOLD);
			if (bold != null) {
				atext.addAttribute(java.awt.font.TextAttribute.WEIGHT, bold, b, e);
			}
			
			Object underline = it.getAttribute(TextAttribute.UNDERLINE);
			if (bold != null) {
				atext.addAttribute(java.awt.font.TextAttribute.UNDERLINE, underline, b, e);
			}

			GraphicAttribute rep = (GraphicAttribute) it.getAttribute(TextAttribute.REPLACEMENT);
			if (rep != null) {
				atext.addAttribute(java.awt.font.TextAttribute.CHAR_REPLACEMENT, new InnerReplaceMent(rep), b, e);
			}
			
			
		}
		return atext;
	}
	
	
	static class InnerReplaceMent extends java.awt.font.GraphicAttribute
	{
		GraphicAttribute ga;
		
		java.awt.Rectangle rect = new java.awt.Rectangle();
		
		public InnerReplaceMent(GraphicAttribute ga) {
			super(ga.getAlignment());
			rect.setBounds(ga.getBounds().x, ga.getBounds().y, ga.getBounds().width, ga.getBounds().height);
		}
		
		@Override
		public void draw(java.awt.Graphics2D graphics, float x, float y) {
			ga.draw(new AwtGraphics2D(graphics), x, y);
		}
		
	    public float getAscent() {
	        return 0;
	    }
	    public float getDescent() {
	        return rect.height;
	    }
	    public float getAdvance() {
	        return rect.width;
	    }
	    public java.awt.geom.Rectangle2D getBounds() {
			return rect;
	    }
	}
	
	
}
