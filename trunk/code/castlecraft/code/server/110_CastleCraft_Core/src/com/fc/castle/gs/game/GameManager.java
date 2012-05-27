package com.fc.castle.gs.game;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cell.CUtil;
import com.cell.net.io.Comment;
import com.cell.security.MD5;
import com.cell.util.StringUtil;
import com.fc.castle.data.Account;
import com.fc.castle.data.BattlePlayer;
import com.fc.castle.data.ExploreState;
import com.fc.castle.data.ItemData;
import com.fc.castle.data.ItemDatas;
import com.fc.castle.data.Mail;
import com.fc.castle.data.PlayerData;
import com.fc.castle.data.PlayerFriend;
import com.fc.castle.data.PlayerMailData;
import com.fc.castle.data.PlayerQuestData;
import com.fc.castle.data.ShopItem;
import com.fc.castle.data.SkillData;
import com.fc.castle.data.SkillDatas;
import com.fc.castle.data.SoldierData;
import com.fc.castle.data.SoldierDatas;
import com.fc.castle.data.message.AbstractData;
import com.fc.castle.data.message.MessageCodecJava;
import com.fc.castle.data.message.MessageFactory;
import com.fc.castle.data.message.Request;
import com.fc.castle.data.message.Messages.*;
import com.fc.castle.data.message.Response;
import com.fc.castle.data.message.Messages.BattleStartRequest;
import com.fc.castle.data.message.Messages.BattleStartResponse;
import com.fc.castle.data.message.Messages.GetUnitTemplateRequest;
import com.fc.castle.data.message.Messages.GetUnitTemplateResponse;
import com.fc.castle.data.message.Messages.LoginRequest;
import com.fc.castle.data.message.Messages.LoginResponse;
import com.fc.castle.data.template.EventTemplate;
import com.fc.castle.data.template.ExpTab;
import com.fc.castle.data.template.GuideData;
import com.fc.castle.data.template.ItemTemplate;
import com.fc.castle.data.template.SkillTemplate;
import com.fc.castle.data.template.UnitTemplate;
import com.fc.castle.gs.config.GameConfig;
import com.fc.castle.gs.scene.ExploreSite;
import com.fc.castle.service.DataManager;
import com.fc.castle.service.PersistanceManager;

public abstract class GameManager 
{
	private static final Logger log = LoggerFactory.getLogger(GameManager.class);

	private static GameManager instance;

	public  static GameManager getInstance() {
		return instance;
	}
	
	protected GameManager() {
		instance = this;
	}


	
//	---------------------------------------------------------------------------------------------------------------
//	request/response
//	---------------------------------------------------------------------------------------------------------------
	
	public Response onRequest(AbstractData request) throws Exception
	{
		if (request instanceof LoginRequest) {
			return doLogin((LoginRequest)request);
		} 
//		--------------------------------------------------------------------------------
		else if (request instanceof GetUnitTemplateRequest) {
			return doGetUnitTemplate((GetUnitTemplateRequest)request);
		}
		else if (request instanceof GetSkillTemplateRequest) {
			return doGetSkillTemplate((GetSkillTemplateRequest)request);
		}
		else if (request instanceof GetItemTemplateRequest) {
			return doGetItemTemplate((GetItemTemplateRequest)request);
		}
		else if (request instanceof GetEventTemplateRequest) {
			return doGetEventTemplateRequest((GetEventTemplateRequest)request);
		}
		else if (request instanceof GetBuffTemplateRequest) {
			return doGetBuffTemplateRequest((GetBuffTemplateRequest)request);
		}
//		--------------------------------------------------------------------------------
		else if (request instanceof BattleStartRequest) {
			return doBattleStart((BattleStartRequest)request);
		}
		else if (request instanceof CommitBattleResultRequest) {
			return doCommitBattleResultRequest((CommitBattleResultRequest)request);
		}
		else if (request instanceof OrganizeDefenseRequest) {
			return doOrganizeDefenseRequest((OrganizeDefenseRequest)request);
		}
//		--------------------------------------------------------------------------------
		else if (request instanceof GetPlayerQuestRequest) {
			return doGetPlayerQuestRequest((GetPlayerQuestRequest)request);
		}
		else if (request instanceof GetMailSnapsRequest) {
			return doGetMailSnapsRequest((GetMailSnapsRequest)request);
		}
		else if (request instanceof GetMailRequest) {
			return doGetMailRequest((GetMailRequest)request);
		}
		else if (request instanceof SendMailRequest) {
			return doSendMailRequest((SendMailRequest)request);
		}
		else if (request instanceof CommitGuideRequest) {
			return doCommitGuideRequest((CommitGuideRequest)request);
		}
		else if (request instanceof GetExploreDataListRequest){
			return doGetExploreDataListRequest((GetExploreDataListRequest)request);
		}
		else if (request instanceof GetExploreStateRequest){
			return doGetExploreStateRequest((GetExploreStateRequest)request);
		}
//		--------------------------------------------------------------------------------
		else if (request instanceof GetRandomOnlinePlayersRequest) {
			return doGetRandomOnlinePlayersRequest((GetRandomOnlinePlayersRequest)request);
		}
		else if (request instanceof RefreshPlayerDataRequest){
			return doRefreshPlayerDataRequest((RefreshPlayerDataRequest)request);
		}
//		--------------------------------------------------------------------------------
		else if (request instanceof UseItemRequest) {
			return doUseItemRequest((UseItemRequest)request);
		}
//		--------------------------------------------------------------------------------
		else if (request instanceof GetShopItemRequest) {
			return doGetShopItemRequest((GetShopItemRequest)request);
		}
		else if (request instanceof BuyShopItemRequest) {
			return doBuyShopItemRequest((BuyShopItemRequest)request);
		}
		else {
			log.error("Unknow Messge : " + request);
			return null;
		}
	}
	
	/**********************************************************************************
	 * 玩家登录请求
	 **********************************************************************************/
	protected LoginResponse doLogin(LoginRequest request)
	{
		LoginResponse response = new LoginResponse();
//		response.server_time		= System.currentTimeMillis();
//		response.server_interval	= CUtil.getRandom().nextDouble();
		
		if (request.account_id!=null && request.sign!=null && !request.account_id.trim().isEmpty()) 
		{
			Account ac = PersistanceManager.getInstance().getAccount(request.account_id);
					
			// 用户存在
			if (ac != null) 
			{
				if (request.sign.equals(ac.sign)) {
					defaultAccount(ac);
					response.login_account	= ac.id;
					ArrayList<PlayerData> players = PersistanceManager.getInstance().getPlayers(ac);
					if (players.isEmpty()) {
						PlayerData dp = PersistanceManager.getInstance().registPlayer(ac, createDefaultPlayer(ac, request.createNickName));
						players.add(dp);
					} else {
						for (PlayerData pd : players) {
							defaultPlayer(pd);
						}
					}
					response.player_data 	= players.toArray(new PlayerData[players.size()]);
					response.player_quests	= new PlayerQuestData[players.size()];
					for (int i=players.size()-1; i>=0; --i) {
						response.player_quests[i] = PersistanceManager.getInstance().getPlayerQuest(players.get(i).player_id);
						PersistanceManager.getInstance().pushLoginPlayer(players.get(i));
					}
					response.result 		= LoginResponse.RESULT_SUCCEED;
				} else {
					response.result 		= LoginResponse.RESULT_FAILED_USER_SIGN;
				}
			}
			// 用户不存在并允许自动注册
			else if (request.create && GameConfig.LOGIN_AUTO_REGIST)
			{
				ac = PersistanceManager.getInstance().registAccount(createDefaultAccount(request.account_id, request.sign));
				// 自动创建玩家
				if (ac != null) {
					PlayerData dp = PersistanceManager.getInstance().registPlayer(ac, createDefaultPlayer(ac, request.createNickName));
					response.login_account	= ac.id;
					response.player_data 	= new PlayerData[]{dp};
					response.player_quests	= new PlayerQuestData[1];
					response.player_quests[0] = PersistanceManager.getInstance().getPlayerQuest(response.player_data[0].player_id);
					PersistanceManager.getInstance().pushLoginPlayer(dp);
					response.result 		= LoginResponse.RESULT_SUCCEED;
				} else {
					response.result 		= LoginResponse.RESULT_FAILED_USER_NOT_EXIST;
				}
			} 
			// 用户不存在
			else {
				response.result = LoginResponse.RESULT_FAILED_USER_NOT_EXIST;
			}
		} 
		else {
			response.result = LoginResponse.RESULT_FAILED_UNKNOW;
		}
		return response;
	}
	
	
	/**********************************************************************************
	 * 获取兵种数据模板
	 **********************************************************************************/
	protected GetUnitTemplateResponse doGetUnitTemplate(GetUnitTemplateRequest request)
	{
		GetUnitTemplateResponse res = new GetUnitTemplateResponse();
		res.unit_templates = DataManager.getInstance().getTemplates(
				UnitTemplate.class, 
				request.unit_types);
		if (res.unit_templates != null) {
			res.result = GetUnitTemplateResponse.RESULT_SUCCEED;
		} else {
			res.result = GetUnitTemplateResponse.RESULT_ST_IS_NULL;
		}
		return res;
	}
	
	/**********************************************************************************
	 * 获取技能数据模板
	 **********************************************************************************/
	protected GetSkillTemplateResponse doGetSkillTemplate(GetSkillTemplateRequest request)
	{
		GetSkillTemplateResponse res = new GetSkillTemplateResponse();
		res.skill_templates = DataManager.getInstance().getTemplates(
				SkillTemplate.class, 
				request.skill_types);
		if (res.skill_templates != null) {
			res.result = GetSkillTemplateResponse.RESULT_SUCCEED;
		} else {
			res.result = GetSkillTemplateResponse.RESULT_ST_IS_NULL;
		}
		return res;
	}
	
	/**********************************************************************************
	 * 获取技能数据模板
	 **********************************************************************************/
	protected GetItemTemplateResponse doGetItemTemplate(GetItemTemplateRequest request)
	{
		GetItemTemplateResponse res = new GetItemTemplateResponse();
		res.item_templates = DataManager.getInstance().getTemplates(
				ItemTemplate.class, 
				request.item_types);
		if (res.item_templates != null) {
			res.result = GetItemTemplateResponse.RESULT_SUCCEED;
		} else {
			res.result = GetItemTemplateResponse.RESULT_ST_IS_NULL;
		}
		return res;
	}
	
	/**********************************************************************************
	 * 获取数据模板
	 **********************************************************************************/
	protected GetEventTemplateResponse doGetEventTemplateRequest(GetEventTemplateRequest request)
	{
		GetEventTemplateResponse res = new GetEventTemplateResponse();
		res.event_templates = DataManager.getInstance().getTemplates(
				EventTemplate.class, 
				request.event_types);
		if (res.event_templates != null) {
			res.result = GetEventTemplateResponse.RESULT_SUCCEED;
		} else {
			res.result = GetEventTemplateResponse.RESULT_ST_IS_NULL;
		}
		return res;
	}
	
	/**********************************************************************************
	 * 获取数据模板
	 **********************************************************************************/
	protected GetBuffTemplateResponse doGetBuffTemplateRequest(GetBuffTemplateRequest request)
	{
		GetBuffTemplateResponse res = new GetBuffTemplateResponse();
		res.buff_templates = DataManager.getInstance().getAllBuffTemplate();
		if (res.buff_templates != null) {
			res.result = GetEventTemplateResponse.RESULT_SUCCEED;
		} else {
			res.result = GetEventTemplateResponse.RESULT_ST_IS_NULL;
		}
		return res;
	}
	
	/**********************************************************************************
	 * 获取玩家任务状态
	 **********************************************************************************/
	protected GetPlayerQuestResponse doGetPlayerQuestRequest(GetPlayerQuestRequest request)
	{
		GetPlayerQuestResponse res = new GetPlayerQuestResponse();
		res.data = PersistanceManager.getInstance().getPlayerQuest(request.player_id);
		if (res.data != null) {
			res.result = GetPlayerQuestResponse.RESULT_SUCCEED;
		} else {
			res.result = GetPlayerQuestResponse.RESULT_FAILED_UNKNOW;
		}
		return res;
	}
	
	/**********************************************************************************
	 * 任务引导＋1
	 **********************************************************************************/
	protected CommitGuideResponse doCommitGuideRequest(CommitGuideRequest request)
	{
		CommitGuideResponse res = new CommitGuideResponse();
		res.data = PersistanceManager.getInstance().getPlayerQuest(request.player_id);
		if (res.data != null) {
			if (request.guide_steps > res.data.guide_steps) {
				res.data.guide_steps = request.guide_steps;
				if (PersistanceManager.getInstance().savePlayerQuest(res.data)) {
					res.result = CommitGuideResponse.RESULT_SUCCEED;
				} else {
					res.result = CommitGuideResponse.RESULT_FAILED_UNKNOW;
				}
			} else {
				res.result = CommitGuideResponse.RESULT_FAILED_UNKNOW;
			} 
		} else {
			res.result = CommitGuideResponse.RESULT_FAILED_UNKNOW;
		}
		return res;
	}

	
	/**********************************************************************************
	 * 获取邮件列表
	 **********************************************************************************/
	protected GetMailSnapsResponse doGetMailSnapsRequest(GetMailSnapsRequest request)
	{
		GetMailSnapsResponse ret = new GetMailSnapsResponse();
		PlayerMailData pm = PersistanceManager.getInstance().getPlayerMail(request.player_id);
		if (pm != null) {
			ret.mailSnaps = pm;
			ret.result = GetMailResponse.RESULT_SUCCEED;
		} else {
			ret.result = GetMailResponse.RESULT_FAILED_UNKNOW;
		}
		return ret;
	}
	
	protected GetMailResponse doGetMailRequest(GetMailRequest request)
	{
		GetMailResponse ret = new GetMailResponse();
		Mail mail = PersistanceManager.getInstance().getMail(request.mailID);
		if (mail != null && mail.receiverPlayerID == request.player_id) {
			ret.mail = mail;
			ret.result = GetMailResponse.RESULT_SUCCEED;
		} else {
			ret.result = GetMailResponse.RESULT_FAILED_UNKNOW;
		}
		return ret;
	}
	
	protected SendMailResponse doSendMailRequest(SendMailRequest request)
	{
		SendMailResponse ret = new SendMailResponse();
		Mail mail = PersistanceManager.getInstance().postMail(request.mail);
		if (mail != null) {
			ret.result = SendMailResponse.RESULT_SUCCEED;
		} else {
			ret.result = SendMailResponse.RESULT_FAILED_RECEIVER_IS_NULL;
		}
		return ret;
	}
	
	/**********************************************************************************
	 * 战斗开始
	 **********************************************************************************/
	protected BattleStartResponse doBattleStart(BattleStartRequest request)
	{
		BattleStartResponse res = new BattleStartResponse();
		res.battleType = request.battleType;
		res.scene_unit_name = request.scene_unit_name;
		res.targetPlayerID = request.targetPlayerID;
		PlayerData pdata = PersistanceManager.getInstance().getPlayer(request.player_id);
		if (pdata == null) {
			log.error("BattleStartRequest : Player Not found [" + request.player_id + "]");
			res.result = BattleStartResponse.RESULT_FAILED_VALIDATE;
			return res;
		}
		
		//////////////////////////////////////////////////////////////////////
		// PVE
		if (request.battleType == BattleStartRequest.TYPE_EXPLORE) 
		{
			Player playerA = new Player(pdata);
			
			Player playerTarget = null;
			// 探索别人家的探索点
			if (request.targetPlayerID == request.player_id) {
				playerTarget = playerA;
			} else {
				playerTarget = new Player(request.targetPlayerID);
			}
			
			ExploreSite explore = DataManager.getInstance().getExploreSite(
					request.scene_unit_name);
			if (explore == null || !explore.isVisible(playerTarget.getData())) {
				res.result = BattleStartResponse.RESULT_FAILED_UNKNOW;
				return res;
			} else if (explore.isCoolDown(playerTarget.getData())) {
				res.result = BattleStartResponse.RESULT_FAILED_EXPLORE_CD;
				return res;
			}

			EventTemplate event = playerTarget.makeExploreEvent(explore);
			if (event == null) {
				res.result = BattleStartResponse.RESULT_FAILED_UNKNOW;
				return res;
			}
			// 玩家所有兵种必须包含所选择的兵种
			if (!playerA.tryBattleSoldiers(request.soldiers)) {
				res.result = BattleStartResponse.RESULT_FAILED_VALIDATE;
				return res;
			}
			if (!playerA.tryBattleSkills(request.skills)) {
				res.result = BattleStartResponse.RESULT_FAILED_VALIDATE;
				return res;
			}
			res.forceA = new BattlePlayer();
			res.forceA.playerID	= pdata.player_id;
			res.forceA.type 	= BattlePlayer.TYPE_PLAYER;
			res.forceA.name 	= pdata.player_name;
			res.forceA.hp 		= pdata.hp;
			res.forceA.ap 		= GameConfig.BATTLE_START_AP;
			res.forceA.mp		= GameConfig.BATTLE_START_MP;
			res.forceA.soldiers = request.soldiers;
			res.forceA.skills 	= request.skills;
			
			
			// 需要根据请求怪物类型产生单位
			res.forceB = new BattlePlayer();
			res.forceB.type 	= BattlePlayer.TYPE_MONSTER;
			res.forceB.name 	= event.name;
			res.forceB.hp 		= event.hp;
			res.forceB.ap 		= GameConfig.BATTLE_START_AP;
			res.forceB.mp 		= GameConfig.BATTLE_START_MP;
			res.forceB.soldiers = event.soldiers;
			if (event.skills==null){
				event.skills = new SkillDatas();
			}
			res.forceB.skills 	= event.skills;
			
			res.cmap_id = event.battleMap;//"000001/000002";
		}
		//////////////////////////////////////////////////////////////////////
		// GUIDE
		else if (request.battleType == BattleStartRequest.TYPE_GUIDE)
		{
			PlayerQuestData pqd = PersistanceManager.getInstance().getPlayerQuest(request.player_id);
			GuideData gd = DataManager.getInstance().getGuideData(pqd.guide_steps);
			if (gd != null)
			{
				res.guideStep		= gd.getPrimaryKey();
				res.cmap_id			= gd.GUIDE_BATTLE_MAP;
				
				res.forceA 			= new BattlePlayer();
				res.forceA.type 	= BattlePlayer.TYPE_PLAYER;
				res.forceA.playerID	= pdata.player_id;
				res.forceA.name 	= pdata.player_name;
				res.forceA.hp 		= gd.GUIDE_START_HP;
				res.forceA.ap 		= gd.GUIDE_START_AP;
				res.forceA.mp 		= gd.GUIDE_START_MP;
				res.forceA.soldiers = DataManager.getInstance().createSoldiers(gd.GUIDE_PLAYER_SOLDIER);
				res.forceA.skills 	= DataManager.getInstance().createSkills(gd.GUIDE_PLAYER_SKILL);
				
				res.forceB 			= new BattlePlayer();
				res.forceB.type 	= BattlePlayer.TYPE_MONSTER;
				res.forceB.name 	= gd.GUIDE_AI_NAME;
				res.forceB.hp 		= gd.GUIDE_START_HP;
				res.forceB.ap 		= gd.GUIDE_START_AP;
				res.forceB.mp 		= gd.GUIDE_START_MP;
				res.forceB.soldiers = DataManager.getInstance().createSoldiers(gd.GUIDE_AI_SOLDIER);
				res.forceB.skills 	= DataManager.getInstance().createSkills(gd.GUIDE_AI_SKILL);
			}
			else {
				res.result = BattleStartResponse.RESULT_FAILED_UNKNOW;
				return res;
			}
		}
		//////////////////////////////////////////////////////////////////////
		// PVP
		else if (request.battleType == BattleStartRequest.TYPE_PLAYER) 
		{
			PlayerData pd_target = PersistanceManager.getInstance().getPlayer(request.targetPlayerID);
			if (pd_target != null)
			{
				Player playerA = new Player(pdata);
				Player playerB = new Player(pd_target);
				
				// 玩家所有兵种必须包含所选择的兵种
				if (!playerA.tryBattleSoldiers(request.soldiers)) {
					res.result = BattleStartResponse.RESULT_FAILED_VALIDATE;
					return res;
				}
				if (!playerA.tryBattleSkills(request.skills)) {
					res.result = BattleStartResponse.RESULT_FAILED_VALIDATE;
					return res;
				}
				
				res.forceA 			= new BattlePlayer();
				res.forceA.type 	= BattlePlayer.TYPE_PLAYER;
				res.forceA.playerID	= pdata.player_id;
				res.forceA.name 	= pdata.player_name;
				res.forceA.hp 		= pdata.hp;
				res.forceA.ap 		= GameConfig.BATTLE_START_AP;
				res.forceA.mp		= GameConfig.BATTLE_START_MP;
				res.forceA.soldiers = request.soldiers;
				res.forceA.skills 	= request.skills;
				
				res.forceB 			= new BattlePlayer();
				res.forceB.type 	= BattlePlayer.TYPE_PLAYER;
				res.forceA.playerID	= pd_target.player_id;
				res.forceB.name 	= pd_target.player_name;
				res.forceB.hp 		= pd_target.hp;
				res.forceB.ap 		= GameConfig.BATTLE_START_AP;
				res.forceB.mp 		= GameConfig.BATTLE_START_MP;
				res.forceB.soldiers = playerB.getDefenseSoldiers();
				res.forceB.skills 	= playerB.getDefenseSkills();
				
				res.cmap_id = "000001/000002";
			
			}
			else {
				res.result = BattleStartResponse.RESULT_FAILED_TARGET_PLAYER_NOT_EXIST;
				return res;
			}
		}
		//////////////////////////////////////////////////////////////////////
		// TEST
		else if (request.battleType == BattleStartRequest.TYPE_TEST)
		{
			Player playerA = new Player(pdata);
			
			// 玩家所有兵种必须包含所选择的兵种
			if (!playerA.tryBattleSoldiers(request.soldiers)) {
				res.result = BattleStartResponse.RESULT_FAILED_VALIDATE;
				return res;
			}
			if (!playerA.tryBattleSkills(request.skills)) {
				res.result = BattleStartResponse.RESULT_FAILED_VALIDATE;
				return res;
			}
			res.forceA = new BattlePlayer();
			res.forceA.playerID	= pdata.player_id;
			res.forceA.type 	= BattlePlayer.TYPE_PLAYER;
			res.forceA.name 	= pdata.player_name;
			res.forceA.hp 		= pdata.hp;
			res.forceA.ap 		= GameConfig.BATTLE_START_AP;
			res.forceA.mp		= GameConfig.BATTLE_START_MP;
			res.forceA.soldiers = request.soldiers;
			res.forceA.skills 	= request.skills;
			
			// 需要根据请求怪物类型产生单位
			res.forceB = new BattlePlayer();
			res.forceB.type 	= BattlePlayer.TYPE_MONSTER;
			res.forceB.name 	= "test ai";
			res.forceB.hp 		= pdata.hp;
			res.forceB.ap 		= GameConfig.BATTLE_START_AP;
			res.forceB.mp 		= GameConfig.BATTLE_START_MP;
			res.forceB.soldiers = request.test_forceB_soldiers;
			res.forceB.skills 	= request.test_forceB_skills;
			
			res.cmap_id = "000001/000002";
		}
		else
		{
			res.result = BattleStartResponse.RESULT_FAILED_UNKNOW;
			return res;
		}

		////////////////////////////////////////////////////////////////////////////////////
		res.result = BattleStartResponse.RESULT_SUCCEED;
		res.ap_upgrade_count = GameConfig.BATTLE_AP_UPGRADE_COUNT;
		res.ap_upgrade_time  = GameConfig.BATTLE_AP_UPGRADE_TIME;
		res.formual_map = DataManager.getInstance().getFormualMap();
		
		pdata.lastBattle = res;
		if (!PersistanceManager.getInstance().savePlayerFields(pdata, "lastBattle")) {
			res.result = BattleStartResponse.RESULT_FAILED_UNKNOW;
			return res;
		}
		return res;
	}
	
	
	protected CommitBattleResultResponse doCommitBattleResultRequest(CommitBattleResultRequest request)
	{
		CommitBattleResultResponse ret = new CommitBattleResultResponse();

		PlayerData playerdata = PersistanceManager.getInstance().getPlayer(request.player_id);
		
		if (playerdata != null && playerdata.lastBattle != null && playerdata.lastBattle.result == BattleStartResponse.RESULT_SUCCEED)
		{
			Player player = new Player(playerdata);
			// 检查此战斗是否复合规范
			if (player.validateBattle(request, playerdata.lastBattle)) 
			{
				if (playerdata.lastBattle.battleType == BattleStartRequest.TYPE_EXPLORE)
				{
					if (request.is_win) {
						Player playerTarget = null;
						if (playerdata.lastBattle.targetPlayerID == request.player_id) {
							playerTarget = player;
						} else {
							playerTarget = new Player(PersistanceManager.getInstance().getPlayerFields(
									playerdata.lastBattle.targetPlayerID, "exploreStates"));
						}
						
						ExploreState es = playerTarget.getOrCreateExploreState(request.scene_unit_name);
						if (es != null) {
							EventTemplate et 	= playerTarget.makeExploreEvent(DataManager.getInstance().getExploreSite(request.scene_unit_name));
							// 被探索玩家的资源被当前玩家掠夺
							player.addExp		(et.exp);
							player.addCoin		(et.gold);
							player.addDropItems	(et.itemDrops);
							es.last_time 		= new java.sql.Date(System.currentTimeMillis());
							es.explore_count++;				
							if (playerTarget != player) {
								es.last_explorer = new PlayerFriend(playerdata);
								PersistanceManager.getInstance().savePlayerFields(
										playerTarget.getData(), "exploreStates");
							}
							ret.explore_state = es;	
						}
					}
				}
				else if (playerdata.lastBattle.battleType == BattleStartRequest.TYPE_GUIDE)
				{
					if (request.is_win) {
						GuideData gd = DataManager.getInstance().getGuideData(playerdata.lastBattle.guideStep);
						if (gd != null) {
							player.addExp		(gd.AWARD_EXP);
							player.addCoin		(gd.AWARD_GOLD);
							player.addItems		(gd.AWARD_ITEMS);
						}
					}
				}
				// 清理上次战斗
				playerdata.lastBattle = null;
				PersistanceManager.getInstance().savePlayer(playerdata);
				ret.result = CommitBattleResultResponse.RESULT_SUCCEED;
			}
			else
			{
				ret.result = CommitBattleResultResponse.RESULT_FAILED_VALIDATE;
			}
		}
		else
		{
			ret.result = CommitBattleResultResponse.RESULT_FAILED_UNKNOW;
		}

		return ret;
	}
	

	protected OrganizeDefenseResponse doOrganizeDefenseRequest(OrganizeDefenseRequest request)
	{
		OrganizeDefenseResponse ret = new OrganizeDefenseResponse();
		
		PlayerData playerdata = PersistanceManager.getInstance().getPlayerFields(
				request.player_id, "soldiers", "skills", "battle_soldier_count", "battle_skill_count");

		Player player = new Player(playerdata);
		
		if (player.tryBattleSkills(request.skills) && player.tryBattleSoldiers(request.soldiers))
		{
			player.getData().organizeDefense = request;
			
			PersistanceManager.getInstance().savePlayerFields(playerdata, "organizeDefense");
			
			ret.result = OrganizeDefenseResponse.RESULT_SUCCEED;
		} 
		else
		{
			ret.result = OrganizeDefenseResponse.RESULT_FAILED_UNKNOW;
		}
		
		return ret;
	}
	
//	---------------------------------------------------------------------------------------------------------------

	protected GetExploreDataListResponse doGetExploreDataListRequest(GetExploreDataListRequest request)
	{
		GetExploreDataListResponse ret = new GetExploreDataListResponse();
		// 查询的不仅仅是自己的内城
		PlayerData playerdata = PersistanceManager.getInstance().getPlayerFields(
				request.targetPlayerID,
				"exploreStates", "homeScene", "player_name", "nick_name");
		if (playerdata != null) {
			Player player = new Player(playerdata);
			ret.explores = player.getActiveSceneExplores();
			ret.targetPlayerHomeScene = playerdata.homeScene;
			ret.targetPlayer = new PlayerFriend(playerdata);
			ret.result = GetExploreDataListResponse.RESULT_SUCCEED;
		} else {
			ret.result = GetExploreDataListResponse.RESULT_FAILED;
		}
		return ret;
	}
	
	protected GetExploreStateResponse doGetExploreStateRequest(GetExploreStateRequest request)
	{
		GetExploreStateResponse ret = new GetExploreStateResponse();
		// 查询的不仅仅是自己的内城
		PlayerData playerdata = PersistanceManager.getInstance().getPlayerFields(
				request.targetPlayerID, 
				"exploreStates");
		if (playerdata != null) {
			ExploreSite site = DataManager.getInstance().getExploreSite(request.unitName);
			if (site != null) {
				Player player = new Player(playerdata);
				ret.state = player.getExploreState(request.unitName);
				ret.event = player.makeExploreEvent(site);
				ret.result = GetExploreStateResponse.RESULT_SUCCEED;
			} else {
				ret.result = GetExploreStateResponse.RESULT_FAILED;
			}
		}else{
			ret.result = GetExploreStateResponse.RESULT_FAILED;
		}
		return ret;
	}
	
	
	

	
	/**********************************************************************************
	 * 获取周围玩家
	 **********************************************************************************/
	protected GetRandomOnlinePlayersResponse doGetRandomOnlinePlayersRequest(GetRandomOnlinePlayersRequest request) 
	{
		GetRandomOnlinePlayersResponse ret = new GetRandomOnlinePlayersResponse();
		ret.friends = new ArrayList<PlayerFriend>(PersistanceManager.getInstance().getLastLoginPlayers().values());
		ret.result = GetRandomOnlinePlayersResponse.RESULT_FAILED;
		return ret;
	}

	
	/**********************************************************************************
	 * 刷新玩家状态
	 **********************************************************************************/
	protected RefreshPlayerDataResponse doRefreshPlayerDataRequest(RefreshPlayerDataRequest request)
	{
		RefreshPlayerDataResponse ret = new RefreshPlayerDataResponse();
		PlayerData playerdata = PersistanceManager.getInstance().getPlayer(request.player_id);
		if (playerdata != null){
			ret.result = RefreshPlayerDataResponse.RESULT_SUCCEED;
			ret.player = playerdata;
		}else{
			ret.result = RefreshPlayerDataResponse.RESULT_FAILED_PLAYER_NOTEXIST;
		}
		return ret;
	}
	

	
	/**********************************************************************************
	 * 使用用道具
	 **********************************************************************************/
	protected UseItemResponse doUseItemRequest(UseItemRequest request)
	{
		UseItemResponse ret = new UseItemResponse();
		PlayerData playerdata = PersistanceManager.getInstance().getPlayer(request.player_id);
		if (playerdata != null) {
			Player player = new Player(playerdata);
			int rcount = player.useItem(request.indexOfItems, request.count);
			if (rcount == request.count) {
				PersistanceManager.getInstance().savePlayerFields(playerdata, "items", "skills", "soldiers");
				ret.item_slot = playerdata.items.datas.get(request.indexOfItems);
				ret.result = UseItemResponse.RESULT_SUCCEED;
			}
			else if (rcount < request.count) {
				ret.result = UseItemResponse.RESULT_FAILED_NOT_ENOUGH;
			}
			else {
				ret.result = UseItemResponse.RESULT_FAILED;
			}
		} else {
			ret.result = UseItemResponse.RESULT_FAILED;
		}
		return ret;
	}

	/**********************************************************************************
	 * 获取商店道具列表
	 **********************************************************************************/
	protected GetShopItemResponse doGetShopItemRequest(GetShopItemRequest request)
	{
		GetShopItemResponse ret = new GetShopItemResponse();
		ret.result = GetShopItemResponse.RESULT_SUCCEED;
		ret.items  = GameConfig.SHOP_ITEMS;
		return ret;
	}
	
	/**********************************************************************************
	 * 购买商店道具列表
	 **********************************************************************************/
	protected BuyShopItemResponse doBuyShopItemRequest(BuyShopItemRequest request)
	{
		BuyShopItemResponse ret = new BuyShopItemResponse();
		if (request.indexOfShop >= 0 && request.indexOfShop < GameConfig.SHOP_ITEMS.datas.size()) {
			ShopItem goods = GameConfig.SHOP_ITEMS.datas.get(request.indexOfShop);
			ItemTemplate it = DataManager.getInstance().getItemTemplate(goods.itemType);
			if (it != null) {
				Player player = new Player(request.player_id);
				if (player.getData().coin >= it.priceCoin) {
					player.addCoin(-it.priceCoin);
					ret.bag_index = player.addItem(goods.itemType, goods.count);
					ret.bag_item  = player.getItem(ret.bag_index);
					ret.my_coin   = player.getData().coin;
					ret.result    = BuyShopItemResponse.RESULT_SUCCEED;
					PersistanceManager.getInstance().savePlayerFields(player.getData(), "items", "coin");
				} else {
					ret.result = BuyShopItemResponse.RESULT_NEED_MORE_COIN;
				}
			} else {
				ret.result = BuyShopItemResponse.RESULT_FAILED;
			}
		} else {
			ret.result = BuyShopItemResponse.RESULT_FAILED;
		}
		return ret;
	}
	
	
//	---------------------------------------------------------------------------------------------------------------
//	init base
//	---------------------------------------------------------------------------------------------------------------
	
	
	public Account createDefaultAccount(String id, String sign)
	{	
		Account ret 	= new Account();
		ret.id			= id;
		ret.sign		= sign;
		ret.gold 		= 99999;
		
		defaultAccount(ret);
		return ret;
	}
	
	public PlayerData createDefaultPlayer(Account acc, String nickName)
	{
		PlayerData ret 				= new PlayerData();
		ret.player_name				= acc.id;
		ret.account_id 				= acc.id;
		ret.nick_name				= nickName;
		ret.level					= 0;
		ret.coin					= GameConfig.PLAYER_START_COIN;
		ret.hp						= GameConfig.PLAYER_START_HP;
		ret.ap						= GameConfig.PLAYER_START_AP;
		ret.mp						= GameConfig.PLAYER_START_MP;
		
		ret.battle_soldier_count	= GameConfig.PLAYER_BASE_SOLDIER;
		ret.battle_skill_count		= GameConfig.PLAYER_BASE_SKILL;
		
		ret.soldiers				= GameConfig.PLAYER_INIT_SOLDIER;
		ret.skills					= GameConfig.PLAYER_INIT_SKILL;
		ret.items					= GameConfig.PLAYER_INIT_ITEM;
		
		ret.exploreStates			= new HashMap<String, ExploreState>();
		
		defaultPlayer(ret);
		
		return ret;
	}
	

	
	/**默认数据设置，比如玩家当前数据比配置文件还低*/
	public boolean defaultAccount(Account acc)
	{
		
		return false;
	}
	
	/**默认数据设置，比如玩家当前数据比配置文件还低*/
	public boolean defaultPlayer(PlayerData pd)
	{
		pd.hp						= Math.max(GameConfig.PLAYER_START_HP, 		pd.hp);
		pd.ap						= Math.max(GameConfig.PLAYER_START_AP, 		pd.ap);
		pd.mp						= Math.max(GameConfig.PLAYER_START_MP, 		pd.mp);
		pd.battle_soldier_count		= Math.max(GameConfig.PLAYER_BASE_SOLDIER, 	pd.battle_soldier_count);
		pd.battle_skill_count		= Math.max(GameConfig.PLAYER_BASE_SKILL, 	pd.battle_skill_count);
		pd.homeScene				= "scene/home";
		
		if (pd.exploreStates == null) {
			pd.exploreStates = new HashMap<String, ExploreState>();
		}
		if (pd.level <= 0) {
			pd.level = 1;
		}
		pd.cur_exp = (int) (pd.experience-DataManager.getInstance().getExpTab(pd.level).exp_pre);
		pd.next_exp = DataManager.getInstance().getExpTab(pd.level).exp_next-DataManager.getInstance().getExpTab(pd.level).exp_pre;
//		PersistanceManager.getInstance().savePlayer(pd);
		return true;
	}
	
}
