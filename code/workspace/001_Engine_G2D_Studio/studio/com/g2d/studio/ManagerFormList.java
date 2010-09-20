package com.g2d.studio;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.io.File;
import com.g2d.studio.res.Res;
import com.g2d.studio.sound.SoundFile;
import com.g2d.studio.sound.SoundList;
import com.g2d.studio.swing.G2DList;
import com.g2d.studio.swing.G2DListItem;
import com.g2d.util.AbstractFrame;

@SuppressWarnings("serial")
public abstract class ManagerFormList<T extends G2DListItem> extends ManagerForm
{
	final protected Vector<T>	files = new Vector<T>();
	final protected G2DList<T> 	list;
	final protected File 		save_list_file;
	
	public ManagerFormList(
			Studio studio, 
			ProgressForm progress, String title, 
			BufferedImage icon, 
			File res_root, File save_list_file) 
	{
		super(studio, progress, title, icon);
		
		this.save_list_file = save_list_file;
		
		File sound_dir		= Studio.getInstance().root_sound_path;
		File files[]		= sound_dir.listFiles();
		
		progress.setMaximum("", files.length);
		for (int i=0; i<files.length; i++) {
			File file = files[i];
			T node = createNode(file);
			if (node != null) {
				this.files.add(node);
			}
			progress.setValue("", i);
		}
		
		list = createList(this.files);
		list.setVisibleRowCount(this.files.size()/10+1);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		this.add(new JScrollPane(list), BorderLayout.CENTER);
	}
	
	
	abstract protected T 			createNode(File file);
	
	abstract protected G2DList<T> 	createList(Vector<T> files);
	
	abstract protected String 		getSaveListName(T node);
	
	
	
	public Vector<T> getNodes() {
		return files;
	}
	
	public T getNode(String node_name) {
		for (T n : files) {
			if (n.getListName().equals(node_name)) {
				return n;
			}
		}
		return null;
	}
	
	
	public void saveAll() 
	{
		StringBuffer sb = new StringBuffer();
		for (T icon : files) {
			sb.append(icon.getListName()+"\n");
		}
		save_list_file.writeUTF(sb.toString());
	}

}
