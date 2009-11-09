package com.g2d.studio.icon;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import com.g2d.Tools;
import com.g2d.studio.Config;
import com.g2d.studio.Studio;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DList;

public class IconManager extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	G2DList<IconFile> icons;
	
	public IconManager(ProgressForm progress) 
	{
		super.setSize(800, Studio.getInstance().getHeight());
		super.setLocation(Studio.getInstance().getX()+Studio.getInstance().getWidth(), Studio.getInstance().getY());
		super.setTitle("图标管理器");
		super.setIconImage(Res.icon_edit);
		
		icons = new G2DList<IconFile>(getIcons());
		icons.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		this.add(new JScrollPane(icons));
	}
	
	public static Vector<IconFile> getIcons() {
		Vector<IconFile> icons = new Vector<IconFile>();
		File icon_dir = Studio.getInstance().getFile(Config.ICON_ROOT);
		for (File file : icon_dir.listFiles()) {
			if (file.getName().endsWith(Config.ICON_SUFFIX)) {
				try{
					BufferedImage img = Tools.readImage(file.getPath());
					if (img!=null) {
						IconFile icon = new IconFile(file.getName().substring(0, file.getName().length() - Config.ICON_SUFFIX.length()), img);
						icons.add(icon);
					}
				}catch(Exception err){}
			}
		}
		return icons;
	}
	
}
