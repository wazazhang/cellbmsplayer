package com.g2d.studio.sound;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import com.cell.sound.ISound;
import com.cell.sound.SoundInfo;
import com.cell.sound.openal_impl.JALSoundManager;
import com.cell.sound.util.SoundPlayer;
import com.cell.sound.util.StaticSoundPlayer;
import com.g2d.Tools;
import com.g2d.display.ui.UIComponent;
import com.g2d.studio.Config;
import com.g2d.studio.ManagerForm;
import com.g2d.studio.Studio;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.icon.IconFile;
import com.g2d.studio.icon.IconList;
import com.g2d.studio.res.Res;
import com.g2d.util.AbstractFrame;

public class SoundManager extends ManagerForm implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	com.cell.sound.SoundManager sound_system = Studio.getInstance().getSoundSystem();

	Vector<SoundFile> sound_files = new Vector<SoundFile>();
	
	SoundList sound_list;
	
	JButton btn_play	= new JButton("播放");
	JButton btn_stop	= new JButton("停止");
	
	public SoundManager(ProgressForm progress) 
	{
		super(progress, "声音编辑器");

		{
			JToolBar play_bar = new JToolBar();
			btn_play.addActionListener(this);
			btn_stop.addActionListener(this);
			play_bar.add(btn_play);
			play_bar.add(btn_stop);
			
			this.add(play_bar, BorderLayout.NORTH);		
		}

		{
			File sound_dir = Studio.getInstance().root_sound_path;
			for (File file : sound_dir.listFiles()) {
				if (file.getName().endsWith(Config.SOUND_SUFFIX)) {
					SoundFile sound = new SoundFile(
							file.getName().substring(0, file.getName().length() - Config.SOUND_SUFFIX.length()));
					sound_files.add(sound);
				}
			}
			
			sound_list = new SoundList(getSounds());
			sound_list.setVisibleRowCount(sound_files.size()/5+1);
			sound_list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
			this.add(new JScrollPane(sound_list), BorderLayout.CENTER);

		}
		
		
	}
	
	public Vector<SoundFile> getSounds() {
		return sound_files;
	}
	
	public SoundFile getSound(String sound_name) {
		for (SoundFile sound : sound_files) {
			if (sound.sound_file_name.equals(sound_name)) {
				return sound;
			}
		}
		return null;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_play) {
			sound_list.playSelected();
		} else if (e.getSource() == btn_stop) {
			sound_list.stopSelected();
		}
	}
	
}
