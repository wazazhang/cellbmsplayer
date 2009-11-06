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

public class IconFile 
{
	final public String 			icon_file_name;
	transient public BufferedImage	image;
	public IconFile(String name) {
		this.icon_file_name = name;
	}
	
	
}
