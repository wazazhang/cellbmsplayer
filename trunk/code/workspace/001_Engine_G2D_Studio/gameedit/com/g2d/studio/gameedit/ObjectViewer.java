package com.g2d.studio.gameedit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.cell.rpg.RPGObject;
import com.g2d.Tools;
import com.g2d.display.DisplayObject;
import com.g2d.editor.DisplayObjectViewer;
import com.g2d.studio.old.Studio;
import com.g2d.util.AbstractFrame;
import com.thoughtworks.xstream.XStream;

public abstract class ObjectViewer<T extends RPGObject> extends AbstractFrame
{
	private static final long serialVersionUID = 1L;

	final public Studio studio = Studio.getInstance();
	
//	属性
	final  T			rpg_object;
	protected boolean	no_save			= false;
	protected boolean	is_changed		= false;
	
//	DisplayObjectPanel
	
	public ObjectViewer(T object, File save_path) 
	{		
		this.rpg_object = object;
		this.setLocation(
				studio.getX() + studio.getWidth(), 
				studio.getY());
		this.setLayout(new BorderLayout());
		setMinimumSize(new Dimension(200, 200));
	}
	
	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		if (b) {
			is_changed = true;
		}
	}
	
	public T getRPGObject() {
		return rpg_object;
	}
	
//	-------------------------------------------------------------------------------------------------------------------------

//	-------------------------------------------------------------------------------------------------------------------------
	
//	-------------------------------------------------------------------------------------------------------------------------
	
	abstract public void saveObject(ObjectOutputStream os, File file) throws Exception;
	
	abstract public void loadObject(ObjectInputStream is, File file) throws Exception;
	
	public void load()
	{
		if (no_save) return;

		File file = null;
			
		try 
		{ 
			file = new File(studio.project_path.getPath() + "/" + ".xml");
			
			if (file.exists())
			{
				byte[] data = com.cell.io.File.readData(file);
				if (data!=null) {
					String text_data = new String(data, "UTF-8");
					Reader reader = new StringReader(text_data);
					try{
						XStream xstream = new XStream();
						ObjectInputStream in = xstream.createObjectInputStream(reader);
						try{
							loadObject(in, file);
						}finally{
							in.close();
						}
					}finally{
						reader.close();
					}
//					System.out.println("load obj : " + file.getPath());
				}
			}
		} 
		catch (Exception e) {
			System.err.println("load error : " + " : " + file);
			e.printStackTrace();
			is_changed = true;
		}
		
	}
	
	public void save()
	{
		if (no_save) return;

		File file = null ;
		
		try 
		{	
			file = studio.getFile(".xml") ;
			
			if (!file.exists()) {
				file.createNewFile();
			} else if (!is_changed) {
				return;
			}
			
			Writer writer = new StringWriter(1024);
			
			try{
				XStream xstream = new XStream();
				ObjectOutputStream out = xstream.createObjectOutputStream(writer);
				try{
					saveObject(out, file);
				}finally{
					out.close();
				}
				writer.flush();
				String text_data = writer.toString();
//				System.out.println(text_data);
				com.cell.io.File.wirteData(file, text_data.getBytes("UTF-8"));
			}finally{
				writer.close();
			}
			
//			System.out.println("save obj : " + file.getPath());
		} 
		catch (Exception e) {
			System.err.println("save error : " + " : " + file);
			e.printStackTrace();
			is_changed = true;
		}
		
	}
	
}
