package com.g2d.studio;

import java.awt.Image;

import javax.swing.JFrame;

import com.g2d.studio.Studio.ProgressForm;
import com.g2d.util.AbstractFrame;

public abstract class ManagerForm extends JFrame
{
	private static final long serialVersionUID = 1L;

	final public Studio studio;
	
	public ManagerForm(Studio studio, ProgressForm progress, String title, Image icon) 
	{
		super.setSize(AbstractFrame.getScreenWidth()-Studio.getInstance().getWidth(), Studio.getInstance().getHeight());
		super.setLocation(Studio.getInstance().getX()+Studio.getInstance().getWidth(), Studio.getInstance().getY());
		super.setIconImage(icon);		
		super.setTitle(title);
		this.studio = studio;
		progress.startReadBlock("初始化" + title + "...");
	}
	
	abstract public void saveAll() throws Throwable;
}
