package com.g2d.studio;

import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.res.Res;
import com.g2d.util.AbstractFrame;

public class ManagerForm extends JFrame
{
	private static final long serialVersionUID = 1L;

	final public Studio studio;
	
	public ManagerForm(Studio studio, ProgressForm progress, String title) 
	{
		super.setSize(AbstractFrame.getScreenWidth()-Studio.getInstance().getWidth(), Studio.getInstance().getHeight());
		super.setLocation(Studio.getInstance().getX()+Studio.getInstance().getWidth(), Studio.getInstance().getY());
		super.setIconImage(Res.icon_edit);		
		super.setTitle(title);
		this.studio = studio;
	}
}
