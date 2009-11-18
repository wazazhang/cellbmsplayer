package com.g2d.studio.cpj.entity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.ImageIcon;

import com.cell.game.CSprite;
import com.g2d.Tools;
import com.g2d.cell.CellSetResource.WorldSet;
import com.g2d.studio.StudioResource;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DTreeNode;
import com.g2d.studio.cpj.CPJResourceType;

public class CPJFile extends G2DTreeNode<CPJObject<?>>
{	
	final public String res_root;
	final public String res_prefix;
	final public String res_suffix;
	final public String	name;
	
	File			output_file;
	File			cpj_dir;
	
	StudioResource 	set_resource;

	HashMap<String, CPJSprite>
					sprites = new HashMap<String, CPJSprite>();
	HashMap<String, CPJWorld>
					worlds = new HashMap<String, CPJWorld>();
	
	CPJFile(String name, 
			String root,
			String res_root, 
			String res_prefix, 
			String res_suffix) throws Exception 
	{
		this.name			= name;
		this.res_root		= res_root;
		this.res_prefix 	= res_prefix;
		this.res_suffix		= res_suffix;
		
		this.cpj_dir		= new File(
				root		+ File.separatorChar +
				res_root	+ File.separatorChar+
				name);
		this.output_file = new File(
				root		+ File.separatorChar +
				res_root	+ File.separatorChar + 
				name 		+ File.separatorChar + 
				res_suffix);
		
		if (!output_file.exists()) {
			throw new IOException("path not a cpj file : " + output_file.getPath());
		} 
		
		set_resource = new StudioResource(output_file, name);
//		System.out.println("read a cpj file : " + output_file.getPath());
		
		
	}
	
	
	
	@Override
	public String getName() {
		return name;	
	}

	@Override
	public ImageIcon createIcon() {
		return Tools.createIcon(Res.icon_cpj);
	}

	// 
	public File getOutputFile() {
		return output_file;
	}
	
	public File getCPJDir() {
		return cpj_dir;
	}
	
	//
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
	
	/**
	public static String ROOT_SCENE					= "scene";<br>
	public static String RES_SCENE_					= "scene_";<br>
	public static String RES_SCENE_OUTPUT			= "output/scene.properties";<br>
	@see {@link Config}
	*/
	public static ArrayList<CPJFile> listFile (String root, String res_root, String res_prefix, String res_suffix)
	{
		res_prefix = res_prefix.toLowerCase();
		ArrayList<CPJFile> ret = new ArrayList<CPJFile>();
		try{
			for (File file : new File(root+"/"+res_root).listFiles()) {
				if (file.isDirectory() && file.getName().toLowerCase().startsWith(res_prefix)) {
					try{
						ret.add(new CPJFile(file.getName(), root, res_root, res_prefix, res_suffix));
					} catch(Exception err){}
				}
			}
		}catch (Exception err){
			err.printStackTrace();
		}
		return ret;
	}
}
