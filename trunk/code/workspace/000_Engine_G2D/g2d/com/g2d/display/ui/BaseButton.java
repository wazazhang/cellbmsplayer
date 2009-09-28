package com.g2d.display.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Image;

import com.g2d.Version;
import com.g2d.annotation.Property;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.util.Drawing;


public abstract class BaseButton extends UIComponent 
{
	private static final long serialVersionUID	= Version.VersionG2D;
	
	transient public UILayout	layout_down;
	transient public UILayout	layout_up;
	/** 当鼠标放置在该控件上的效果 */
	transient public Image		mouse_catched_mask;
	
	@Property("自定义按下造型")
	public UILayout				custom_layout_down;
	@Property("自定义正常造型")
	public UILayout				custom_layout_up;
	
	@Override
	protected void init_transient() 
	{
		super.init_transient();
//		cursor 		= Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
		layout_down	= UILayout.createBlankRect();
		layout_up	= UILayout.createBlankRect();
	}
	
	public BaseButton(int width, int height) {
		setSize(width, height);
	}
	
	public BaseButton() {
		setSize(20, 20);
	}
	
	
	@Override
	public void setLayout(UILayout layout) {
		super.setLayout(layout);
		layout_down = layout;
		layout_up = layout;
	}
	
	public void setLayout(UILayout up, UILayout down) {
		super.setLayout(up);
		layout_down = down;
		layout_up = up;
	}
	
	@Override
	public void setCustomLayout(UILayout layout) {
		super.setCustomLayout(layout);
		custom_layout_down = layout;
		custom_layout_up = layout;
	}
	public void setCustomLayout(UILayout up, UILayout down) {
		super.setCustomLayout(up);
		custom_layout_down = down;
		custom_layout_up = up;
	}
	
	public void render(Graphics2D g) 
	{
		if (isOnDragged()) {
			if (custom_layout_down!=null){
				custom_layout = custom_layout_down;
			}else{
				layout = layout_down;
			}
		}else{
			if (custom_layout_up!=null){
				custom_layout = custom_layout_up;
			}else{
				layout = layout_up;
			}
		}
		
		super.render(g);
	}
	

	
	
	protected void onDrawMouseHover(Graphics2D g) {
		if(mouse_catched_mask==null){
			g.setColor(new Color(1,1,1,0.2f));
			g.fill(local_bounds);
		}else{
			g.drawImage(mouse_catched_mask, 0, 0, getWidth(), getHeight(), this);
		}
	}
	
}
