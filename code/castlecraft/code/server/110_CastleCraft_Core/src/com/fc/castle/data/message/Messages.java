package com.fc.castle.data.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.cell.net.io.Comment;
import com.cell.sql.SQLType;
import com.cell.sql.anno.SQLField;
import com.fc.castle.data.BattleLog;
import com.fc.castle.data.BattlePlayer;
import com.fc.castle.data.ExploreData;
import com.fc.castle.data.ExploreState;
import com.fc.castle.data.ItemData;
import com.fc.castle.data.ItemDatas;
import com.fc.castle.data.Mail;
import com.fc.castle.data.MailSnap;
import com.fc.castle.data.PlayerData;
import com.fc.castle.data.PlayerFriend;
import com.fc.castle.data.PlayerMailData;
import com.fc.castle.data.PlayerQuestData;
import com.fc.castle.data.ShopItems;
import com.fc.castle.data.SkillDatas;
import com.fc.castle.data.SoldierDatas;
import com.fc.castle.data.template.BuffTemplate;
import com.fc.castle.data.template.EventTemplate;
import com.fc.castle.data.template.FormualMap;
import com.fc.castle.data.template.ItemTemplate;
import com.fc.castle.data.template.SkillTemplate;
import com.fc.castle.data.template.UnitTemplate;

@SuppressWarnings("serial")
public class Messages
{
//	-----------------------------------------------------------------------------------------
	
	@Comment("登录请求")
	public static class LoginRequest extends Request
	{
//		public long local_time;
//
//		public double local_interval;

		@Comment("帐号id")
		public String 	account_id;
		@Comment("验证串")
		public String 	sign;
		@Comment("是否自动创建帐号，如果服务器支持")
		public boolean 	create;
		
		public String createNickName;	
		
	}

	@Comment("登录回馈")
	public static class LoginResponse extends Response
	{
		public static final byte RESULT_SUCCEED 					= 1;
		public static final byte RESULT_FAILED_UNKNOW				= 2;
		public static final byte RESULT_FAILED_USER_NOT_EXIST		= 3;
		public static final byte RESULT_FAILED_USER_SIGN			= 4;

		@Comment("如何")
		public byte result;

//		@Comment("服务器当前时间")
//		public long server_time;
//
//		@Comment("服务器更新")
//		public double server_interval;
		
		@Comment("你的帐号id")
		public String login_account;
		
		@Comment("登录获取的钥匙，登录后获取唯一钥匙，游戏内操作需要此钥匙作为验证。")
		public String login_key;

		@Comment("玩家数据，如果一个帐号有多个玩家，则返回数组")
		public PlayerData[] player_data;
		
		public PlayerQuestData[] player_quests;
	}

//	-----------------------------------------------------------------------------------------
	
	@Comment("获取玩家任务数据")
	public static class GetPlayerQuestRequest extends Request
	{

	}
	
	@Comment("获取玩家任务数据")
	public static class GetPlayerQuestResponse extends Response
	{
		public static final byte RESULT_SUCCEED			= 1;
		public static final byte RESULT_FAILED_UNKNOW	= 2;
		
		public byte result;
		
		public PlayerQuestData data;
	}
	
	
	@Comment("提交新手引导数据")
	public static class CommitGuideRequest extends Request
	{
		public int guide_steps;
	}
	
	@Comment("提交新手引导数据")
	public static class CommitGuideResponse extends Response
	{
		public static final byte RESULT_SUCCEED			= 1;
		public static final byte RESULT_FAILED_UNKNOW	= 2;
		
		public byte result;
		
		public PlayerQuestData data;
	}

	
//	-----------------------------------------------------------------------------------------
	
	@Comment("获取兵种详细数据")
	public static class GetUnitTemplateRequest extends Request
	{
		public int[] unit_types;
	}
	
	@Comment("获取兵种详细数据")
	public static class GetUnitTemplateResponse extends Response
	{
		public static final byte RESULT_SUCCEED 					= 1;
		public static final byte RESULT_FAILED_UNKNOW				= 2;
		public static final byte RESULT_ST_IS_NULL					= 3;
		
		public byte result;
		
		public UnitTemplate[] unit_templates;
	}

//	-----------------------------------------------------------------------------------------
	
	@Comment("获取玩家技能详细数据")
	public static class GetSkillTemplateRequest extends Request
	{
		public int[] skill_types;
	}
	
	@Comment("获取玩家技能详细数据")
	public static class GetSkillTemplateResponse extends Response
	{
		public static final byte RESULT_SUCCEED 					= 1;
		public static final byte RESULT_FAILED_UNKNOW				= 2;
		public static final byte RESULT_ST_IS_NULL					= 3;
		
		public byte result;
		
		public SkillTemplate[] skill_templates;
	}


//	-----------------------------------------------------------------------------------------
	
	@Comment("获取道具模板数据")
	public static class GetItemTemplateRequest extends Request
	{
		public int[] item_types;
	}
	
	@Comment("获取道具模板数据")
	public static class GetItemTemplateResponse extends Response
	{
		public static final byte RESULT_SUCCEED 					= 1;
		public static final byte RESULT_FAILED_UNKNOW				= 2;
		public static final byte RESULT_ST_IS_NULL					= 3;
		
		public byte result;
		
		public ItemTemplate[] item_templates;
	}

//	-----------------------------------------------------------------------------------------
	
	@Comment("获取BUFF详细数据")
	public static class GetBuffTemplateRequest extends Request
	{
	}
	
	@Comment("获取BUFF详细数据")
	public static class GetBuffTemplateResponse extends Response
	{
		public static final byte RESULT_SUCCEED 					= 1;
		public static final byte RESULT_FAILED_UNKNOW				= 2;
		public static final byte RESULT_ST_IS_NULL					= 3;
		
		public byte result;
		
		public LinkedHashMap<Integer, BuffTemplate> buff_templates;
	}

	
//	-----------------------------------------------------------------------------------------
	
	@Comment("获取事件点详细数据")
	public static class GetEventTemplateRequest extends Request
	{
		public int[] event_types;
	}
	
	@Comment("获取事件点详细数据")
	public static class GetEventTemplateResponse extends Response
	{
		public static final byte RESULT_SUCCEED 					= 1;
		public static final byte RESULT_FAILED_UNKNOW				= 2;
		public static final byte RESULT_ST_IS_NULL					= 3;
		
		public byte result;
		
		public EventTemplate[] event_templates;
	}

	
//	-----------------------------------------------------------------------------------------

	@Comment("请求开始战斗")
	public static class BattleStartRequest extends Request
	{
		final public static int TYPE_TEST							= 0;
		final public static int TYPE_PLAYER							= 1;
		final public static int TYPE_GUIDE							= 2;
		final public static int TYPE_EXPLORE						= 3;
		final public static int TYPE_EVENT							= 4;
		
		public int battleType;

		// pvp or explore another
		public int targetPlayerID;
		
		// pve
		public String scene_unit_name;
		
//		// event
//		public int event_type;
		
		@Comment("SoldierDatas")
		public SoldierDatas soldiers;
		
		@Comment("SoldierDatas")
		public SkillDatas skills;
		
		
		@Comment("TestForceB-SoldierDatas")
		public SoldierDatas test_forceB_soldiers;
		
		@Comment("TestForceB-SoldierDatas")
		public SkillDatas test_forceB_skills;
	}
	
	@Comment("请求开始战斗")
	public static class BattleStartResponse extends Response
	{
		public static final byte RESULT_SUCCEED 						= 1;
		public static final byte RESULT_FAILED_UNKNOW					= 2;
		public static final byte RESULT_FAILED_VALIDATE					= 3;
		public static final byte RESULT_FAILED_EXPLORE_CD				= 4;
		public static final byte RESULT_FAILED_TARGET_PLAYER_NOT_EXIST	= 5;
		
		public byte result;

//		------------------------------------------
		
		public int battleType;
		
		// pvp or explore another
		public int targetPlayerID;
		
		// pve
		public String scene_unit_name;
		
		public int guideStep;
		
//		------------------------------------------
		
		@Comment("地图名字")
		public String cmap_id;

		@Comment("多长时间加一次ap")
		public int ap_upgrade_time;

		@Comment("加一次ap多少点")
		public int ap_upgrade_count;
		
		public FormualMap formual_map;
		
		public BattlePlayer forceA;
		
		public BattlePlayer forceB;
	}
	
//	-----------------------------------------------------------------------------------------

	@Comment("战斗结束时反馈结果")
	public static class CommitBattleResultRequest extends Request
	{
		public BattleLog log;
		public String scene_unit_name;
		public boolean is_win;
	}
	
	@Comment("战斗结束时反馈结果")
	public static class CommitBattleResultResponse extends Response
	{
		public static final byte RESULT_SUCCEED 					= 1;
		public static final byte RESULT_FAILED_UNKNOW				= 2;
		public static final byte RESULT_FAILED_VALIDATE				= 3;
			
		public byte result;
				
		public ExploreState explore_state;
	}	
	
//	-----------------------------------------------------------------------------------------

	@Comment("布防，用于怪物来袭或者玩家来袭")
	public static class OrganizeDefenseRequest extends Request
	{
		public SoldierDatas	soldiers;
		
		public SkillDatas	skills;
	}
	
	@Comment("布防，用于怪物来袭或者玩家来袭")
	public static class OrganizeDefenseResponse extends Response
	{
		public static final byte RESULT_SUCCEED 					= 1;
		public static final byte RESULT_FAILED_UNKNOW				= 2;
			
		public byte result;
		
	}	

//	-----------------------------------------------------------------------------------------
	
	@Comment("发送邮件")
	public static class SendMailRequest extends Request
	{
		public Mail mail;
	}
	
	@Comment("发送邮件")
	public static class SendMailResponse extends Response
	{
		public static final byte RESULT_SUCCEED 					= 1;
		public static final byte RESULT_FAILED_UNKNOW				= 2;
		public static final byte RESULT_FAILED_RECEIVER_IS_NULL		= 3;
			
		public byte result;
	}	
	
	
	@Comment("获取邮件列表")
	public static class GetMailSnapsRequest extends Request
	{
	}
	
	@Comment("获取邮件列表")
	public static class GetMailSnapsResponse extends Response
	{
		public static final byte RESULT_SUCCEED 					= 1;
		public static final byte RESULT_FAILED_UNKNOW				= 2;
		
		public byte result;
		
		public PlayerMailData mailSnaps;
	}	

	@Comment("获取邮件")
	public static class GetMailRequest extends Request
	{
		public int mailID;
	}

	@Comment("获取邮件")
	public static class GetMailResponse extends Response
	{
		public static final byte RESULT_SUCCEED 					= 1;
		public static final byte RESULT_FAILED_UNKNOW				= 2;
		public static final byte RESULT_FAILED_VALIDATE				= 3;

		public byte result;
		
		public Mail mail;
	}
	
//	-----------------------------------------------------------------------------------------
		
	@Comment("获取探索点列表")
	public static class GetExploreDataListRequest extends Request
	{
		public int targetPlayerID;
	}

	@Comment("获取探索点列表")
	public static class GetExploreDataListResponse extends Response
	{
		public static final byte RESULT_SUCCEED						= 1;
		public static final byte RESULT_FAILED						= 2;
		public static final byte RESULT_FAILED_PLAYER_NOT_FOUND		= 3;
		
		public byte result;

		public PlayerFriend targetPlayer;
		
		public String targetPlayerHomeScene;

		public HashMap<String, ExploreData> explores;
	}
	
	@Comment("获取探索点")
	public static class GetExploreStateRequest extends Request
	{
		public String unitName;
		public int targetPlayerID;
	}

	@Comment("获取探索点")
	public static class GetExploreStateResponse extends Response
	{
		public static final byte RESULT_SUCCEED		= 1;
		public static final byte RESULT_FAILED		= 2;
		
		public byte result;
		
		public ExploreState state;
		
		public EventTemplate event;
		
	}
	
//	-----------------------------------------------------------------------------------------
		

	@Comment("获取随机在线玩家")
	public static class GetRandomOnlinePlayersRequest extends Request
	{
	}
	
	@Comment("获取随机在线玩家")
	public static class GetRandomOnlinePlayersResponse extends Response
	{
		public static final byte RESULT_SUCCEED		= 1;
		public static final byte RESULT_FAILED		= 2;
		
		public byte result;
		
		public ArrayList<PlayerFriend> friends;
	}
	
	@Comment("刷新玩家数据请求")
	public static class RefreshPlayerDataRequest extends Request
	{
		
	}
	
	@Comment("刷新玩家数据返回")
	public static class RefreshPlayerDataResponse extends Response
	{
		public static final byte RESULT_SUCCEED		= 1;
		public static final byte RESULT_FAILED_PLAYER_NOTEXIST		= 2;
		
		public byte result;
		public PlayerData player;
	}

//	-----------------------------------------------------------------------------------------
//	@Comment("添加玩家好友请求")
//	public static class AddFriendRequest extends Request
//	{
//		public int targetID;
//		
//	}
//	
//	@Comment("添加玩家好友返回")
//	public static class AddFriendResponse extends Response
//	{
//		public static final byte RESULT_SUCCEED		= 1;
//		public static final byte RESULT_FAILED_PLAYER_NOTEXIST		= 2;
//		
//		public byte result;
//		public PlayerMailData player_mail_data;
//	}
//	-----------------------------------------------------------------------------------------
		

	@Comment("使用道具")
	public static class UseItemRequest extends Request
	{
		public int indexOfItems;
		
		public int count;
	}
	
	@Comment("使用道具")
	public static class UseItemResponse extends Response
	{
		public static final byte RESULT_SUCCEED				= 1;
		public static final byte RESULT_FAILED				= 2;
		public static final byte RESULT_FAILED_NOT_ENOUGH	= 3;
		
		public byte result;
		
		@Comment("该道具栏位道具状态")
		public ItemData item_slot;
	}
	
	
	
//	-----------------------------------------------------------------------------------------
	
	
	@Comment("获取商店道具")
	public static class GetShopItemRequest extends Request
	{
		
	}
	
	@Comment("获取商店道具")
	public static class GetShopItemResponse extends Response
	{
		public static final byte RESULT_SUCCEED				= 1;
		public static final byte RESULT_FAILED				= 2;
		
		public byte result;
		public ShopItems items;
	}

//	-----------------------------------------------------------------------------------------

	@Comment("购买商店道具")
	public static class BuyShopItemRequest extends Request
	{
		public int indexOfShop;
	}
	

	@Comment("购买商店道具")
	public static class BuyShopItemResponse extends Response
	{
		public static final byte RESULT_SUCCEED				= 1;
		public static final byte RESULT_FAILED				= 2;
		public static final byte RESULT_NEED_MORE_COIN		= 3;
		
		public byte 	result;
		public int 		bag_index;
		public ItemData	bag_item;
		public int		my_coin;
	}
	
	
	
	
	
	
	
	
	
	
}
