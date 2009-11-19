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
	
	final public File		cpj_file;
	final public File		output_file;
	
	final public String		name;	
	final public String		res_root;
	
	StudioResource 	set_resource;

	HashMap<String, CPJSprite>
					sprites = new HashMap<String, CPJSprite>();
	HashMap<String, CPJWorld>
					worlds = new HashMap<String, CPJWorld>();
	
	CPJFile(File cpj_file, File out_file) throws Exception 
	{
		this.cpj_file		= cpj_file;
		this.output_file	= out_file;
		this.name			= cpj_file.getParentFile().getName();
		this.res_root		= cpj_file.getParentFile().getParentFile().getName();
		
		if (!output_file.exists()) {
			throw new IOException("path not a cpj file : " + output_file.getPath());
		} 

		set_resource = new StudioResource(output_file, name);
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
		String output_properties = 
			res_root + "/" + 
			cpj_file.getParentFile().getName() + "/" + 
			output_file.getParentFile().getName() + "/" +
			output_file.getName();
		return output_properties;
	}
	
	@Override
	public ImageIcon createIcon() {
		return Tools.createIcon(Res.icon_cpj);
	}

	public File getFile() {
		return cpj_file;
	}
	
	public File getOutputFile() {
		return output_file;
	}
	
	public File getCPJDir() {
		return cpj_file.getParentFile();
	}
	
	public StudioResource getSetResource() {
		return set_resource;
	}
	
	//
	public CPJSprite loadSprite(String name, CPJResourceType res_type) {
		CPJSprite ret = sprites.get(name);
		if (ret == null) {
			CSprite csprite = set_resource.getSprite(name);
			if (csprite!=null) {
				ret = new CPJSprite(this, name, res_type);
				sprites.put(name, ret);
			}
		}
		return ret;
	}
	
	public void loadAllSprite(CPJResourceType res_type)
	{
		for (String spr : set_resource.SprTable.keySet()) {
			try{
				 addChild(loadSprite(spr, res_type));
			}catch(Exception err){
				err.printStackTrace();
			}
		}
	}
	
	public void loadAllWorld() 
	{
		for (String world : set_resource.WorldTable.keySet()) {
			try{
				 addChild(getWorld(world));
			}catch(Exception err){
				err.printStackTrace();
			}
		}
	}
	
	//	
	public CPJWorld getWorld(String name) {
		CPJWorld ret = worlds.get(name);
		if (ret == null) {
			WorldSet wordset = set_resource.getSetWorld(name);
			if (wordset!=null) {
				ret = new CPJWorld(this, name);
				worlds.put(name, ret);
			}
		}
		return ret;
	}

	public Collection<String> getSpriteNames() {
		return set_resource.SprTable.keySet();
	}
	
	public Collection<String> getWorldNames() {
		return set_resource.WorldTable.keySet();
	}
	
//	------------------------------------------------------------------------------------------------------------------------------

	@Override
	public void onRightClicked(JTree tree, MouseEvent e) {
		new CPJFileMenu().show(tree, e.getX(), e.getY());
	}
	
	

//	------------------------------------------------------------------------------------------------------------------------------
	
	class CPJFileMenu extends JPopupMenu implements ActionListener
	{
		private static final long serialVersionUID = 1L;
		
		JMenuItem item_open_cell_game_editor	= new JMenuItem("打开编辑器");
		JMenuItem item_rebuild_objects			= new JMenuItem("编译文件");
		
		public CPJFileMenu() {
			item_open_cell_game_editor.addActionListener(this);
			item_rebuild_objects.addActionListener(this);
			add(item_open_cell_game_editor);
			add(item_rebuild_objects);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == item_open_cell_game_editor) {
				if (cpj_file.exists()) {
					Builder.openCellGameEdit(cpj_file);
				}
			}
			else if (e.getSource() == item_rebuild_objects) {
				if (cpj_file.exists()) {
					if (!worlds.isEmpty()) {
						Builder.buildScene(cpj_file);
					} else if (!sprites.isEmpty()) {
						Builder.buildSprite(cpj_file);
					}
				}
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
	public static ArrayList<CPJFile> listFile (String root, String res_root/*, String res_prefix, String res_suffix*/)
	{
//		res_prefix = res_prefix.toLowerCase();
		ArrayList<CPJFile> ret = new ArrayList<CPJFile>();
		try{
			for (File file : new File(root+File.separator+res_root).listFiles()) {
				File[] cpj = getCPJFile(file);
				if (cpj != null) {
					try{
						ret.add(new CPJFile(cpj[0], cpj[2]));
					} catch(Exception err){
						err.printStackTrace();
					}
				}
//				if (file.isDirectory() && file.getName().toLowerCase().startsWith(res_prefix)) {
//					try{
//						ret.add(new CPJFile(file.getName(), root, res_root, res_prefix, res_suffix));
//					} catch(Exception err){}
//				}
			}
		}catch (Exception err){
			err.printStackTrace();
		}
		return ret;
	}
	
	private static File[] getCPJFile(File cpj_root) 
	{
		try{		
			if (cpj_root.isDirectory()) {
				
				File cpj_file		= null;
				File output			= null;
				File output_file	= null;

				for (File f : cpj_root.listFiles()) {
					if (f.getName().toLowerCase().endsWith(".cpj")) {
						cpj_file = f;
					} 
					else if (f.getName().toLowerCase().equals("output")) {
						output = f;
					}
				}
				
				if (cpj_file != null && output != null) {
					String cpj_file_name = cpj_file.getName().toLowerCase().replace("cpj", "");
					for (File o : output.listFiles()) {
						if (o.getName().toLowerCase().startsWith(cpj_file_name) && o.getName().toLowerCase().endsWith(".properties")) {
							output_file = o;
//							System.out.println(cpj_root);
							return new File[]{cpj_file, output, output_file};
						}
					}
				}
			}
			
		}catch(Exception err){}
		return null;
	}
}

