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
	
	boolean is_checked = false;
	
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

		transient public UILayout	focuse_layout_down	= UILayout.createBlankRect();
		transient public UILayout	focuse_layout_up	= UILayout.createBlankRect();
		
		public ImageToggleButton(Image checked, Image unchecked)
		{
			custom_layout_down = new UILayout();
			custom_layout_down.setImages(checked, UILayout.ImageStyle.IMAGE_STYLE_BACK_4, 0);
			
			custom_layout_up = new UILayout();
			custom_layout_up.setImages(unchecked, UILayout.ImageStyle.IMAGE_STYLE_BACK_4, 0);
		}
		
		public ImageToggleButton(Image checked, Image unchecked, Image focuse_checked, Image focuse_unchecked)
		{
			this(focuse_checked, focuse_unchecked);
			
			focuse_layout_down = new UILayout();
			focuse_layout_down.setImages(focuse_checked, UILayout.ImageStyle.IMAGE_STYLE_BACK_4, 0);
			
			focuse_layout_up = new UILayout();
			focuse_layout_up.setImages(focuse_unchecked, UILayout.ImageStyle.IMAGE_STYLE_BACK_4, 0);
		}
		
		public void render(Graphics2D g) 
		{
			if (isCatchedMouse() && focuse_layout_down != null && focuse_layout_up != null) {
				if (is_checked) {
					custom_layout = focuse_layout_down;
				} else {
					custom_layout = focuse_layout_up;
				}
			} else {
				if (is_checked) {
					custom_layout = custom_layout_down;
				} else {
					custom_layout = custom_layout_up;
				}
			}
			
			renderLayout(g);
		}
	}
}
