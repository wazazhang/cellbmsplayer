package com.g2d.studio.sound;

import com.g2d.studio.Studio;
import com.g2d.studio.res.Res;
import com.g2d.util.AbstractFrame;

public class SoundManager extends AbstractFrame
{
	private static final long serialVersionUID = 1L;
	
	public SoundManager() 
	{
		super.setSize(800, 600);
		super.setLocation(Studio.getInstance().getX()+Studio.getInstance().getWidth(), Studio.getInstance().getY()+40);
		super.setTitle("声音编辑器");
		super.setIconImage(Res.icon_edit);
	}
	
}
