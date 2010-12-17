package com.g2d.util;


import com.g2d.Color;
import com.g2d.Graphics2D;
import com.g2d.Image;
import com.g2d.Paint;
import com.g2d.geom.Rectangle;
import com.g2d.geom.Shape;
import com.g2d.text.MultiTextLayout;

public class Drawing 
{
	final static public void fillPaintRect(Graphics2D g, Paint paint, int x, int y, int width, int height) 
	{
		g.pushPaint();
		g.translate(x, y);
		try {
			g.setPaint(paint);
			g.fillRect(0, 0, width, height);
		} finally {
			g.translate(-x, -y);
			g.popPaint();
		}
	}	
	
	final static public void fillPaint(Graphics2D g, Paint paint, int x, int y, Shape shape) 
	{
		g.pushPaint();
		g.translate(x, y);
		try {
			g.setPaint(paint);
			g.fill(shape);
		} finally {
			g.translate(-x, -y);
			g.popPaint();
		}
	}
	
	final static public void drawRoundImage(Graphics2D g, Image src, int x, int y, int width, int height)
	{
		g.pushClip();
		
		g.clipRect(x , y , width, height);
		
		int w = src.getWidth();
		int h = src.getHeight();
		
		for (int dx = 0; dx < width;) {
			for (int dy = 0; dy < height;) {
				g.drawImage(src, x + dx, y + dy);
				dy += h;
			}
			dx += w;
		}
		
		g.popClip();
	}
	
	final static public void drawRoundImageH(Graphics2D g, Image src, int x, int y, int width, int height)
	{
		g.pushClip();
		
		g.clipRect(x , y , width, height);
		
		int w = src.getWidth();
		
		for(int dx=0;dx<width;){
			g.drawImage(src, x+dx, y, w, height);
			dx += w;
		}
		
		g.popClip();
	}
	
	final static public void drawRoundImageV(Graphics2D g, Image src, int x, int y, int width, int height)
	{
		g.pushClip();
		
		g.clipRect(x , y , width, height);
		
		int h = src.getHeight();
		
		for(int dy=0;dy<height;){
			g.drawImage(src, x, y+dy, width, h);
			dy += h;
		}
		
		g.popClip();
	}
	
	
	final static public int TEXT_ANCHOR_LEFT 	= 0x00;
	final static public int TEXT_ANCHOR_RIGHT 	= 0x01;
	final static public int TEXT_ANCHOR_HCENTER = 0x02;
	final static public int TEXT_ANCHOR_TOP 	= 0x00;
	final static public int TEXT_ANCHOR_BOTTON 	= 0x10;
	final static public int TEXT_ANCHOR_VCENTER = 0x20;
	
	final static public Rectangle drawString(Graphics2D g, String src, int x, int y)
	{
		Rectangle rect = g.getFont().getStringBounds(src, g);
		rect.setBounds(0, 0, rect.width, rect.height);
		g.drawString(src, x, y);
		return rect;
	}
	
	final static public Rectangle drawString(Graphics2D g, String src, int x, int y, int anchor)
	{
		Rectangle rect = g.getFont().getStringBounds(src, g);
		rect.setBounds(0, 0, rect.width, rect.height);

		if ((anchor & TEXT_ANCHOR_RIGHT) != 0) {
			x -= rect.width;
		} else if ((anchor & TEXT_ANCHOR_HCENTER) != 0) {
			x -= (rect.width >> 1);
		}

		if ((anchor & TEXT_ANCHOR_BOTTON) != 0) {
			y -= rect.height;
		} else if ((anchor & TEXT_ANCHOR_VCENTER) != 0) {
			y -= (rect.height >> 1);
		}
		
		g.drawString(src, x, y);
		
		return rect;
	}
	
	final static public Rectangle drawString(Graphics2D g, String src, int x, int y, int w, int h, int anchor)
	{
		Rectangle rect = g.getFont().getStringBounds(src, g);
		rect.setBounds(0, 0, rect.width, rect.height);
		
		if ((anchor & TEXT_ANCHOR_RIGHT) != 0) {
			x += w - rect.width;
		} else if ((anchor & TEXT_ANCHOR_HCENTER) != 0) {
			x += ((w - rect.width) >> 1);
		}

		if ((anchor & TEXT_ANCHOR_BOTTON) != 0) {
			y += h - rect.height;
		} else if ((anchor & TEXT_ANCHOR_VCENTER) != 0) {
			y += ((h - rect.height) >> 1);
		}
		
		g.drawString(src, x, y);
		
		return rect;
	}
	
	final static public Rectangle drawStringBorder(Graphics2D g, String src, int x, int y, int w, int h, int anchor)
	{
		return drawStringBorder(g, src, x, y, w, h, anchor, Color.BLACK);
	}
	
	final static public Rectangle drawStringBorder(Graphics2D g, String src, int x, int y, int w, int h, int anchor, Color back_color)
	{
		Color c = g.getColor();
		{
			g.setColor(back_color);
			drawString(g, src, x-1, y-1, w, h, anchor);
			drawString(g, src, x-1, y-0, w, h, anchor);
			drawString(g, src, x-1, y+1, w, h, anchor);
			drawString(g, src, x-0, y-1, w, h, anchor);
			drawString(g, src, x-0, y+1, w, h, anchor);
			drawString(g, src, x+1, y-1, w, h, anchor);
			drawString(g, src, x+1, y-0, w, h, anchor);
			drawString(g, src, x+1, y+1, w, h, anchor);
		}
		g.setColor(c);
		return drawString(g, src, x, y, w, h, anchor);
	}
	
	final static public Rectangle drawStringShadow(Graphics2D g, String src, int x, int y, int w, int h, int anchor)
	{
		return drawStringShadow(g, src, x, y, w, h, anchor, Color.BLACK);
	}
	
	final static public Rectangle drawStringShadow(Graphics2D g, String src, int x, int y, int w, int h, int anchor, Color back_color)
	{
		Color c = g.getColor();
		
		{
			g.setColor(back_color);
			drawString(g, src, x-0, y+1, w, h, anchor);
			drawString(g, src, x+1, y+1, w, h, anchor);
		}
		
		g.setColor(c);
		return drawString(g, src, x, y, w, h, anchor);
	}
	
	final static public Rectangle drawStringBorder(Graphics2D g, String src, int x, int y, int anchor)
	{
		return drawStringBorder(g, src, x, y, anchor, Color.BLACK);
	}
	
	final static public Rectangle drawStringBorder(Graphics2D g, String src, int x, int y, int anchor, Color back_color)
	{
		Color c = g.getColor();
		{
			g.setColor(back_color);
			drawString(g, src, x-1, y-1, anchor);
			drawString(g, src, x-1, y-0, anchor);
			drawString(g, src, x-1, y+1, anchor);
			drawString(g, src, x-0, y-1, anchor);
			drawString(g, src, x-0, y+1, anchor);
			drawString(g, src, x+1, y-1, anchor);
			drawString(g, src, x+1, y-0, anchor);
			drawString(g, src, x+1, y+1, anchor);
		}
		g.setColor(c);
		return drawString(g, src, x, y, anchor);
	}
	
	final static public Rectangle drawStringShadow(Graphics2D g, String src, int x, int y, int anchor)
	{
		return drawStringShadow(g, src, x, y, anchor, Color.BLACK);
	}
	
	final static public Rectangle drawStringShadow(Graphics2D g, String src, int x, int y, int anchor, Color back_color)
	{
		Color c = g.getColor();
		{
			g.setColor(back_color);
			drawString(g, src, x-0, y+1, anchor);
			drawString(g, src, x+1, y+1, anchor);
		}
		g.setColor(c);
		return drawString(g, src, x, y, anchor);
	}
	
	
	final static public void drawString(Graphics2D g, MultiTextLayout src, int x, int y, int anchor)
	{
		if ((anchor & TEXT_ANCHOR_RIGHT) != 0) {
			x -= src.getWidth();
		} else if ((anchor & TEXT_ANCHOR_HCENTER) != 0) {
			x -= (src.getWidth() >> 1);
		}

		if ((anchor & TEXT_ANCHOR_BOTTON) != 0) {
			y -= src.getHeight();
		} else if ((anchor & TEXT_ANCHOR_VCENTER) != 0) {
			y -= (src.getHeight() >> 1);
		}
		
		src.drawText(g, x, y);
		
	}
	
	final static public void drawString(Graphics2D g, MultiTextLayout src, int x, int y, int w, int h, int anchor)
	{
		if ((anchor & TEXT_ANCHOR_RIGHT) != 0) {
			x += w - src.getWidth();
		} else if ((anchor & TEXT_ANCHOR_HCENTER) != 0) {
			x += ((w - src.getWidth()) >> 1);
		}

		if ((anchor & TEXT_ANCHOR_BOTTON) != 0) {
			y += h - src.getHeight();
		} else if ((anchor & TEXT_ANCHOR_VCENTER) != 0) {
			y += ((h - src.getHeight()) >> 1);
		}
		
		src.drawText(g, x, y, 0, 0, w, h);
	}
	
	final static public void drawStringShwdow(Graphics2D g, MultiTextLayout src, int x, int y, int w, int h, int anchor)
	{
		if ((anchor & TEXT_ANCHOR_RIGHT) != 0) {
			x += w - src.getWidth();
		} else if ((anchor & TEXT_ANCHOR_HCENTER) != 0) {
			x += ((w - src.getWidth()) >> 1);
		}

		if ((anchor & TEXT_ANCHOR_BOTTON) != 0) {
			y += h - src.getHeight();
		} else if ((anchor & TEXT_ANCHOR_VCENTER) != 0) {
			y += ((h - src.getHeight()) >> 1);
		}

		src.drawText(g, x, y, 0, 0, w, h, 1, 1, 1f, 0);
	}
}
