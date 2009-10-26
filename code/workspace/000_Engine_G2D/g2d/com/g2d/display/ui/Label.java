package com.g2d.display.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import com.g2d.Version;
import com.g2d.annotation.Property;
import com.g2d.util.Drawing;

public class Label extends UIComponent
{
	private static final long serialVersionUID = Version.VersionG2D;
	
	@Property("文字颜色")
	public Color	textColor;
	
	@Property("text")
	public String	text;
	
	@Property("text_anchor")
	public int		text_anchor;

	/**文字是否抗锯齿*/
	@Property("文字是否抗锯齿")
	public boolean	enable_antialiasing	 = false;

	@Override
	protected void init_field() 
	{
		super.init_field();
		
		textColor	= Color.WHITE;
		text		= "";
		text_anchor	= Drawing.TEXT_ANCHOR_HCENTER | Drawing.TEXT_ANCHOR_VCENTER ;
	}
	public Label(int w, int h) 
	{
		this();
		setSize(w, h);
	}
	
	public Label() 
	{
		this("Label");
		this.enable_focus = false;
	}
	
	public Label(String text)
	{
		this.text = text;
		this.enable_focus = false;
	}
	
	public Label(String text, int width, int height) 
	{
		this.text = text;
		this.setSize(width, height);
		this.enable_focus = false;
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public void render(Graphics2D g) 
	{
		super.render(g);
		
		g.setColor(textColor);
		if (enable_antialiasing) {
			Object v = g.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			Drawing.drawStringBorder(g, text, 0, 0, getWidth(), getHeight(), text_anchor);
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, v);
		} else {
			Drawing.drawStringBorder(g, text, 0, 0, getWidth(), getHeight(), text_anchor);
		}
		
		
	}
	
}
