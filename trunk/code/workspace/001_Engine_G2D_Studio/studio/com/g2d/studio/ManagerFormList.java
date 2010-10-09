package com.g2d.studio;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import com.g2d.Tools;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.io.File;
import com.g2d.studio.res.Res;
import com.g2d.studio.sound.SoundFile;
import com.g2d.studio.sound.SoundList;
import com.g2d.studio.swing.G2DList;
import com.g2d.studio.swing.G2DListItem;
import com.g2d.studio.swing.G2DWindowToolBar;
import com.g2d.util.AbstractFrame;

@SuppressWarnings("serial")
public abstract class ManagerFormList<T extends G2DListItem> extends ManagerForm implements ActionListener
{
	final protected Vector<T>	files = new Vector<T>();
	final protected G2DList<T> 	list;
	final protected File 		save_list_file;
	final protected File 		res_root;
	
	final protected G2DWindowToolBar	tool_bar 		= new G2DWindowToolBar(this);
	
	final protected JButton				btn_refresh 	= new JButton(Tools.createIcon(Res.icon_refresh));
	final protected JButton				btn_view		= new JButton(Tools.createIcon(Res.icon_refresh));

	
	public ManagerFormList(
			Studio studio, 
			ProgressForm progress, String title, 
			BufferedImage icon, 
			File res_root, 
			File save_list_file) 
	{
		super(studio, progress, title, icon);
		
		this.save_list_file = save_list_file;
		this.res_root		= res_root;
		
		File files[]		= res_root.listFiles();
		progress.setMaximum("", files.length);
		for (int i=0; i<files.length; i++) {
			File file = files[i];
			T node = createNode(file);
			if (node != null) {
				this.files.add(node);
			}
			progress.setValue("", i);
		}

		this.btn_refresh.addActionListener(this);
		this.tool_bar.add(btn_refresh);
		this.add(tool_bar, BorderLayout.NORTH);		
		
		list = createList(this.files);
		list.setVisibleRowCount(this.files.size()/10+1);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		this.add(new JScrollPane(list), BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == tool_bar.save) {
			saveAll();
		}
		else if (e.getSource() == btn_refresh) {
			refresh();
		}
	}
	
	public void refresh() {
		Vector<T> added 	= new Vector<T>();
		Vector<T> removed 	= new Vector<T>();
		File files[]		= res_root.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			T node = getNode(asNodeName(file));
			if (node == null) {
				node = createNode(file);
				if (node != null) {
					added.add(node);
				}
			}
		}
		if (!added.isEmpty()) {
			this.files.addAll(added);
			this.list.setListData(this.files);
			this.list.repaint();
			saveAll();
		}
	}
	
	abstract protected String		asNodeName(File file);
	
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
			sb.append(getSaveListName(icon)+"\n");
		}
		save_list_file.writeUTF(sb.toString());
	}

}
