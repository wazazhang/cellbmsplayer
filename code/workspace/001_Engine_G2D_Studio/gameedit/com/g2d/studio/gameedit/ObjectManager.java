package com.g2d.studio.gameedit;

import javax.swing.JFrame;

import com.g2d.Tools;
import com.g2d.studio.Studio;
import com.g2d.studio.res.Res;
import com.g2d.util.AbstractFrame;

public class ObjectManager extends AbstractFrame
{
	private static final long serialVersionUID = 1L;
	
	public ObjectManager() 
	{
		super.setSize(800, 600);
		super.setLocation(Studio.getInstance().getX()+Studio.getInstance().getWidth(), Studio.getInstance().getY()+20);
		super.setTitle("物体编辑器");
		super.setIconImage(Res.icon_edit);
	}
	
}
