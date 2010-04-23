package com.cell.rpg.res;

import java.awt.image.BufferedImage;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicReference;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.gfx.IImage;
import com.cell.rpg.RPGObject;
import com.cell.rpg.io.RPGObjectMap;
import com.cell.rpg.item.ItemProperties;
import com.cell.rpg.quest.Quest;
import com.cell.rpg.quest.QuestGroup;
import com.cell.rpg.res.ResourceSet.SceneSet;
import com.cell.rpg.res.ResourceSet.SpriteSet;
import com.cell.rpg.scene.Scene;
import com.cell.rpg.scene.graph.SceneGraph;
import com.cell.rpg.template.TAvatar;
import com.cell.rpg.template.TEffect;
import com.cell.rpg.template.TItem;
import com.cell.rpg.template.TItemList;
import com.cell.rpg.template.TShopItem;
import com.cell.rpg.template.TShopItemList;
import com.cell.rpg.template.TSkill;
import com.cell.rpg.template.TUnit;
import com.cell.rpg.template.TemplateNode;
import com.cell.sound.ISound;
import com.g2d.Tools;
import com.g2d.cell.CellSetResource;
import com.g2d.cell.CellSetResourceManager;


/**
 * @author WAZA
 * 第三方程序用来读入G2D Studio对象的类。
 * 该类的初始化方法只能在构造中调用。
 * 该类不需要同步，因为运行过程中只读，不能改动其内容。
 */
public abstract class ResourceManager extends CellSetResourceManager
{
	public static boolean	PRINT_VERBOS 	= false;
//	--------------------------------------------------------------------------------------------------------------------
	
	final public String res_root;
	final public String save_dir;
	
	
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
	
	
	// icons , sounds, talks
	protected Hashtable<String, AtomicReference<BufferedImage>>	all_icons;
	protected Hashtable<String, AtomicReference<ISound>>		all_sounds;
	protected Hashtable<String, AtomicReference<String>>		all_npc_talks;
	
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

	abstract protected ThreadPoolExecutor getLoadingService() ;
	
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
	
		item_properties = readRPGObjects(save_dir + "/item_properties/item_properties.list",	ItemProperties.class);
		
		tunits			= readTemplateObjects(TUnit.class);
		titems			= readTemplateObjects(TItem.class);
		tshopitems		= readTemplateObjects(TShopItem.class);
		tavatars		= readTemplateObjects(TAvatar.class);
		tskills			= readTemplateObjects(TSkill.class);
		teffects		= readTemplateObjects(TEffect.class);
		titemlists		= readTemplateObjects(TItemList.class);
		tshopitemlists	= readTemplateObjects(TShopItemList.class);
		
		quests			= readRPGObjects(save_dir + "/quests/quest.list",						Quest.class);
		questgroups		= readRPGObjects(save_dir + "/questgroups/questgroups.list",			QuestGroup.class);
		
		scenes			= readRPGObjects(save_dir + "/scenes/scene.list", 						Scene.class);
	}
	
	final protected void initAllXmlNames()  throws Exception
	{
		names_item_properties	= readRPGObjectNames(save_dir + "/item_properties/name_item_properties.list");
		
		names_tunits			= readRPGObjectNames(toNameListFile(TUnit.class));
		names_titems			= readRPGObjectNames(toNameListFile(TItem.class));
		names_tshopitems		= readRPGObjectNames(toNameListFile(TShopItem.class));
		names_tavatars			= readRPGObjectNames(toNameListFile(TAvatar.class));
		names_tskills			= readRPGObjectNames(toNameListFile(TSkill.class));
		names_teffects			= readRPGObjectNames(toNameListFile(TEffect.class));
		names_titemlists		= readRPGObjectNames(toNameListFile(TItemList.class));
		names_tshopitemlists	= readRPGObjectNames(toNameListFile(TShopItemList.class));
		
		names_quests			= readRPGObjectNames(save_dir + "/quests/name_quest.list");
		names_questgroups		= readRPGObjectNames(save_dir + "/questgroups/name_questgroups.list");
		names_scenes			= readRPGObjectNames(save_dir + "/scenes/name_scene.list");
	}
	
	final protected void initIcons()
	{
		all_icons	= readIcons(save_dir + "/icons/icon.list" );
	}
	
	final protected void initSounds()
	{
		all_sounds	= readSounds(save_dir + "/sounds/sound.list" );
	}
	
	final protected void initNpcTalks() 
	{
		all_npc_talks = readNpcTalks(save_dir + "/talks/talks.list" );
	}
	
//	--------------------------------------------------------------------------------------------------------------------

	protected CellSetResource createSet(String path) throws Exception
	{
		return new Resource(path, getLoadingService());
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
	
	final protected Hashtable<String, AtomicReference<String>> readNpcTalks(String talklist)
	{
		System.out.println("list npc talks : " + talklist);

		Hashtable<String, AtomicReference<String>> table = new Hashtable<String, AtomicReference<String>>();
		
		String[] res_list = CIO.readAllLine(talklist, "UTF-8");
		
		for (int i=0; i<res_list.length; i++)
		{
			table.put(res_list[i].trim(), new AtomicReference<String>(null));
			if (PRINT_VERBOS)
			System.out.println("\tget npc talk : " + res_list[i]);
		}
		
		System.out.println("list npc talks : " + table.size());

		return table;
	}

	final protected Hashtable<Integer, String> readRPGObjectNames(String list_file) throws Exception
	{
		System.out.println("list rpg object names : " + list_file);

		Hashtable<Integer, String> table = new Hashtable<Integer, String>();
		
		String[] res_list = CIO.readAllLine(list_file, "UTF-8");
		
		for (String line : res_list) {
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
	
	final public <T extends RPGObject>  String toListFile(Class<T> type) 
	{
		String type_name = type.getSimpleName().toLowerCase();
		return save_dir + 
		"/objects/" + type_name + ".obj" + 
		"/" + type_name + ".list";
	}
	
	final public <T extends RPGObject>  String toNameListFile(Class<T> type) 
	{
		String type_name = type.getSimpleName().toLowerCase();
		return save_dir + 
		"/objects/" + type_name + ".obj" + 
		"/name_" + type_name + ".list";
	}
	
	final protected <T extends RPGObject> Hashtable<Integer, T> readTemplateObjects(Class<T> type) throws Exception
	{
		return readRPGObjects(toListFile(type), type);
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
//	SetResources
//	--------------------------------------------------------------------------------------------------------------------
	private static void SetResources_____________________________________________________(){}
	
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

	public CellSetResource.StreamTiles getEffectImages(String cpj_project_name, String cpj_sprite_name) {
		try
		{
			SpriteSet					effect_set	= getEffectSet(cpj_project_name, cpj_sprite_name);
			CellSetResource				resource 	= effect_set.getSetResource(this);
			CellSetResource.SpriteSet 	spr_set 	= effect_set.getSetObject(this);
			CellSetResource.StreamTiles	images		= resource.getImages(spr_set.ImagesName);
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
			CellSetResource.StreamTiles	images		= getEffectImages(cpj_project_name, cpj_sprite_name);
			IImage						image		= images.getImage(index);
			return Tools.createImage(image);
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
		return tunits.get(id);
	}
	public TItem getTItem(int id) {
		return titems.get(id);
	}
	public TShopItem getTShopItem(int id) {
		return tshopitems.get(id);
	}
	public TAvatar getTAvatar(int id) {
		return tavatars.get(id);
	}
	public TSkill getTSkill(int id) {
		return tskills.get(id);
	}
	public TEffect getTEffect(int id) {
		return teffects.get(id);
	}
	public TItemList getItemList(int id) {
		return titemlists.get(id);
	}
	public TShopItemList getShopItemList(int id) {
		return tshopitemlists.get(id);
	}
	
	
	public String getTUnitName(int id) {
		return names_tunits.get(id);
	}
	public String getTItemName(int id) {
		return names_titems.get(id);
	}
	public String getTShopItemName(int id) {
		return names_tshopitems.get(id);
	}
	public String getTAvatarName(int id) {
		return names_tavatars.get(id);
	}
	public String getTSkillName(int id) {
		return names_tskills.get(id);
	}
	public String getTEffectName(int id) {
		return names_teffects.get(id);
	}
	public String getTItemListName(int id) {
		return names_titemlists.get(id);
	}
	public String getTShopItemListName(int id) {
		return names_tshopitemlists.get(id);
	}
	
	public Vector<TItemList> getAllItemList() {
		return new Vector<TItemList>(titemlists.values());
	}
	public Vector<TShopItemList> getAllShopItemList() {
		return new Vector<TShopItemList>(tshopitemlists.values());
	}
	
//	--------------------------------------------------------------------------------------------------------------------
//	ItemProperties
//	--------------------------------------------------------------------------------------------------------------------
	private static void ItemProperties_____________________________________________________(){}
	
	public Hashtable<Integer, ItemProperties> getAllItemProperties() {
		return new Hashtable<Integer, ItemProperties>(item_properties);
	}

	public ItemProperties getItemProperties(int id) {
		return item_properties.get(id);
	}

	public String getItemPropertiesName(int id) {
		return names_item_properties.get(id);
	}
	
//	--------------------------------------------------------------------------------------------------------------------
//	Quests
//	--------------------------------------------------------------------------------------------------------------------
	private static void Quests_____________________________________________________(){}
	
	public Hashtable<Integer, Quest> getAllQuests(){
		return new Hashtable<Integer, Quest>(quests);
	}

	public Quest getQuest(int quest_id) {
		return quests.get(quest_id);
	}
	
	public Hashtable<Integer, QuestGroup> getAllQuestGroups() {
		return new Hashtable<Integer, QuestGroup>(questgroups);
	}
	
	public QuestGroup getQuestGroup(int quest_group_id) {
		return questgroups.get(quest_group_id);
	}

	public String getQuestName(int id) {
		return names_quests.get(id);
	}
	
	public String getQuestGroupName(int id) {
		return names_questgroups.get(id);
	}
	
//	--------------------------------------------------------------------------------------------------------------------
//	Scenes
//	--------------------------------------------------------------------------------------------------------------------
	private static void Scenes_____________________________________________________(){}
	
	public Scene getRPGScene(int id) {
		return scenes.get(id);
	}

	public String getRPGSceneName(int id) {
		return names_scenes.get(id);
	}
	
	public Hashtable<Integer, Scene> getAllRPGScenes() {
		return new Hashtable<Integer, Scene>(scenes);
	}
	
	public SceneGraph createSceneGraph() {
		return new SceneGraph(scenes.values());
	}
	
//	--------------------------------------------------------------------------------------------------------------------
//	EditResources
//	--------------------------------------------------------------------------------------------------------------------
	private static void EditResources_____________________________________________________(){}
	
	public BufferedImage getIcon(String index)
	{
		return all_icons.get(index).get();
	}
	
	public ISound getSound(String index)
	{
		return all_sounds.get(index).get();
	}
	
	public String getNpcTalk(String index)
	{
		return all_npc_talks.get(index).get();
	}

//	--------------------------------------------------------------------------------------------------------------------
//	Name List
//	--------------------------------------------------------------------------------------------------------------------
	
//	public Resource getEffectResource(String cpj_project_name) {
//		try {
//			Resource resource = getSet(res_root + "/" + "" + cpj_project_name);
//			return resource;
//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	

	
}
