package com.g2d.display;

import java.awt.Color;
import java.awt.Graphics2D;
import java.text.AttributedString;

import com.g2d.Version;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.display.ui.text.MultiTextLayout;

public class Tip extends DisplayObject 
{
	private static final long serialVersionUID = Version.VersionG2D;
	
	public static int 			DefaultTipSize = 250;
	public static UILayout		DefaultLayout;
	
	public Color 				textColor;
	public Color				backColor;
	
	transient MultiTextLayout	text;
	transient boolean	paint;
//	transient String 			next_tip;
//	transient AttributedString 	next_atip;
	
	@Override
	protected void init_field() {
		super.init_field();
		textColor 	= new Color(0xffffffff, true);
		backColor	= new Color(0x60000000, true);
	}
	
	@Override
	protected void init_transient() {
		super.init_transient();
		text = new MultiTextLayout();
		this.text.is_read_only = true;
		this.text.is_show_caret = false;
	}
	
	public Tip() {
		this("");
	}
	
	public Tip(String text){
		if (text!=null && text.length()>0) {
			this.text.setText(text);
			paint = true;
//			next_tip = text;
//			next_atip = null;
		}else{
			this.text.setText("");
			paint = false;
		}
	}
	
	public void clearText() {
		text.setText("");
		paint = false;
//		next_atip	= null;
//		next_tip	= null;
	}
	
	public void setText(String text) {
		if (text!=null && text.length()>0) {
			this.text.setText(text);
			paint = true;
//			next_tip = text;
//			next_atip = null;
		}else{
			this.text.setText("");
			paint = false;
		}
	}
	
	public void setText(AttributedString text) {
		if (text!=null && !text.equals("")) {
			this.text.setText(text);
			paint = true;
//			next_atip = text;
//			next_tip = null;
		}else{
			this.text.setText("");
			paint = false;
		}

	}
	
	public void update() {}
	
	public void render(Graphics2D g) 
	{
//		if (next_tip!=null && !"".equals(next_tip)) {
//			text.setText(next_tip);
//			paint = true;
//		}
//		else if (next_atip!=null) {
//			text.setText(next_atip);
//			paint = true;
//		}
		
		if (paint)
		{
			text.setWidth(DefaultTipSize);
			
			int x = 0;
			int y = 0;
			
			if (x==0||y==0){
				setLocation(20,20);
			}
			if (getParent().getX()+this.getX()+this.getWidth()>getRoot().getWidth()){
				x = getRoot().getWidth()-(getParent().getX()+this.getX()+this.getWidth());
			}
			
			if (getParent().getY()+this.getY()+this.getHeight()>getRoot().getHeight()){
				y = getRoot().getHeight()-(getParent().getY()+this.getY()+this.getHeight());
			}
//			System.out.println("getRoot().getWidth() = " + getRoot().getWidth()+", getParent().getX() = "+ getParent().getX());
//			System.out.println("getRoot().getHeight() = " + getRoot().getHeight()+", getParent().getY() = "+ getParent().getY());
//			System.out.println("getX = "+ getX()+ ", getY = "+ getY());
			setLocation(getX()+x, getY()+y);
//			System.out.println("getX = "+ getX()+ ", getY = "+ getY());
			
			int twidth = 0;
			if (DefaultLayout!=null){
				DefaultLayout.render(g, 0, 0, getWidth(), getHeight());
				int bw = DefaultLayout.BorderSize;
				g.setColor(textColor);
				twidth = text.drawText(g, bw, bw).width + (bw<<1);
				this.setSize(twidth, text.getHeight() + (bw<<1));
			}else{
				g.setColor(backColor);
				g.fill(local_bounds);
				g.setColor(textColor);
				twidth = text.drawText(g, 4, 4).width + 8;
				this.setSize(twidth, text.getHeight() + 8);
			}
		}
		else
		{
			this.setSize(0, 0);
		}
	}

	public void added(DisplayObjectContainer parent) {}

	public void removed(DisplayObjectContainer parent) {}


}
