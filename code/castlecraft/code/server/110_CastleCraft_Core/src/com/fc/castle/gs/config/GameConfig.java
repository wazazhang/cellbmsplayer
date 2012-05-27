package com.fc.castle.gs.config;

import com.cell.util.Config;
import com.cell.util.anno.ConfigField;
import com.cell.util.anno.ConfigSeparator;
import com.cell.util.anno.ConfigType;
import com.fc.castle.data.ItemData;
import com.fc.castle.data.ItemDatas;
import com.fc.castle.data.ShopItem;
import com.fc.castle.data.ShopItems;
import com.fc.castle.data.SkillData;
import com.fc.castle.data.SkillDatas;
import com.fc.castle.data.SoldierData;
import com.fc.castle.data.SoldierDatas;



@ConfigType("游戏配置")
public class GameConfig extends Config
{
//	---------------------------------------------------------------------
	@ConfigSeparator("玩家基础数据")
//	---------------------------------------------------------------------
	
	@ConfigField("玩家基础出战单位数量")
	public static int			PLAYER_BASE_SOLDIER 	= 5;
	@ConfigField("玩家基础单位")
	public static SoldierDatas	PLAYER_INIT_SOLDIER		= new SoldierDatas(new SoldierData(1), new SoldierData(4));
	
	@ConfigField("玩家基础出战技能数量")
	public static int			PLAYER_BASE_SKILL	 	= 5;
	@ConfigField("玩家基础技能")
	public static SkillDatas	PLAYER_INIT_SKILL		= new SkillDatas(new SkillData(1), new SkillData(5));

	@ConfigField("玩家基础道具")
	public static ItemDatas		PLAYER_INIT_ITEM		= new ItemDatas();
	
	@ConfigField("玩家基础ap")
	public static int			PLAYER_START_AP			= 200;

	@ConfigField("玩家基础mp")
	public static int			PLAYER_START_MP			= 200;

	@ConfigField("玩家基础hp")
	public static int			PLAYER_START_HP			= 200;
	
	@ConfigField("玩家基础游戏币")
	public static int			PLAYER_START_COIN		= 20000;

	
//	---------------------------------------------------------------------
	@ConfigSeparator("战斗")
//	---------------------------------------------------------------------

	@ConfigField("战斗开始基础mp")
	public static int			BATTLE_START_MP			= 100;

	@ConfigField("战斗开始基础ap")
	public static int			BATTLE_START_AP			= 100;
	
	@ConfigField("多长时间加一次ap")
	public static int			BATTLE_AP_UPGRADE_TIME	= 100;

	@ConfigField("加一次ap多少点")
	public static int			BATTLE_AP_UPGRADE_COUNT	= 1;

//	---------------------------------------------------------------------
	@ConfigSeparator("商店和道具")
//	---------------------------------------------------------------------

	@ConfigField("商店道具")
	public static ShopItems SHOP_ITEMS = new ShopItems(
			new ShopItem(100001,1),
			new ShopItem(100002,1),
			new ShopItem(100003,1),
			new ShopItem(100004,1),
			new ShopItem(100101,1),
			new ShopItem(100102,1),
			new ShopItem(100103,1),
			new ShopItem(100104,1),
			new ShopItem(100201,1),
			new ShopItem(100202,1),
			new ShopItem(100203,1),
			new ShopItem(100301,1),
			new ShopItem(100302,1),
			new ShopItem(100303,1),
			new ShopItem(100304,1),
			new ShopItem(100401,1),
			new ShopItem(100402,1),
			new ShopItem(100403,1),
			new ShopItem(100404,1),
			new ShopItem(100501,1),
			new ShopItem(100502,1),
			new ShopItem(100503,1),
			new ShopItem(200001,1),
			new ShopItem(200002,1),
			new ShopItem(200003,1),
			new ShopItem(200004,1),
			new ShopItem(200005,1),
			new ShopItem(200006,1),
			new ShopItem(200007,1),
			new ShopItem(200008,1),
			new ShopItem(200009,1),
			new ShopItem(200010,1),
			new ShopItem(200011,1),
			new ShopItem(200012,1),
			new ShopItem(200013,1),
			new ShopItem(200014,1),
			new ShopItem(200015,1),
			new ShopItem(200016,1),
			new ShopItem(200017,1),
			new ShopItem(200018,1)
			);
			
//	---------------------------------------------------------------------
	@ConfigSeparator("功能")
//	---------------------------------------------------------------------

	@ConfigField("是否可以再登录时自动注册")
	public static boolean	LOGIN_AUTO_REGIST 	= true;

	
}
