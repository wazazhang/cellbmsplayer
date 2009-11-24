package com.g2d.studio.icon;

import java.awt.Component;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JList;

import com.g2d.Tools;
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
//		System.out.println("create a icon file : " + name);
	}
	
	@Override
	public ImageIcon getListIcon(boolean update) {
		return icon;
	}
	
	@Override
	public String getListName() {
		return icon_file_name;
	}
	@Override
	public Component getListComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		return null;
	}
}
