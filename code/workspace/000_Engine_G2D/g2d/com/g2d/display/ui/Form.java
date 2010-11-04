package com.g2d.display.ui;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.Serializable;

import com.g2d.Version;
import com.g2d.annotation.Property;
import com.g2d.display.DisplayObject;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.event.MouseEvent;
import com.g2d.editor.FormEditor;
import com.g2d.editor.UIComponentEditor;
import com.g2d.util.Drawing;


public class Form extends Window
{
	private static final long serialVersionUID = Version.VersionG2D;
	
//	--------------------------------------------------------------------------------------------------------------------------

	protected ButtonClose btn_close = new ButtonClose();
	public int close_border_size = 4;

//	@Property("title")
//	public String title;

//	@Property("title_y")
//	public int title_y = 4;
	
//	--------------------------------------------------------------------------------------------------------------------------

	public Form()
	{
		addChild(btn_close);
	}

	
//	public void setTitle(String title) {
//		this.title = title;
//	}
	
	public void setCloseEnable(boolean enable) {
		btn_close.visible = enable;
		btn_close.enable = enable;
	}
	
	public ButtonClose getCloseButton() {
		return btn_close;
	}
	
	public boolean getCloseEnable() {
		return this.contains(btn_close);
	}
	
	@Override
	public UIComponentEditor<?> createEditorForm() {
		return new FormEditor(this);
	}
	
	/**
	 * @return true if cancel close
	 */
	protected boolean onCloseButtonClick() {return false;}
	
//	--------------------------------------------------------------------------------------------------------------------------

	@Override
	public void render(Graphics2D g)
	{
		super.render(g);
//		drawTitle(g);
	}
	
//	protected void drawTitle(Graphics2D g) {
//		if (title!=null) {
//			g.setColor(Color.WHITE);
//			Drawing.drawStringShadow(g, title, getWidth()/2, title_y,
//					Drawing.TEXT_ANCHOR_HCENTER | Drawing.TEXT_ANCHOR_TOP);
//		}
//	}
	
//	--------------------------------------------------------------------------------------------------------------------------
	

	public class ButtonClose extends Button 
	{
		private static final long serialVersionUID = Version.VersionG2D;
		
		public ButtonClose() {
			super("");
			this.setSize(17, 17);
		}
		protected void onMouseClick(MouseEvent event) {
			super.onMouseClick(event);
			if (event.mouseButton == MouseEvent.BUTTON_LEFT) {
				if (!onCloseButtonClick()){
					Form.this.close();
				}
			}
		}
		public void update() {
			super.update();
			this.setLocation(
					Form.this.getWidth()-getWidth()-close_border_size, 
					close_border_size
					);
		}
	}
}
