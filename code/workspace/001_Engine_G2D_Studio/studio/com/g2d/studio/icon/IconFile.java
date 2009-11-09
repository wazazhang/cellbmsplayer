package com.g2d.studio.icon;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import com.cell.util.MarkedHashtable;
import com.g2d.Tools;
import com.g2d.studio.Studio;
import com.g2d.studio.cpj.CPJIndex;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.cpj.entity.CPJSprite;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DListItem;

public class IconFile implements G2DListItem
{
	final public String 		icon_file_name;
	
	transient 
	final public BufferedImage	image;
	
	transient 
	final protected ImageIcon 	icon;	
	
	
	IconFile(String name, BufferedImage	image) {
		this.icon_file_name = name;
		this.image			= image;
		this.icon			= Tools.createIcon(image);
		System.out.println("create a icon file : " + name);
	}
	
	@Override
	public ImageIcon getIcon(boolean update) {
		return icon;
	}
	
	@Override
	public String getName() {
		return icon_file_name;
	}
}
