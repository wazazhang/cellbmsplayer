package com.g2d.studio.sound;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import com.cell.sound.ISound;
import com.cell.sound.SoundInfo;
import com.cell.sound.util.SoundPlayer;
import com.cell.sound.util.StaticSoundPlayer;
import com.g2d.Tools;
import com.g2d.studio.Config;
import com.g2d.studio.Studio;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DListItem;

public class SoundFile implements G2DListItem
{
	final static ImageIcon 		icon = Tools.createIcon(Res.icons_bar[3]);	
	
	final public String 		sound_file_name;
	
	SoundFile(String name) 
	{
		this.sound_file_name 	= name;
		System.out.println("create a sound file : " + name);
	}
	
	@Override
	public ImageIcon getIcon(boolean update) {
		return icon;
	}
	
	@Override
	public String getName() {
		return sound_file_name;
	}

	
	public StaticSoundPlayer createSoundPlayer()
	{
		String file = Studio.getInstance().root_sound_path + "/" + sound_file_name + Config.SOUND_SUFFIX;
//		System.out.println(file);
		StaticSoundPlayer sound_player = new StaticSoundPlayer(file);
		return sound_player;
	}
}
