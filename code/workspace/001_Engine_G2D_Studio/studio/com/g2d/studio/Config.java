package com.g2d.studio;




public class Config extends com.cell.util.Config
{
	public static String TITLE						= "G2DStudio";
	
	// 角色，场景，特效资源目录
	public static String RES_ACTOR_ROOT				= "character";
	public static String RES_AVATAR_ROOT			= "avatar";
	public static String RES_EFFECT_ROOT			= "effect";
	public static String RES_SCENE_ROOT				= "scene";
	
	// XLS模板数据目录
	public static String XLS_TPLAYER				= "xls/tplayer.xls";
	public static String XLS_TUNIT					= "xls/tnpc.xls";
	public static String XLS_TITEM					= "xls/titem.xls";
	public static String XLS_TSKILL					= "xls/tskill.xls";

	// 声音和图像资源目录
	public static String SOUND_ROOT					= "sound";
	public static String SOUND_SUFFIX				= ".ogg";
	public static String ICON_ROOT					= "icons";
	public static String ICON_SUFFIX				= ".png";
	
	// 低级界面默认的FPS
	public static Integer DEFAULT_FPS				= 30;
	
	
	// 动态编译的Java文件目录
	public static String PLUGINS_ROOT				= "plugins";
	public static String PLUGIN_ITEM_TYPES;			//动态编译的道具属性类型


	// 动态加载的类
	public static String DYNAMIC_QUEST_PLAYER_CLASS;//与TriggerUnitMethod映射的类型PLAYER
	public static String DYNAMIC_QUEST_PET_CLASS;	//与TriggerUnitMethod映射的类型PET
	public static String DYNAMIC_QUEST_NPC_CLASS;	//与TriggerUnitMethod映射的类型NPC
	
	// 资源编辑器
	public static String CELL_GAME_EDIT_CMD			= "CellGameEdit.exe";
	
}
