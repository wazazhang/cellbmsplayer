package com.g2d.studio.icon;

import java.util.Vector;

import javax.swing.JFrame;

import com.g2d.studio.Studio;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.res.Res;

public class IconManager extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	public IconManager(ProgressForm progress) 
	{
		super.setSize(800, Studio.getInstance().getHeight());
		super.setLocation(Studio.getInstance().getX()+Studio.getInstance().getWidth(), Studio.getInstance().getY());
		super.setTitle("图标管理器");
		super.setIconImage(Res.icon_edit);
	}
	
	public static Vector<IconFile> getItemIcons() {
		return null;
	}
	
}
