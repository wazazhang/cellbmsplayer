package com.cell.rpg.res;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.rpg.io.RPGObjectMap;
import com.cell.rpg.res.ResourceSet.*;
import com.cell.rpg.scene.Scene;
import com.cell.rpg.template.TAvatar;
import com.cell.rpg.template.TItem;
import com.cell.rpg.template.TSkill;
import com.cell.rpg.template.TUnit;
import com.cell.rpg.template.TemplateNode;
import com.cell.rpg.xls.XLSTable;
import com.cell.sql.SQLTableRow;
import com.g2d.cell.CellSetResource;
import com.g2d.cell.CellSetResourceManager;
import com.g2d.cell.CellSetResource.CellSetObject;
import com.g2d.cell.CellSetResource.WorldSet;


/**
 * @author WAZA
 * 第三方程序用来读入G2D Studio对象的类
 */
public class ResourceManager extends CellSetResourceManager
{
//	--------------------------------------------------------------------------------------------------------------------
	
	final public String res_root;
	
	// res objects
	final Hashtable<String, SceneSet>	all_scene_set;
	final Hashtable<String, SpriteSet>	all_actor_set;
	final Hashtable<String, SpriteSet>	all_avatar_set;
	final Hashtable<String, SpriteSet>	all_effect_set;

	// save objects	
	final Hashtable<Integer, TUnit>		tunits;
	final Hashtable<Integer, TItem>		titems;
	final Hashtable<Integer, TAvatar>	tavatars;
	final Hashtable<Integer, TSkill>	tskills;
	final Hashtable<Integer, Scene>		scenes;
	

//	--------------------------------------------------------------------------------------------------------------------
	
	public ResourceManager(
			String res_root, 
			String save_path,
			String icon_root,
			String sound_root) throws Exception
	{
		this.res_root = res_root;
		//
		all_scene_set	= readSets(res_root + "/" + save_path + "/resources/scene_list.list",	SceneSet.class);
		all_actor_set	= readSets(res_root + "/" + save_path + "/resources/actor_list.list",	SpriteSet.class);
		all_avatar_set	= readSets(res_root + "/" + save_path + "/resources/avatar_list.list",	SpriteSet.class);
		all_effect_set	= readSets(res_root + "/" + save_path + "/resources/effect_list.list",	SpriteSet.class);
		//
		tunits			= readTemplates(res_root + "/" + save_path + "/objects/tunit.obj", 		TUnit.class);
		titems			= readTemplates(res_root + "/" + save_path + "/objects/titem.obj", 		TItem.class);
		tavatars		= readTemplates(res_root + "/" + save_path + "/objects/tavatar.obj",	TAvatar.class);
		tskills			= readTemplates(res_root + "/" + save_path + "/objects/tskill.obj",		TSkill.class);
		scenes			= readRPGScenes(res_root + "/" + save_path + "/scenes");
		
	}

	protected CellSetResource createSet(String path) throws Exception
	{
		return new Resource(path);
	}

//	--------------------------------------------------------------------------------------------------------------------
//	Resources
//	--------------------------------------------------------------------------------------------------------------------
	
	public SceneSet getSceneSet(String cpj_name, String obj_name) throws Exception{
		SceneSet set = all_scene_set.get(ResourceSet.toID(cpj_name, obj_name));
		set.loadSetObject(this);
		return set;
	}

	public SpriteSet getActorSet(String cpj_name, String obj_name) throws Exception{
		SpriteSet set = all_actor_set.get(ResourceSet.toID(cpj_name, obj_name));
		set.loadSetObject(this);
		return set;
	}
	
	public SpriteSet getAvatarSet(String cpj_name, String obj_name) throws Exception{
		SpriteSet set = all_avatar_set.get(ResourceSet.toID(cpj_name, obj_name));
		set.loadSetObject(this);
		return set;
	}
	
	public SpriteSet getEffectSet(String cpj_name, String obj_name) throws Exception{
		SpriteSet set = all_effect_set.get(ResourceSet.toID(cpj_name, obj_name));
		set.loadSetObject(this);
		return set;
	}
	
	public Vector<SceneSet> getAllScenes() {
		return new Vector<SceneSet>(all_scene_set.values());
	}
	
	public Vector<SpriteSet> getAllActors() {
		return new Vector<SpriteSet>(all_actor_set.values());
	}
	
	public Vector<SpriteSet> getAllAvatars() {
		return new Vector<SpriteSet>(all_avatar_set.values());
	}
	
	public Vector<SpriteSet> getAllEffects() {
		return new Vector<SpriteSet>(all_effect_set.values());
	}

	protected <T extends ResourceSet<?>> Hashtable<String, T> readSets(String file, Class<T> type) throws Exception
	{
		System.out.println("list resource : " + file);
		
		Hashtable<String, T> table = new Hashtable<String, T>();
		
		String[] res_list = CIO.readAllLine(file, "UTF-8");
		
		for (int i=0; i<res_list.length; i++)
		{
			String[] split = CUtil.splitString(res_list[i], ";");
			String res_root		= split[0];
			String res_prefix	= split[1];
			String res_suffix	= split[2];
			String cpj_name 	= split[3];
			String obj_name 	= split[4];

			T set = null;
			if (type.equals(SceneSet.class)) {
				set = type.cast(new SceneSet(cpj_name, obj_name, res_root, res_prefix, res_suffix));
			} else if (type.equals(SpriteSet.class)) {
				set = type.cast(new SpriteSet(cpj_name, obj_name, res_root, res_prefix, res_suffix));
			}
			
			System.out.println("\tget " + type.getSimpleName() + " : " + cpj_name + "(" + obj_name + ")");
			
			table.put(set.getID(), set);
		}
		
		return table;
	}
	
//	--------------------------------------------------------------------------------------------------------------------
//	Templates
//	--------------------------------------------------------------------------------------------------------------------
	
	public TUnit getTUnit(int id) {
		return tunits.get(id);
	}
	public TItem getTItem(int id) {
		return titems.get(id);
	}
	public TAvatar getTAvatar(int id) {
		return tavatars.get(id);
	}
	public TSkill getTSkill(int id) {
		return tskills.get(id);
	}
	
	protected <T extends TemplateNode> Hashtable<Integer, T> readTemplates(String tdir, Class<T> type) throws Exception
	{
		System.out.println("list template : " + tdir);

		String tinfo = tdir + "/" + type.getSimpleName().toLowerCase()+".list";
		
		Hashtable<Integer, T> table = new Hashtable<Integer, T>();
		
		String[] res_list = CIO.readAllLine(tinfo, "UTF-8");
		
		for (int i=0; i<res_list.length; i++)
		{
			T set = RPGObjectMap.readNode(tdir + "/" + res_list[i], type);			
			
			System.out.println("\tget " + type.getSimpleName() + " : " + set.name + "(" + set.id + ")");

			table.put(set.getIntID(), set);
		}
		
		return table;
	}

	
//	--------------------------------------------------------------------------------------------------------------------
//	Scenes
//	--------------------------------------------------------------------------------------------------------------------
	
	public Scene getRPGScene(int id) {
		return scenes.get(id);
	}
	
	public HashMap<Integer, Scene> getAllRPGScenes() {
		return new HashMap<Integer, Scene>(scenes);
	}
	
	protected Hashtable<Integer, Scene> readRPGScenes(String scene_path) throws Exception
	{
		String scene_list = scene_path + "/scene.list";
		
		System.out.println("list scenes : " + scene_list);

		Hashtable<Integer, Scene> table = new Hashtable<Integer, Scene>();
		
		String[] res_list = CIO.readAllLine(scene_list, "UTF-8");
		
		for (int i=0; i<res_list.length; i++)
		{
			int last_split = res_list[i].lastIndexOf("/");
			if (last_split>=0) {
				res_list[i] = res_list[i].substring(last_split+1);
			}
			
			String scene_file = scene_path +"/"+ res_list[i];
			
			Scene set = RPGObjectMap.readNode(scene_file, Scene.class);			
			
			System.out.println("\tget " + set.getClass().getSimpleName() + " : " + set.name + "(" + set.id + ")");

			table.put(set.getIntID(), set);
		}
		
		return table;
	}
	
	
	
//	--------------------------------------------------------------------------------------------------------------------


	
	public static void main(String[] args) throws Exception 
	{
		try{
			new ResourceManager(args[0], args[1], args[2], args[3]);
		} catch (Exception err) {
			new ResourceManager(
					"D:/EatWorld/trunk/eatworld/data/edit/resource", 
					"project.g2d.save", 
					"icons", 
					"sound");
		}
		
	}
}
