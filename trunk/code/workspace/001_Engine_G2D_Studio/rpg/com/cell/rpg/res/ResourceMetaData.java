package com.cell.rpg.res;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
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


public class ResourceMetaData implements Serializable
{
	private static final long serialVersionUID = 1L;

	// res objects
	public Hashtable<String, SceneSet>			all_scene_set;
	public Hashtable<String, SpriteSet>			all_actor_set;
	public Hashtable<String, SpriteSet>			all_avatar_set;
	public Hashtable<String, SpriteSet>			all_effect_set;

	// xml templates dynamic object and scenes
	public Hashtable<Integer, ItemProperties>	item_properties;
	public Hashtable<Integer, TUnit>			tunits;
	public Hashtable<Integer, TItem>			titems;
	public Hashtable<Integer, TShopItem>		tshopitems;
	public Hashtable<Integer, TAvatar>			tavatars;
	public Hashtable<Integer, TSkill>			tskills;
	public Hashtable<Integer, TEffect>			teffects;
	public Hashtable<Integer, TItemList>		titemlists;
	public Hashtable<Integer, TShopItemList>	tshopitemlists;
	public Hashtable<Integer, Quest> 			quests;
	public Hashtable<Integer, QuestGroup> 		questgroups;
	public Hashtable<Integer, Scene>			scenes;
	public Hashtable<Integer, InstanceZone>		instance_zones;

	public Hashtable<Integer, String> 			names_item_properties;
	public Hashtable<Integer, String>			names_tunits;
	public Hashtable<Integer, String>			names_titems;
	public Hashtable<Integer, String>			names_tshopitems;
	public Hashtable<Integer, String>			names_tavatars;
	public Hashtable<Integer, String>			names_tskills;
	public Hashtable<Integer, String>			names_teffects;
	public Hashtable<Integer, String>			names_titemlists;
	public Hashtable<Integer, String>			names_tshopitemlists;
	public Hashtable<Integer, String> 			names_quests;
	public Hashtable<Integer, String> 			names_questgroups;
	public Hashtable<Integer, String>			names_scenes;
	public Hashtable<Integer, String>			names_instance_zones;
	
	// icons , sounds, talks
	public HashSet<String>						all_icons;
	public HashSet<String>						all_sounds;
	public HashSet<String>						all_npc_talks;
	
//	--------------------------------------------------------------------------------------------------------------------

}
