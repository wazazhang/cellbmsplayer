package com.cell.rpg.res;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.rpg.NamedObject;
import com.cell.rpg.RPGObject;
import com.cell.rpg.io.RPGObjectMap;
import com.cell.rpg.item.ItemProperties;
import com.cell.rpg.quest.Quest;
import com.cell.rpg.res.ResourceSet.SceneSet;
import com.cell.rpg.res.ResourceSet.SpriteSet;
import com.cell.rpg.scene.Scene;
import com.cell.rpg.scene.graph.SceneGraph;
import com.cell.rpg.template.TAvatar;
import com.cell.rpg.template.TItem;
import com.cell.rpg.template.TSkill;
import com.cell.rpg.template.TUnit;
import com.cell.rpg.template.TemplateNode;
import com.cell.sound.ISound;
import com.g2d.cell.CellSetResource;
import com.g2d.cell.CellSetResourceManager;


/**
 * @author WAZA
 * 第三方程序用来读入G2D Studio对象的类
 */
public abstract class ResourceManager extends CellSetResourceManager
{
	public static boolean	PRINT_VERBOS 	= false;
//	--------------------------------------------------------------------------------------------------------------------
	
	final public String res_root;
	final public String save_dir;
	// res objects
	protected Hashtable<String, SceneSet>	all_scene_set;
	protected Hashtable<String, SpriteSet>	all_actor_set;
	protected Hashtable<String, SpriteSet>	all_avatar_set;
	protected Hashtable<String, SpriteSet>	all_effect_set;

	// xml templates dynamic object and scenes
	protected Hashtable<Integer, ItemProperties> item_properties;
	protected Hashtable<Integer, TUnit>		tunits;
	protected Hashtable<Integer, TItem>		titems;
	protected Hashtable<Integer, TAvatar>	tavatars;
	protected Hashtable<Integer, TSkill>	tskills;
	protected Hashtable<Integer, Quest> 	quests;
	protected Hashtable<Integer, Scene>		scenes;

	protected Hashtable<Integer, String> 	names_item_properties;
	protected Hashtable<Integer, String>	names_tunits;
	protected Hashtable<Integer, String>	names_titems;
	protected Hashtable<Integer, String>	names_tavatars;
	protected Hashtable<Integer, String>	names_tskills;
	protected Hashtable<Integer, String> 	names_quests;
	protected Hashtable<Integer, String>	names_scenes;
	
	
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
	public ResourceManager(
			String res_root, 
			String save_name) throws Exception
	{
		this.res_root	= res_root;
		this.save_dir	= res_root + "/" + save_name;
	}

	public ResourceManager(
			String persistance_manager, 
			String res_root, 
			String save_name,
			boolean init_set,
			boolean init_xml,
			boolean init_icon,
			boolean init_sound)  throws Exception
	{
		this.res_root	= res_root;
		this.save_dir	= res_root + "/" + save_name;
		
		if (init_set) 
			initAllSet();
		
		if (init_xml) 
			initAllXml(persistance_manager);
		
		if (init_icon) 
			initIcons();
		
		if (init_sound) 
			initSounds();
	}
	
//	--------------------------------------------------------------------------------------------------------------------

	final protected void initAllSet() throws Exception
	{
		all_scene_set	= readSets(save_dir + "/resources/scene_list.list",		SceneSet.class);
		all_actor_set	= readSets(save_dir + "/resources/actor_list.list",		SpriteSet.class);
		all_avatar_set	= readSets(save_dir + "/resources/avatar_list.list",	SpriteSet.class);
		all_effect_set	= readSets(save_dir + "/resources/effect_list.list",	SpriteSet.class);
	}
	
	final protected void initAllXml(String persistance_manager)  throws Exception
	{
		RPGObjectMap.setPersistanceManagerDriver(persistance_manager);
	
		item_properties = readRPGObjects(save_dir + "/item_properties/item_properties.list", ItemProperties.class);
		
		tunits			= readRPGObjects(save_dir + "/objects/tunit.obj/tunit.list", 		TUnit.class);
		titems			= readRPGObjects(save_dir + "/objects/titem.obj/titem.list", 		TItem.class);
		tavatars		= readRPGObjects(save_dir + "/objects/tavatar.obj/tavatar.list",	TAvatar.class);
		tskills			= readRPGObjects(save_dir + "/objects/tskill.obj/tskill.list",		TSkill.class);
		
		quests			= readRPGObjects(save_dir + "/quests/quest.list",		Quest.class);
		scenes			= readRPGObjects(save_dir + "/scenes/scene.list", 		Scene.class);
	}
	
	final protected void initAllXmlNames()  throws Exception
	{
		names_item_properties	= readRPGObjectNames(save_dir + "/item_properties/names_item_properties.list");
		
		names_tunits			= readRPGObjectNames(save_dir + "/objects/tunit.obj/names_tunit.list");
		names_titems			= readRPGObjectNames(save_dir + "/objects/titem.obj/names_titem.list");
		names_tavatars			= readRPGObjectNames(save_dir + "/objects/tavatar.obj/names_tavatar.list");
		names_tskills			= readRPGObjectNames(save_dir + "/objects/tskill.obj/names_tskill.list");
		
		names_quests			= readRPGObjectNames(save_dir + "/quests/names_quest.list");
		names_scenes			= readRPGObjectNames(save_dir + "/scenes/names_scene.list");
	}
	
	final protected void initIcons()
	{
		all_icons	= readIcons(save_dir + "/icons/icon.list" );
	}
	
	final protected void initSounds()
	{
		all_sounds	= readSounds(save_dir + "/sounds/sound.list" );
	}
	
//	--------------------------------------------------------------------------------------------------------------------

	protected CellSetResource createSet(String path) throws Exception
	{
		return new Resource(path);
	}
	
	final protected <T extends ResourceSet<?>> Hashtable<String, T> readSets(String file, Class<T> type) throws Exception
	{
		System.out.println("list resource : " + file);
		
		Hashtable<String, T> table = new Hashtable<String, T>();
		
		String[] res_list = CIO.readAllLine(file, "UTF-8");
		
		for (int i=0; i<res_list.length; i++)
		{
			String[] split = CUtil.splitString(res_list[i], ";");
			String res_path		= split[0];
			String cpj_name 	= split[1];
			String obj_name 	= split[2];
			
			T set = null;
			if (type.equals(SceneSet.class)) {
				set = type.cast(new SceneSet(cpj_name, obj_name, res_path));
			} else if (type.equals(SpriteSet.class)) {
				set = type.cast(new SpriteSet(cpj_name, obj_name, res_path));
			}
			if (PRINT_VERBOS)
			System.out.println("\tget " + type.getSimpleName() + " : " + cpj_name + "(" + obj_name + ")");
			
			table.put(set.getID(), set);
		}
		
		System.out.println("size : " + table.size());
		
		return table;
	}

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
			
			if (PRINT_VERBOS)
			System.out.println("\tget icon : " + icon_id + "(" + icon_w + "x" + icon_h + ")");
		}
		
		System.out.println("size : " + table.size());

		return table;
	}


	final protected Hashtable<String, AtomicReference<ISound>> readSounds(String sound_list)
	{
		System.out.println("list sounds : " + sound_list);

		Hashtable<String, AtomicReference<ISound>> table = new Hashtable<String, AtomicReference<ISound>>();
		
		String[] res_list = CIO.readAllLine(sound_list, "UTF-8");
		
		for (int i=0; i<res_list.length; i++)
		{
			table.put(res_list[i].trim(), new AtomicReference<ISound>(null));
			if (PRINT_VERBOS)
			System.out.println("\tget sound : " + res_list[i]);
		}
		
		System.out.println("list sounds : " + table.size());

		return table;
	}

	final protected Hashtable<Integer, String> readRPGObjectNames(String list_file) throws Exception
	{
		System.out.println("list rpg object names : " + list_file);

		Hashtable<Integer, String> table = new Hashtable<Integer, String>();
		
		String[] res_list = CIO.readAllLine(list_file, "UTF-8");
		
		for (String line : res_list) {
			String[] kv = CUtil.splitString(line, ")", 2);
			kv[0] = kv[0].substring(1);
			table.put(Integer.parseInt(kv[0]), kv[1]);
		}
		
		return table;
	}
	
	final protected <T extends RPGObject> Hashtable<Integer, T> readRPGObjects(String list_file, Class<T> type) throws Exception
	{		
		System.out.println("list rpg objects : " + list_file);
		
		String tdir = CIO.getPathDir(list_file);

		Hashtable<Integer, T> table = new Hashtable<Integer, T>();
		
		String[] res_list = CIO.readAllLine(list_file, "UTF-8");
		
		for (int i=0; i<res_list.length; i++)
		{
			int last_split = res_list[i].lastIndexOf("/");
			if (last_split>=0) {
				res_list[i] = res_list[i].substring(last_split+1);
			}
			
			String xml_file = tdir +"/"+ res_list[i];
			
			T set = RPGObjectMap.readNode(xml_file, type);			
			if (PRINT_VERBOS)
			System.out.println("\tget " + type.getSimpleName() + " : " + set + "(" + set.id + ")");

			table.put(Integer.parseInt(set.id), set);
		}
		
		System.out.println("size : " + table.size());
		
		return table;
	}

	
	final public <T extends RPGObject> T readRPGObject(String xml_file, Class<T> type) 
	{
		T set = RPGObjectMap.readNode(xml_file, type);			
		if (PRINT_VERBOS)
		System.out.println("readRPGObject : " + type.getSimpleName() + " : " + set + "(" + set.id + ")");
		return set;
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
	
//	--------------------------------------------------------------------------------------------------------------------
//	ItemProperties
//	--------------------------------------------------------------------------------------------------------------------
	
	public Hashtable<Integer, ItemProperties> getAllItemProperties(){
		return new Hashtable<Integer, ItemProperties>(item_properties);
	}

	public ItemProperties getItemProperties(int id)
	{
		return item_properties.get(id);
	}
	
//	--------------------------------------------------------------------------------------------------------------------
//	Quests
//	--------------------------------------------------------------------------------------------------------------------
	
	public Hashtable<Integer, Quest> getAllQuests(){
		return new Hashtable<Integer, Quest>(quests);
	}

	public Quest getQuest(int quest_id)
	{
		return quests.get(quest_id);
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
	
	public SceneGraph createSceneGraph() {
		return new SceneGraph(scenes.values());
	}
	
//	--------------------------------------------------------------------------------------------------------------------
//	Icons
//	--------------------------------------------------------------------------------------------------------------------
	
	public BufferedImage getIcon(String index)
	{
		return all_icons.get(index).get();
	}
	
//	--------------------------------------------------------------------------------------------------------------------
//	sounds
//	--------------------------------------------------------------------------------------------------------------------
	
	public ISound getSound(String index)
	{
		return all_sounds.get(index).get();
	}
	
	
//	--------------------------------------------------------------------------------------------------------------------
//	
//	--------------------------------------------------------------------------------------------------------------------
	
	public String getItemPropertiesName(int id) {
		return names_item_properties.get(id);
	}
	public String getTUnitName(int id) {
		return names_tunits.get(id);
	}
	public String getTItemName(int id) {
		return names_titems.get(id);
	}
	public String getTAvatarName(int id) {
		return names_tavatars.get(id);
	}
	public String getTSkillName(int id) {
		return names_tskills.get(id);
	}
	public String getQuestName(int id) {
		return names_quests.get(id);
	}
	public String getRPGSceneName(int id) {
		return names_scenes.get(id);
	}

}
