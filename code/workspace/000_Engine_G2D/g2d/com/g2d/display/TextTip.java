package com.g2d.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

import com.g2d.Tools;
import com.g2d.Version;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.display.ui.text.MultiTextLayout;

public class TextTip extends Tip 
{
	public static int 			DefaultTipSize = 250;
	public static UILayout		DefaultLayout;
//	---------------------------------------------------------------------------------------------------------------
	
	public UILayout				layout;
	public int 					text_width;

	public Color 				textColor;
	public Color				backColor;
	
	transient MultiTextLayout	text;
	transient BufferedImage		buffer;
	transient Graphics2D		buffer_g;
	
	@Override
	protected void init_field() {
		super.init_field();
		textColor 	= new Color(0xffffffff, true);
		backColor	= new Color(0x60000000, true);
	}
	
	@Override
	protected void init_transient() {
		super.init_transient();
		this.text = new MultiTextLayout();
		this.text.is_read_only = true;
		this.text.is_show_caret = false;
		
		this.layout		= DefaultLayout;
		this.text_width	= DefaultTipSize;
		
		buffer = Tools.createImage(1, 1);
		buffer_g = buffer.createGraphics();
	}
	
	public TextTip() {
		this("");
	}
	
	public TextTip(String text){
		setText(text);
	}
	
	final public void setText(String text) {
		this.text.setText(text);
	}
	
	final public void setText(AttributedString atext) {
		this.text.setText(atext);
	}

	public void update() 
	{
		int bw = 4;
		if (layout!=null){
			bw = layout.BorderSize;
		}
		text.setWidth(text_width - (bw<<1));
		
		Dimension draw_size		= text.drawText(buffer_g, 0, 0);
		draw_size.height		= text.getHeight();
		this.setSize(draw_size.width+(bw<<1), draw_size.height+(bw<<1));
	}
	
	public void render(Graphics2D g) 
	{
		int bw = 4;
		if (layout!=null){
			bw = layout.BorderSize;
			layout.render(g, 0, 0, getWidth(), getHeight());
		}else{
			g.setColor(backColor);
			g.fill(local_bounds);
		}
		g.setColor(textColor);
		text.drawText(g, bw, bw);
	}

	public void added(DisplayObjectContainer parent) {}

	public void removed(DisplayObjectContainer parent) {}


}
