package com.fc.castle.data.message;

import java.io.IOException;
import com.cell.net.io.*;


/**
 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
 */
public class MessageCodecJava implements MutualMessageCodec
{
	public String getVersion() {
		return "Mon Mar 19 13:16:17 CST 2012";
	}

	public Class<?>[] getClasses()
	{
		return new Class<?>[]{
			com.fc.castle.data.Account.class, //1
			com.fc.castle.data.BattleEvent.class, //2
			com.fc.castle.data.BattleLog.class, //3
			com.fc.castle.data.BattlePlayer.class, //4
			com.fc.castle.data.ExploreData.class, //5
			com.fc.castle.data.ExploreState.class, //6
			com.fc.castle.data.ItemData.class, //7
			com.fc.castle.data.ItemDatas.class, //8
			com.fc.castle.data.ItemDrop.class, //9
			com.fc.castle.data.ItemDrops.class, //10
			com.fc.castle.data.Mail.class, //11
			com.fc.castle.data.MailSnap.class, //12
			com.fc.castle.data.PlayerData.class, //13
			com.fc.castle.data.PlayerFriend.class, //14
			com.fc.castle.data.PlayerMailData.class, //15
			com.fc.castle.data.PlayerQuestData.class, //16
			com.fc.castle.data.ShopItem.class, //17
			com.fc.castle.data.ShopItems.class, //18
			com.fc.castle.data.SkillData.class, //19
			com.fc.castle.data.SkillDatas.class, //20
			com.fc.castle.data.SoldierData.class, //21
			com.fc.castle.data.SoldierDatas.class, //22
			com.fc.castle.data.message.AbstractData.class, //23
			com.fc.castle.data.message.AbstractTemplate.class, //24
			com.fc.castle.data.message.Messages.BattleStartRequest.class, //25
			com.fc.castle.data.message.Messages.BattleStartResponse.class, //26
			com.fc.castle.data.message.Messages.BuyShopItemRequest.class, //27
			com.fc.castle.data.message.Messages.BuyShopItemResponse.class, //28
			com.fc.castle.data.message.Messages.CommitBattleResultRequest.class, //29
			com.fc.castle.data.message.Messages.CommitBattleResultResponse.class, //30
			com.fc.castle.data.message.Messages.CommitGuideRequest.class, //31
			com.fc.castle.data.message.Messages.CommitGuideResponse.class, //32
			com.fc.castle.data.message.Messages.GetBuffTemplateRequest.class, //33
			com.fc.castle.data.message.Messages.GetBuffTemplateResponse.class, //34
			com.fc.castle.data.message.Messages.GetEventTemplateRequest.class, //35
			com.fc.castle.data.message.Messages.GetEventTemplateResponse.class, //36
			com.fc.castle.data.message.Messages.GetExploreDataListRequest.class, //37
			com.fc.castle.data.message.Messages.GetExploreDataListResponse.class, //38
			com.fc.castle.data.message.Messages.GetExploreStateRequest.class, //39
			com.fc.castle.data.message.Messages.GetExploreStateResponse.class, //40
			com.fc.castle.data.message.Messages.GetItemTemplateRequest.class, //41
			com.fc.castle.data.message.Messages.GetItemTemplateResponse.class, //42
			com.fc.castle.data.message.Messages.GetMailRequest.class, //43
			com.fc.castle.data.message.Messages.GetMailResponse.class, //44
			com.fc.castle.data.message.Messages.GetMailSnapsRequest.class, //45
			com.fc.castle.data.message.Messages.GetMailSnapsResponse.class, //46
			com.fc.castle.data.message.Messages.GetPlayerQuestRequest.class, //47
			com.fc.castle.data.message.Messages.GetPlayerQuestResponse.class, //48
			com.fc.castle.data.message.Messages.GetRandomOnlinePlayersRequest.class, //49
			com.fc.castle.data.message.Messages.GetRandomOnlinePlayersResponse.class, //50
			com.fc.castle.data.message.Messages.GetShopItemRequest.class, //51
			com.fc.castle.data.message.Messages.GetShopItemResponse.class, //52
			com.fc.castle.data.message.Messages.GetSkillTemplateRequest.class, //53
			com.fc.castle.data.message.Messages.GetSkillTemplateResponse.class, //54
			com.fc.castle.data.message.Messages.GetUnitTemplateRequest.class, //55
			com.fc.castle.data.message.Messages.GetUnitTemplateResponse.class, //56
			com.fc.castle.data.message.Messages.LoginRequest.class, //57
			com.fc.castle.data.message.Messages.LoginResponse.class, //58
			com.fc.castle.data.message.Messages.OrganizeDefenseRequest.class, //59
			com.fc.castle.data.message.Messages.OrganizeDefenseResponse.class, //60
			com.fc.castle.data.message.Messages.RefreshPlayerDataRequest.class, //61
			com.fc.castle.data.message.Messages.RefreshPlayerDataResponse.class, //62
			com.fc.castle.data.message.Messages.SendMailRequest.class, //63
			com.fc.castle.data.message.Messages.SendMailResponse.class, //64
			com.fc.castle.data.message.Messages.UseItemRequest.class, //65
			com.fc.castle.data.message.Messages.UseItemResponse.class, //66
			com.fc.castle.data.message.Request.class, //67
			com.fc.castle.data.message.Response.class, //68
			com.fc.castle.data.template.BuffTemplate.class, //69
			com.fc.castle.data.template.Enums.AttackType.class, //70
			com.fc.castle.data.template.Enums.DefenseType.class, //71
			com.fc.castle.data.template.Enums.FightType.class, //72
			com.fc.castle.data.template.Enums.MotionType.class, //73
			com.fc.castle.data.template.Enums.SceneEventType.class, //74
			com.fc.castle.data.template.Enums.SkillSpecialEffect.class, //75
			com.fc.castle.data.template.Enums.SkillTargetType.class, //76
			com.fc.castle.data.template.EventTemplate.class, //77
			com.fc.castle.data.template.FormualMap.class, //78
			com.fc.castle.data.template.GuideData.class, //79
			com.fc.castle.data.template.ItemTemplate.class, //80
			com.fc.castle.data.template.SkillTemplate.class, //81
			com.fc.castle.data.template.UnitTemplate.class, //82

		};
	}
	
	public void readMutual(MutualMessage msg, NetDataInput in) throws IOException 
	{
		if (msg.getClass().equals(com.fc.castle.data.Account.class)) {
			_r((com.fc.castle.data.Account)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.BattleEvent.class)) {
			_r((com.fc.castle.data.BattleEvent)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.BattleLog.class)) {
			_r((com.fc.castle.data.BattleLog)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.BattlePlayer.class)) {
			_r((com.fc.castle.data.BattlePlayer)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.ExploreData.class)) {
			_r((com.fc.castle.data.ExploreData)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.ExploreState.class)) {
			_r((com.fc.castle.data.ExploreState)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.ItemData.class)) {
			_r((com.fc.castle.data.ItemData)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.ItemDatas.class)) {
			_r((com.fc.castle.data.ItemDatas)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.ItemDrop.class)) {
			_r((com.fc.castle.data.ItemDrop)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.ItemDrops.class)) {
			_r((com.fc.castle.data.ItemDrops)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.Mail.class)) {
			_r((com.fc.castle.data.Mail)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.MailSnap.class)) {
			_r((com.fc.castle.data.MailSnap)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.PlayerData.class)) {
			_r((com.fc.castle.data.PlayerData)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.PlayerFriend.class)) {
			_r((com.fc.castle.data.PlayerFriend)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.PlayerMailData.class)) {
			_r((com.fc.castle.data.PlayerMailData)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.PlayerQuestData.class)) {
			_r((com.fc.castle.data.PlayerQuestData)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.ShopItem.class)) {
			_r((com.fc.castle.data.ShopItem)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.ShopItems.class)) {
			_r((com.fc.castle.data.ShopItems)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.SkillData.class)) {
			_r((com.fc.castle.data.SkillData)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.SkillDatas.class)) {
			_r((com.fc.castle.data.SkillDatas)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.SoldierData.class)) {
			_r((com.fc.castle.data.SoldierData)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.SoldierDatas.class)) {
			_r((com.fc.castle.data.SoldierDatas)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.BattleStartRequest.class)) {
			_r((com.fc.castle.data.message.Messages.BattleStartRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.BattleStartResponse.class)) {
			_r((com.fc.castle.data.message.Messages.BattleStartResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.BuyShopItemRequest.class)) {
			_r((com.fc.castle.data.message.Messages.BuyShopItemRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.BuyShopItemResponse.class)) {
			_r((com.fc.castle.data.message.Messages.BuyShopItemResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.CommitBattleResultRequest.class)) {
			_r((com.fc.castle.data.message.Messages.CommitBattleResultRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.CommitBattleResultResponse.class)) {
			_r((com.fc.castle.data.message.Messages.CommitBattleResultResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.CommitGuideRequest.class)) {
			_r((com.fc.castle.data.message.Messages.CommitGuideRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.CommitGuideResponse.class)) {
			_r((com.fc.castle.data.message.Messages.CommitGuideResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetBuffTemplateRequest.class)) {
			_r((com.fc.castle.data.message.Messages.GetBuffTemplateRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetBuffTemplateResponse.class)) {
			_r((com.fc.castle.data.message.Messages.GetBuffTemplateResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetEventTemplateRequest.class)) {
			_r((com.fc.castle.data.message.Messages.GetEventTemplateRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetEventTemplateResponse.class)) {
			_r((com.fc.castle.data.message.Messages.GetEventTemplateResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetExploreDataListRequest.class)) {
			_r((com.fc.castle.data.message.Messages.GetExploreDataListRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetExploreDataListResponse.class)) {
			_r((com.fc.castle.data.message.Messages.GetExploreDataListResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetExploreStateRequest.class)) {
			_r((com.fc.castle.data.message.Messages.GetExploreStateRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetExploreStateResponse.class)) {
			_r((com.fc.castle.data.message.Messages.GetExploreStateResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetItemTemplateRequest.class)) {
			_r((com.fc.castle.data.message.Messages.GetItemTemplateRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetItemTemplateResponse.class)) {
			_r((com.fc.castle.data.message.Messages.GetItemTemplateResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetMailRequest.class)) {
			_r((com.fc.castle.data.message.Messages.GetMailRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetMailResponse.class)) {
			_r((com.fc.castle.data.message.Messages.GetMailResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetMailSnapsRequest.class)) {
			_r((com.fc.castle.data.message.Messages.GetMailSnapsRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetMailSnapsResponse.class)) {
			_r((com.fc.castle.data.message.Messages.GetMailSnapsResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetPlayerQuestRequest.class)) {
			_r((com.fc.castle.data.message.Messages.GetPlayerQuestRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetPlayerQuestResponse.class)) {
			_r((com.fc.castle.data.message.Messages.GetPlayerQuestResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetRandomOnlinePlayersRequest.class)) {
			_r((com.fc.castle.data.message.Messages.GetRandomOnlinePlayersRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetRandomOnlinePlayersResponse.class)) {
			_r((com.fc.castle.data.message.Messages.GetRandomOnlinePlayersResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetShopItemRequest.class)) {
			_r((com.fc.castle.data.message.Messages.GetShopItemRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetShopItemResponse.class)) {
			_r((com.fc.castle.data.message.Messages.GetShopItemResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetSkillTemplateRequest.class)) {
			_r((com.fc.castle.data.message.Messages.GetSkillTemplateRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetSkillTemplateResponse.class)) {
			_r((com.fc.castle.data.message.Messages.GetSkillTemplateResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetUnitTemplateRequest.class)) {
			_r((com.fc.castle.data.message.Messages.GetUnitTemplateRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetUnitTemplateResponse.class)) {
			_r((com.fc.castle.data.message.Messages.GetUnitTemplateResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.LoginRequest.class)) {
			_r((com.fc.castle.data.message.Messages.LoginRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.LoginResponse.class)) {
			_r((com.fc.castle.data.message.Messages.LoginResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.OrganizeDefenseRequest.class)) {
			_r((com.fc.castle.data.message.Messages.OrganizeDefenseRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.OrganizeDefenseResponse.class)) {
			_r((com.fc.castle.data.message.Messages.OrganizeDefenseResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.RefreshPlayerDataRequest.class)) {
			_r((com.fc.castle.data.message.Messages.RefreshPlayerDataRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.RefreshPlayerDataResponse.class)) {
			_r((com.fc.castle.data.message.Messages.RefreshPlayerDataResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.SendMailRequest.class)) {
			_r((com.fc.castle.data.message.Messages.SendMailRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.SendMailResponse.class)) {
			_r((com.fc.castle.data.message.Messages.SendMailResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.UseItemRequest.class)) {
			_r((com.fc.castle.data.message.Messages.UseItemRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.UseItemResponse.class)) {
			_r((com.fc.castle.data.message.Messages.UseItemResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.template.BuffTemplate.class)) {
			_r((com.fc.castle.data.template.BuffTemplate)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.template.Enums.AttackType.class)) {
			_r((com.fc.castle.data.template.Enums.AttackType)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.template.Enums.DefenseType.class)) {
			_r((com.fc.castle.data.template.Enums.DefenseType)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.template.Enums.FightType.class)) {
			_r((com.fc.castle.data.template.Enums.FightType)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.template.Enums.MotionType.class)) {
			_r((com.fc.castle.data.template.Enums.MotionType)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.template.Enums.SceneEventType.class)) {
			_r((com.fc.castle.data.template.Enums.SceneEventType)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.template.Enums.SkillSpecialEffect.class)) {
			_r((com.fc.castle.data.template.Enums.SkillSpecialEffect)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.template.Enums.SkillTargetType.class)) {
			_r((com.fc.castle.data.template.Enums.SkillTargetType)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.template.EventTemplate.class)) {
			_r((com.fc.castle.data.template.EventTemplate)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.template.FormualMap.class)) {
			_r((com.fc.castle.data.template.FormualMap)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.template.GuideData.class)) {
			_r((com.fc.castle.data.template.GuideData)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.template.ItemTemplate.class)) {
			_r((com.fc.castle.data.template.ItemTemplate)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.template.SkillTemplate.class)) {
			_r((com.fc.castle.data.template.SkillTemplate)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.template.UnitTemplate.class)) {
			_r((com.fc.castle.data.template.UnitTemplate)msg, in); return;
		}

	}

	public void writeMutual(MutualMessage msg, NetDataOutput out) throws IOException 
	{
		if (msg.getClass().equals(com.fc.castle.data.Account.class)) {
			_w((com.fc.castle.data.Account)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.BattleEvent.class)) {
			_w((com.fc.castle.data.BattleEvent)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.BattleLog.class)) {
			_w((com.fc.castle.data.BattleLog)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.BattlePlayer.class)) {
			_w((com.fc.castle.data.BattlePlayer)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.ExploreData.class)) {
			_w((com.fc.castle.data.ExploreData)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.ExploreState.class)) {
			_w((com.fc.castle.data.ExploreState)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.ItemData.class)) {
			_w((com.fc.castle.data.ItemData)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.ItemDatas.class)) {
			_w((com.fc.castle.data.ItemDatas)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.ItemDrop.class)) {
			_w((com.fc.castle.data.ItemDrop)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.ItemDrops.class)) {
			_w((com.fc.castle.data.ItemDrops)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.Mail.class)) {
			_w((com.fc.castle.data.Mail)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.MailSnap.class)) {
			_w((com.fc.castle.data.MailSnap)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.PlayerData.class)) {
			_w((com.fc.castle.data.PlayerData)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.PlayerFriend.class)) {
			_w((com.fc.castle.data.PlayerFriend)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.PlayerMailData.class)) {
			_w((com.fc.castle.data.PlayerMailData)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.PlayerQuestData.class)) {
			_w((com.fc.castle.data.PlayerQuestData)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.ShopItem.class)) {
			_w((com.fc.castle.data.ShopItem)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.ShopItems.class)) {
			_w((com.fc.castle.data.ShopItems)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.SkillData.class)) {
			_w((com.fc.castle.data.SkillData)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.SkillDatas.class)) {
			_w((com.fc.castle.data.SkillDatas)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.SoldierData.class)) {
			_w((com.fc.castle.data.SoldierData)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.SoldierDatas.class)) {
			_w((com.fc.castle.data.SoldierDatas)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.BattleStartRequest.class)) {
			_w((com.fc.castle.data.message.Messages.BattleStartRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.BattleStartResponse.class)) {
			_w((com.fc.castle.data.message.Messages.BattleStartResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.BuyShopItemRequest.class)) {
			_w((com.fc.castle.data.message.Messages.BuyShopItemRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.BuyShopItemResponse.class)) {
			_w((com.fc.castle.data.message.Messages.BuyShopItemResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.CommitBattleResultRequest.class)) {
			_w((com.fc.castle.data.message.Messages.CommitBattleResultRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.CommitBattleResultResponse.class)) {
			_w((com.fc.castle.data.message.Messages.CommitBattleResultResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.CommitGuideRequest.class)) {
			_w((com.fc.castle.data.message.Messages.CommitGuideRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.CommitGuideResponse.class)) {
			_w((com.fc.castle.data.message.Messages.CommitGuideResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetBuffTemplateRequest.class)) {
			_w((com.fc.castle.data.message.Messages.GetBuffTemplateRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetBuffTemplateResponse.class)) {
			_w((com.fc.castle.data.message.Messages.GetBuffTemplateResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetEventTemplateRequest.class)) {
			_w((com.fc.castle.data.message.Messages.GetEventTemplateRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetEventTemplateResponse.class)) {
			_w((com.fc.castle.data.message.Messages.GetEventTemplateResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetExploreDataListRequest.class)) {
			_w((com.fc.castle.data.message.Messages.GetExploreDataListRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetExploreDataListResponse.class)) {
			_w((com.fc.castle.data.message.Messages.GetExploreDataListResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetExploreStateRequest.class)) {
			_w((com.fc.castle.data.message.Messages.GetExploreStateRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetExploreStateResponse.class)) {
			_w((com.fc.castle.data.message.Messages.GetExploreStateResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetItemTemplateRequest.class)) {
			_w((com.fc.castle.data.message.Messages.GetItemTemplateRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetItemTemplateResponse.class)) {
			_w((com.fc.castle.data.message.Messages.GetItemTemplateResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetMailRequest.class)) {
			_w((com.fc.castle.data.message.Messages.GetMailRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetMailResponse.class)) {
			_w((com.fc.castle.data.message.Messages.GetMailResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetMailSnapsRequest.class)) {
			_w((com.fc.castle.data.message.Messages.GetMailSnapsRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetMailSnapsResponse.class)) {
			_w((com.fc.castle.data.message.Messages.GetMailSnapsResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetPlayerQuestRequest.class)) {
			_w((com.fc.castle.data.message.Messages.GetPlayerQuestRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetPlayerQuestResponse.class)) {
			_w((com.fc.castle.data.message.Messages.GetPlayerQuestResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetRandomOnlinePlayersRequest.class)) {
			_w((com.fc.castle.data.message.Messages.GetRandomOnlinePlayersRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetRandomOnlinePlayersResponse.class)) {
			_w((com.fc.castle.data.message.Messages.GetRandomOnlinePlayersResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetShopItemRequest.class)) {
			_w((com.fc.castle.data.message.Messages.GetShopItemRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetShopItemResponse.class)) {
			_w((com.fc.castle.data.message.Messages.GetShopItemResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetSkillTemplateRequest.class)) {
			_w((com.fc.castle.data.message.Messages.GetSkillTemplateRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetSkillTemplateResponse.class)) {
			_w((com.fc.castle.data.message.Messages.GetSkillTemplateResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetUnitTemplateRequest.class)) {
			_w((com.fc.castle.data.message.Messages.GetUnitTemplateRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.GetUnitTemplateResponse.class)) {
			_w((com.fc.castle.data.message.Messages.GetUnitTemplateResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.LoginRequest.class)) {
			_w((com.fc.castle.data.message.Messages.LoginRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.LoginResponse.class)) {
			_w((com.fc.castle.data.message.Messages.LoginResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.OrganizeDefenseRequest.class)) {
			_w((com.fc.castle.data.message.Messages.OrganizeDefenseRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.OrganizeDefenseResponse.class)) {
			_w((com.fc.castle.data.message.Messages.OrganizeDefenseResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.RefreshPlayerDataRequest.class)) {
			_w((com.fc.castle.data.message.Messages.RefreshPlayerDataRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.RefreshPlayerDataResponse.class)) {
			_w((com.fc.castle.data.message.Messages.RefreshPlayerDataResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.SendMailRequest.class)) {
			_w((com.fc.castle.data.message.Messages.SendMailRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.SendMailResponse.class)) {
			_w((com.fc.castle.data.message.Messages.SendMailResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.UseItemRequest.class)) {
			_w((com.fc.castle.data.message.Messages.UseItemRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.message.Messages.UseItemResponse.class)) {
			_w((com.fc.castle.data.message.Messages.UseItemResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.template.BuffTemplate.class)) {
			_w((com.fc.castle.data.template.BuffTemplate)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.template.Enums.AttackType.class)) {
			_w((com.fc.castle.data.template.Enums.AttackType)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.template.Enums.DefenseType.class)) {
			_w((com.fc.castle.data.template.Enums.DefenseType)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.template.Enums.FightType.class)) {
			_w((com.fc.castle.data.template.Enums.FightType)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.template.Enums.MotionType.class)) {
			_w((com.fc.castle.data.template.Enums.MotionType)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.template.Enums.SceneEventType.class)) {
			_w((com.fc.castle.data.template.Enums.SceneEventType)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.template.Enums.SkillSpecialEffect.class)) {
			_w((com.fc.castle.data.template.Enums.SkillSpecialEffect)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.template.Enums.SkillTargetType.class)) {
			_w((com.fc.castle.data.template.Enums.SkillTargetType)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.template.EventTemplate.class)) {
			_w((com.fc.castle.data.template.EventTemplate)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.template.FormualMap.class)) {
			_w((com.fc.castle.data.template.FormualMap)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.template.GuideData.class)) {
			_w((com.fc.castle.data.template.GuideData)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.template.ItemTemplate.class)) {
			_w((com.fc.castle.data.template.ItemTemplate)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.template.SkillTemplate.class)) {
			_w((com.fc.castle.data.template.SkillTemplate)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.castle.data.template.UnitTemplate.class)) {
			_w((com.fc.castle.data.template.UnitTemplate)msg, out); return;
		}

	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.Account
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.Account msg, NetDataInput in) throws IOException {
		msg.id = in.readUTF();
		msg.sign = in.readUTF();
		msg.gold = in.readInt();
		msg.players_id = in.readIntArray();
	}
	private void _w(com.fc.castle.data.Account msg, NetDataOutput out) throws IOException {
		out.writeUTF(msg.id);
		out.writeUTF(msg.sign);
		out.writeInt(msg.gold);
		out.writeIntArray(msg.players_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.BattleEvent
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.BattleEvent msg, NetDataInput in) throws IOException {
		msg.time = in.readInt();
		msg.event = in.readByte();
		msg.force = in.readByte();
		msg.datas = in.readIntArray();
	}
	private void _w(com.fc.castle.data.BattleEvent msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.time);
		out.writeByte(msg.event);
		out.writeByte(msg.force);
		out.writeIntArray(msg.datas);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.BattleLog
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.BattleLog msg, NetDataInput in) throws IOException {
		msg.battle = in.readMutual(com.fc.castle.data.message.Messages.BattleStartResponse.class);
		msg.logs = (java.util.ArrayList)in.readCollection(java.util.ArrayList.class, NetDataTypes.TYPE_MUTUAL);
	}
	private void _w(com.fc.castle.data.BattleLog msg, NetDataOutput out) throws IOException {
		out.writeMutual(msg.battle);
		out.writeCollection(msg.logs, NetDataTypes.TYPE_MUTUAL);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.BattlePlayer
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.BattlePlayer msg, NetDataInput in) throws IOException {
		msg.type = in.readInt();
		msg.playerID = in.readInt();
		msg.name = in.readUTF();
		msg.hp = in.readInt();
		msg.mp = in.readInt();
		msg.ap = in.readInt();
		msg.soldiers = in.readMutual(com.fc.castle.data.SoldierDatas.class);
		msg.skills = in.readMutual(com.fc.castle.data.SkillDatas.class);
	}
	private void _w(com.fc.castle.data.BattlePlayer msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.type);
		out.writeInt(msg.playerID);
		out.writeUTF(msg.name);
		out.writeInt(msg.hp);
		out.writeInt(msg.mp);
		out.writeInt(msg.ap);
		out.writeMutual(msg.soldiers);
		out.writeMutual(msg.skills);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.ExploreData
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.ExploreData msg, NetDataInput in) throws IOException {
		msg.UnitName = in.readUTF();
		msg.explore_name = in.readUTF();
		msg.refreshTime = in.readInt();
		msg.last_time = in.readDate(java.sql.Date.class);
		msg.last_explorer = in.readMutual(com.fc.castle.data.PlayerFriend.class);
	}
	private void _w(com.fc.castle.data.ExploreData msg, NetDataOutput out) throws IOException {
		out.writeUTF(msg.UnitName);
		out.writeUTF(msg.explore_name);
		out.writeInt(msg.refreshTime);
		out.writeDate(msg.last_time);
		out.writeMutual(msg.last_explorer);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.ExploreState
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.ExploreState msg, NetDataInput in) throws IOException {
		msg.UnitName = in.readUTF();
		msg.last_time = in.readDate(java.sql.Date.class);
		msg.last_explorer = in.readMutual(com.fc.castle.data.PlayerFriend.class);
		msg.explore_count = in.readInt();
	}
	private void _w(com.fc.castle.data.ExploreState msg, NetDataOutput out) throws IOException {
		out.writeUTF(msg.UnitName);
		out.writeDate(msg.last_time);
		out.writeMutual(msg.last_explorer);
		out.writeInt(msg.explore_count);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.ItemData
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.ItemData msg, NetDataInput in) throws IOException {
		msg.itemType = in.readInt();
		msg.count = in.readInt();
	}
	private void _w(com.fc.castle.data.ItemData msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.itemType);
		out.writeInt(msg.count);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.ItemDatas
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.ItemDatas msg, NetDataInput in) throws IOException {
		msg.datas = (java.util.ArrayList)in.readCollection(java.util.ArrayList.class, NetDataTypes.TYPE_MUTUAL);
	}
	private void _w(com.fc.castle.data.ItemDatas msg, NetDataOutput out) throws IOException {
		out.writeCollection(msg.datas, NetDataTypes.TYPE_MUTUAL);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.ItemDrop
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.ItemDrop msg, NetDataInput in) throws IOException {
		msg.itemType = in.readInt();
		msg.count = in.readInt();
		msg.percent = in.readFloat();
	}
	private void _w(com.fc.castle.data.ItemDrop msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.itemType);
		out.writeInt(msg.count);
		out.writeFloat(msg.percent);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.ItemDrops
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.ItemDrops msg, NetDataInput in) throws IOException {
		msg.datas = (java.util.ArrayList)in.readCollection(java.util.ArrayList.class, NetDataTypes.TYPE_MUTUAL);
	}
	private void _w(com.fc.castle.data.ItemDrops msg, NetDataOutput out) throws IOException {
		out.writeCollection(msg.datas, NetDataTypes.TYPE_MUTUAL);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.Mail
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.Mail msg, NetDataInput in) throws IOException {
		msg.id = in.readInt();
		msg.type = in.readInt();
		msg.sendTime = in.readDate(java.sql.Date.class);
		msg.cyccode = in.readInt();
		msg.senderName = in.readUTF();
		msg.receiverName = in.readUTF();
		msg.senderPlayerID = in.readInt();
		msg.receiverPlayerID = in.readInt();
		msg.title = in.readUTF();
		msg.content = in.readUTF();
		msg.readed = in.readBoolean();
	}
	private void _w(com.fc.castle.data.Mail msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.id);
		out.writeInt(msg.type);
		out.writeDate(msg.sendTime);
		out.writeInt(msg.cyccode);
		out.writeUTF(msg.senderName);
		out.writeUTF(msg.receiverName);
		out.writeInt(msg.senderPlayerID);
		out.writeInt(msg.receiverPlayerID);
		out.writeUTF(msg.title);
		out.writeUTF(msg.content);
		out.writeBoolean(msg.readed);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.MailSnap
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.MailSnap msg, NetDataInput in) throws IOException {
		msg.id = in.readInt();
		msg.title = in.readUTF();
		msg.senderPlayerName = in.readUTF();
		msg.senderPlayerID = in.readInt();
		msg.readed = in.readBoolean();
	}
	private void _w(com.fc.castle.data.MailSnap msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.id);
		out.writeUTF(msg.title);
		out.writeUTF(msg.senderPlayerName);
		out.writeInt(msg.senderPlayerID);
		out.writeBoolean(msg.readed);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.PlayerData
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.PlayerData msg, NetDataInput in) throws IOException {
		msg.player_id = in.readInt();
		msg.player_name = in.readUTF();
		msg.nick_name = in.readUTF();
		msg.account_id = in.readUTF();
		msg.level = in.readInt();
		msg.hp = in.readInt();
		msg.ap = in.readInt();
		msg.mp = in.readInt();
		msg.experience = in.readInt();
		msg.cur_exp = in.readInt();
		msg.next_exp = in.readInt();
		msg.coin = in.readInt();
		msg.homeScene = in.readUTF();
		msg.soldiers = in.readMutual(com.fc.castle.data.SoldierDatas.class);
		msg.skills = in.readMutual(com.fc.castle.data.SkillDatas.class);
		msg.items = in.readMutual(com.fc.castle.data.ItemDatas.class);
		msg.battle_soldier_count = in.readInt();
		msg.battle_skill_count = in.readInt();
		msg.lastBattle = in.readMutual(com.fc.castle.data.message.Messages.BattleStartResponse.class);
		msg.organizeDefense = in.readMutual(com.fc.castle.data.message.Messages.OrganizeDefenseRequest.class);
		msg.exploreStates = (java.util.HashMap)in.readMap(java.util.HashMap.class, NetDataTypes.TYPE_STRING, NetDataTypes.TYPE_MUTUAL);
	}
	private void _w(com.fc.castle.data.PlayerData msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.player_id);
		out.writeUTF(msg.player_name);
		out.writeUTF(msg.nick_name);
		out.writeUTF(msg.account_id);
		out.writeInt(msg.level);
		out.writeInt(msg.hp);
		out.writeInt(msg.ap);
		out.writeInt(msg.mp);
		out.writeInt(msg.experience);
		out.writeInt(msg.cur_exp);
		out.writeInt(msg.next_exp);
		out.writeInt(msg.coin);
		out.writeUTF(msg.homeScene);
		out.writeMutual(msg.soldiers);
		out.writeMutual(msg.skills);
		out.writeMutual(msg.items);
		out.writeInt(msg.battle_soldier_count);
		out.writeInt(msg.battle_skill_count);
		out.writeMutual(msg.lastBattle);
		out.writeMutual(msg.organizeDefense);
		out.writeMap(msg.exploreStates, NetDataTypes.TYPE_STRING, NetDataTypes.TYPE_MUTUAL);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.PlayerFriend
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.PlayerFriend msg, NetDataInput in) throws IOException {
		msg.playerID = in.readInt();
		msg.playerName = in.readUTF();
		msg.playerNickName = in.readUTF();
		msg.headUrl = in.readUTF();
		msg.level = in.readInt();
	}
	private void _w(com.fc.castle.data.PlayerFriend msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.playerID);
		out.writeUTF(msg.playerName);
		out.writeUTF(msg.playerNickName);
		out.writeUTF(msg.headUrl);
		out.writeInt(msg.level);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.PlayerMailData
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.PlayerMailData msg, NetDataInput in) throws IOException {
		msg.player_id = in.readInt();
		msg.player_name = in.readUTF();
		msg.mails = (java.util.HashMap)in.readMap(java.util.HashMap.class, NetDataTypes.TYPE_INT, NetDataTypes.TYPE_MUTUAL);
		msg.friendsMaxCount = in.readInt();
		msg.firends = (java.util.HashMap)in.readMap(java.util.HashMap.class, NetDataTypes.TYPE_INT, NetDataTypes.TYPE_MUTUAL);
	}
	private void _w(com.fc.castle.data.PlayerMailData msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.player_id);
		out.writeUTF(msg.player_name);
		out.writeMap(msg.mails, NetDataTypes.TYPE_INT, NetDataTypes.TYPE_MUTUAL);
		out.writeInt(msg.friendsMaxCount);
		out.writeMap(msg.firends, NetDataTypes.TYPE_INT, NetDataTypes.TYPE_MUTUAL);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.PlayerQuestData
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.PlayerQuestData msg, NetDataInput in) throws IOException {
		msg.player_id = in.readInt();
		msg.guide_steps = in.readInt();
	}
	private void _w(com.fc.castle.data.PlayerQuestData msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.player_id);
		out.writeInt(msg.guide_steps);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.ShopItem
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.ShopItem msg, NetDataInput in) throws IOException {
		msg.itemType = in.readInt();
		msg.count = in.readInt();
	}
	private void _w(com.fc.castle.data.ShopItem msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.itemType);
		out.writeInt(msg.count);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.ShopItems
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.ShopItems msg, NetDataInput in) throws IOException {
		msg.datas = (java.util.ArrayList)in.readCollection(java.util.ArrayList.class, NetDataTypes.TYPE_MUTUAL);
	}
	private void _w(com.fc.castle.data.ShopItems msg, NetDataOutput out) throws IOException {
		out.writeCollection(msg.datas, NetDataTypes.TYPE_MUTUAL);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.SkillData
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.SkillData msg, NetDataInput in) throws IOException {
		msg.skillType = in.readInt();
	}
	private void _w(com.fc.castle.data.SkillData msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.skillType);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.SkillDatas
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.SkillDatas msg, NetDataInput in) throws IOException {
		msg.datas = (java.util.ArrayList)in.readCollection(java.util.ArrayList.class, NetDataTypes.TYPE_MUTUAL);
	}
	private void _w(com.fc.castle.data.SkillDatas msg, NetDataOutput out) throws IOException {
		out.writeCollection(msg.datas, NetDataTypes.TYPE_MUTUAL);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.SoldierData
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.SoldierData msg, NetDataInput in) throws IOException {
		msg.unitType = in.readInt();
	}
	private void _w(com.fc.castle.data.SoldierData msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.unitType);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.SoldierDatas
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.SoldierDatas msg, NetDataInput in) throws IOException {
		msg.datas = (java.util.ArrayList)in.readCollection(java.util.ArrayList.class, NetDataTypes.TYPE_MUTUAL);
	}
	private void _w(com.fc.castle.data.SoldierDatas msg, NetDataOutput out) throws IOException {
		out.writeCollection(msg.datas, NetDataTypes.TYPE_MUTUAL);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.BattleStartRequest
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.BattleStartRequest msg, NetDataInput in) throws IOException {
		msg.battleType = in.readInt();
		msg.targetPlayerID = in.readInt();
		msg.scene_unit_name = in.readUTF();
		msg.soldiers = in.readMutual(com.fc.castle.data.SoldierDatas.class);
		msg.skills = in.readMutual(com.fc.castle.data.SkillDatas.class);
		msg.test_forceB_soldiers = in.readMutual(com.fc.castle.data.SoldierDatas.class);
		msg.test_forceB_skills = in.readMutual(com.fc.castle.data.SkillDatas.class);
		msg.player_id = in.readInt();
	}
	private void _w(com.fc.castle.data.message.Messages.BattleStartRequest msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.battleType);
		out.writeInt(msg.targetPlayerID);
		out.writeUTF(msg.scene_unit_name);
		out.writeMutual(msg.soldiers);
		out.writeMutual(msg.skills);
		out.writeMutual(msg.test_forceB_soldiers);
		out.writeMutual(msg.test_forceB_skills);
		out.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.BattleStartResponse
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.BattleStartResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readByte();
		msg.battleType = in.readInt();
		msg.targetPlayerID = in.readInt();
		msg.scene_unit_name = in.readUTF();
		msg.guideStep = in.readInt();
		msg.cmap_id = in.readUTF();
		msg.ap_upgrade_time = in.readInt();
		msg.ap_upgrade_count = in.readInt();
		msg.formual_map = in.readMutual(com.fc.castle.data.template.FormualMap.class);
		msg.forceA = in.readMutual(com.fc.castle.data.BattlePlayer.class);
		msg.forceB = in.readMutual(com.fc.castle.data.BattlePlayer.class);
	}
	private void _w(com.fc.castle.data.message.Messages.BattleStartResponse msg, NetDataOutput out) throws IOException {
		out.writeByte(msg.result);
		out.writeInt(msg.battleType);
		out.writeInt(msg.targetPlayerID);
		out.writeUTF(msg.scene_unit_name);
		out.writeInt(msg.guideStep);
		out.writeUTF(msg.cmap_id);
		out.writeInt(msg.ap_upgrade_time);
		out.writeInt(msg.ap_upgrade_count);
		out.writeMutual(msg.formual_map);
		out.writeMutual(msg.forceA);
		out.writeMutual(msg.forceB);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.BuyShopItemRequest
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.BuyShopItemRequest msg, NetDataInput in) throws IOException {
		msg.indexOfShop = in.readInt();
		msg.player_id = in.readInt();
	}
	private void _w(com.fc.castle.data.message.Messages.BuyShopItemRequest msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.indexOfShop);
		out.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.BuyShopItemResponse
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.BuyShopItemResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readByte();
		msg.bag_index = in.readInt();
		msg.bag_item = in.readMutual(com.fc.castle.data.ItemData.class);
		msg.my_coin = in.readInt();
	}
	private void _w(com.fc.castle.data.message.Messages.BuyShopItemResponse msg, NetDataOutput out) throws IOException {
		out.writeByte(msg.result);
		out.writeInt(msg.bag_index);
		out.writeMutual(msg.bag_item);
		out.writeInt(msg.my_coin);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.CommitBattleResultRequest
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.CommitBattleResultRequest msg, NetDataInput in) throws IOException {
		msg.log = in.readMutual(com.fc.castle.data.BattleLog.class);
		msg.scene_unit_name = in.readUTF();
		msg.is_win = in.readBoolean();
		msg.player_id = in.readInt();
	}
	private void _w(com.fc.castle.data.message.Messages.CommitBattleResultRequest msg, NetDataOutput out) throws IOException {
		out.writeMutual(msg.log);
		out.writeUTF(msg.scene_unit_name);
		out.writeBoolean(msg.is_win);
		out.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.CommitBattleResultResponse
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.CommitBattleResultResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readByte();
		msg.explore_state = in.readMutual(com.fc.castle.data.ExploreState.class);
	}
	private void _w(com.fc.castle.data.message.Messages.CommitBattleResultResponse msg, NetDataOutput out) throws IOException {
		out.writeByte(msg.result);
		out.writeMutual(msg.explore_state);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.CommitGuideRequest
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.CommitGuideRequest msg, NetDataInput in) throws IOException {
		msg.guide_steps = in.readInt();
		msg.player_id = in.readInt();
	}
	private void _w(com.fc.castle.data.message.Messages.CommitGuideRequest msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.guide_steps);
		out.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.CommitGuideResponse
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.CommitGuideResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readByte();
		msg.data = in.readMutual(com.fc.castle.data.PlayerQuestData.class);
	}
	private void _w(com.fc.castle.data.message.Messages.CommitGuideResponse msg, NetDataOutput out) throws IOException {
		out.writeByte(msg.result);
		out.writeMutual(msg.data);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetBuffTemplateRequest
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.GetBuffTemplateRequest msg, NetDataInput in) throws IOException {
		msg.player_id = in.readInt();
	}
	private void _w(com.fc.castle.data.message.Messages.GetBuffTemplateRequest msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetBuffTemplateResponse
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.GetBuffTemplateResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readByte();
		msg.buff_templates = (java.util.LinkedHashMap)in.readMap(java.util.LinkedHashMap.class, NetDataTypes.TYPE_INT, NetDataTypes.TYPE_MUTUAL);
	}
	private void _w(com.fc.castle.data.message.Messages.GetBuffTemplateResponse msg, NetDataOutput out) throws IOException {
		out.writeByte(msg.result);
		out.writeMap(msg.buff_templates, NetDataTypes.TYPE_INT, NetDataTypes.TYPE_MUTUAL);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetEventTemplateRequest
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.GetEventTemplateRequest msg, NetDataInput in) throws IOException {
		msg.event_types = in.readIntArray();
		msg.player_id = in.readInt();
	}
	private void _w(com.fc.castle.data.message.Messages.GetEventTemplateRequest msg, NetDataOutput out) throws IOException {
		out.writeIntArray(msg.event_types);
		out.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetEventTemplateResponse
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.GetEventTemplateResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readByte();
		msg.event_templates = (com.fc.castle.data.template.EventTemplate[])in.readMutualArray(com.fc.castle.data.template.EventTemplate.class);
	}
	private void _w(com.fc.castle.data.message.Messages.GetEventTemplateResponse msg, NetDataOutput out) throws IOException {
		out.writeByte(msg.result);
		out.writeMutualArray(msg.event_templates);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetExploreDataListRequest
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.GetExploreDataListRequest msg, NetDataInput in) throws IOException {
		msg.targetPlayerID = in.readInt();
		msg.player_id = in.readInt();
	}
	private void _w(com.fc.castle.data.message.Messages.GetExploreDataListRequest msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.targetPlayerID);
		out.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetExploreDataListResponse
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.GetExploreDataListResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readByte();
		msg.targetPlayer = in.readMutual(com.fc.castle.data.PlayerFriend.class);
		msg.targetPlayerHomeScene = in.readUTF();
		msg.explores = (java.util.HashMap)in.readMap(java.util.HashMap.class, NetDataTypes.TYPE_STRING, NetDataTypes.TYPE_MUTUAL);
	}
	private void _w(com.fc.castle.data.message.Messages.GetExploreDataListResponse msg, NetDataOutput out) throws IOException {
		out.writeByte(msg.result);
		out.writeMutual(msg.targetPlayer);
		out.writeUTF(msg.targetPlayerHomeScene);
		out.writeMap(msg.explores, NetDataTypes.TYPE_STRING, NetDataTypes.TYPE_MUTUAL);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetExploreStateRequest
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.GetExploreStateRequest msg, NetDataInput in) throws IOException {
		msg.unitName = in.readUTF();
		msg.targetPlayerID = in.readInt();
		msg.player_id = in.readInt();
	}
	private void _w(com.fc.castle.data.message.Messages.GetExploreStateRequest msg, NetDataOutput out) throws IOException {
		out.writeUTF(msg.unitName);
		out.writeInt(msg.targetPlayerID);
		out.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetExploreStateResponse
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.GetExploreStateResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readByte();
		msg.state = in.readMutual(com.fc.castle.data.ExploreState.class);
		msg.event = in.readMutual(com.fc.castle.data.template.EventTemplate.class);
	}
	private void _w(com.fc.castle.data.message.Messages.GetExploreStateResponse msg, NetDataOutput out) throws IOException {
		out.writeByte(msg.result);
		out.writeMutual(msg.state);
		out.writeMutual(msg.event);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetItemTemplateRequest
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.GetItemTemplateRequest msg, NetDataInput in) throws IOException {
		msg.item_types = in.readIntArray();
		msg.player_id = in.readInt();
	}
	private void _w(com.fc.castle.data.message.Messages.GetItemTemplateRequest msg, NetDataOutput out) throws IOException {
		out.writeIntArray(msg.item_types);
		out.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetItemTemplateResponse
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.GetItemTemplateResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readByte();
		msg.item_templates = (com.fc.castle.data.template.ItemTemplate[])in.readMutualArray(com.fc.castle.data.template.ItemTemplate.class);
	}
	private void _w(com.fc.castle.data.message.Messages.GetItemTemplateResponse msg, NetDataOutput out) throws IOException {
		out.writeByte(msg.result);
		out.writeMutualArray(msg.item_templates);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetMailRequest
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.GetMailRequest msg, NetDataInput in) throws IOException {
		msg.mailID = in.readInt();
		msg.player_id = in.readInt();
	}
	private void _w(com.fc.castle.data.message.Messages.GetMailRequest msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.mailID);
		out.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetMailResponse
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.GetMailResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readByte();
		msg.mail = in.readMutual(com.fc.castle.data.Mail.class);
	}
	private void _w(com.fc.castle.data.message.Messages.GetMailResponse msg, NetDataOutput out) throws IOException {
		out.writeByte(msg.result);
		out.writeMutual(msg.mail);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetMailSnapsRequest
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.GetMailSnapsRequest msg, NetDataInput in) throws IOException {
		msg.player_id = in.readInt();
	}
	private void _w(com.fc.castle.data.message.Messages.GetMailSnapsRequest msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetMailSnapsResponse
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.GetMailSnapsResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readByte();
		msg.mailSnaps = in.readMutual(com.fc.castle.data.PlayerMailData.class);
	}
	private void _w(com.fc.castle.data.message.Messages.GetMailSnapsResponse msg, NetDataOutput out) throws IOException {
		out.writeByte(msg.result);
		out.writeMutual(msg.mailSnaps);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetPlayerQuestRequest
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.GetPlayerQuestRequest msg, NetDataInput in) throws IOException {
		msg.player_id = in.readInt();
	}
	private void _w(com.fc.castle.data.message.Messages.GetPlayerQuestRequest msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetPlayerQuestResponse
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.GetPlayerQuestResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readByte();
		msg.data = in.readMutual(com.fc.castle.data.PlayerQuestData.class);
	}
	private void _w(com.fc.castle.data.message.Messages.GetPlayerQuestResponse msg, NetDataOutput out) throws IOException {
		out.writeByte(msg.result);
		out.writeMutual(msg.data);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetRandomOnlinePlayersRequest
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.GetRandomOnlinePlayersRequest msg, NetDataInput in) throws IOException {
		msg.player_id = in.readInt();
	}
	private void _w(com.fc.castle.data.message.Messages.GetRandomOnlinePlayersRequest msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetRandomOnlinePlayersResponse
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.GetRandomOnlinePlayersResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readByte();
		msg.friends = (java.util.ArrayList)in.readCollection(java.util.ArrayList.class, NetDataTypes.TYPE_MUTUAL);
	}
	private void _w(com.fc.castle.data.message.Messages.GetRandomOnlinePlayersResponse msg, NetDataOutput out) throws IOException {
		out.writeByte(msg.result);
		out.writeCollection(msg.friends, NetDataTypes.TYPE_MUTUAL);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetShopItemRequest
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.GetShopItemRequest msg, NetDataInput in) throws IOException {
		msg.player_id = in.readInt();
	}
	private void _w(com.fc.castle.data.message.Messages.GetShopItemRequest msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetShopItemResponse
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.GetShopItemResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readByte();
		msg.items = in.readMutual(com.fc.castle.data.ShopItems.class);
	}
	private void _w(com.fc.castle.data.message.Messages.GetShopItemResponse msg, NetDataOutput out) throws IOException {
		out.writeByte(msg.result);
		out.writeMutual(msg.items);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetSkillTemplateRequest
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.GetSkillTemplateRequest msg, NetDataInput in) throws IOException {
		msg.skill_types = in.readIntArray();
		msg.player_id = in.readInt();
	}
	private void _w(com.fc.castle.data.message.Messages.GetSkillTemplateRequest msg, NetDataOutput out) throws IOException {
		out.writeIntArray(msg.skill_types);
		out.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetSkillTemplateResponse
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.GetSkillTemplateResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readByte();
		msg.skill_templates = (com.fc.castle.data.template.SkillTemplate[])in.readMutualArray(com.fc.castle.data.template.SkillTemplate.class);
	}
	private void _w(com.fc.castle.data.message.Messages.GetSkillTemplateResponse msg, NetDataOutput out) throws IOException {
		out.writeByte(msg.result);
		out.writeMutualArray(msg.skill_templates);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetUnitTemplateRequest
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.GetUnitTemplateRequest msg, NetDataInput in) throws IOException {
		msg.unit_types = in.readIntArray();
		msg.player_id = in.readInt();
	}
	private void _w(com.fc.castle.data.message.Messages.GetUnitTemplateRequest msg, NetDataOutput out) throws IOException {
		out.writeIntArray(msg.unit_types);
		out.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetUnitTemplateResponse
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.GetUnitTemplateResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readByte();
		msg.unit_templates = (com.fc.castle.data.template.UnitTemplate[])in.readMutualArray(com.fc.castle.data.template.UnitTemplate.class);
	}
	private void _w(com.fc.castle.data.message.Messages.GetUnitTemplateResponse msg, NetDataOutput out) throws IOException {
		out.writeByte(msg.result);
		out.writeMutualArray(msg.unit_templates);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.LoginRequest
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.LoginRequest msg, NetDataInput in) throws IOException {
		msg.account_id = in.readUTF();
		msg.sign = in.readUTF();
		msg.create = in.readBoolean();
		msg.createNickName = in.readUTF();
		msg.player_id = in.readInt();
	}
	private void _w(com.fc.castle.data.message.Messages.LoginRequest msg, NetDataOutput out) throws IOException {
		out.writeUTF(msg.account_id);
		out.writeUTF(msg.sign);
		out.writeBoolean(msg.create);
		out.writeUTF(msg.createNickName);
		out.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.LoginResponse
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.LoginResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readByte();
		msg.login_account = in.readUTF();
		msg.login_key = in.readUTF();
		msg.player_data = (com.fc.castle.data.PlayerData[])in.readMutualArray(com.fc.castle.data.PlayerData.class);
		msg.player_quests = (com.fc.castle.data.PlayerQuestData[])in.readMutualArray(com.fc.castle.data.PlayerQuestData.class);
	}
	private void _w(com.fc.castle.data.message.Messages.LoginResponse msg, NetDataOutput out) throws IOException {
		out.writeByte(msg.result);
		out.writeUTF(msg.login_account);
		out.writeUTF(msg.login_key);
		out.writeMutualArray(msg.player_data);
		out.writeMutualArray(msg.player_quests);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.OrganizeDefenseRequest
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.OrganizeDefenseRequest msg, NetDataInput in) throws IOException {
		msg.soldiers = in.readMutual(com.fc.castle.data.SoldierDatas.class);
		msg.skills = in.readMutual(com.fc.castle.data.SkillDatas.class);
		msg.player_id = in.readInt();
	}
	private void _w(com.fc.castle.data.message.Messages.OrganizeDefenseRequest msg, NetDataOutput out) throws IOException {
		out.writeMutual(msg.soldiers);
		out.writeMutual(msg.skills);
		out.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.OrganizeDefenseResponse
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.OrganizeDefenseResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readByte();
	}
	private void _w(com.fc.castle.data.message.Messages.OrganizeDefenseResponse msg, NetDataOutput out) throws IOException {
		out.writeByte(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.RefreshPlayerDataRequest
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.RefreshPlayerDataRequest msg, NetDataInput in) throws IOException {
		msg.player_id = in.readInt();
	}
	private void _w(com.fc.castle.data.message.Messages.RefreshPlayerDataRequest msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.RefreshPlayerDataResponse
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.RefreshPlayerDataResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readByte();
		msg.player = in.readMutual(com.fc.castle.data.PlayerData.class);
	}
	private void _w(com.fc.castle.data.message.Messages.RefreshPlayerDataResponse msg, NetDataOutput out) throws IOException {
		out.writeByte(msg.result);
		out.writeMutual(msg.player);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.SendMailRequest
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.SendMailRequest msg, NetDataInput in) throws IOException {
		msg.mail = in.readMutual(com.fc.castle.data.Mail.class);
		msg.player_id = in.readInt();
	}
	private void _w(com.fc.castle.data.message.Messages.SendMailRequest msg, NetDataOutput out) throws IOException {
		out.writeMutual(msg.mail);
		out.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.SendMailResponse
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.SendMailResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readByte();
	}
	private void _w(com.fc.castle.data.message.Messages.SendMailResponse msg, NetDataOutput out) throws IOException {
		out.writeByte(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.UseItemRequest
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.UseItemRequest msg, NetDataInput in) throws IOException {
		msg.indexOfItems = in.readInt();
		msg.count = in.readInt();
		msg.player_id = in.readInt();
	}
	private void _w(com.fc.castle.data.message.Messages.UseItemRequest msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.indexOfItems);
		out.writeInt(msg.count);
		out.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.UseItemResponse
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.message.Messages.UseItemResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readByte();
		msg.item_slot = in.readMutual(com.fc.castle.data.ItemData.class);
	}
	private void _w(com.fc.castle.data.message.Messages.UseItemResponse msg, NetDataOutput out) throws IOException {
		out.writeByte(msg.result);
		out.writeMutual(msg.item_slot);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.template.BuffTemplate
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.template.BuffTemplate msg, NetDataInput in) throws IOException {
		msg.type = in.readInt();
		msg.time = in.readInt();
		msg.debuff = in.readInt();
		msg.effect = in.readUTF();
		msg.enhanceDamage = in.readFloat();
		msg.addDamage = in.readFloat();
		msg.damageReduction = in.readFloat();
		msg.enhanceDefens = in.readFloat();
		msg.addDefense = in.readFloat();
		msg.hasteRating = in.readFloat();
		msg.moveRating = in.readFloat();
		msg.addHP = in.readFloat();
		msg.enhanceHP = in.readFloat();
		msg.burning = in.readFloat();
		msg.burningInterval = in.readInt();
		msg.icon = in.readUTF();
		msg.name = in.readUTF();
	}
	private void _w(com.fc.castle.data.template.BuffTemplate msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.type);
		out.writeInt(msg.time);
		out.writeInt(msg.debuff);
		out.writeUTF(msg.effect);
		out.writeFloat(msg.enhanceDamage);
		out.writeFloat(msg.addDamage);
		out.writeFloat(msg.damageReduction);
		out.writeFloat(msg.enhanceDefens);
		out.writeFloat(msg.addDefense);
		out.writeFloat(msg.hasteRating);
		out.writeFloat(msg.moveRating);
		out.writeFloat(msg.addHP);
		out.writeFloat(msg.enhanceHP);
		out.writeFloat(msg.burning);
		out.writeInt(msg.burningInterval);
		out.writeUTF(msg.icon);
		out.writeUTF(msg.name);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.template.Enums.AttackType
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.template.Enums.AttackType msg, NetDataInput in) throws IOException {
	}
	private void _w(com.fc.castle.data.template.Enums.AttackType msg, NetDataOutput out) throws IOException {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.template.Enums.DefenseType
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.template.Enums.DefenseType msg, NetDataInput in) throws IOException {
	}
	private void _w(com.fc.castle.data.template.Enums.DefenseType msg, NetDataOutput out) throws IOException {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.template.Enums.FightType
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.template.Enums.FightType msg, NetDataInput in) throws IOException {
	}
	private void _w(com.fc.castle.data.template.Enums.FightType msg, NetDataOutput out) throws IOException {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.template.Enums.MotionType
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.template.Enums.MotionType msg, NetDataInput in) throws IOException {
	}
	private void _w(com.fc.castle.data.template.Enums.MotionType msg, NetDataOutput out) throws IOException {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.template.Enums.SceneEventType
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.template.Enums.SceneEventType msg, NetDataInput in) throws IOException {
	}
	private void _w(com.fc.castle.data.template.Enums.SceneEventType msg, NetDataOutput out) throws IOException {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.template.Enums.SkillSpecialEffect
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.template.Enums.SkillSpecialEffect msg, NetDataInput in) throws IOException {
	}
	private void _w(com.fc.castle.data.template.Enums.SkillSpecialEffect msg, NetDataOutput out) throws IOException {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.template.Enums.SkillTargetType
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.template.Enums.SkillTargetType msg, NetDataInput in) throws IOException {
	}
	private void _w(com.fc.castle.data.template.Enums.SkillTargetType msg, NetDataOutput out) throws IOException {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.template.EventTemplate
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.template.EventTemplate msg, NetDataInput in) throws IOException {
		msg.type = in.readInt();
		msg.subtype = in.readInt();
		msg.hp = in.readInt();
		msg.ap = in.readInt();
		msg.mp = in.readInt();
		msg.battleMap = in.readUTF();
		msg.refreshTime = in.readInt();
		msg.soldiers = in.readMutual(com.fc.castle.data.SoldierDatas.class);
		msg.skills = in.readMutual(com.fc.castle.data.SkillDatas.class);
		msg.exp = in.readInt();
		msg.gold = in.readInt();
		msg.itemDrops = in.readMutual(com.fc.castle.data.ItemDrops.class);
		msg.name = in.readUTF();
		msg.desc = in.readUTF();
	}
	private void _w(com.fc.castle.data.template.EventTemplate msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.type);
		out.writeInt(msg.subtype);
		out.writeInt(msg.hp);
		out.writeInt(msg.ap);
		out.writeInt(msg.mp);
		out.writeUTF(msg.battleMap);
		out.writeInt(msg.refreshTime);
		out.writeMutual(msg.soldiers);
		out.writeMutual(msg.skills);
		out.writeInt(msg.exp);
		out.writeInt(msg.gold);
		out.writeMutual(msg.itemDrops);
		out.writeUTF(msg.name);
		out.writeUTF(msg.desc);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.template.FormualMap
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.template.FormualMap msg, NetDataInput in) throws IOException {
		msg.ammor_map = (float[][])in.readAnyArray(float[][].class, NetDataTypes.TYPE_FLOAT);
	}
	private void _w(com.fc.castle.data.template.FormualMap msg, NetDataOutput out) throws IOException {
		out.writeAnyArray(msg.ammor_map, NetDataTypes.TYPE_FLOAT);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.template.GuideData
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.template.GuideData msg, NetDataInput in) throws IOException {
		msg.GUIDE_STEP = in.readInt();
		msg.GUIDE_START_AP = in.readInt();
		msg.GUIDE_START_MP = in.readInt();
		msg.GUIDE_START_HP = in.readInt();
		msg.GUIDE_PLAYER_SOLDIER = in.readIntArray();
		msg.GUIDE_PLAYER_SKILL = in.readIntArray();
		msg.GUIDE_AI_NAME = in.readUTF();
		msg.GUIDE_AI_SOLDIER = in.readIntArray();
		msg.GUIDE_AI_SKILL = in.readIntArray();
		msg.GUIDE_BATTLE_MAP = in.readUTF();
		msg.AWARD_EXP = in.readInt();
		msg.AWARD_GOLD = in.readInt();
		msg.AWARD_ITEMS = in.readMutual(com.fc.castle.data.ItemDatas.class);
	}
	private void _w(com.fc.castle.data.template.GuideData msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.GUIDE_STEP);
		out.writeInt(msg.GUIDE_START_AP);
		out.writeInt(msg.GUIDE_START_MP);
		out.writeInt(msg.GUIDE_START_HP);
		out.writeIntArray(msg.GUIDE_PLAYER_SOLDIER);
		out.writeIntArray(msg.GUIDE_PLAYER_SKILL);
		out.writeUTF(msg.GUIDE_AI_NAME);
		out.writeIntArray(msg.GUIDE_AI_SOLDIER);
		out.writeIntArray(msg.GUIDE_AI_SKILL);
		out.writeUTF(msg.GUIDE_BATTLE_MAP);
		out.writeInt(msg.AWARD_EXP);
		out.writeInt(msg.AWARD_GOLD);
		out.writeMutual(msg.AWARD_ITEMS);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.template.ItemTemplate
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.template.ItemTemplate msg, NetDataInput in) throws IOException {
		msg.type = in.readInt();
		msg.icon = in.readUTF();
		msg.priceCoin = in.readInt();
		msg.getUnits = in.readMutual(com.fc.castle.data.SoldierDatas.class);
		msg.getSkills = in.readMutual(com.fc.castle.data.SkillDatas.class);
		msg.name = in.readUTF();
		msg.desc = in.readUTF();
	}
	private void _w(com.fc.castle.data.template.ItemTemplate msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.type);
		out.writeUTF(msg.icon);
		out.writeInt(msg.priceCoin);
		out.writeMutual(msg.getUnits);
		out.writeMutual(msg.getSkills);
		out.writeUTF(msg.name);
		out.writeUTF(msg.desc);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.template.SkillTemplate
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.template.SkillTemplate msg, NetDataInput in) throws IOException {
		msg.type = in.readInt();
		msg.attack = in.readInt();
		msg.costMP = in.readInt();
		msg.costHP = in.readInt();
		msg.coolDown = in.readInt();
		msg.spriteBehavior = in.readUTF();
		msg.spriteEffect = in.readUTF();
		msg.icon = in.readUTF();
		msg.range = in.readInt();
		msg.aoe = in.readInt();
		msg.targetType = in.readInt();
		msg.buffList = in.readIntArray();
		msg.specialEffects = in.readIntArray();
		msg.name = in.readUTF();
		msg.desc = in.readUTF();
	}
	private void _w(com.fc.castle.data.template.SkillTemplate msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.type);
		out.writeInt(msg.attack);
		out.writeInt(msg.costMP);
		out.writeInt(msg.costHP);
		out.writeInt(msg.coolDown);
		out.writeUTF(msg.spriteBehavior);
		out.writeUTF(msg.spriteEffect);
		out.writeUTF(msg.icon);
		out.writeInt(msg.range);
		out.writeInt(msg.aoe);
		out.writeInt(msg.targetType);
		out.writeIntArray(msg.buffList);
		out.writeIntArray(msg.specialEffects);
		out.writeUTF(msg.name);
		out.writeUTF(msg.desc);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.template.UnitTemplate
//	----------------------------------------------------------------------------------------------------
	private void _r(com.fc.castle.data.template.UnitTemplate msg, NetDataInput in) throws IOException {
		msg.type = in.readInt();
		msg.csprite_id = in.readUTF();
		msg.icon = in.readUTF();
		msg.motionType = in.readInt();
		msg.fightType = in.readInt();
		msg.attack = in.readInt();
		msg.attackType = in.readInt();
		msg.attackCD = in.readInt();
		msg.attackRange = in.readInt();
		msg.attackEffect = in.readUTF();
		msg.guardRange = in.readInt();
		msg.defense = in.readInt();
		msg.defenseType = in.readInt();
		msg.hp = in.readInt();
		msg.mp = in.readInt();
		msg.moveSpeed = in.readFloat();
		msg.trainingTime = in.readInt();
		msg.cost = in.readInt();
		msg.skills = in.readIntArray();
		msg.name = in.readUTF();
		msg.description = in.readUTF();
	}
	private void _w(com.fc.castle.data.template.UnitTemplate msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.type);
		out.writeUTF(msg.csprite_id);
		out.writeUTF(msg.icon);
		out.writeInt(msg.motionType);
		out.writeInt(msg.fightType);
		out.writeInt(msg.attack);
		out.writeInt(msg.attackType);
		out.writeInt(msg.attackCD);
		out.writeInt(msg.attackRange);
		out.writeUTF(msg.attackEffect);
		out.writeInt(msg.guardRange);
		out.writeInt(msg.defense);
		out.writeInt(msg.defenseType);
		out.writeInt(msg.hp);
		out.writeInt(msg.mp);
		out.writeFloat(msg.moveSpeed);
		out.writeInt(msg.trainingTime);
		out.writeInt(msg.cost);
		out.writeIntArray(msg.skills);
		out.writeUTF(msg.name);
		out.writeUTF(msg.description);
	}



}
