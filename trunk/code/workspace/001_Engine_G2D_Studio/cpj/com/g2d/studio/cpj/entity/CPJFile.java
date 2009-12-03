package com.g2d.studio.cpj.entity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

import com.cell.game.CSprite;
import com.g2d.Tools;
import com.g2d.cell.CellSetResource.WorldSet;
import com.g2d.studio.StudioResource;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DTreeNode;
import com.g2d.studio.cell.gameedit.Builder;
import com.g2d.studio.cpj.CPJResourceType;

public class CPJFile extends G2DTreeNode<CPJObject<?>>
{	
//	final public String res_prefix;
//	final public String res_suffix;
	/** cpj dir name */
	final public String				name;	
	/** cpj file name */
	final public File				cpj_file;
	/** res root name*/
	final public String				res_root;
	final public CPJResourceType	res_type;

	private StudioResource 				set_resource;
	private HashMap<String, CPJSprite>	sprites			= new HashMap<String, CPJSprite>();
	private HashMap<String, CPJWorld>	worlds			= new HashMap<String, CPJWorld>();
	
	CPJFile(File cpj_file, CPJResourceType res_type) throws Exception 
	{
		this.cpj_file		= cpj_file;
		this.name			= cpj_file.getParentFile().getName();		
		this.res_root		= cpj_file.getParentFile().getParentFile().getName();
		this.res_type		= res_type;

		
//		if (!output_file.exists()) {
//			throw new IOException("path not a cpj file : " + output_file.getPath());
//		}

//		set_resource = new StudioResource(output_file, name);
		
		refresh();
	}
	
	
	
	@Override
	public String getName() {
		return name;	
	}

	/**
	 * get output.properties
	 * @return
	 */
	public String getResourcePath() {
		File output_file	= getOutputFile(cpj_file);
		if (output_file!=null && output_file.exists())  {
			String output_properties = 
				res_root + "/" + 
				cpj_file.getParentFile().getName() + "/" + 
				output_file.getParentFile().getName() + "/" +
				output_file.getName();
			return output_properties;
		}
		else{
			String output_properties = 
				res_root + "/" + 
				cpj_file.getParentFile().getName() + "/" +
				"null/" +
				"null";
			return output_properties;
		}
	}
	
	@Override
	public ImageIcon createIcon() {
		return Tools.createIcon(Res.icon_cpj);
	}

	public File getFile() {
		return cpj_file;
	}
	
	public File getCPJDir() {
		return cpj_file.getParentFile();
	}
	
	public StudioResource getSetResource() {
		return set_resource;
	}

//	------------------------------------------------------------------------------------------------------------------------------

	//
	private CPJSprite loadSprite(String name, CPJResourceType res_type) {
		CPJSprite ret = sprites.get(name);
		if (ret == null) {
			try{
				CSprite csprite = set_resource.getSprite(name);
				if (csprite!=null) {
					ret = new CPJSprite(this, name, res_type);
					sprites.put(name, ret);
				}
				add(ret);
			}catch(Exception err){
				err.printStackTrace();
			}
		} else {
			
		}
		return ret;
	}
	
	private void loadAllSprite(CPJResourceType res_type)
	{
		if (set_resource != null) {
			for (String spr : set_resource.SprTable.keySet()) {
				loadSprite(spr, res_type);
			}
		}
	}

	//	
	private CPJWorld loadWorld(String name) {
		CPJWorld ret = worlds.get(name);
		if (ret == null) {
			try{
				WorldSet wordset = set_resource.getSetWorld(name);
				if (wordset!=null) {
					ret = new CPJWorld(this, name);
					worlds.put(name, ret);
				}
				add(ret);
			}catch(Exception err){
				err.printStackTrace();
			}
		}
		return ret;
	}
	
	private void loadAllWorld() {
		if (set_resource != null) {
			for (String world : set_resource.WorldTable.keySet()) {
				loadWorld(world);
			}
		}
	}

//	------------------------------------------------------------------------------------------------------------------------------


	public Collection<String> getSpriteNames() {
		return set_resource.SprTable.keySet();
	}
	
	public Collection<String> getWorldNames() {
		return set_resource.WorldTable.keySet();
	}
	
//	------------------------------------------------------------------------------------------------------------------------------

	@Override
	public void onRightClicked(JTree tree, MouseEvent e) {
		new CPJFileMenu(tree).show(tree, e.getX(), e.getY());
	}
	
	

//	------------------------------------------------------------------------------------------------------------------------------

	public void refresh()
	{
		File output_file	= getOutputFile(cpj_file);

		if (output_file!=null && output_file.exists()) 
		{
			try {
				set_resource = new StudioResource(output_file, name);
				switch (res_type) {
				case ACTOR:	
				case AVATAR:
				case EFFECT:
					loadAllSprite(res_type);
					break;
				case WORLD:
					loadAllWorld();
					break;
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
	
	public void rebuild()
	{
		if (cpj_file.exists()) {
			switch (res_type) {
			case ACTOR:	
			case AVATAR:
			case EFFECT:
				Builder.buildSprite(cpj_file);
				break;
			case WORLD:
				Builder.buildScene(cpj_file);
				break;
			}
		}
	}
	
	
	public void openEdit()
	{
		if (cpj_file.exists()) {
			Builder.openCellGameEdit(cpj_file);
		}
	}
//	------------------------------------------------------------------------------------------------------------------------------

	class CPJFileMenu extends JPopupMenu implements ActionListener
	{
		private static final long serialVersionUID = 1L;
		JTree tree;
		JMenuItem item_open_cell_game_editor	= new JMenuItem("打开编辑器");
		JMenuItem item_rebuild_objects			= new JMenuItem("编译文件");
		
		public CPJFileMenu(JTree tree) {
			item_open_cell_game_editor.addActionListener(this);
			item_rebuild_objects.addActionListener(this);
			add(item_open_cell_game_editor);
			add(item_rebuild_objects);
			this.tree = tree;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == item_open_cell_game_editor) {
				openEdit();
			} else if (e.getSource() == item_rebuild_objects) {
				rebuild();
				refresh();
				try{
					DefaultTreeModel tm = (DefaultTreeModel)tree.getModel();
					tm.reload(CPJFile.this);
				}catch (Exception err){}
			}
		}
		
	}
	
	
//	------------------------------------------------------------------------------------------------------------------------------
	
	/**
	public static String ROOT_SCENE					= "scene";<br>
	public static String RES_SCENE_					= "scene_";<br>
	public static String RES_SCENE_OUTPUT			= "output/scene.properties";<br>
	@see {@link Config}
	*/
	public static ArrayList<CPJFile> listFile (String root, String res_root, CPJResourceType res_type)
	{
//		res_prefix = res_prefix.toLowerCase();
		ArrayList<CPJFile> ret = new ArrayList<CPJFile>();
		try{
			for (File file : new File(root+File.separator+res_root).listFiles()) {
				File cpj = getCPJFile(file);
				if (cpj != null) {
					try{
						ret.add(new CPJFile(cpj, res_type));
					} catch(Exception err){
						err.printStackTrace();
					}
				}
			}
		}catch (Exception err){
			err.printStackTrace();
		}
		return ret;
	}
	
	private static File getOutputFile(File cpj_file)
	{
		try{
			String cpj_file_name = cpj_file.getName().toLowerCase().replace("cpj", "");
			for (File output : cpj_file.getParentFile().listFiles()) {
				if (output.getName().toLowerCase().equals("output")) {
					for (File o : output.listFiles()) {
						String output_name = o.getName().toLowerCase();
						if (output_name.startsWith(cpj_file_name) && 
							output_name.endsWith(".properties")) {
							return o;
						}
					}
				}
			}
		}
		catch(Exception err){}
		return null;
	}
	
	private static File getCPJFile(File cpj_root) 
	{
		try{		
			if (cpj_root.isDirectory()) {
				for (File f : cpj_root.listFiles()) {
					if (f.getName().toLowerCase().endsWith(".cpj")) {
						return f;
					}
				}
			}
		}catch(Exception err){}
		return null;
	}
}

