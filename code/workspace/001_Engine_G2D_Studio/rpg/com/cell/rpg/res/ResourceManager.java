package com.cell.rpg.res;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;

import com.cell.CIO;
import com.cell.CObject;
import com.cell.CUtil;
import com.cell.gameedit.StreamTiles;
import com.cell.gfx.IImage;
import com.cell.gfx.game.CSprite;
import com.cell.rpg.RPGObject;
import com.cell.rpg.io.RPGObjectMap;
import com.cell.rpg.item.ItemProperties;
import com.cell.rpg.quest.Quest;
import com.cell.rpg.quest.QuestGroup;
import com.cell.rpg.quest.ability.QuestAccepter;
import com.cell.rpg.quest.ability.QuestPublisher;
import com.cell.rpg.res.ResourceSet.SceneSet;
import com.cell.rpg.res.ResourceSet.SpriteSet;
import com.cell.rpg.scene.Scene;
import com.cell.rpg.scene.SceneUnit;
import com.cell.rpg.scene.graph.SceneGraph;
import com.cell.rpg.scene.instance.InstanceZone;
import com.cell.rpg.template.TAvatar;
import com.cell.rpg.template.TEffect;
import com.cell.rpg.template.TItem;
import com.cell.rpg.template.TItemList;
import com.cell.rpg.template.TShopItem;
import com.cell.rpg.template.TShopItemList;
import com.cell.rpg.template.TSkill;
import com.cell.rpg.template.TUnit;
import com.cell.sound.ISound;
import com.cell.util.concurrent.ThreadPoolService;
import com.g2d.BufferedImage;
import com.g2d.Tools;
import com.g2d.cell.CellSetResource;
import com.g2d.cell.CellSetResourceManager;


/**
 * @author WAZA
 * 第三方程序用来读入G2D Studio对象的类。
 * 该类的初始化方法只能在构造中调用。
 * 该类不需要同步，因为运行过程中只读，不能改动其内容。
 */	
@SuppressWarnings("unused")
public abstract class ResourceManager extends CellSetResourceManager
{
	public static boolean	PRINT_VERBOS 	= false;
//	--------------------------------------------------------------------------------------------------------------------
	
	final public String res_root;
	
	/*
	// res objects
	protected Hashtable<String, SceneSet>			all_scene_set;
	protected Hashtable<String, SpriteSet>			all_actor_set;
	protected Hashtable<String, SpriteSet>			all_avatar_set;
	protected Hashtable<String, SpriteSet>			all_effect_set;

	
	// xml templates dynamic object and scenes
	protected Hashtable<Integer, ItemProperties>	item_properties;
	protected Hashtable<Integer, TUnit>				tunits;
	protected Hashtable<Integer, TItem>				titems;
	protected Hashtable<Integer, TShopItem>			tshopitems;
	protected Hashtable<Integer, TAvatar>			tavatars;
	protected Hashtable<Integer, TSkill>			tskills;
	protected Hashtable<Integer, TEffect>			teffects;
	protected Hashtable<Integer, TItemList>			titemlists;
	protected Hashtable<Integer, TShopItemList>		tshopitemlists;
	protected Hashtable<Integer, Quest> 			quests;
	protected Hashtable<Integer, QuestGroup> 		questgroups;
	protected Hashtable<Integer, Scene>				scenes;
	protected Hashtable<Integer, InstanceZone>		instance_zones;

	protected Hashtable<Integer, String> 			names_item_properties;
	protected Hashtable<Integer, String>			names_tunits;
	protected Hashtable<Integer, String>			names_titems;
	protected Hashtable<Integer, String>			names_tshopitems;
	protected Hashtable<Integer, String>			names_tavatars;
	protected Hashtable<Integer, String>			names_tskills;
	protected Hashtable<Integer, String>			names_teffects;
	protected Hashtable<Integer, String>			names_titemlists;
	protected Hashtable<Integer, String>			names_tshopitemlists;
	protected Hashtable<Integer, String> 			names_quests;
	protected Hashtable<Integer, String> 			names_questgroups;
	protected Hashtable<Integer, String>			names_scenes;
	protected Hashtable<Integer, String>			names_instance_zones;
	*/
	
	// icons , sounds, talks
	protected Hashtable<String, AtomicReference<BufferedImage>>	all_icons;
	protected Hashtable<String, AtomicReference<ISound>>		all_sounds;
	protected Hashtable<String, AtomicReference<String>>		all_npc_talks;
	
	final protected ResourceMetaData			meta_data;
	
//	--------------------------------------------------------------------------------------------------------------------

	/**
	 * 子类自定义初始化什么
	 * @param res_root
	 * @throws Exception
	 */
	public ResourceManager(String res_root) throws Exception
	{
		this.res_root	= res_root;
		this.meta_data	= new ResourceMetaData();
	}

	public ResourceManager(
			String persistance_manager, 
			String res_root, 
			boolean init_set,
			boolean init_xml,
			boolean init_icon,
			boolean init_sound)  throws Exception
	{
		this.res_root	= res_root;
		this.meta_data	= new ResourceMetaData();
		
		if (init_set) 
			initAllSet();
		
		if (init_xml) 
			initAllXml(persistance_manager);
		
		if (init_icon) 
			initIcons();
		
		if (init_sound) 
			initSounds();
	}
	
	public ResourceManager(String res_root, ResourceMetaData meta)  throws Exception
	{
		this.res_root	= res_root;
		this.meta_data	= meta;

		if (meta_data.all_icons != null) {
			this.all_icons = new Hashtable<String, AtomicReference<BufferedImage>>();
			for (String name : meta_data.all_icons) {
				all_icons.put(name, new AtomicReference<BufferedImage>());
			}
		}
		if (meta_data.all_sounds != null) {
			this.all_sounds = new Hashtable<String, AtomicReference<ISound>>();
			for (String name : meta_data.all_sounds) {
				all_sounds.put(name, new AtomicReference<ISound>());
			}
		}
		if (meta_data.all_npc_talks != null) {
			this.all_npc_talks = new Hashtable<String, AtomicReference<String>>();
			for (String name : meta_data.all_npc_talks) {
				all_npc_talks.put(name, new AtomicReference<String>());
			}
		}
	}

	
//	--------------------------------------------------------------------------------------------------------------------

//	--------------------------------------------------------------------------------------------------------------------

//	abstract protected ThreadPoolService getLoadingService() ;
	
	abstract protected CellSetResource createSet(String path) throws Exception;

	@Override
	public CellSetResource getSet(String path) throws Exception {
		return super.getSet(path);
	}
	
	protected byte[] getResource(String sub_file_name) {
		byte[] data = CIO.loadData(res_root+sub_file_name);
		return data;
	}

	protected String readAllText(String path)
	{
		return readAllText(path, CObject.getEncoding());
	}
	
	protected String readAllText(String path, String encoding)
	{
		byte[] data = getResource(path);
		if (data != null) {
			return CIO.stringDecode(data, encoding);
		}
		return null;
	}

	protected String[] readAllLine(String path) {
		return readAllLine(path, CObject.getEncoding());
	}
	
	protected String[] readAllLine(String path, String encoding)
	{
		try {
			String src = readAllText(path, encoding);
			String[] ret = CUtil.splitString(src, "\n");
			for (int i = ret.length - 1; i >= 0; i--) {
				int ld = ret[i].lastIndexOf('\r');
				if (ld >= 0) {
					ret[i] = ret[i].substring(0, ld);
				}
			}
			return ret;
		} catch (Exception err) {
			err.printStackTrace();
			return new String[] { "" };
		}
	}

//	--------------------------------------------------------------------------------------------------------------------

//	--------------------------------------------------------------------------------------------------------------------

	final protected void initAllSet() throws Exception{
		initAllSet(new AtomicReference<Float>(0f));
	}

	final protected void initAllSet(AtomicReference<Float> percent) throws Exception
	{
		meta_data.all_scene_set	= readSets("/project.g2d.save/resources/scene_list.list",	SceneSet.class,		percent, 0 , 4);
		meta_data.all_actor_set	= readSets("/project.g2d.save/resources/actor_list.list",	SpriteSet.class,	percent, 1 , 4);
		meta_data.all_avatar_set= readSets("/project.g2d.save/resources/avatar_list.list",	SpriteSet.class,	percent, 2 , 4);
		meta_data.all_effect_set= readSets("/project.g2d.save/resources/effect_list.list",	SpriteSet.class,	percent, 3 , 4);
	}
	
	
	final protected void initAllXml(String persistance_manager)  throws Exception
	{
		initAllXml(persistance_manager, new AtomicReference<Float>(0f));
	}
	
	final protected void initAllXml(String persistance_manager, AtomicReference<Float> percent)  throws Exception
	{
		RPGObjectMap.setPersistanceManagerDriver(persistance_manager);
		
		meta_data.item_properties = readRPGObjects(ItemProperties.class,	percent, 0,  13);
		
		meta_data.tunits			= readRPGObjects(TUnit.class,			percent, 1,  13);
		meta_data.titems			= readRPGObjects(TItem.class,			percent, 2,  13);
		meta_data.tshopitems		= readRPGObjects(TShopItem.class,		percent, 3,  13);
		meta_data.tavatars			= readRPGObjects(TAvatar.class,			percent, 4,  13);
		meta_data.tskills			= readRPGObjects(TSkill.class,			percent, 5,  13);
		meta_data.teffects			= readRPGObjects(TEffect.class,			percent, 6,  13);
		meta_data.titemlists		= readRPGObjects(TItemList.class,		percent, 7,  13);
		meta_data.tshopitemlists	= readRPGObjects(TShopItemList.class,	percent, 8,  13);
		
		meta_data.quests			= readRPGObjects(Quest.class,			percent, 9,  13);
		meta_data.questgroups		= readRPGObjects(QuestGroup.class,		percent, 10, 13);
		
		meta_data.scenes			= readRPGObjects(Scene.class,			percent, 11, 13);
		meta_data.instance_zones	= readRPGObjects(InstanceZone.class,	percent, 12, 13);
	}
	
	final protected void initAllXmlNames()  throws Exception
	{
		initAllXmlNames(new AtomicReference<Float>(0f));
	}
	
	final protected void initAllXmlNames(AtomicReference<Float> percent)  throws Exception
	{
		percent.set(0f);
		float factor = 1f / 13f; 
		
		meta_data.names_item_properties	= readRPGObjectNames(ItemProperties.class);
		percent.set(1 * factor);
		
		meta_data.names_tunits			= readRPGObjectNames(TUnit.class);
		percent.set(2 * factor);
		meta_data.names_titems			= readRPGObjectNames(TItem.class);
		percent.set(3 * factor);
		meta_data.names_tshopitems		= readRPGObjectNames(TShopItem.class);
		percent.set(4 * factor);
		meta_data.names_tavatars		= readRPGObjectNames(TAvatar.class);
		percent.set(5 * factor);
		meta_data.names_tskills			= readRPGObjectNames(TSkill.class);
		percent.set(6 * factor);
		meta_data.names_teffects		= readRPGObjectNames(TEffect.class);
		percent.set(7 * factor);
		meta_data.names_titemlists		= readRPGObjectNames(TItemList.class);
		percent.set(8 * factor);
		meta_data.names_tshopitemlists	= readRPGObjectNames(TShopItemList.class);
		percent.set(9 * factor);
		
		meta_data.names_quests			= readRPGObjectNames(Quest.class);
		percent.set(10 * factor);
		meta_data.names_questgroups		= readRPGObjectNames(QuestGroup.class);
		percent.set(11 * factor);
		meta_data.names_scenes			= readRPGObjectNames(Scene.class);
		percent.set(12 * factor);
		meta_data.names_instance_zones	= readRPGObjectNames(InstanceZone.class);
		percent.set(13 * factor);
	}
	
	public static class WrapperPercent
	{
		final AtomicReference<Float> 				src;
		final ArrayList<AtomicReference<Float>> 	subs;
		
		public WrapperPercent(AtomicReference<Float> src, int size) {
			this.src	= src;
			this.subs	= new ArrayList<AtomicReference<Float>>(size);
			for (int i=0; i<size; i++) {
				this.subs.add(new AtomicReference<Float>());
			}
		}
		
		private void set(float percent) {
			
		}
	}
	
	final protected void initIcons()
	{
		meta_data.all_icons = readIcons("/project.g2d.save/icons/icon.list" );
		this.all_icons = new Hashtable<String, AtomicReference<BufferedImage>>();
		for (String name : meta_data.all_icons) {
			all_icons.put(name, new AtomicReference<BufferedImage>());
		}
	}
	
	final protected void initSounds()
	{
		meta_data.all_sounds	= readSounds("/project.g2d.save/sounds/sound.list" );
		this.all_sounds = new Hashtable<String, AtomicReference<ISound>>();
		for (String name : meta_data.all_sounds) {
			all_sounds.put(name, new AtomicReference<ISound>());
		}
	}
	
	final protected void initNpcTalks() 
	{
		meta_data.all_npc_talks = readNpcTalks("/project.g2d.save/talks/talks.list" );
		this.all_npc_talks = new Hashtable<String, AtomicReference<String>>();
		for (String name : meta_data.all_npc_talks) {
			all_npc_talks.put(name, new AtomicReference<String>());
		}
	}
	
//	--------------------------------------------------------------------------------------------------------------------

	
	final protected <T extends ResourceSet<?>> Hashtable<String, T> readSets(
			String file, 
			Class<T> type, 
			AtomicReference<Float> percent, int step, int total
			) throws Exception
	{		
		float init = (float)step / (float)total;
		percent.set(init);

		System.out.println("list resource : " + file);
		
		Hashtable<String, T> table = new Hashtable<String, T>();
		
		String[] res_list = readAllLine(file, "UTF-8");
		
		for (int i=0; i<res_list.length; i++)
		{
			String[] split = CUtil.splitString(res_list[i].trim(), ";");
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
			
			percent.set(init  + ((float)i / (float)res_list.length) / total);
		}
		
		System.out.println("size : " + table.size());
		
		return table;
	}

	final protected HashSet<String> readIcons(String icon_list)
	{
		System.out.println("list icons : " + icon_list);

		HashSet<String> table = new HashSet<String>();
		
		String[] res_list = readAllLine(icon_list, "UTF-8");
		
		for (int i=0; i<res_list.length; i++)
		{
			String[] split 	= CUtil.splitString(res_list[i].trim(), ",");
			String icon_id 	= split[0];
			String icon_w 	= split[1];
			String icon_h 	= split[2];
			table.add(icon_id);
			
			if (PRINT_VERBOS)
			System.out.println("\tget icon : " + icon_id + "(" + icon_w + "x" + icon_h + ")");
		}
		
		System.out.println("size : " + table.size());

		return table;
	}


	final protected HashSet<String> readSounds(String sound_list)
	{
		System.out.println("list sounds : " + sound_list);

		HashSet<String> table = new HashSet<String>();
		
		String[] res_list = readAllLine(sound_list, "UTF-8");
		
		for (int i=0; i<res_list.length; i++)
		{
			table.add(res_list[i].trim());
			if (PRINT_VERBOS)
			System.out.println("\tget sound : " + res_list[i]);
		}
		
		System.out.println("list sounds : " + table.size());

		return table;
	}
	
	final protected HashSet<String> readNpcTalks(String talklist)
	{
		System.out.println("list npc talks : " + talklist);

		HashSet<String> table = new HashSet<String>();
		
		String[] res_list = readAllLine(talklist, "UTF-8");
		
		for (int i=0; i<res_list.length; i++)
		{
			table.add(res_list[i].trim());
			if (PRINT_VERBOS)
			System.out.println("\tget npc talk : " + res_list[i]);
		}
		
		System.out.println("list npc talks : " + table.size());

		return table;
	}

//	--------------------------------------------------------------------------------------------------------------------------------------
	
	public <T extends RPGObject>  String toListFile(Class<T> type) 
	{
		if (type.equals(ItemProperties.class)) {
			return "/project.g2d.save/item_properties/item_properties.list";
		}
		else if (type.equals(Quest.class)) {
			return "/project.g2d.save/quests/quest.list";					
		}
		else if (type.equals(QuestGroup.class)) {
			return "/project.g2d.save/questgroups/questgroups.list";					
		}
		else if (type.equals(Scene.class)) {
			return "/project.g2d.save/scenes/scene.list";					
		}
		else if (type.equals(InstanceZone.class)) {
			return "/project.g2d.save/instance_zones/zones.list";					
		}
		else {
			String type_name = type.getSimpleName().toLowerCase();
			return "/project.g2d.save/objects/" + type_name + ".obj" + "/" + type_name + ".list";
		}
	}
	
	public <T extends RPGObject>  String toNameListFile(Class<T> type) 
	{
		if (type.equals(ItemProperties.class)) {
			return "/project.g2d.save/item_properties/name_item_properties.list";
		}
		else if (type.equals(Quest.class)) {
			return "/project.g2d.save/quests/name_quest.list";					
		}
		else if (type.equals(QuestGroup.class)) {
			return "/project.g2d.save/questgroups/name_questgroups.list";					
		}
		else if (type.equals(Scene.class)) {
			return "/project.g2d.save/scenes/name_scene.list";					
		}
		else if (type.equals(InstanceZone.class)) {
			return "/project.g2d.save/instance_zones/name_zones.list";					
		}
		else {
			String type_name = type.getSimpleName().toLowerCase();
			return "/project.g2d.save/objects/" + type_name + ".obj" + "/name_" + type_name + ".list";
		}
	}
	

	final protected <T extends RPGObject> Hashtable<Integer, String> readRPGObjectNames(Class<T> type) throws Exception
	{
		String list_file = toNameListFile(type);
		
		System.out.println("list rpg object names : " + list_file);

		Hashtable<Integer, String> table = new Hashtable<Integer, String>();
		
		String[] res_list = readAllLine(list_file, "UTF-8");
		
		for (String line : res_list) {
			line = line.trim();
			int bl = line.indexOf("(");
			int br = line.indexOf(")");
			String id = line.substring(bl+1, br);
			if (br < line.length()) {
				table.put(Integer.parseInt(id), line.substring(br+1));
			} else {
				table.put(Integer.parseInt(id), "");
			}
		}
		
		return table;
	}
	
	
	final protected <T extends RPGObject> Hashtable<Integer, T> readRPGObjects(
			Class<T> type, 
			AtomicReference<Float> percent, int step, int total) throws Exception
	{	
		float init = (float)step / (float)total;
		percent.set(init);
		
		String list_file = toListFile(type);
		
		System.out.println("list rpg objects : " + list_file);
		
		String tdir = list_file;
		tdir		= tdir.replace('\\', '/');
		tdir		= tdir.substring(0, tdir.lastIndexOf("/"));
		
		Hashtable<Integer, T> table = new Hashtable<Integer, T>();
		
		String[] res_list = readAllLine(list_file, "UTF-8");
		
		for (int i=0; i<res_list.length; i++)
		{
			String display_list = res_list[i];
			int last_split = res_list[i].lastIndexOf("/");
			if (last_split>=0) {
				res_list[i] = res_list[i].substring(last_split+1);
			}
			
			String xml_file = tdir +"/"+ res_list[i];
			
			T set = RPGObjectMap.readNode(new ByteArrayInputStream(getResource(xml_file)), xml_file, type);
			set.loadTreePath(this, display_list);
			if (PRINT_VERBOS)
			System.out.println("\tget " + type.getSimpleName() + " : " + set + "(" + set.id + ")");

			table.put(Integer.parseInt(set.id), set);
			
			percent.set(init  + ((float)i / (float)res_list.length) / total);
		}
		
		System.out.println("size : " + table.size());
		
		return table;
	}

	
//	final public <T extends RPGObject> T readRPGObject(String xml_file, Class<T> type) 
//	{
//		T set = RPGObjectMap.readNode(new ByteArrayInputStream(getResource(xml_file)), xml_file, type);			
//		if (PRINT_VERBOS)
//		System.out.println("readRPGObject : " + type.getSimpleName() + " : " + set + "(" + set.id + ")");
//		return set;
//	}

//	--------------------------------------------------------------------------------------------------------------------
//	SetResources
//	--------------------------------------------------------------------------------------------------------------------

	private static void SetResources_____________________________________________________(){}
	
	public SceneSet getSceneSet(String cpj_name, String obj_name) throws Exception{
		SceneSet set = meta_data.all_scene_set.get(ResourceSet.toID(cpj_name, obj_name));
		set.loadSetObject(this);
		return set;
	}

	public SpriteSet getActorSet(String cpj_name, String obj_name) throws Exception{
		SpriteSet set = meta_data.all_actor_set.get(ResourceSet.toID(cpj_name, obj_name));
		set.loadSetObject(this);
		return set;
	}
	
	public SpriteSet getAvatarSet(String cpj_name, String obj_name) throws Exception{
		SpriteSet set = meta_data.all_avatar_set.get(ResourceSet.toID(cpj_name, obj_name));
		set.loadSetObject(this);
		return set;
	}
	
	public SpriteSet getEffectSet(String cpj_name, String obj_name) throws Exception{
		SpriteSet set = meta_data.all_effect_set.get(ResourceSet.toID(cpj_name, obj_name));
		set.loadSetObject(this);
		return set;
	}
	
	public Vector<SceneSet> getAllScenes() {
		return new Vector<SceneSet>(meta_data.all_scene_set.values());
	}
	
	public Vector<SpriteSet> getAllActors() {
		return new Vector<SpriteSet>(meta_data.all_actor_set.values());
	}
	
	public Vector<SpriteSet> getAllAvatars() {
		return new Vector<SpriteSet>(meta_data.all_avatar_set.values());
	}
	
	public Vector<SpriteSet> getAllEffects() {
		return new Vector<SpriteSet>(meta_data.all_effect_set.values());
	}

	public StreamTiles getEffectImages(String cpj_project_name, String cpj_sprite_name) {
		try
		{
			SpriteSet 							effect_set 	= getEffectSet(cpj_project_name, cpj_sprite_name);
			CellSetResource 					resource 	= effect_set.getSetResource(this);
			com.cell.gameedit.object.SpriteSet 	spr_set 	= effect_set.getSetObject(this);
			StreamTiles							images		= resource.getImages(spr_set.ImagesName);
			return images;
		} catch (Exception err) {
			err.printStackTrace();
		}
		return null;
	}
	
	public BufferedImage getEffectImage(String cpj_project_name, String cpj_sprite_name, int index)
	{
		try
		{
			StreamTiles images = getEffectImages(cpj_project_name, cpj_sprite_name);
			IImage image = images.getImage(index);
			return Tools.createImage(image);
		} catch (Exception err) {
			err.printStackTrace();
		}
		return null;
	}
	
	public CSprite getEffectSprite(String cpj_project_name, String cpj_sprite_name) {
		try
		{
			SpriteSet							effect_set	= getEffectSet(cpj_project_name, cpj_sprite_name);
			CellSetResource						resource 	= effect_set.getSetResource(this);
			com.cell.gameedit.object.SpriteSet 	spr_set 	= effect_set.getSetObject(this);
			CSprite								sprite		= resource.getSprite(spr_set);
			return sprite;
		} catch (Exception err) {
			err.printStackTrace();
		}
		return null;
	}
	
	
//	--------------------------------------------------------------------------------------------------------------------
//	Templates
//	--------------------------------------------------------------------------------------------------------------------
	private static void Templates_____________________________________________________(){}
	
	public TUnit getTUnit(int id) {
		return meta_data.tunits.get(id);
	}
	public TItem getTItem(int id) {
		return meta_data.titems.get(id);
	}
	public TShopItem getTShopItem(int id) {
		return meta_data.tshopitems.get(id);
	}
	public TAvatar getTAvatar(int id) {
		return meta_data.tavatars.get(id);
	}
	public TSkill getTSkill(int id) {
		return meta_data.tskills.get(id);
	}
	public TEffect getTEffect(int id) {
		return meta_data.teffects.get(id);
	}
	public TItemList getItemList(int id) {
		return meta_data.titemlists.get(id);
	}
	public TShopItemList getShopItemList(int id) {
		return meta_data.tshopitemlists.get(id);
	}
	
	
	public String getTUnitName(int id) {
		return meta_data.names_tunits.get(id);
	}
	public String getTItemName(int id) {
		return meta_data.names_titems.get(id);
	}
	public String getTShopItemName(int id) {
		return meta_data.names_tshopitems.get(id);
	}
	public String getTAvatarName(int id) {
		return meta_data.names_tavatars.get(id);
	}
	public String getTSkillName(int id) {
		return meta_data.names_tskills.get(id);
	}
	public String getTEffectName(int id) {
		return meta_data.names_teffects.get(id);
	}
	public String getTItemListName(int id) {
		return meta_data.names_titemlists.get(id);
	}
	public String getTShopItemListName(int id) {
		return meta_data.names_tshopitemlists.get(id);
	}
	
	public Vector<TItemList> getAllItemList() {
		return new Vector<TItemList>(meta_data.titemlists.values());
	}
	public Vector<TShopItemList> getAllShopItemList() {
		return new Vector<TShopItemList>(meta_data.tshopitemlists.values());
	}

	public Vector<TAvatar> getAllTAvatar() {
		return new Vector<TAvatar>(meta_data.tavatars.values());
	}
//	--------------------------------------------------------------------------------------------------------------------
//	ItemProperties
//	--------------------------------------------------------------------------------------------------------------------
	private static void ItemProperties_____________________________________________________(){}
	
	public Hashtable<Integer, ItemProperties> getAllItemProperties() {
		return new Hashtable<Integer, ItemProperties>(meta_data.item_properties);
	}

	public ItemProperties getItemProperties(int id) {
		return meta_data.item_properties.get(id);
	}

	public String getItemPropertiesName(int id) {
		return meta_data.names_item_properties.get(id);
	}
	
//	--------------------------------------------------------------------------------------------------------------------
//	Quests
//	--------------------------------------------------------------------------------------------------------------------
	private static void Quests_____________________________________________________(){}
	
	public Hashtable<Integer, Quest> getAllQuests(){
		return new Hashtable<Integer, Quest>(meta_data.quests);
	}

	public Quest getQuest(int quest_id) {
		return meta_data.quests.get(quest_id);
	}
	
	public Hashtable<Integer, QuestGroup> getAllQuestGroups() {
		return new Hashtable<Integer, QuestGroup>(meta_data.questgroups);
	}
	
	public QuestGroup getQuestGroup(int quest_group_id) {
		return meta_data.questgroups.get(quest_group_id);
	}

	public String getQuestName(int id) {
		return meta_data.names_quests.get(id);
	}
	
	public String getQuestGroupName(int id) {
		return meta_data.names_questgroups.get(id);
	}
	
//	--------------------------------------------------------------------------------------------------------------------
//	Scenes
//	--------------------------------------------------------------------------------------------------------------------
	private static void Scenes_____________________________________________________(){}
	
	public Scene getRPGScene(int id) {
		return meta_data.scenes.get(id);
	}

	public String getRPGSceneName(int id) {
		return meta_data.names_scenes.get(id);
	}
	
	public Hashtable<Integer, Scene> getAllRPGScenes() {
		return new Hashtable<Integer, Scene>(meta_data.scenes);
	}
	
	public SceneGraph createSceneGraph() {
		return new SceneGraph(meta_data.scenes.values());
	}
	
	
	

	public InstanceZone getInstanceZone(int id) {
		return meta_data.instance_zones.get(id);
	}

	public String getInstanceZoneName(int id) {
		return meta_data.names_instance_zones.get(id);
	}
	
	public Hashtable<Integer, InstanceZone> getAllInstanceZone() {
		return new Hashtable<Integer, InstanceZone>(meta_data.instance_zones);
	}
	
	
	
//	--------------------------------------------------------------------------------------------------------------------
//	EditResources
//	--------------------------------------------------------------------------------------------------------------------
	private static void EditResources_____________________________________________________(){}
	
	public AtomicReference<BufferedImage> getIcon(String index) {
		return all_icons.get(index);
	}

	public ISound getSound(String index) {
		return all_sounds.get(index).get();
	}

	public String getNpcTalk(String index) {
		return all_npc_talks.get(index).get();
	}

	

	protected void check() throws Exception
	{
		ArrayList<String> errors = new ArrayList<String>();
		
		for (TUnit unit : meta_data.tunits.values()) {
			checkQuest(unit, errors,
					" unit="+unit.getName() + "("+unit.getIntID()+")");
		}
		
		for (Scene s : getAllRPGScenes().values()) {
			for (SceneUnit su : s.scene_units) {
				checkQuest(su, errors,
						" scene="+s.getName()+"("+s.getIntID()+")" + 
						" unit="+su.getName());
			}
		}
		
		if (!errors.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (String err : errors) {
				sb.append(err + "\n");
			}
			throw new Exception("resource check failed !\n" + sb);
		}
	}
	
	protected void checkQuest(RPGObject obj, ArrayList<String> errors, String obj_info) 
	{
		for (QuestPublisher qp : obj.getAbilities(QuestPublisher.class)) {
			if (getQuest(qp.quest_id) == null) {
				errors.add("quest not found : quest_id=" + qp.quest_id + obj_info);
			}
		}
		for (QuestAccepter qa : obj.getAbilities(QuestAccepter.class)) {
			if (getQuest(qa.quest_id) == null) {
				errors.add("quest not found : quest_id=" + qa.quest_id + obj_info);
			}
		}
	}
	
}
