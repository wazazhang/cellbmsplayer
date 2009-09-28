package com.g2d.display.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import com.g2d.Tools;
import com.g2d.Version;
import com.g2d.annotation.Property;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.util.Drawing;

public class Guage extends UIComponent 
{
	private static final long serialVersionUID = Version.VersionG2D;
	
	double 					max;
	double 					min;
	double 					value;
	
	@Property("文字颜色")
	public Color			textColor;
	@Property("is_show_text")
	public boolean 			is_show_text;
	@Property("is_show_percent")
	public boolean 			is_show_percent;
	
	transient public Image	strip_back;
	transient public Image	strip_fore;
	
	@Override
	protected void init_field() 
	{
		super.init_field();
		
		max 			= 100;
		min 			= -50;
		value 			= 50;
		
		textColor 		= Color.WHITE;
		is_show_text	= true;
		is_show_percent	= true;
	}
	
	@Override
	protected void init_transient()
	{
		super.init_transient();
		{
			strip_back = Tools.createImage(100, 10);
			Graphics2D g2d = (Graphics2D)strip_back.getGraphics();
			g2d.setColor(Color.RED);
			g2d.fillRect(0, 0, 100, 10);
		}
		{
			strip_fore = Tools.createImage(100, 10);
			Graphics2D g2d = (Graphics2D)strip_fore.getGraphics();
			g2d.setColor(Color.GREEN);
			g2d.fillRect(0, 0, 100, 10);
		}
	}
	
	public Guage() 
	{}
	
	public Guage(int w, int h)
	{
		setSize(w, h);
	}
	
	public double getMax() {
		return max;
	}
	
	public double getMin() {
		return min;
	}
	
	public double getValue() {
		return value;
	}
	
	public void setRange(double max, double min) {
		this.max = Math.max(max, min);
		this.min = Math.min(max, min);
		this.value = Math.max(value, min);
		this.value = Math.min(value, max);
	}
	
	public void setMax(double max) {
		this.max = max;
		this.value = Math.min(value, max);
	}
	
	public void setValue(double value) {
		value = Math.max(value, min);
		value = Math.min(value, max);
		this.value = value;
	}
	
	public int getPercent() {
		double sw = max - min;
		double sx = value - min;
		return (int)(sx / sw * 100);
	}
	
	public void render(Graphics2D g)
	{
		super.render(g);
		
		double sw = max - min;
		double sx = value - min;
		UILayout ui = this.getLayout();
		int x = ui.BorderSize;
		int y = ui.BorderSizeTop;
		int w = getWidth()- ui.BorderSize*2;
		int h = getHeight() - ui.BorderSizeTop- ui.BorderSizeBottom;
		
//		g.drawImage(strip_back, 0, 0, getWidth(), getHeight(), null);
		g.clipRect(x, y, (int)(w*sx/sw), h);
		g.drawImage(strip_fore, x, y, w, h, null);
		g.setClip(local_bounds);
		
		if (is_show_text) {
			g.setColor(textColor);
			String text;
			if (is_show_percent || min<0) {
				text = (int)(sx / sw * 100) + "%";
			} else {
				text = (int)value + "/"+ (int)max;
			}
			Drawing.drawString(g, text, 0, 0, getWidth(), getHeight(), 
					Drawing.TEXT_ANCHOR_HCENTER | Drawing.TEXT_ANCHOR_VCENTER
			);
		}
	}
	
}
