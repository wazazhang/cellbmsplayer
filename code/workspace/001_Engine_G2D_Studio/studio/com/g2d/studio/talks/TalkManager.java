package com.g2d.studio.talks;

import java.io.File;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JScrollPane;

import com.g2d.studio.Config;
import com.g2d.studio.ManagerForm;
import com.g2d.studio.Studio;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.res.Res;

public class TalkManager extends ManagerForm
{
	private static final long serialVersionUID = 1L;
	
	Vector<TalkFile> talk_files = new Vector<TalkFile>();
	
	public TalkManager(Studio studio, ProgressForm progress) 
	{
		super(studio, progress, "对话管理器", Res.icon_talk);
		{
			File talk_dir 	= Studio.getInstance().root_talk_path;	
			File files[]	= talk_dir.listFiles();
			progress.setMaximum("", files.length);
			for (int i=0;i<files.length; i++) {
				File file = files[i];
				if (file.getName().endsWith(Config.TALK_SUFFIX)) {
					TalkFile talk = new TalkFile(
							file.getName().substring(0, file.getName().length() - Config.TALK_SUFFIX.length()), 
							file);
					talk_files.add(talk);
				}					
				progress.setValue("", i);
			}
		}
		
		TalkList talks = new TalkList(getTalks());		
		talks.setVisibleRowCount(talk_files.size()/10+1);
		talks.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		this.add(new JScrollPane(talks));
		
		new Thread(){
			public void run() {
				saveAll();
			}
		}.start();
	}
	
	public Vector<TalkFile> getTalks() {
		return talk_files;
	}
	
	public TalkFile getTalk(String talk_name) {
		for (TalkFile talk : talk_files) {
			if (talk.talk_file_name.equals(talk_name)) {
				return talk;
			}
		}
		return null;
	}
	
	public void saveAll() 
	{
		synchronized (talk_files) {
			File save_dir = new File(Studio.getInstance().project_save_path.getPath() + File.separatorChar +"talks");
			save_dir.mkdirs();
			StringBuffer sb = new StringBuffer();
			for (TalkFile talk : talk_files) {
				sb.append(talk.getListName()+","+"\n");
			}
			File save_file = new File(save_dir, "talks.list");
			com.cell.io.CFile.writeText(save_file, sb.toString(), "UTF-8");
		}
	}
}
