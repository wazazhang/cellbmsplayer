package com.cell.rpg.res;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;

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
import com.cell.sound.ISound;
import com.cell.sql.SQLTableRow;
import com.g2d.Tools;
import com.g2d.cell.CellSetResource;
import com.g2d.cell.CellSetResourceManager;
import com.g2d.cell.CellSetResource.CellSetObject;
import com.g2d.cell.CellSetResource.WorldSet;
import com.g2d.studio.Config;
import com.g2d.studio.Studio;
import com.g2d.studio.icon.IconFile;


/**
 * @author WAZA
 * 第三方程序用来读入G2D Studio对象的类
 */
public abstract class ResourceManager extends CellSetResourceManager
{
//	--------------------------------------------------------------------------------------------------------------------
	
	final public String res_root;
	
	// res objects
	protected Hashtable<String, SceneSet>	all_scene_set;
	protected Hashtable<String, SpriteSet>	all_actor_set;
	protected Hashtable<String, SpriteSet>	all_avatar_set;
	protected Hashtable<String, SpriteSet>	all_effect_set;

	// xml templates and scenes
	protected Hashtable<Integer, TUnit>		tunits;
	protected Hashtable<Integer, TItem>		titems;
	protected Hashtable<Integer, TAvatar>	tavatars;
	protected Hashtable<Integer, TSkill>	tskills;
	protected Hashtable<Integer, Scene>		scenes;
	
	// icons and sounds
	protected Hashtable<String, AtomicReference<BufferedImage>> 		all_icons;
	protected Hashtable<String, AtomicReference<ISound>> 				all_sounds;

//	--------------------------------------------------------------------------------------------------------------------

	/**
	 * 子类自定义初始化什么
	 * @param res_root
	 * @param icon_root
	 * @param sound_root
	 * @throws Exception
	 */
	public ResourceManager(String res_root) throws Exception
	{
		this.res_root	= res_root;
		
		
	}

	public ResourceManager(
			String res_root, 
			String save_name,
			boolean init_set,
			boolean init_xml,
			boolean init_icon,
			boolean init_sound)  throws Exception
	{
		this.res_root	= res_root;

		if (init_set) 
			initAllSet(save_name);
		
		if (init_xml) 
			initAllXml(save_name);
		
		if (init_icon) 
			initIcons(save_name);
		
		if (init_sound) 
			initSounds(save_name);
	}
	
//	--------------------------------------------------------------------------------------------------------------------

	final protected void initAllSet(String save_name) throws Exception
	{
		all_scene_set	= readSets(res_root + "/" + save_name + "/resources/scene_list.list",	SceneSet.class);
		all_actor_set	= readSets(res_root + "/" + save_name + "/resources/actor_list.list",	SpriteSet.class);
		all_avatar_set	= readSets(res_root + "/" + save_name + "/resources/avatar_list.list",	SpriteSet.class);
		all_effect_set	= readSets(res_root + "/" + save_name + "/resources/effect_list.list",	SpriteSet.class);
	}
	
	final protected void initAllXml(String save_name)  throws Exception
	{
		tunits		= readTemplates(res_root + "/" + save_name + "/objects/tunit.obj", 		TUnit.class);
		titems		= readTemplates(res_root + "/" + save_name + "/objects/titem.obj", 		TItem.class);
		tavatars	= readTemplates(res_root + "/" + save_name + "/objects/tavatar.obj",	TAvatar.class);
		tskills		= readTemplates(res_root + "/" + save_name + "/objects/tskill.obj",		TSkill.class);
		scenes		= readRPGScenes(res_root + "/" + save_name + "/scenes");
	}
	
	final protected void initIcons(String save_name)
	{
		all_icons	= readIcons(res_root + "/" + save_name + "/icons/icon.list" );
	}
	
	final protected void initSounds(String save_name)
	{
		all_sounds	= readSounds(res_root + "/" + save_name + "/sounds/sound.list" );
	}
	
//	--------------------------------------------------------------------------------------------------------------------

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

	final protected <T extends ResourceSet<?>> Hashtable<String, T> readSets(String file, Class<T> type) throws Exception
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
	
	final protected <T extends TemplateNode> Hashtable<Integer, T> readTemplates(String tdir, Class<T> type) throws Exception
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
	
	final protected Hashtable<Integer, Scene> readRPGScenes(String scene_path) throws Exception
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
//	Icons
//	--------------------------------------------------------------------------------------------------------------------
	
	final protected Hashtable<String, AtomicReference<BufferedImage>> readIcons(String icon_list)
	{
		System.out.println("list icons : " + icon_list);

		Hashtable<String, AtomicReference<BufferedImage>> table = new Hashtable<String, AtomicReference<BufferedImage>>();
		
		String[] res_list = CIO.readAllLine(icon_list, "UTF-8");
		
		for (int i=0; i<res_list.length; i++)
		{
			String[] split 	= CUtil.splitString(res_list[i], ",");
			String icon_id 	= split[0];
			String icon_w 	= split[1];
			String icon_h 	= split[2];
			table.put(icon_id, new AtomicReference<BufferedImage>(null));
			System.out.println("\tget icon : " + icon_id + "(" + icon_w + "x" + icon_h + ")");
		}
		
		return table;
	}

	public BufferedImage getIcon(String index)
	{
		return all_icons.get(index).get();
	}
	
//	--------------------------------------------------------------------------------------------------------------------
//	sounds
//	--------------------------------------------------------------------------------------------------------------------
	

	final protected Hashtable<String, AtomicReference<ISound>> readSounds(String sound_list)
	{
		System.out.println("list sounds : " + sound_list);

		Hashtable<String, AtomicReference<ISound>> table = new Hashtable<String, AtomicReference<ISound>>();
		
		String[] res_list = CIO.readAllLine(sound_list, "UTF-8");
		
		for (int i=0; i<res_list.length; i++)
		{
			table.put(res_list[i].trim(), new AtomicReference<ISound>(null));
			System.out.println("\tget sound : " + res_list[i]);
		}
		
		return table;
	}

	public ISound getSound(String index)
	{
		return all_sounds.get(index).get();
	}
	
	
//	--------------------------------------------------------------------------------------------------------------------
//	
//	--------------------------------------------------------------------------------------------------------------------
	
	public static void main(String[] args) throws Exception 
	{
		try
		{
			new ResourceManager(
					args[0], 
					args[1], 
					true, true, true, true){};
		}
		catch (Exception err) 
		{
			new ResourceManager(
					"D:/EatWorld/trunk/eatworld/data/edit/resource", 
					"project.g2d.save", 
					true, true, true, true){};
		}
		
	}
}
