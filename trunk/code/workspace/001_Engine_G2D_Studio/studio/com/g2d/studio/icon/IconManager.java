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
import com.g2d.studio.ManagerForm;
import com.g2d.studio.Studio;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DList;
import com.g2d.util.AbstractFrame;

public class IconManager extends ManagerForm
{
	private static final long serialVersionUID = 1L;
	
	Vector<IconFile> icon_files = new Vector<IconFile>();
	
	public IconManager(Studio studio, ProgressForm progress) 
	{
		super(studio, progress, "图标管理器");
		{
			File icon_dir 	= Studio.getInstance().root_icon_path;	
			File files[]	= icon_dir.listFiles();
			progress.setMaximum("", files.length);
			for (int i=0;i<files.length; i++) {
				File file = files[i];
				if (file.getName().endsWith(Config.ICON_SUFFIX)) {
					try{
						BufferedImage img = Tools.readImage(file.getPath());
						if (img!=null) {
							IconFile icon = new IconFile(
									file.getName().substring(0, file.getName().length() - Config.ICON_SUFFIX.length()), 
									img);
							icon_files.add(icon);
						}
					}catch(Exception err){}
				}					
				progress.setValue("", i);
			}
		}
		
		IconList icons = new IconList(getIcons());		
		icons.setVisibleRowCount(icon_files.size()/10+1);
		icons.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		this.add(new JScrollPane(icons));
		
		 saveAll();
	}
	
	public Vector<IconFile> getIcons() {
		return icon_files;
	}
	
	public IconFile getIcon(String icon_name) {
		for (IconFile icon : icon_files) {
			if (icon.icon_file_name.equals(icon_name)) {
				return icon;
			}
		}
		return null;
	}
	
	public void saveAll() 
	{
		File save_dir = new File(Studio.getInstance().project_save_path.getPath() + File.separatorChar +"icons");
		save_dir.mkdirs();
		StringBuffer sb = new StringBuffer();
		for (IconFile icon : icon_files) {
			sb.append(icon.getListName()+","+icon.image.getWidth()+","+icon.image.getHeight()+"\n");
		}
		File save_file = new File(save_dir, "icon.list");
		com.cell.io.CFile.writeText(save_file, sb.toString(), "UTF-8");
	}
}
