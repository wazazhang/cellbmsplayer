package com.g2d.display.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import com.g2d.Version;
import com.g2d.annotation.Property;
import com.g2d.display.event.MouseEvent;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.util.Drawing;


public abstract class ToggleButton extends BaseButton 
{
	private static final long serialVersionUID	= Version.VersionG2D;
	
	private boolean is_checked = false;
	
	public ToggleButton() {}
	
	public boolean isChecked() {
		return this.is_checked;
	}
	
	public void setChecked(boolean checked) {
		this.is_checked = checked;
	}
	
	@Override
	protected void onMouseClick(MouseEvent event) {
		super.onMouseClick(event);
		this.setChecked(!is_checked);
	}
	
	public void render(Graphics2D g) 
	{
		if (is_checked) {
			if (custom_layout_down != null) {
				custom_layout = custom_layout_down;
			} else {
				layout = layout_down;
			}
		} else {
			if (custom_layout_up != null) {
				custom_layout = custom_layout_up;
			} else {
				layout = layout_up;
			}
		}
		
		renderLayout(g);
	}
	
	
	public static class ImageToggleButton extends ToggleButton
	{
		public ImageToggleButton(Image checked, Image unchecked)
		{
			custom_layout_down = new UILayout();
			custom_layout_down.setImages(checked, UILayout.ImageStyle.IMAGE_STYLE_BACK_4, 0);
			
			custom_layout_up = new UILayout();
			custom_layout_up.setImages(unchecked, UILayout.ImageStyle.IMAGE_STYLE_BACK_4, 0);
		}
	}
}
