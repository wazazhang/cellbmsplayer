package com.g2d.display.ui;

import java.awt.Graphics2D;
import java.awt.Image;

import com.g2d.Tools;
import com.g2d.Version;

public class ImageBox extends UIComponent
{
	private static final long serialVersionUID = Version.VersionG2D;
	
	
	transient public Image	background;
	
	String 					image_path;
	
	public ImageBox(){}
	
	public ImageBox(String path)
	{
		setImage(path);
	}
	
	public ImageBox(Image img) {
		setImage(img);
		setSize(img.getWidth(null), img.getHeight(null));
	}

	@Override
	protected void init_transient() 
	{
		super.init_transient();
		setImage(image_path);
	}
	
	public void setImage(Image img) {
		background = img;
	}
	
	public void setImage(String path) {
		image_path = path;
		if (image_path!=null) {
			try {
				background = Tools.readImage(image_path);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void render(Graphics2D g)
	{
		super.render(g);
		if (background!=null) {
			g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
		}
		
	}
	
}
