package com.g2d.studio;

import com.g2d.studio.cell.gameedit.Builder;




public class Config extends com.cell.util.Config
{
	public static String TITLE						= "G2DStudio";

//	--------------------------------------------------------------------------------------------------------
//	数据
//	--------------------------------------------------------------------------------------------------------
	
//	 角色，场景，特效资源目录
	public static String RES_ACTOR_ROOT				= "character";
	public static String RES_AVATAR_ROOT			= "avatar";
	public static String RES_EFFECT_ROOT			= "effect";
	public static String RES_SCENE_ROOT				= "scene";
	
//	 XLS模板数据目录
	public static String XLS_TPLAYER				= "xls/tplayer.xls";
	public static String XLS_TUNIT					= "xls/tnpc.xls";
	public static String XLS_TITEM					= "xls/titem.xls";
	public static String XLS_TSHOPITEM				= "xls/tshopitem.xls";
	public static String XLS_TSKILL					= "xls/tskill.xls";

//	 声音和图像资源目录
	public static String SOUND_ROOT					= "sound";
	public static String SOUND_SUFFIX				= ".ogg";
	public static String ICON_ROOT					= "icons";
	public static String ICON_SUFFIX				= ".png";
	public static String TALK_ROOT					= "npctalk";
	public static String TALK_SUFFIX				= ".js";
	
	/**NPC说话脚本例子*/
	public static String TALK_EXAMPLE				= null;
	
	/**低级界面默认的FPS*/
	public static Integer DEFAULT_FPS				= 30;

//	--------------------------------------------------------------------------------------------------------
//	(插件类)
//	--------------------------------------------------------------------------------------------------------
//	动态加载的类(插件类)
	
	/**所有道具属性*/
	public static String DYNAMIC_ITEM_PROPERTY_MANAGER_CLASS;
	/**与TriggerUnitMethod映射的类型PLAYER*/
	public static String DYNAMIC_QUEST_PLAYER_CLASS;
	/**与TriggerUnitMethod映射的类型PET*/
	public static String DYNAMIC_QUEST_PET_CLASS;
	/**与TriggerUnitMethod映射的类型NPC*/
	public static String DYNAMIC_QUEST_NPC_CLASS;
	
	/**XML持久化工具*/
	public static String PERSISTANCE_MANAGER		= "com.cell.xstream.XStreamAdapter";
	/**SQL持久化工具*/
	public static String PERSISTANCE_SQL_TYPE		= "com.cell.mysql.SQLTypeComparerMySQL";
	
//	--------------------------------------------------------------------------------------------------------
//	 扩展资源编辑器
//	--------------------------------------------------------------------------------------------------------
	
	/**执行Builder的类*/
	public static String BUILDER_CLASS				= "com.g2d.studio.cell.gameedit.EatBuilder";
	
	/**CellGameEdit可执行文件*/
	public static String CELL_GAME_EDIT_CMD			= "CellGameEdit.exe";
	
	/**保存的导出精灵批处理<br>
	 * {file}变量为cpj文件*/
	public static String CELL_BUILD_SPRITE_CMD		= 
		"@java -classpath \"g2dstudio.jar\" com.g2d.studio.cell.gameedit.EatBuilder \"{file}\" \"sprite\"";
	
	/**保存的导出场景批处理<br>
	 * {file}变量为cpj文件*/
	public static String CELL_BUILD_SCENE_CMD		= 
		"@java -classpath \"g2dstudio.jar\" com.g2d.studio.cell.gameedit.EatBuilder \"{file}\" \"scene\"";	

}
