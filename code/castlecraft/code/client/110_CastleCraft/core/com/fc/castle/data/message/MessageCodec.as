package com.fc.castle.data.message
{
	import com.cell.net.io.MessageFactory;
	import com.cell.net.io.MutualMessage;
	import com.cell.net.io.NetDataInput;
	import com.cell.net.io.NetDataOutput;
	import com.cell.net.io.NetDataTypes;
	import com.cell.util.Map;

	import flash.utils.getQualifiedClassName;	
	import com.fc.castle.data.*;
	import com.fc.castle.data.message.*;
	import com.fc.castle.data.message.Messages.*;
	import com.fc.castle.data.template.*;
	import com.fc.castle.data.template.Enums.*;


	/**
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	public class MessageCodec implements MessageFactory
	{
	
		public function getVersion() : String{
			return "Mon Mar 19 13:16:17 CST 2012";
		}
	
		public function	getType(msg : Object) : int 
		{
			var cname : String = getQualifiedClassName(msg);
			
			if (cname == "com.fc.castle.data::Account") return 1;
			if (cname == "com.fc.castle.data::BattleEvent") return 2;
			if (cname == "com.fc.castle.data::BattleLog") return 3;
			if (cname == "com.fc.castle.data::BattlePlayer") return 4;
			if (cname == "com.fc.castle.data::ExploreData") return 5;
			if (cname == "com.fc.castle.data::ExploreState") return 6;
			if (cname == "com.fc.castle.data::ItemData") return 7;
			if (cname == "com.fc.castle.data::ItemDatas") return 8;
			if (cname == "com.fc.castle.data::ItemDrop") return 9;
			if (cname == "com.fc.castle.data::ItemDrops") return 10;
			if (cname == "com.fc.castle.data::Mail") return 11;
			if (cname == "com.fc.castle.data::MailSnap") return 12;
			if (cname == "com.fc.castle.data::PlayerData") return 13;
			if (cname == "com.fc.castle.data::PlayerFriend") return 14;
			if (cname == "com.fc.castle.data::PlayerMailData") return 15;
			if (cname == "com.fc.castle.data::PlayerQuestData") return 16;
			if (cname == "com.fc.castle.data::ShopItem") return 17;
			if (cname == "com.fc.castle.data::ShopItems") return 18;
			if (cname == "com.fc.castle.data::SkillData") return 19;
			if (cname == "com.fc.castle.data::SkillDatas") return 20;
			if (cname == "com.fc.castle.data::SoldierData") return 21;
			if (cname == "com.fc.castle.data::SoldierDatas") return 22;
			if (cname == "com.fc.castle.data.message.Messages::BattleStartRequest") return 25;
			if (cname == "com.fc.castle.data.message.Messages::BattleStartResponse") return 26;
			if (cname == "com.fc.castle.data.message.Messages::BuyShopItemRequest") return 27;
			if (cname == "com.fc.castle.data.message.Messages::BuyShopItemResponse") return 28;
			if (cname == "com.fc.castle.data.message.Messages::CommitBattleResultRequest") return 29;
			if (cname == "com.fc.castle.data.message.Messages::CommitBattleResultResponse") return 30;
			if (cname == "com.fc.castle.data.message.Messages::CommitGuideRequest") return 31;
			if (cname == "com.fc.castle.data.message.Messages::CommitGuideResponse") return 32;
			if (cname == "com.fc.castle.data.message.Messages::GetBuffTemplateRequest") return 33;
			if (cname == "com.fc.castle.data.message.Messages::GetBuffTemplateResponse") return 34;
			if (cname == "com.fc.castle.data.message.Messages::GetEventTemplateRequest") return 35;
			if (cname == "com.fc.castle.data.message.Messages::GetEventTemplateResponse") return 36;
			if (cname == "com.fc.castle.data.message.Messages::GetExploreDataListRequest") return 37;
			if (cname == "com.fc.castle.data.message.Messages::GetExploreDataListResponse") return 38;
			if (cname == "com.fc.castle.data.message.Messages::GetExploreStateRequest") return 39;
			if (cname == "com.fc.castle.data.message.Messages::GetExploreStateResponse") return 40;
			if (cname == "com.fc.castle.data.message.Messages::GetItemTemplateRequest") return 41;
			if (cname == "com.fc.castle.data.message.Messages::GetItemTemplateResponse") return 42;
			if (cname == "com.fc.castle.data.message.Messages::GetMailRequest") return 43;
			if (cname == "com.fc.castle.data.message.Messages::GetMailResponse") return 44;
			if (cname == "com.fc.castle.data.message.Messages::GetMailSnapsRequest") return 45;
			if (cname == "com.fc.castle.data.message.Messages::GetMailSnapsResponse") return 46;
			if (cname == "com.fc.castle.data.message.Messages::GetPlayerQuestRequest") return 47;
			if (cname == "com.fc.castle.data.message.Messages::GetPlayerQuestResponse") return 48;
			if (cname == "com.fc.castle.data.message.Messages::GetRandomOnlinePlayersRequest") return 49;
			if (cname == "com.fc.castle.data.message.Messages::GetRandomOnlinePlayersResponse") return 50;
			if (cname == "com.fc.castle.data.message.Messages::GetShopItemRequest") return 51;
			if (cname == "com.fc.castle.data.message.Messages::GetShopItemResponse") return 52;
			if (cname == "com.fc.castle.data.message.Messages::GetSkillTemplateRequest") return 53;
			if (cname == "com.fc.castle.data.message.Messages::GetSkillTemplateResponse") return 54;
			if (cname == "com.fc.castle.data.message.Messages::GetUnitTemplateRequest") return 55;
			if (cname == "com.fc.castle.data.message.Messages::GetUnitTemplateResponse") return 56;
			if (cname == "com.fc.castle.data.message.Messages::LoginRequest") return 57;
			if (cname == "com.fc.castle.data.message.Messages::LoginResponse") return 58;
			if (cname == "com.fc.castle.data.message.Messages::OrganizeDefenseRequest") return 59;
			if (cname == "com.fc.castle.data.message.Messages::OrganizeDefenseResponse") return 60;
			if (cname == "com.fc.castle.data.message.Messages::RefreshPlayerDataRequest") return 61;
			if (cname == "com.fc.castle.data.message.Messages::RefreshPlayerDataResponse") return 62;
			if (cname == "com.fc.castle.data.message.Messages::SendMailRequest") return 63;
			if (cname == "com.fc.castle.data.message.Messages::SendMailResponse") return 64;
			if (cname == "com.fc.castle.data.message.Messages::UseItemRequest") return 65;
			if (cname == "com.fc.castle.data.message.Messages::UseItemResponse") return 66;
			if (cname == "com.fc.castle.data.template::BuffTemplate") return 69;
			if (cname == "com.fc.castle.data.template.Enums::AttackType") return 70;
			if (cname == "com.fc.castle.data.template.Enums::DefenseType") return 71;
			if (cname == "com.fc.castle.data.template.Enums::FightType") return 72;
			if (cname == "com.fc.castle.data.template.Enums::MotionType") return 73;
			if (cname == "com.fc.castle.data.template.Enums::SceneEventType") return 74;
			if (cname == "com.fc.castle.data.template.Enums::SkillSpecialEffect") return 75;
			if (cname == "com.fc.castle.data.template.Enums::SkillTargetType") return 76;
			if (cname == "com.fc.castle.data.template::EventTemplate") return 77;
			if (cname == "com.fc.castle.data.template::FormualMap") return 78;
			if (cname == "com.fc.castle.data.template::GuideData") return 79;
			if (cname == "com.fc.castle.data.template::ItemTemplate") return 80;
			if (cname == "com.fc.castle.data.template::SkillTemplate") return 81;
			if (cname == "com.fc.castle.data.template::UnitTemplate") return 82;

			return 0;
		}
		
		public function	createMessage(type : int) : MutualMessage
		{
			switch(type)
			{
			case 1 : return new com.fc.castle.data.Account;
			case 2 : return new com.fc.castle.data.BattleEvent;
			case 3 : return new com.fc.castle.data.BattleLog;
			case 4 : return new com.fc.castle.data.BattlePlayer;
			case 5 : return new com.fc.castle.data.ExploreData;
			case 6 : return new com.fc.castle.data.ExploreState;
			case 7 : return new com.fc.castle.data.ItemData;
			case 8 : return new com.fc.castle.data.ItemDatas;
			case 9 : return new com.fc.castle.data.ItemDrop;
			case 10 : return new com.fc.castle.data.ItemDrops;
			case 11 : return new com.fc.castle.data.Mail;
			case 12 : return new com.fc.castle.data.MailSnap;
			case 13 : return new com.fc.castle.data.PlayerData;
			case 14 : return new com.fc.castle.data.PlayerFriend;
			case 15 : return new com.fc.castle.data.PlayerMailData;
			case 16 : return new com.fc.castle.data.PlayerQuestData;
			case 17 : return new com.fc.castle.data.ShopItem;
			case 18 : return new com.fc.castle.data.ShopItems;
			case 19 : return new com.fc.castle.data.SkillData;
			case 20 : return new com.fc.castle.data.SkillDatas;
			case 21 : return new com.fc.castle.data.SoldierData;
			case 22 : return new com.fc.castle.data.SoldierDatas;
			case 25 : return new com.fc.castle.data.message.Messages.BattleStartRequest;
			case 26 : return new com.fc.castle.data.message.Messages.BattleStartResponse;
			case 27 : return new com.fc.castle.data.message.Messages.BuyShopItemRequest;
			case 28 : return new com.fc.castle.data.message.Messages.BuyShopItemResponse;
			case 29 : return new com.fc.castle.data.message.Messages.CommitBattleResultRequest;
			case 30 : return new com.fc.castle.data.message.Messages.CommitBattleResultResponse;
			case 31 : return new com.fc.castle.data.message.Messages.CommitGuideRequest;
			case 32 : return new com.fc.castle.data.message.Messages.CommitGuideResponse;
			case 33 : return new com.fc.castle.data.message.Messages.GetBuffTemplateRequest;
			case 34 : return new com.fc.castle.data.message.Messages.GetBuffTemplateResponse;
			case 35 : return new com.fc.castle.data.message.Messages.GetEventTemplateRequest;
			case 36 : return new com.fc.castle.data.message.Messages.GetEventTemplateResponse;
			case 37 : return new com.fc.castle.data.message.Messages.GetExploreDataListRequest;
			case 38 : return new com.fc.castle.data.message.Messages.GetExploreDataListResponse;
			case 39 : return new com.fc.castle.data.message.Messages.GetExploreStateRequest;
			case 40 : return new com.fc.castle.data.message.Messages.GetExploreStateResponse;
			case 41 : return new com.fc.castle.data.message.Messages.GetItemTemplateRequest;
			case 42 : return new com.fc.castle.data.message.Messages.GetItemTemplateResponse;
			case 43 : return new com.fc.castle.data.message.Messages.GetMailRequest;
			case 44 : return new com.fc.castle.data.message.Messages.GetMailResponse;
			case 45 : return new com.fc.castle.data.message.Messages.GetMailSnapsRequest;
			case 46 : return new com.fc.castle.data.message.Messages.GetMailSnapsResponse;
			case 47 : return new com.fc.castle.data.message.Messages.GetPlayerQuestRequest;
			case 48 : return new com.fc.castle.data.message.Messages.GetPlayerQuestResponse;
			case 49 : return new com.fc.castle.data.message.Messages.GetRandomOnlinePlayersRequest;
			case 50 : return new com.fc.castle.data.message.Messages.GetRandomOnlinePlayersResponse;
			case 51 : return new com.fc.castle.data.message.Messages.GetShopItemRequest;
			case 52 : return new com.fc.castle.data.message.Messages.GetShopItemResponse;
			case 53 : return new com.fc.castle.data.message.Messages.GetSkillTemplateRequest;
			case 54 : return new com.fc.castle.data.message.Messages.GetSkillTemplateResponse;
			case 55 : return new com.fc.castle.data.message.Messages.GetUnitTemplateRequest;
			case 56 : return new com.fc.castle.data.message.Messages.GetUnitTemplateResponse;
			case 57 : return new com.fc.castle.data.message.Messages.LoginRequest;
			case 58 : return new com.fc.castle.data.message.Messages.LoginResponse;
			case 59 : return new com.fc.castle.data.message.Messages.OrganizeDefenseRequest;
			case 60 : return new com.fc.castle.data.message.Messages.OrganizeDefenseResponse;
			case 61 : return new com.fc.castle.data.message.Messages.RefreshPlayerDataRequest;
			case 62 : return new com.fc.castle.data.message.Messages.RefreshPlayerDataResponse;
			case 63 : return new com.fc.castle.data.message.Messages.SendMailRequest;
			case 64 : return new com.fc.castle.data.message.Messages.SendMailResponse;
			case 65 : return new com.fc.castle.data.message.Messages.UseItemRequest;
			case 66 : return new com.fc.castle.data.message.Messages.UseItemResponse;
			case 69 : return new com.fc.castle.data.template.BuffTemplate;
			case 70 : return new com.fc.castle.data.template.Enums.AttackType;
			case 71 : return new com.fc.castle.data.template.Enums.DefenseType;
			case 72 : return new com.fc.castle.data.template.Enums.FightType;
			case 73 : return new com.fc.castle.data.template.Enums.MotionType;
			case 74 : return new com.fc.castle.data.template.Enums.SceneEventType;
			case 75 : return new com.fc.castle.data.template.Enums.SkillSpecialEffect;
			case 76 : return new com.fc.castle.data.template.Enums.SkillTargetType;
			case 77 : return new com.fc.castle.data.template.EventTemplate;
			case 78 : return new com.fc.castle.data.template.FormualMap;
			case 79 : return new com.fc.castle.data.template.GuideData;
			case 80 : return new com.fc.castle.data.template.ItemTemplate;
			case 81 : return new com.fc.castle.data.template.SkillTemplate;
			case 82 : return new com.fc.castle.data.template.UnitTemplate;

			}
			return null;
		}
		
		public function	readExternal(msg : MutualMessage,  input : NetDataInput) : void  
		{
		if (msg is com.fc.castle.data.Account) {
			r_Account_1(com.fc.castle.data.Account(msg), input); return;
		}
		if (msg is com.fc.castle.data.BattleEvent) {
			r_BattleEvent_2(com.fc.castle.data.BattleEvent(msg), input); return;
		}
		if (msg is com.fc.castle.data.BattleLog) {
			r_BattleLog_3(com.fc.castle.data.BattleLog(msg), input); return;
		}
		if (msg is com.fc.castle.data.BattlePlayer) {
			r_BattlePlayer_4(com.fc.castle.data.BattlePlayer(msg), input); return;
		}
		if (msg is com.fc.castle.data.ExploreData) {
			r_ExploreData_5(com.fc.castle.data.ExploreData(msg), input); return;
		}
		if (msg is com.fc.castle.data.ExploreState) {
			r_ExploreState_6(com.fc.castle.data.ExploreState(msg), input); return;
		}
		if (msg is com.fc.castle.data.ItemData) {
			r_ItemData_7(com.fc.castle.data.ItemData(msg), input); return;
		}
		if (msg is com.fc.castle.data.ItemDatas) {
			r_ItemDatas_8(com.fc.castle.data.ItemDatas(msg), input); return;
		}
		if (msg is com.fc.castle.data.ItemDrop) {
			r_ItemDrop_9(com.fc.castle.data.ItemDrop(msg), input); return;
		}
		if (msg is com.fc.castle.data.ItemDrops) {
			r_ItemDrops_10(com.fc.castle.data.ItemDrops(msg), input); return;
		}
		if (msg is com.fc.castle.data.Mail) {
			r_Mail_11(com.fc.castle.data.Mail(msg), input); return;
		}
		if (msg is com.fc.castle.data.MailSnap) {
			r_MailSnap_12(com.fc.castle.data.MailSnap(msg), input); return;
		}
		if (msg is com.fc.castle.data.PlayerData) {
			r_PlayerData_13(com.fc.castle.data.PlayerData(msg), input); return;
		}
		if (msg is com.fc.castle.data.PlayerFriend) {
			r_PlayerFriend_14(com.fc.castle.data.PlayerFriend(msg), input); return;
		}
		if (msg is com.fc.castle.data.PlayerMailData) {
			r_PlayerMailData_15(com.fc.castle.data.PlayerMailData(msg), input); return;
		}
		if (msg is com.fc.castle.data.PlayerQuestData) {
			r_PlayerQuestData_16(com.fc.castle.data.PlayerQuestData(msg), input); return;
		}
		if (msg is com.fc.castle.data.ShopItem) {
			r_ShopItem_17(com.fc.castle.data.ShopItem(msg), input); return;
		}
		if (msg is com.fc.castle.data.ShopItems) {
			r_ShopItems_18(com.fc.castle.data.ShopItems(msg), input); return;
		}
		if (msg is com.fc.castle.data.SkillData) {
			r_SkillData_19(com.fc.castle.data.SkillData(msg), input); return;
		}
		if (msg is com.fc.castle.data.SkillDatas) {
			r_SkillDatas_20(com.fc.castle.data.SkillDatas(msg), input); return;
		}
		if (msg is com.fc.castle.data.SoldierData) {
			r_SoldierData_21(com.fc.castle.data.SoldierData(msg), input); return;
		}
		if (msg is com.fc.castle.data.SoldierDatas) {
			r_SoldierDatas_22(com.fc.castle.data.SoldierDatas(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.BattleStartRequest) {
			r_BattleStartRequest_25(com.fc.castle.data.message.Messages.BattleStartRequest(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.BattleStartResponse) {
			r_BattleStartResponse_26(com.fc.castle.data.message.Messages.BattleStartResponse(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.BuyShopItemRequest) {
			r_BuyShopItemRequest_27(com.fc.castle.data.message.Messages.BuyShopItemRequest(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.BuyShopItemResponse) {
			r_BuyShopItemResponse_28(com.fc.castle.data.message.Messages.BuyShopItemResponse(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.CommitBattleResultRequest) {
			r_CommitBattleResultRequest_29(com.fc.castle.data.message.Messages.CommitBattleResultRequest(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.CommitBattleResultResponse) {
			r_CommitBattleResultResponse_30(com.fc.castle.data.message.Messages.CommitBattleResultResponse(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.CommitGuideRequest) {
			r_CommitGuideRequest_31(com.fc.castle.data.message.Messages.CommitGuideRequest(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.CommitGuideResponse) {
			r_CommitGuideResponse_32(com.fc.castle.data.message.Messages.CommitGuideResponse(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetBuffTemplateRequest) {
			r_GetBuffTemplateRequest_33(com.fc.castle.data.message.Messages.GetBuffTemplateRequest(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetBuffTemplateResponse) {
			r_GetBuffTemplateResponse_34(com.fc.castle.data.message.Messages.GetBuffTemplateResponse(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetEventTemplateRequest) {
			r_GetEventTemplateRequest_35(com.fc.castle.data.message.Messages.GetEventTemplateRequest(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetEventTemplateResponse) {
			r_GetEventTemplateResponse_36(com.fc.castle.data.message.Messages.GetEventTemplateResponse(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetExploreDataListRequest) {
			r_GetExploreDataListRequest_37(com.fc.castle.data.message.Messages.GetExploreDataListRequest(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetExploreDataListResponse) {
			r_GetExploreDataListResponse_38(com.fc.castle.data.message.Messages.GetExploreDataListResponse(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetExploreStateRequest) {
			r_GetExploreStateRequest_39(com.fc.castle.data.message.Messages.GetExploreStateRequest(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetExploreStateResponse) {
			r_GetExploreStateResponse_40(com.fc.castle.data.message.Messages.GetExploreStateResponse(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetItemTemplateRequest) {
			r_GetItemTemplateRequest_41(com.fc.castle.data.message.Messages.GetItemTemplateRequest(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetItemTemplateResponse) {
			r_GetItemTemplateResponse_42(com.fc.castle.data.message.Messages.GetItemTemplateResponse(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetMailRequest) {
			r_GetMailRequest_43(com.fc.castle.data.message.Messages.GetMailRequest(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetMailResponse) {
			r_GetMailResponse_44(com.fc.castle.data.message.Messages.GetMailResponse(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetMailSnapsRequest) {
			r_GetMailSnapsRequest_45(com.fc.castle.data.message.Messages.GetMailSnapsRequest(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetMailSnapsResponse) {
			r_GetMailSnapsResponse_46(com.fc.castle.data.message.Messages.GetMailSnapsResponse(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetPlayerQuestRequest) {
			r_GetPlayerQuestRequest_47(com.fc.castle.data.message.Messages.GetPlayerQuestRequest(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetPlayerQuestResponse) {
			r_GetPlayerQuestResponse_48(com.fc.castle.data.message.Messages.GetPlayerQuestResponse(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetRandomOnlinePlayersRequest) {
			r_GetRandomOnlinePlayersRequest_49(com.fc.castle.data.message.Messages.GetRandomOnlinePlayersRequest(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetRandomOnlinePlayersResponse) {
			r_GetRandomOnlinePlayersResponse_50(com.fc.castle.data.message.Messages.GetRandomOnlinePlayersResponse(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetShopItemRequest) {
			r_GetShopItemRequest_51(com.fc.castle.data.message.Messages.GetShopItemRequest(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetShopItemResponse) {
			r_GetShopItemResponse_52(com.fc.castle.data.message.Messages.GetShopItemResponse(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetSkillTemplateRequest) {
			r_GetSkillTemplateRequest_53(com.fc.castle.data.message.Messages.GetSkillTemplateRequest(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetSkillTemplateResponse) {
			r_GetSkillTemplateResponse_54(com.fc.castle.data.message.Messages.GetSkillTemplateResponse(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetUnitTemplateRequest) {
			r_GetUnitTemplateRequest_55(com.fc.castle.data.message.Messages.GetUnitTemplateRequest(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetUnitTemplateResponse) {
			r_GetUnitTemplateResponse_56(com.fc.castle.data.message.Messages.GetUnitTemplateResponse(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.LoginRequest) {
			r_LoginRequest_57(com.fc.castle.data.message.Messages.LoginRequest(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.LoginResponse) {
			r_LoginResponse_58(com.fc.castle.data.message.Messages.LoginResponse(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.OrganizeDefenseRequest) {
			r_OrganizeDefenseRequest_59(com.fc.castle.data.message.Messages.OrganizeDefenseRequest(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.OrganizeDefenseResponse) {
			r_OrganizeDefenseResponse_60(com.fc.castle.data.message.Messages.OrganizeDefenseResponse(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.RefreshPlayerDataRequest) {
			r_RefreshPlayerDataRequest_61(com.fc.castle.data.message.Messages.RefreshPlayerDataRequest(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.RefreshPlayerDataResponse) {
			r_RefreshPlayerDataResponse_62(com.fc.castle.data.message.Messages.RefreshPlayerDataResponse(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.SendMailRequest) {
			r_SendMailRequest_63(com.fc.castle.data.message.Messages.SendMailRequest(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.SendMailResponse) {
			r_SendMailResponse_64(com.fc.castle.data.message.Messages.SendMailResponse(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.UseItemRequest) {
			r_UseItemRequest_65(com.fc.castle.data.message.Messages.UseItemRequest(msg), input); return;
		}
		if (msg is com.fc.castle.data.message.Messages.UseItemResponse) {
			r_UseItemResponse_66(com.fc.castle.data.message.Messages.UseItemResponse(msg), input); return;
		}
		if (msg is com.fc.castle.data.template.BuffTemplate) {
			r_BuffTemplate_69(com.fc.castle.data.template.BuffTemplate(msg), input); return;
		}
		if (msg is com.fc.castle.data.template.Enums.AttackType) {
			r_AttackType_70(com.fc.castle.data.template.Enums.AttackType(msg), input); return;
		}
		if (msg is com.fc.castle.data.template.Enums.DefenseType) {
			r_DefenseType_71(com.fc.castle.data.template.Enums.DefenseType(msg), input); return;
		}
		if (msg is com.fc.castle.data.template.Enums.FightType) {
			r_FightType_72(com.fc.castle.data.template.Enums.FightType(msg), input); return;
		}
		if (msg is com.fc.castle.data.template.Enums.MotionType) {
			r_MotionType_73(com.fc.castle.data.template.Enums.MotionType(msg), input); return;
		}
		if (msg is com.fc.castle.data.template.Enums.SceneEventType) {
			r_SceneEventType_74(com.fc.castle.data.template.Enums.SceneEventType(msg), input); return;
		}
		if (msg is com.fc.castle.data.template.Enums.SkillSpecialEffect) {
			r_SkillSpecialEffect_75(com.fc.castle.data.template.Enums.SkillSpecialEffect(msg), input); return;
		}
		if (msg is com.fc.castle.data.template.Enums.SkillTargetType) {
			r_SkillTargetType_76(com.fc.castle.data.template.Enums.SkillTargetType(msg), input); return;
		}
		if (msg is com.fc.castle.data.template.EventTemplate) {
			r_EventTemplate_77(com.fc.castle.data.template.EventTemplate(msg), input); return;
		}
		if (msg is com.fc.castle.data.template.FormualMap) {
			r_FormualMap_78(com.fc.castle.data.template.FormualMap(msg), input); return;
		}
		if (msg is com.fc.castle.data.template.GuideData) {
			r_GuideData_79(com.fc.castle.data.template.GuideData(msg), input); return;
		}
		if (msg is com.fc.castle.data.template.ItemTemplate) {
			r_ItemTemplate_80(com.fc.castle.data.template.ItemTemplate(msg), input); return;
		}
		if (msg is com.fc.castle.data.template.SkillTemplate) {
			r_SkillTemplate_81(com.fc.castle.data.template.SkillTemplate(msg), input); return;
		}
		if (msg is com.fc.castle.data.template.UnitTemplate) {
			r_UnitTemplate_82(com.fc.castle.data.template.UnitTemplate(msg), input); return;
		}

		}
		
		public function	writeExternal(msg : MutualMessage, output : NetDataOutput) : void  
		{
		if (msg is com.fc.castle.data.Account) {
			w_Account_1(com.fc.castle.data.Account(msg), output); return;
		}
		if (msg is com.fc.castle.data.BattleEvent) {
			w_BattleEvent_2(com.fc.castle.data.BattleEvent(msg), output); return;
		}
		if (msg is com.fc.castle.data.BattleLog) {
			w_BattleLog_3(com.fc.castle.data.BattleLog(msg), output); return;
		}
		if (msg is com.fc.castle.data.BattlePlayer) {
			w_BattlePlayer_4(com.fc.castle.data.BattlePlayer(msg), output); return;
		}
		if (msg is com.fc.castle.data.ExploreData) {
			w_ExploreData_5(com.fc.castle.data.ExploreData(msg), output); return;
		}
		if (msg is com.fc.castle.data.ExploreState) {
			w_ExploreState_6(com.fc.castle.data.ExploreState(msg), output); return;
		}
		if (msg is com.fc.castle.data.ItemData) {
			w_ItemData_7(com.fc.castle.data.ItemData(msg), output); return;
		}
		if (msg is com.fc.castle.data.ItemDatas) {
			w_ItemDatas_8(com.fc.castle.data.ItemDatas(msg), output); return;
		}
		if (msg is com.fc.castle.data.ItemDrop) {
			w_ItemDrop_9(com.fc.castle.data.ItemDrop(msg), output); return;
		}
		if (msg is com.fc.castle.data.ItemDrops) {
			w_ItemDrops_10(com.fc.castle.data.ItemDrops(msg), output); return;
		}
		if (msg is com.fc.castle.data.Mail) {
			w_Mail_11(com.fc.castle.data.Mail(msg), output); return;
		}
		if (msg is com.fc.castle.data.MailSnap) {
			w_MailSnap_12(com.fc.castle.data.MailSnap(msg), output); return;
		}
		if (msg is com.fc.castle.data.PlayerData) {
			w_PlayerData_13(com.fc.castle.data.PlayerData(msg), output); return;
		}
		if (msg is com.fc.castle.data.PlayerFriend) {
			w_PlayerFriend_14(com.fc.castle.data.PlayerFriend(msg), output); return;
		}
		if (msg is com.fc.castle.data.PlayerMailData) {
			w_PlayerMailData_15(com.fc.castle.data.PlayerMailData(msg), output); return;
		}
		if (msg is com.fc.castle.data.PlayerQuestData) {
			w_PlayerQuestData_16(com.fc.castle.data.PlayerQuestData(msg), output); return;
		}
		if (msg is com.fc.castle.data.ShopItem) {
			w_ShopItem_17(com.fc.castle.data.ShopItem(msg), output); return;
		}
		if (msg is com.fc.castle.data.ShopItems) {
			w_ShopItems_18(com.fc.castle.data.ShopItems(msg), output); return;
		}
		if (msg is com.fc.castle.data.SkillData) {
			w_SkillData_19(com.fc.castle.data.SkillData(msg), output); return;
		}
		if (msg is com.fc.castle.data.SkillDatas) {
			w_SkillDatas_20(com.fc.castle.data.SkillDatas(msg), output); return;
		}
		if (msg is com.fc.castle.data.SoldierData) {
			w_SoldierData_21(com.fc.castle.data.SoldierData(msg), output); return;
		}
		if (msg is com.fc.castle.data.SoldierDatas) {
			w_SoldierDatas_22(com.fc.castle.data.SoldierDatas(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.BattleStartRequest) {
			w_BattleStartRequest_25(com.fc.castle.data.message.Messages.BattleStartRequest(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.BattleStartResponse) {
			w_BattleStartResponse_26(com.fc.castle.data.message.Messages.BattleStartResponse(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.BuyShopItemRequest) {
			w_BuyShopItemRequest_27(com.fc.castle.data.message.Messages.BuyShopItemRequest(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.BuyShopItemResponse) {
			w_BuyShopItemResponse_28(com.fc.castle.data.message.Messages.BuyShopItemResponse(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.CommitBattleResultRequest) {
			w_CommitBattleResultRequest_29(com.fc.castle.data.message.Messages.CommitBattleResultRequest(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.CommitBattleResultResponse) {
			w_CommitBattleResultResponse_30(com.fc.castle.data.message.Messages.CommitBattleResultResponse(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.CommitGuideRequest) {
			w_CommitGuideRequest_31(com.fc.castle.data.message.Messages.CommitGuideRequest(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.CommitGuideResponse) {
			w_CommitGuideResponse_32(com.fc.castle.data.message.Messages.CommitGuideResponse(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetBuffTemplateRequest) {
			w_GetBuffTemplateRequest_33(com.fc.castle.data.message.Messages.GetBuffTemplateRequest(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetBuffTemplateResponse) {
			w_GetBuffTemplateResponse_34(com.fc.castle.data.message.Messages.GetBuffTemplateResponse(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetEventTemplateRequest) {
			w_GetEventTemplateRequest_35(com.fc.castle.data.message.Messages.GetEventTemplateRequest(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetEventTemplateResponse) {
			w_GetEventTemplateResponse_36(com.fc.castle.data.message.Messages.GetEventTemplateResponse(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetExploreDataListRequest) {
			w_GetExploreDataListRequest_37(com.fc.castle.data.message.Messages.GetExploreDataListRequest(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetExploreDataListResponse) {
			w_GetExploreDataListResponse_38(com.fc.castle.data.message.Messages.GetExploreDataListResponse(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetExploreStateRequest) {
			w_GetExploreStateRequest_39(com.fc.castle.data.message.Messages.GetExploreStateRequest(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetExploreStateResponse) {
			w_GetExploreStateResponse_40(com.fc.castle.data.message.Messages.GetExploreStateResponse(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetItemTemplateRequest) {
			w_GetItemTemplateRequest_41(com.fc.castle.data.message.Messages.GetItemTemplateRequest(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetItemTemplateResponse) {
			w_GetItemTemplateResponse_42(com.fc.castle.data.message.Messages.GetItemTemplateResponse(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetMailRequest) {
			w_GetMailRequest_43(com.fc.castle.data.message.Messages.GetMailRequest(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetMailResponse) {
			w_GetMailResponse_44(com.fc.castle.data.message.Messages.GetMailResponse(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetMailSnapsRequest) {
			w_GetMailSnapsRequest_45(com.fc.castle.data.message.Messages.GetMailSnapsRequest(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetMailSnapsResponse) {
			w_GetMailSnapsResponse_46(com.fc.castle.data.message.Messages.GetMailSnapsResponse(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetPlayerQuestRequest) {
			w_GetPlayerQuestRequest_47(com.fc.castle.data.message.Messages.GetPlayerQuestRequest(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetPlayerQuestResponse) {
			w_GetPlayerQuestResponse_48(com.fc.castle.data.message.Messages.GetPlayerQuestResponse(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetRandomOnlinePlayersRequest) {
			w_GetRandomOnlinePlayersRequest_49(com.fc.castle.data.message.Messages.GetRandomOnlinePlayersRequest(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetRandomOnlinePlayersResponse) {
			w_GetRandomOnlinePlayersResponse_50(com.fc.castle.data.message.Messages.GetRandomOnlinePlayersResponse(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetShopItemRequest) {
			w_GetShopItemRequest_51(com.fc.castle.data.message.Messages.GetShopItemRequest(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetShopItemResponse) {
			w_GetShopItemResponse_52(com.fc.castle.data.message.Messages.GetShopItemResponse(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetSkillTemplateRequest) {
			w_GetSkillTemplateRequest_53(com.fc.castle.data.message.Messages.GetSkillTemplateRequest(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetSkillTemplateResponse) {
			w_GetSkillTemplateResponse_54(com.fc.castle.data.message.Messages.GetSkillTemplateResponse(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetUnitTemplateRequest) {
			w_GetUnitTemplateRequest_55(com.fc.castle.data.message.Messages.GetUnitTemplateRequest(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.GetUnitTemplateResponse) {
			w_GetUnitTemplateResponse_56(com.fc.castle.data.message.Messages.GetUnitTemplateResponse(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.LoginRequest) {
			w_LoginRequest_57(com.fc.castle.data.message.Messages.LoginRequest(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.LoginResponse) {
			w_LoginResponse_58(com.fc.castle.data.message.Messages.LoginResponse(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.OrganizeDefenseRequest) {
			w_OrganizeDefenseRequest_59(com.fc.castle.data.message.Messages.OrganizeDefenseRequest(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.OrganizeDefenseResponse) {
			w_OrganizeDefenseResponse_60(com.fc.castle.data.message.Messages.OrganizeDefenseResponse(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.RefreshPlayerDataRequest) {
			w_RefreshPlayerDataRequest_61(com.fc.castle.data.message.Messages.RefreshPlayerDataRequest(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.RefreshPlayerDataResponse) {
			w_RefreshPlayerDataResponse_62(com.fc.castle.data.message.Messages.RefreshPlayerDataResponse(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.SendMailRequest) {
			w_SendMailRequest_63(com.fc.castle.data.message.Messages.SendMailRequest(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.SendMailResponse) {
			w_SendMailResponse_64(com.fc.castle.data.message.Messages.SendMailResponse(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.UseItemRequest) {
			w_UseItemRequest_65(com.fc.castle.data.message.Messages.UseItemRequest(msg), output); return;
		}
		if (msg is com.fc.castle.data.message.Messages.UseItemResponse) {
			w_UseItemResponse_66(com.fc.castle.data.message.Messages.UseItemResponse(msg), output); return;
		}
		if (msg is com.fc.castle.data.template.BuffTemplate) {
			w_BuffTemplate_69(com.fc.castle.data.template.BuffTemplate(msg), output); return;
		}
		if (msg is com.fc.castle.data.template.Enums.AttackType) {
			w_AttackType_70(com.fc.castle.data.template.Enums.AttackType(msg), output); return;
		}
		if (msg is com.fc.castle.data.template.Enums.DefenseType) {
			w_DefenseType_71(com.fc.castle.data.template.Enums.DefenseType(msg), output); return;
		}
		if (msg is com.fc.castle.data.template.Enums.FightType) {
			w_FightType_72(com.fc.castle.data.template.Enums.FightType(msg), output); return;
		}
		if (msg is com.fc.castle.data.template.Enums.MotionType) {
			w_MotionType_73(com.fc.castle.data.template.Enums.MotionType(msg), output); return;
		}
		if (msg is com.fc.castle.data.template.Enums.SceneEventType) {
			w_SceneEventType_74(com.fc.castle.data.template.Enums.SceneEventType(msg), output); return;
		}
		if (msg is com.fc.castle.data.template.Enums.SkillSpecialEffect) {
			w_SkillSpecialEffect_75(com.fc.castle.data.template.Enums.SkillSpecialEffect(msg), output); return;
		}
		if (msg is com.fc.castle.data.template.Enums.SkillTargetType) {
			w_SkillTargetType_76(com.fc.castle.data.template.Enums.SkillTargetType(msg), output); return;
		}
		if (msg is com.fc.castle.data.template.EventTemplate) {
			w_EventTemplate_77(com.fc.castle.data.template.EventTemplate(msg), output); return;
		}
		if (msg is com.fc.castle.data.template.FormualMap) {
			w_FormualMap_78(com.fc.castle.data.template.FormualMap(msg), output); return;
		}
		if (msg is com.fc.castle.data.template.GuideData) {
			w_GuideData_79(com.fc.castle.data.template.GuideData(msg), output); return;
		}
		if (msg is com.fc.castle.data.template.ItemTemplate) {
			w_ItemTemplate_80(com.fc.castle.data.template.ItemTemplate(msg), output); return;
		}
		if (msg is com.fc.castle.data.template.SkillTemplate) {
			w_SkillTemplate_81(com.fc.castle.data.template.SkillTemplate(msg), output); return;
		}
		if (msg is com.fc.castle.data.template.UnitTemplate) {
			w_UnitTemplate_82(com.fc.castle.data.template.UnitTemplate(msg), output); return;
		}

		}
		
//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.Account
//	----------------------------------------------------------------------------------------------------
	private function r_Account_1(msg : com.fc.castle.data.Account, input : NetDataInput) : void {
		msg.id = input.readJavaUTF();
		msg.sign = input.readJavaUTF();
		msg.gold = input.readInt();
		msg.players_id = input.readIntArray();
	}
	private function w_Account_1(msg : com.fc.castle.data.Account, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.id);
		output.writeJavaUTF(msg.sign);
		output.writeInt(msg.gold);
		output.writeIntArray(msg.players_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.BattleEvent
//	----------------------------------------------------------------------------------------------------
	private function r_BattleEvent_2(msg : com.fc.castle.data.BattleEvent, input : NetDataInput) : void {
		msg.time = input.readInt();
		msg.event = input.readByte();
		msg.force = input.readByte();
		msg.datas = input.readIntArray();
	}
	private function w_BattleEvent_2(msg : com.fc.castle.data.BattleEvent, output : NetDataOutput) : void {
		output.writeInt(msg.time);
		output.writeByte(msg.event);
		output.writeByte(msg.force);
		output.writeIntArray(msg.datas);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.BattleLog
//	----------------------------------------------------------------------------------------------------
	private function r_BattleLog_3(msg : com.fc.castle.data.BattleLog, input : NetDataInput) : void {
		msg.battle = input.readMutual() as com.fc.castle.data.message.Messages.BattleStartResponse;
		msg.logs = input.readCollection(NetDataTypes.TYPE_MUTUAL);
	}
	private function w_BattleLog_3(msg : com.fc.castle.data.BattleLog, output : NetDataOutput) : void {
		output.writeMutual(msg.battle);
		output.writeCollection(msg.logs, NetDataTypes.TYPE_MUTUAL);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.BattlePlayer
//	----------------------------------------------------------------------------------------------------
	private function r_BattlePlayer_4(msg : com.fc.castle.data.BattlePlayer, input : NetDataInput) : void {
		msg.type = input.readInt();
		msg.playerID = input.readInt();
		msg.name = input.readJavaUTF();
		msg.hp = input.readInt();
		msg.mp = input.readInt();
		msg.ap = input.readInt();
		msg.soldiers = input.readMutual() as com.fc.castle.data.SoldierDatas;
		msg.skills = input.readMutual() as com.fc.castle.data.SkillDatas;
	}
	private function w_BattlePlayer_4(msg : com.fc.castle.data.BattlePlayer, output : NetDataOutput) : void {
		output.writeInt(msg.type);
		output.writeInt(msg.playerID);
		output.writeJavaUTF(msg.name);
		output.writeInt(msg.hp);
		output.writeInt(msg.mp);
		output.writeInt(msg.ap);
		output.writeMutual(msg.soldiers);
		output.writeMutual(msg.skills);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.ExploreData
//	----------------------------------------------------------------------------------------------------
	private function r_ExploreData_5(msg : com.fc.castle.data.ExploreData, input : NetDataInput) : void {
		msg.UnitName = input.readJavaUTF();
		msg.explore_name = input.readJavaUTF();
		msg.refreshTime = input.readInt();
		msg.last_time = input.readDate();
		msg.last_explorer = input.readMutual() as com.fc.castle.data.PlayerFriend;
	}
	private function w_ExploreData_5(msg : com.fc.castle.data.ExploreData, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.UnitName);
		output.writeJavaUTF(msg.explore_name);
		output.writeInt(msg.refreshTime);
		output.writeDate(msg.last_time);
		output.writeMutual(msg.last_explorer);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.ExploreState
//	----------------------------------------------------------------------------------------------------
	private function r_ExploreState_6(msg : com.fc.castle.data.ExploreState, input : NetDataInput) : void {
		msg.UnitName = input.readJavaUTF();
		msg.last_time = input.readDate();
		msg.last_explorer = input.readMutual() as com.fc.castle.data.PlayerFriend;
		msg.explore_count = input.readInt();
	}
	private function w_ExploreState_6(msg : com.fc.castle.data.ExploreState, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.UnitName);
		output.writeDate(msg.last_time);
		output.writeMutual(msg.last_explorer);
		output.writeInt(msg.explore_count);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.ItemData
//	----------------------------------------------------------------------------------------------------
	private function r_ItemData_7(msg : com.fc.castle.data.ItemData, input : NetDataInput) : void {
		msg.itemType = input.readInt();
		msg.count = input.readInt();
	}
	private function w_ItemData_7(msg : com.fc.castle.data.ItemData, output : NetDataOutput) : void {
		output.writeInt(msg.itemType);
		output.writeInt(msg.count);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.ItemDatas
//	----------------------------------------------------------------------------------------------------
	private function r_ItemDatas_8(msg : com.fc.castle.data.ItemDatas, input : NetDataInput) : void {
		msg.datas = input.readCollection(NetDataTypes.TYPE_MUTUAL);
	}
	private function w_ItemDatas_8(msg : com.fc.castle.data.ItemDatas, output : NetDataOutput) : void {
		output.writeCollection(msg.datas, NetDataTypes.TYPE_MUTUAL);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.ItemDrop
//	----------------------------------------------------------------------------------------------------
	private function r_ItemDrop_9(msg : com.fc.castle.data.ItemDrop, input : NetDataInput) : void {
		msg.itemType = input.readInt();
		msg.count = input.readInt();
		msg.percent = input.readFloat();
	}
	private function w_ItemDrop_9(msg : com.fc.castle.data.ItemDrop, output : NetDataOutput) : void {
		output.writeInt(msg.itemType);
		output.writeInt(msg.count);
		output.writeFloat(msg.percent);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.ItemDrops
//	----------------------------------------------------------------------------------------------------
	private function r_ItemDrops_10(msg : com.fc.castle.data.ItemDrops, input : NetDataInput) : void {
		msg.datas = input.readCollection(NetDataTypes.TYPE_MUTUAL);
	}
	private function w_ItemDrops_10(msg : com.fc.castle.data.ItemDrops, output : NetDataOutput) : void {
		output.writeCollection(msg.datas, NetDataTypes.TYPE_MUTUAL);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.Mail
//	----------------------------------------------------------------------------------------------------
	private function r_Mail_11(msg : com.fc.castle.data.Mail, input : NetDataInput) : void {
		msg.id = input.readInt();
		msg.type = input.readInt();
		msg.sendTime = input.readDate();
		msg.cyccode = input.readInt();
		msg.senderName = input.readJavaUTF();
		msg.receiverName = input.readJavaUTF();
		msg.senderPlayerID = input.readInt();
		msg.receiverPlayerID = input.readInt();
		msg.title = input.readJavaUTF();
		msg.content = input.readJavaUTF();
		msg.readed = input.readBoolean();
	}
	private function w_Mail_11(msg : com.fc.castle.data.Mail, output : NetDataOutput) : void {
		output.writeInt(msg.id);
		output.writeInt(msg.type);
		output.writeDate(msg.sendTime);
		output.writeInt(msg.cyccode);
		output.writeJavaUTF(msg.senderName);
		output.writeJavaUTF(msg.receiverName);
		output.writeInt(msg.senderPlayerID);
		output.writeInt(msg.receiverPlayerID);
		output.writeJavaUTF(msg.title);
		output.writeJavaUTF(msg.content);
		output.writeBoolean(msg.readed);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.MailSnap
//	----------------------------------------------------------------------------------------------------
	private function r_MailSnap_12(msg : com.fc.castle.data.MailSnap, input : NetDataInput) : void {
		msg.id = input.readInt();
		msg.title = input.readJavaUTF();
		msg.senderPlayerName = input.readJavaUTF();
		msg.senderPlayerID = input.readInt();
		msg.readed = input.readBoolean();
	}
	private function w_MailSnap_12(msg : com.fc.castle.data.MailSnap, output : NetDataOutput) : void {
		output.writeInt(msg.id);
		output.writeJavaUTF(msg.title);
		output.writeJavaUTF(msg.senderPlayerName);
		output.writeInt(msg.senderPlayerID);
		output.writeBoolean(msg.readed);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.PlayerData
//	----------------------------------------------------------------------------------------------------
	private function r_PlayerData_13(msg : com.fc.castle.data.PlayerData, input : NetDataInput) : void {
		msg.player_id = input.readInt();
		msg.player_name = input.readJavaUTF();
		msg.nick_name = input.readJavaUTF();
		msg.account_id = input.readJavaUTF();
		msg.level = input.readInt();
		msg.hp = input.readInt();
		msg.ap = input.readInt();
		msg.mp = input.readInt();
		msg.experience = input.readInt();
		msg.cur_exp = input.readInt();
		msg.next_exp = input.readInt();
		msg.coin = input.readInt();
		msg.homeScene = input.readJavaUTF();
		msg.soldiers = input.readMutual() as com.fc.castle.data.SoldierDatas;
		msg.skills = input.readMutual() as com.fc.castle.data.SkillDatas;
		msg.items = input.readMutual() as com.fc.castle.data.ItemDatas;
		msg.battle_soldier_count = input.readInt();
		msg.battle_skill_count = input.readInt();
		msg.lastBattle = input.readMutual() as com.fc.castle.data.message.Messages.BattleStartResponse;
		msg.organizeDefense = input.readMutual() as com.fc.castle.data.message.Messages.OrganizeDefenseRequest;
		msg.exploreStates = input.readMap(NetDataTypes.TYPE_STRING, NetDataTypes.TYPE_MUTUAL);
	}
	private function w_PlayerData_13(msg : com.fc.castle.data.PlayerData, output : NetDataOutput) : void {
		output.writeInt(msg.player_id);
		output.writeJavaUTF(msg.player_name);
		output.writeJavaUTF(msg.nick_name);
		output.writeJavaUTF(msg.account_id);
		output.writeInt(msg.level);
		output.writeInt(msg.hp);
		output.writeInt(msg.ap);
		output.writeInt(msg.mp);
		output.writeInt(msg.experience);
		output.writeInt(msg.cur_exp);
		output.writeInt(msg.next_exp);
		output.writeInt(msg.coin);
		output.writeJavaUTF(msg.homeScene);
		output.writeMutual(msg.soldiers);
		output.writeMutual(msg.skills);
		output.writeMutual(msg.items);
		output.writeInt(msg.battle_soldier_count);
		output.writeInt(msg.battle_skill_count);
		output.writeMutual(msg.lastBattle);
		output.writeMutual(msg.organizeDefense);
		output.writeMap(msg.exploreStates, NetDataTypes.TYPE_STRING, NetDataTypes.TYPE_MUTUAL);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.PlayerFriend
//	----------------------------------------------------------------------------------------------------
	private function r_PlayerFriend_14(msg : com.fc.castle.data.PlayerFriend, input : NetDataInput) : void {
		msg.playerID = input.readInt();
		msg.playerName = input.readJavaUTF();
		msg.playerNickName = input.readJavaUTF();
		msg.headUrl = input.readJavaUTF();
		msg.level = input.readInt();
	}
	private function w_PlayerFriend_14(msg : com.fc.castle.data.PlayerFriend, output : NetDataOutput) : void {
		output.writeInt(msg.playerID);
		output.writeJavaUTF(msg.playerName);
		output.writeJavaUTF(msg.playerNickName);
		output.writeJavaUTF(msg.headUrl);
		output.writeInt(msg.level);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.PlayerMailData
//	----------------------------------------------------------------------------------------------------
	private function r_PlayerMailData_15(msg : com.fc.castle.data.PlayerMailData, input : NetDataInput) : void {
		msg.player_id = input.readInt();
		msg.player_name = input.readJavaUTF();
		msg.mails = input.readMap(NetDataTypes.TYPE_INT, NetDataTypes.TYPE_MUTUAL);
		msg.friendsMaxCount = input.readInt();
		msg.firends = input.readMap(NetDataTypes.TYPE_INT, NetDataTypes.TYPE_MUTUAL);
	}
	private function w_PlayerMailData_15(msg : com.fc.castle.data.PlayerMailData, output : NetDataOutput) : void {
		output.writeInt(msg.player_id);
		output.writeJavaUTF(msg.player_name);
		output.writeMap(msg.mails, NetDataTypes.TYPE_INT, NetDataTypes.TYPE_MUTUAL);
		output.writeInt(msg.friendsMaxCount);
		output.writeMap(msg.firends, NetDataTypes.TYPE_INT, NetDataTypes.TYPE_MUTUAL);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.PlayerQuestData
//	----------------------------------------------------------------------------------------------------
	private function r_PlayerQuestData_16(msg : com.fc.castle.data.PlayerQuestData, input : NetDataInput) : void {
		msg.player_id = input.readInt();
		msg.guide_steps = input.readInt();
	}
	private function w_PlayerQuestData_16(msg : com.fc.castle.data.PlayerQuestData, output : NetDataOutput) : void {
		output.writeInt(msg.player_id);
		output.writeInt(msg.guide_steps);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.ShopItem
//	----------------------------------------------------------------------------------------------------
	private function r_ShopItem_17(msg : com.fc.castle.data.ShopItem, input : NetDataInput) : void {
		msg.itemType = input.readInt();
		msg.count = input.readInt();
	}
	private function w_ShopItem_17(msg : com.fc.castle.data.ShopItem, output : NetDataOutput) : void {
		output.writeInt(msg.itemType);
		output.writeInt(msg.count);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.ShopItems
//	----------------------------------------------------------------------------------------------------
	private function r_ShopItems_18(msg : com.fc.castle.data.ShopItems, input : NetDataInput) : void {
		msg.datas = input.readCollection(NetDataTypes.TYPE_MUTUAL);
	}
	private function w_ShopItems_18(msg : com.fc.castle.data.ShopItems, output : NetDataOutput) : void {
		output.writeCollection(msg.datas, NetDataTypes.TYPE_MUTUAL);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.SkillData
//	----------------------------------------------------------------------------------------------------
	private function r_SkillData_19(msg : com.fc.castle.data.SkillData, input : NetDataInput) : void {
		msg.skillType = input.readInt();
	}
	private function w_SkillData_19(msg : com.fc.castle.data.SkillData, output : NetDataOutput) : void {
		output.writeInt(msg.skillType);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.SkillDatas
//	----------------------------------------------------------------------------------------------------
	private function r_SkillDatas_20(msg : com.fc.castle.data.SkillDatas, input : NetDataInput) : void {
		msg.datas = input.readCollection(NetDataTypes.TYPE_MUTUAL);
	}
	private function w_SkillDatas_20(msg : com.fc.castle.data.SkillDatas, output : NetDataOutput) : void {
		output.writeCollection(msg.datas, NetDataTypes.TYPE_MUTUAL);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.SoldierData
//	----------------------------------------------------------------------------------------------------
	private function r_SoldierData_21(msg : com.fc.castle.data.SoldierData, input : NetDataInput) : void {
		msg.unitType = input.readInt();
	}
	private function w_SoldierData_21(msg : com.fc.castle.data.SoldierData, output : NetDataOutput) : void {
		output.writeInt(msg.unitType);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.SoldierDatas
//	----------------------------------------------------------------------------------------------------
	private function r_SoldierDatas_22(msg : com.fc.castle.data.SoldierDatas, input : NetDataInput) : void {
		msg.datas = input.readCollection(NetDataTypes.TYPE_MUTUAL);
	}
	private function w_SoldierDatas_22(msg : com.fc.castle.data.SoldierDatas, output : NetDataOutput) : void {
		output.writeCollection(msg.datas, NetDataTypes.TYPE_MUTUAL);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.BattleStartRequest
//	----------------------------------------------------------------------------------------------------
	private function r_BattleStartRequest_25(msg : com.fc.castle.data.message.Messages.BattleStartRequest, input : NetDataInput) : void {
		msg.battleType = input.readInt();
		msg.targetPlayerID = input.readInt();
		msg.scene_unit_name = input.readJavaUTF();
		msg.soldiers = input.readMutual() as com.fc.castle.data.SoldierDatas;
		msg.skills = input.readMutual() as com.fc.castle.data.SkillDatas;
		msg.test_forceB_soldiers = input.readMutual() as com.fc.castle.data.SoldierDatas;
		msg.test_forceB_skills = input.readMutual() as com.fc.castle.data.SkillDatas;
		msg.player_id = input.readInt();
	}
	private function w_BattleStartRequest_25(msg : com.fc.castle.data.message.Messages.BattleStartRequest, output : NetDataOutput) : void {
		output.writeInt(msg.battleType);
		output.writeInt(msg.targetPlayerID);
		output.writeJavaUTF(msg.scene_unit_name);
		output.writeMutual(msg.soldiers);
		output.writeMutual(msg.skills);
		output.writeMutual(msg.test_forceB_soldiers);
		output.writeMutual(msg.test_forceB_skills);
		output.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.BattleStartResponse
//	----------------------------------------------------------------------------------------------------
	private function r_BattleStartResponse_26(msg : com.fc.castle.data.message.Messages.BattleStartResponse, input : NetDataInput) : void {
		msg.result = input.readByte();
		msg.battleType = input.readInt();
		msg.targetPlayerID = input.readInt();
		msg.scene_unit_name = input.readJavaUTF();
		msg.guideStep = input.readInt();
		msg.cmap_id = input.readJavaUTF();
		msg.ap_upgrade_time = input.readInt();
		msg.ap_upgrade_count = input.readInt();
		msg.formual_map = input.readMutual() as com.fc.castle.data.template.FormualMap;
		msg.forceA = input.readMutual() as com.fc.castle.data.BattlePlayer;
		msg.forceB = input.readMutual() as com.fc.castle.data.BattlePlayer;
	}
	private function w_BattleStartResponse_26(msg : com.fc.castle.data.message.Messages.BattleStartResponse, output : NetDataOutput) : void {
		output.writeByte(msg.result);
		output.writeInt(msg.battleType);
		output.writeInt(msg.targetPlayerID);
		output.writeJavaUTF(msg.scene_unit_name);
		output.writeInt(msg.guideStep);
		output.writeJavaUTF(msg.cmap_id);
		output.writeInt(msg.ap_upgrade_time);
		output.writeInt(msg.ap_upgrade_count);
		output.writeMutual(msg.formual_map);
		output.writeMutual(msg.forceA);
		output.writeMutual(msg.forceB);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.BuyShopItemRequest
//	----------------------------------------------------------------------------------------------------
	private function r_BuyShopItemRequest_27(msg : com.fc.castle.data.message.Messages.BuyShopItemRequest, input : NetDataInput) : void {
		msg.indexOfShop = input.readInt();
		msg.player_id = input.readInt();
	}
	private function w_BuyShopItemRequest_27(msg : com.fc.castle.data.message.Messages.BuyShopItemRequest, output : NetDataOutput) : void {
		output.writeInt(msg.indexOfShop);
		output.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.BuyShopItemResponse
//	----------------------------------------------------------------------------------------------------
	private function r_BuyShopItemResponse_28(msg : com.fc.castle.data.message.Messages.BuyShopItemResponse, input : NetDataInput) : void {
		msg.result = input.readByte();
		msg.bag_index = input.readInt();
		msg.bag_item = input.readMutual() as com.fc.castle.data.ItemData;
		msg.my_coin = input.readInt();
	}
	private function w_BuyShopItemResponse_28(msg : com.fc.castle.data.message.Messages.BuyShopItemResponse, output : NetDataOutput) : void {
		output.writeByte(msg.result);
		output.writeInt(msg.bag_index);
		output.writeMutual(msg.bag_item);
		output.writeInt(msg.my_coin);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.CommitBattleResultRequest
//	----------------------------------------------------------------------------------------------------
	private function r_CommitBattleResultRequest_29(msg : com.fc.castle.data.message.Messages.CommitBattleResultRequest, input : NetDataInput) : void {
		msg.log = input.readMutual() as com.fc.castle.data.BattleLog;
		msg.scene_unit_name = input.readJavaUTF();
		msg.is_win = input.readBoolean();
		msg.player_id = input.readInt();
	}
	private function w_CommitBattleResultRequest_29(msg : com.fc.castle.data.message.Messages.CommitBattleResultRequest, output : NetDataOutput) : void {
		output.writeMutual(msg.log);
		output.writeJavaUTF(msg.scene_unit_name);
		output.writeBoolean(msg.is_win);
		output.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.CommitBattleResultResponse
//	----------------------------------------------------------------------------------------------------
	private function r_CommitBattleResultResponse_30(msg : com.fc.castle.data.message.Messages.CommitBattleResultResponse, input : NetDataInput) : void {
		msg.result = input.readByte();
		msg.explore_state = input.readMutual() as com.fc.castle.data.ExploreState;
	}
	private function w_CommitBattleResultResponse_30(msg : com.fc.castle.data.message.Messages.CommitBattleResultResponse, output : NetDataOutput) : void {
		output.writeByte(msg.result);
		output.writeMutual(msg.explore_state);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.CommitGuideRequest
//	----------------------------------------------------------------------------------------------------
	private function r_CommitGuideRequest_31(msg : com.fc.castle.data.message.Messages.CommitGuideRequest, input : NetDataInput) : void {
		msg.guide_steps = input.readInt();
		msg.player_id = input.readInt();
	}
	private function w_CommitGuideRequest_31(msg : com.fc.castle.data.message.Messages.CommitGuideRequest, output : NetDataOutput) : void {
		output.writeInt(msg.guide_steps);
		output.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.CommitGuideResponse
//	----------------------------------------------------------------------------------------------------
	private function r_CommitGuideResponse_32(msg : com.fc.castle.data.message.Messages.CommitGuideResponse, input : NetDataInput) : void {
		msg.result = input.readByte();
		msg.data = input.readMutual() as com.fc.castle.data.PlayerQuestData;
	}
	private function w_CommitGuideResponse_32(msg : com.fc.castle.data.message.Messages.CommitGuideResponse, output : NetDataOutput) : void {
		output.writeByte(msg.result);
		output.writeMutual(msg.data);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetBuffTemplateRequest
//	----------------------------------------------------------------------------------------------------
	private function r_GetBuffTemplateRequest_33(msg : com.fc.castle.data.message.Messages.GetBuffTemplateRequest, input : NetDataInput) : void {
		msg.player_id = input.readInt();
	}
	private function w_GetBuffTemplateRequest_33(msg : com.fc.castle.data.message.Messages.GetBuffTemplateRequest, output : NetDataOutput) : void {
		output.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetBuffTemplateResponse
//	----------------------------------------------------------------------------------------------------
	private function r_GetBuffTemplateResponse_34(msg : com.fc.castle.data.message.Messages.GetBuffTemplateResponse, input : NetDataInput) : void {
		msg.result = input.readByte();
		msg.buff_templates = input.readMap(NetDataTypes.TYPE_INT, NetDataTypes.TYPE_MUTUAL);
	}
	private function w_GetBuffTemplateResponse_34(msg : com.fc.castle.data.message.Messages.GetBuffTemplateResponse, output : NetDataOutput) : void {
		output.writeByte(msg.result);
		output.writeMap(msg.buff_templates, NetDataTypes.TYPE_INT, NetDataTypes.TYPE_MUTUAL);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetEventTemplateRequest
//	----------------------------------------------------------------------------------------------------
	private function r_GetEventTemplateRequest_35(msg : com.fc.castle.data.message.Messages.GetEventTemplateRequest, input : NetDataInput) : void {
		msg.event_types = input.readIntArray();
		msg.player_id = input.readInt();
	}
	private function w_GetEventTemplateRequest_35(msg : com.fc.castle.data.message.Messages.GetEventTemplateRequest, output : NetDataOutput) : void {
		output.writeIntArray(msg.event_types);
		output.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetEventTemplateResponse
//	----------------------------------------------------------------------------------------------------
	private function r_GetEventTemplateResponse_36(msg : com.fc.castle.data.message.Messages.GetEventTemplateResponse, input : NetDataInput) : void {
		msg.result = input.readByte();
		msg.event_templates = input.readMutualArray();
	}
	private function w_GetEventTemplateResponse_36(msg : com.fc.castle.data.message.Messages.GetEventTemplateResponse, output : NetDataOutput) : void {
		output.writeByte(msg.result);
		output.writeMutualArray(msg.event_templates);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetExploreDataListRequest
//	----------------------------------------------------------------------------------------------------
	private function r_GetExploreDataListRequest_37(msg : com.fc.castle.data.message.Messages.GetExploreDataListRequest, input : NetDataInput) : void {
		msg.targetPlayerID = input.readInt();
		msg.player_id = input.readInt();
	}
	private function w_GetExploreDataListRequest_37(msg : com.fc.castle.data.message.Messages.GetExploreDataListRequest, output : NetDataOutput) : void {
		output.writeInt(msg.targetPlayerID);
		output.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetExploreDataListResponse
//	----------------------------------------------------------------------------------------------------
	private function r_GetExploreDataListResponse_38(msg : com.fc.castle.data.message.Messages.GetExploreDataListResponse, input : NetDataInput) : void {
		msg.result = input.readByte();
		msg.targetPlayer = input.readMutual() as com.fc.castle.data.PlayerFriend;
		msg.targetPlayerHomeScene = input.readJavaUTF();
		msg.explores = input.readMap(NetDataTypes.TYPE_STRING, NetDataTypes.TYPE_MUTUAL);
	}
	private function w_GetExploreDataListResponse_38(msg : com.fc.castle.data.message.Messages.GetExploreDataListResponse, output : NetDataOutput) : void {
		output.writeByte(msg.result);
		output.writeMutual(msg.targetPlayer);
		output.writeJavaUTF(msg.targetPlayerHomeScene);
		output.writeMap(msg.explores, NetDataTypes.TYPE_STRING, NetDataTypes.TYPE_MUTUAL);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetExploreStateRequest
//	----------------------------------------------------------------------------------------------------
	private function r_GetExploreStateRequest_39(msg : com.fc.castle.data.message.Messages.GetExploreStateRequest, input : NetDataInput) : void {
		msg.unitName = input.readJavaUTF();
		msg.targetPlayerID = input.readInt();
		msg.player_id = input.readInt();
	}
	private function w_GetExploreStateRequest_39(msg : com.fc.castle.data.message.Messages.GetExploreStateRequest, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.unitName);
		output.writeInt(msg.targetPlayerID);
		output.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetExploreStateResponse
//	----------------------------------------------------------------------------------------------------
	private function r_GetExploreStateResponse_40(msg : com.fc.castle.data.message.Messages.GetExploreStateResponse, input : NetDataInput) : void {
		msg.result = input.readByte();
		msg.state = input.readMutual() as com.fc.castle.data.ExploreState;
		msg.event = input.readMutual() as com.fc.castle.data.template.EventTemplate;
	}
	private function w_GetExploreStateResponse_40(msg : com.fc.castle.data.message.Messages.GetExploreStateResponse, output : NetDataOutput) : void {
		output.writeByte(msg.result);
		output.writeMutual(msg.state);
		output.writeMutual(msg.event);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetItemTemplateRequest
//	----------------------------------------------------------------------------------------------------
	private function r_GetItemTemplateRequest_41(msg : com.fc.castle.data.message.Messages.GetItemTemplateRequest, input : NetDataInput) : void {
		msg.item_types = input.readIntArray();
		msg.player_id = input.readInt();
	}
	private function w_GetItemTemplateRequest_41(msg : com.fc.castle.data.message.Messages.GetItemTemplateRequest, output : NetDataOutput) : void {
		output.writeIntArray(msg.item_types);
		output.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetItemTemplateResponse
//	----------------------------------------------------------------------------------------------------
	private function r_GetItemTemplateResponse_42(msg : com.fc.castle.data.message.Messages.GetItemTemplateResponse, input : NetDataInput) : void {
		msg.result = input.readByte();
		msg.item_templates = input.readMutualArray();
	}
	private function w_GetItemTemplateResponse_42(msg : com.fc.castle.data.message.Messages.GetItemTemplateResponse, output : NetDataOutput) : void {
		output.writeByte(msg.result);
		output.writeMutualArray(msg.item_templates);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetMailRequest
//	----------------------------------------------------------------------------------------------------
	private function r_GetMailRequest_43(msg : com.fc.castle.data.message.Messages.GetMailRequest, input : NetDataInput) : void {
		msg.mailID = input.readInt();
		msg.player_id = input.readInt();
	}
	private function w_GetMailRequest_43(msg : com.fc.castle.data.message.Messages.GetMailRequest, output : NetDataOutput) : void {
		output.writeInt(msg.mailID);
		output.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetMailResponse
//	----------------------------------------------------------------------------------------------------
	private function r_GetMailResponse_44(msg : com.fc.castle.data.message.Messages.GetMailResponse, input : NetDataInput) : void {
		msg.result = input.readByte();
		msg.mail = input.readMutual() as com.fc.castle.data.Mail;
	}
	private function w_GetMailResponse_44(msg : com.fc.castle.data.message.Messages.GetMailResponse, output : NetDataOutput) : void {
		output.writeByte(msg.result);
		output.writeMutual(msg.mail);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetMailSnapsRequest
//	----------------------------------------------------------------------------------------------------
	private function r_GetMailSnapsRequest_45(msg : com.fc.castle.data.message.Messages.GetMailSnapsRequest, input : NetDataInput) : void {
		msg.player_id = input.readInt();
	}
	private function w_GetMailSnapsRequest_45(msg : com.fc.castle.data.message.Messages.GetMailSnapsRequest, output : NetDataOutput) : void {
		output.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetMailSnapsResponse
//	----------------------------------------------------------------------------------------------------
	private function r_GetMailSnapsResponse_46(msg : com.fc.castle.data.message.Messages.GetMailSnapsResponse, input : NetDataInput) : void {
		msg.result = input.readByte();
		msg.mailSnaps = input.readMutual() as com.fc.castle.data.PlayerMailData;
	}
	private function w_GetMailSnapsResponse_46(msg : com.fc.castle.data.message.Messages.GetMailSnapsResponse, output : NetDataOutput) : void {
		output.writeByte(msg.result);
		output.writeMutual(msg.mailSnaps);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetPlayerQuestRequest
//	----------------------------------------------------------------------------------------------------
	private function r_GetPlayerQuestRequest_47(msg : com.fc.castle.data.message.Messages.GetPlayerQuestRequest, input : NetDataInput) : void {
		msg.player_id = input.readInt();
	}
	private function w_GetPlayerQuestRequest_47(msg : com.fc.castle.data.message.Messages.GetPlayerQuestRequest, output : NetDataOutput) : void {
		output.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetPlayerQuestResponse
//	----------------------------------------------------------------------------------------------------
	private function r_GetPlayerQuestResponse_48(msg : com.fc.castle.data.message.Messages.GetPlayerQuestResponse, input : NetDataInput) : void {
		msg.result = input.readByte();
		msg.data = input.readMutual() as com.fc.castle.data.PlayerQuestData;
	}
	private function w_GetPlayerQuestResponse_48(msg : com.fc.castle.data.message.Messages.GetPlayerQuestResponse, output : NetDataOutput) : void {
		output.writeByte(msg.result);
		output.writeMutual(msg.data);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetRandomOnlinePlayersRequest
//	----------------------------------------------------------------------------------------------------
	private function r_GetRandomOnlinePlayersRequest_49(msg : com.fc.castle.data.message.Messages.GetRandomOnlinePlayersRequest, input : NetDataInput) : void {
		msg.player_id = input.readInt();
	}
	private function w_GetRandomOnlinePlayersRequest_49(msg : com.fc.castle.data.message.Messages.GetRandomOnlinePlayersRequest, output : NetDataOutput) : void {
		output.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetRandomOnlinePlayersResponse
//	----------------------------------------------------------------------------------------------------
	private function r_GetRandomOnlinePlayersResponse_50(msg : com.fc.castle.data.message.Messages.GetRandomOnlinePlayersResponse, input : NetDataInput) : void {
		msg.result = input.readByte();
		msg.friends = input.readCollection(NetDataTypes.TYPE_MUTUAL);
	}
	private function w_GetRandomOnlinePlayersResponse_50(msg : com.fc.castle.data.message.Messages.GetRandomOnlinePlayersResponse, output : NetDataOutput) : void {
		output.writeByte(msg.result);
		output.writeCollection(msg.friends, NetDataTypes.TYPE_MUTUAL);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetShopItemRequest
//	----------------------------------------------------------------------------------------------------
	private function r_GetShopItemRequest_51(msg : com.fc.castle.data.message.Messages.GetShopItemRequest, input : NetDataInput) : void {
		msg.player_id = input.readInt();
	}
	private function w_GetShopItemRequest_51(msg : com.fc.castle.data.message.Messages.GetShopItemRequest, output : NetDataOutput) : void {
		output.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetShopItemResponse
//	----------------------------------------------------------------------------------------------------
	private function r_GetShopItemResponse_52(msg : com.fc.castle.data.message.Messages.GetShopItemResponse, input : NetDataInput) : void {
		msg.result = input.readByte();
		msg.items = input.readMutual() as com.fc.castle.data.ShopItems;
	}
	private function w_GetShopItemResponse_52(msg : com.fc.castle.data.message.Messages.GetShopItemResponse, output : NetDataOutput) : void {
		output.writeByte(msg.result);
		output.writeMutual(msg.items);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetSkillTemplateRequest
//	----------------------------------------------------------------------------------------------------
	private function r_GetSkillTemplateRequest_53(msg : com.fc.castle.data.message.Messages.GetSkillTemplateRequest, input : NetDataInput) : void {
		msg.skill_types = input.readIntArray();
		msg.player_id = input.readInt();
	}
	private function w_GetSkillTemplateRequest_53(msg : com.fc.castle.data.message.Messages.GetSkillTemplateRequest, output : NetDataOutput) : void {
		output.writeIntArray(msg.skill_types);
		output.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetSkillTemplateResponse
//	----------------------------------------------------------------------------------------------------
	private function r_GetSkillTemplateResponse_54(msg : com.fc.castle.data.message.Messages.GetSkillTemplateResponse, input : NetDataInput) : void {
		msg.result = input.readByte();
		msg.skill_templates = input.readMutualArray();
	}
	private function w_GetSkillTemplateResponse_54(msg : com.fc.castle.data.message.Messages.GetSkillTemplateResponse, output : NetDataOutput) : void {
		output.writeByte(msg.result);
		output.writeMutualArray(msg.skill_templates);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetUnitTemplateRequest
//	----------------------------------------------------------------------------------------------------
	private function r_GetUnitTemplateRequest_55(msg : com.fc.castle.data.message.Messages.GetUnitTemplateRequest, input : NetDataInput) : void {
		msg.unit_types = input.readIntArray();
		msg.player_id = input.readInt();
	}
	private function w_GetUnitTemplateRequest_55(msg : com.fc.castle.data.message.Messages.GetUnitTemplateRequest, output : NetDataOutput) : void {
		output.writeIntArray(msg.unit_types);
		output.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.GetUnitTemplateResponse
//	----------------------------------------------------------------------------------------------------
	private function r_GetUnitTemplateResponse_56(msg : com.fc.castle.data.message.Messages.GetUnitTemplateResponse, input : NetDataInput) : void {
		msg.result = input.readByte();
		msg.unit_templates = input.readMutualArray();
	}
	private function w_GetUnitTemplateResponse_56(msg : com.fc.castle.data.message.Messages.GetUnitTemplateResponse, output : NetDataOutput) : void {
		output.writeByte(msg.result);
		output.writeMutualArray(msg.unit_templates);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.LoginRequest
//	----------------------------------------------------------------------------------------------------
	private function r_LoginRequest_57(msg : com.fc.castle.data.message.Messages.LoginRequest, input : NetDataInput) : void {
		msg.account_id = input.readJavaUTF();
		msg.sign = input.readJavaUTF();
		msg.create = input.readBoolean();
		msg.createNickName = input.readJavaUTF();
		msg.player_id = input.readInt();
	}
	private function w_LoginRequest_57(msg : com.fc.castle.data.message.Messages.LoginRequest, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.account_id);
		output.writeJavaUTF(msg.sign);
		output.writeBoolean(msg.create);
		output.writeJavaUTF(msg.createNickName);
		output.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.LoginResponse
//	----------------------------------------------------------------------------------------------------
	private function r_LoginResponse_58(msg : com.fc.castle.data.message.Messages.LoginResponse, input : NetDataInput) : void {
		msg.result = input.readByte();
		msg.login_account = input.readJavaUTF();
		msg.login_key = input.readJavaUTF();
		msg.player_data = input.readMutualArray();
		msg.player_quests = input.readMutualArray();
	}
	private function w_LoginResponse_58(msg : com.fc.castle.data.message.Messages.LoginResponse, output : NetDataOutput) : void {
		output.writeByte(msg.result);
		output.writeJavaUTF(msg.login_account);
		output.writeJavaUTF(msg.login_key);
		output.writeMutualArray(msg.player_data);
		output.writeMutualArray(msg.player_quests);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.OrganizeDefenseRequest
//	----------------------------------------------------------------------------------------------------
	private function r_OrganizeDefenseRequest_59(msg : com.fc.castle.data.message.Messages.OrganizeDefenseRequest, input : NetDataInput) : void {
		msg.soldiers = input.readMutual() as com.fc.castle.data.SoldierDatas;
		msg.skills = input.readMutual() as com.fc.castle.data.SkillDatas;
		msg.player_id = input.readInt();
	}
	private function w_OrganizeDefenseRequest_59(msg : com.fc.castle.data.message.Messages.OrganizeDefenseRequest, output : NetDataOutput) : void {
		output.writeMutual(msg.soldiers);
		output.writeMutual(msg.skills);
		output.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.OrganizeDefenseResponse
//	----------------------------------------------------------------------------------------------------
	private function r_OrganizeDefenseResponse_60(msg : com.fc.castle.data.message.Messages.OrganizeDefenseResponse, input : NetDataInput) : void {
		msg.result = input.readByte();
	}
	private function w_OrganizeDefenseResponse_60(msg : com.fc.castle.data.message.Messages.OrganizeDefenseResponse, output : NetDataOutput) : void {
		output.writeByte(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.RefreshPlayerDataRequest
//	----------------------------------------------------------------------------------------------------
	private function r_RefreshPlayerDataRequest_61(msg : com.fc.castle.data.message.Messages.RefreshPlayerDataRequest, input : NetDataInput) : void {
		msg.player_id = input.readInt();
	}
	private function w_RefreshPlayerDataRequest_61(msg : com.fc.castle.data.message.Messages.RefreshPlayerDataRequest, output : NetDataOutput) : void {
		output.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.RefreshPlayerDataResponse
//	----------------------------------------------------------------------------------------------------
	private function r_RefreshPlayerDataResponse_62(msg : com.fc.castle.data.message.Messages.RefreshPlayerDataResponse, input : NetDataInput) : void {
		msg.result = input.readByte();
		msg.player = input.readMutual() as com.fc.castle.data.PlayerData;
	}
	private function w_RefreshPlayerDataResponse_62(msg : com.fc.castle.data.message.Messages.RefreshPlayerDataResponse, output : NetDataOutput) : void {
		output.writeByte(msg.result);
		output.writeMutual(msg.player);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.SendMailRequest
//	----------------------------------------------------------------------------------------------------
	private function r_SendMailRequest_63(msg : com.fc.castle.data.message.Messages.SendMailRequest, input : NetDataInput) : void {
		msg.mail = input.readMutual() as com.fc.castle.data.Mail;
		msg.player_id = input.readInt();
	}
	private function w_SendMailRequest_63(msg : com.fc.castle.data.message.Messages.SendMailRequest, output : NetDataOutput) : void {
		output.writeMutual(msg.mail);
		output.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.SendMailResponse
//	----------------------------------------------------------------------------------------------------
	private function r_SendMailResponse_64(msg : com.fc.castle.data.message.Messages.SendMailResponse, input : NetDataInput) : void {
		msg.result = input.readByte();
	}
	private function w_SendMailResponse_64(msg : com.fc.castle.data.message.Messages.SendMailResponse, output : NetDataOutput) : void {
		output.writeByte(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.UseItemRequest
//	----------------------------------------------------------------------------------------------------
	private function r_UseItemRequest_65(msg : com.fc.castle.data.message.Messages.UseItemRequest, input : NetDataInput) : void {
		msg.indexOfItems = input.readInt();
		msg.count = input.readInt();
		msg.player_id = input.readInt();
	}
	private function w_UseItemRequest_65(msg : com.fc.castle.data.message.Messages.UseItemRequest, output : NetDataOutput) : void {
		output.writeInt(msg.indexOfItems);
		output.writeInt(msg.count);
		output.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.message.Messages.UseItemResponse
//	----------------------------------------------------------------------------------------------------
	private function r_UseItemResponse_66(msg : com.fc.castle.data.message.Messages.UseItemResponse, input : NetDataInput) : void {
		msg.result = input.readByte();
		msg.item_slot = input.readMutual() as com.fc.castle.data.ItemData;
	}
	private function w_UseItemResponse_66(msg : com.fc.castle.data.message.Messages.UseItemResponse, output : NetDataOutput) : void {
		output.writeByte(msg.result);
		output.writeMutual(msg.item_slot);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.template.BuffTemplate
//	----------------------------------------------------------------------------------------------------
	private function r_BuffTemplate_69(msg : com.fc.castle.data.template.BuffTemplate, input : NetDataInput) : void {
		msg.type = input.readInt();
		msg.time = input.readInt();
		msg.debuff = input.readInt();
		msg.effect = input.readJavaUTF();
		msg.enhanceDamage = input.readFloat();
		msg.addDamage = input.readFloat();
		msg.damageReduction = input.readFloat();
		msg.enhanceDefens = input.readFloat();
		msg.addDefense = input.readFloat();
		msg.hasteRating = input.readFloat();
		msg.moveRating = input.readFloat();
		msg.addHP = input.readFloat();
		msg.enhanceHP = input.readFloat();
		msg.burning = input.readFloat();
		msg.burningInterval = input.readInt();
		msg.icon = input.readJavaUTF();
		msg.name = input.readJavaUTF();
	}
	private function w_BuffTemplate_69(msg : com.fc.castle.data.template.BuffTemplate, output : NetDataOutput) : void {
		output.writeInt(msg.type);
		output.writeInt(msg.time);
		output.writeInt(msg.debuff);
		output.writeJavaUTF(msg.effect);
		output.writeFloat(msg.enhanceDamage);
		output.writeFloat(msg.addDamage);
		output.writeFloat(msg.damageReduction);
		output.writeFloat(msg.enhanceDefens);
		output.writeFloat(msg.addDefense);
		output.writeFloat(msg.hasteRating);
		output.writeFloat(msg.moveRating);
		output.writeFloat(msg.addHP);
		output.writeFloat(msg.enhanceHP);
		output.writeFloat(msg.burning);
		output.writeInt(msg.burningInterval);
		output.writeJavaUTF(msg.icon);
		output.writeJavaUTF(msg.name);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.template.Enums.AttackType
//	----------------------------------------------------------------------------------------------------
	private function r_AttackType_70(msg : com.fc.castle.data.template.Enums.AttackType, input : NetDataInput) : void {
	}
	private function w_AttackType_70(msg : com.fc.castle.data.template.Enums.AttackType, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.template.Enums.DefenseType
//	----------------------------------------------------------------------------------------------------
	private function r_DefenseType_71(msg : com.fc.castle.data.template.Enums.DefenseType, input : NetDataInput) : void {
	}
	private function w_DefenseType_71(msg : com.fc.castle.data.template.Enums.DefenseType, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.template.Enums.FightType
//	----------------------------------------------------------------------------------------------------
	private function r_FightType_72(msg : com.fc.castle.data.template.Enums.FightType, input : NetDataInput) : void {
	}
	private function w_FightType_72(msg : com.fc.castle.data.template.Enums.FightType, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.template.Enums.MotionType
//	----------------------------------------------------------------------------------------------------
	private function r_MotionType_73(msg : com.fc.castle.data.template.Enums.MotionType, input : NetDataInput) : void {
	}
	private function w_MotionType_73(msg : com.fc.castle.data.template.Enums.MotionType, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.template.Enums.SceneEventType
//	----------------------------------------------------------------------------------------------------
	private function r_SceneEventType_74(msg : com.fc.castle.data.template.Enums.SceneEventType, input : NetDataInput) : void {
	}
	private function w_SceneEventType_74(msg : com.fc.castle.data.template.Enums.SceneEventType, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.template.Enums.SkillSpecialEffect
//	----------------------------------------------------------------------------------------------------
	private function r_SkillSpecialEffect_75(msg : com.fc.castle.data.template.Enums.SkillSpecialEffect, input : NetDataInput) : void {
	}
	private function w_SkillSpecialEffect_75(msg : com.fc.castle.data.template.Enums.SkillSpecialEffect, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.template.Enums.SkillTargetType
//	----------------------------------------------------------------------------------------------------
	private function r_SkillTargetType_76(msg : com.fc.castle.data.template.Enums.SkillTargetType, input : NetDataInput) : void {
	}
	private function w_SkillTargetType_76(msg : com.fc.castle.data.template.Enums.SkillTargetType, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.template.EventTemplate
//	----------------------------------------------------------------------------------------------------
	private function r_EventTemplate_77(msg : com.fc.castle.data.template.EventTemplate, input : NetDataInput) : void {
		msg.type = input.readInt();
		msg.subtype = input.readInt();
		msg.hp = input.readInt();
		msg.ap = input.readInt();
		msg.mp = input.readInt();
		msg.battleMap = input.readJavaUTF();
		msg.refreshTime = input.readInt();
		msg.soldiers = input.readMutual() as com.fc.castle.data.SoldierDatas;
		msg.skills = input.readMutual() as com.fc.castle.data.SkillDatas;
		msg.exp = input.readInt();
		msg.gold = input.readInt();
		msg.itemDrops = input.readMutual() as com.fc.castle.data.ItemDrops;
		msg.name = input.readJavaUTF();
		msg.desc = input.readJavaUTF();
	}
	private function w_EventTemplate_77(msg : com.fc.castle.data.template.EventTemplate, output : NetDataOutput) : void {
		output.writeInt(msg.type);
		output.writeInt(msg.subtype);
		output.writeInt(msg.hp);
		output.writeInt(msg.ap);
		output.writeInt(msg.mp);
		output.writeJavaUTF(msg.battleMap);
		output.writeInt(msg.refreshTime);
		output.writeMutual(msg.soldiers);
		output.writeMutual(msg.skills);
		output.writeInt(msg.exp);
		output.writeInt(msg.gold);
		output.writeMutual(msg.itemDrops);
		output.writeJavaUTF(msg.name);
		output.writeJavaUTF(msg.desc);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.template.FormualMap
//	----------------------------------------------------------------------------------------------------
	private function r_FormualMap_78(msg : com.fc.castle.data.template.FormualMap, input : NetDataInput) : void {
		msg.ammor_map = input.readAnyArray(NetDataTypes.TYPE_FLOAT);
	}
	private function w_FormualMap_78(msg : com.fc.castle.data.template.FormualMap, output : NetDataOutput) : void {
		output.writeAnyArray(msg.ammor_map, NetDataTypes.TYPE_FLOAT);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.template.GuideData
//	----------------------------------------------------------------------------------------------------
	private function r_GuideData_79(msg : com.fc.castle.data.template.GuideData, input : NetDataInput) : void {
		msg.GUIDE_STEP = input.readInt();
		msg.GUIDE_START_AP = input.readInt();
		msg.GUIDE_START_MP = input.readInt();
		msg.GUIDE_START_HP = input.readInt();
		msg.GUIDE_PLAYER_SOLDIER = input.readIntArray();
		msg.GUIDE_PLAYER_SKILL = input.readIntArray();
		msg.GUIDE_AI_NAME = input.readJavaUTF();
		msg.GUIDE_AI_SOLDIER = input.readIntArray();
		msg.GUIDE_AI_SKILL = input.readIntArray();
		msg.GUIDE_BATTLE_MAP = input.readJavaUTF();
		msg.AWARD_EXP = input.readInt();
		msg.AWARD_GOLD = input.readInt();
		msg.AWARD_ITEMS = input.readMutual() as com.fc.castle.data.ItemDatas;
	}
	private function w_GuideData_79(msg : com.fc.castle.data.template.GuideData, output : NetDataOutput) : void {
		output.writeInt(msg.GUIDE_STEP);
		output.writeInt(msg.GUIDE_START_AP);
		output.writeInt(msg.GUIDE_START_MP);
		output.writeInt(msg.GUIDE_START_HP);
		output.writeIntArray(msg.GUIDE_PLAYER_SOLDIER);
		output.writeIntArray(msg.GUIDE_PLAYER_SKILL);
		output.writeJavaUTF(msg.GUIDE_AI_NAME);
		output.writeIntArray(msg.GUIDE_AI_SOLDIER);
		output.writeIntArray(msg.GUIDE_AI_SKILL);
		output.writeJavaUTF(msg.GUIDE_BATTLE_MAP);
		output.writeInt(msg.AWARD_EXP);
		output.writeInt(msg.AWARD_GOLD);
		output.writeMutual(msg.AWARD_ITEMS);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.template.ItemTemplate
//	----------------------------------------------------------------------------------------------------
	private function r_ItemTemplate_80(msg : com.fc.castle.data.template.ItemTemplate, input : NetDataInput) : void {
		msg.type = input.readInt();
		msg.icon = input.readJavaUTF();
		msg.priceCoin = input.readInt();
		msg.getUnits = input.readMutual() as com.fc.castle.data.SoldierDatas;
		msg.getSkills = input.readMutual() as com.fc.castle.data.SkillDatas;
		msg.name = input.readJavaUTF();
		msg.desc = input.readJavaUTF();
	}
	private function w_ItemTemplate_80(msg : com.fc.castle.data.template.ItemTemplate, output : NetDataOutput) : void {
		output.writeInt(msg.type);
		output.writeJavaUTF(msg.icon);
		output.writeInt(msg.priceCoin);
		output.writeMutual(msg.getUnits);
		output.writeMutual(msg.getSkills);
		output.writeJavaUTF(msg.name);
		output.writeJavaUTF(msg.desc);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.template.SkillTemplate
//	----------------------------------------------------------------------------------------------------
	private function r_SkillTemplate_81(msg : com.fc.castle.data.template.SkillTemplate, input : NetDataInput) : void {
		msg.type = input.readInt();
		msg.attack = input.readInt();
		msg.costMP = input.readInt();
		msg.costHP = input.readInt();
		msg.coolDown = input.readInt();
		msg.spriteBehavior = input.readJavaUTF();
		msg.spriteEffect = input.readJavaUTF();
		msg.icon = input.readJavaUTF();
		msg.range = input.readInt();
		msg.aoe = input.readInt();
		msg.targetType = input.readInt();
		msg.buffList = input.readIntArray();
		msg.specialEffects = input.readIntArray();
		msg.name = input.readJavaUTF();
		msg.desc = input.readJavaUTF();
	}
	private function w_SkillTemplate_81(msg : com.fc.castle.data.template.SkillTemplate, output : NetDataOutput) : void {
		output.writeInt(msg.type);
		output.writeInt(msg.attack);
		output.writeInt(msg.costMP);
		output.writeInt(msg.costHP);
		output.writeInt(msg.coolDown);
		output.writeJavaUTF(msg.spriteBehavior);
		output.writeJavaUTF(msg.spriteEffect);
		output.writeJavaUTF(msg.icon);
		output.writeInt(msg.range);
		output.writeInt(msg.aoe);
		output.writeInt(msg.targetType);
		output.writeIntArray(msg.buffList);
		output.writeIntArray(msg.specialEffects);
		output.writeJavaUTF(msg.name);
		output.writeJavaUTF(msg.desc);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.castle.data.template.UnitTemplate
//	----------------------------------------------------------------------------------------------------
	private function r_UnitTemplate_82(msg : com.fc.castle.data.template.UnitTemplate, input : NetDataInput) : void {
		msg.type = input.readInt();
		msg.csprite_id = input.readJavaUTF();
		msg.icon = input.readJavaUTF();
		msg.motionType = input.readInt();
		msg.fightType = input.readInt();
		msg.attack = input.readInt();
		msg.attackType = input.readInt();
		msg.attackCD = input.readInt();
		msg.attackRange = input.readInt();
		msg.attackEffect = input.readJavaUTF();
		msg.guardRange = input.readInt();
		msg.defense = input.readInt();
		msg.defenseType = input.readInt();
		msg.hp = input.readInt();
		msg.mp = input.readInt();
		msg.moveSpeed = input.readFloat();
		msg.trainingTime = input.readInt();
		msg.cost = input.readInt();
		msg.skills = input.readIntArray();
		msg.name = input.readJavaUTF();
		msg.description = input.readJavaUTF();
	}
	private function w_UnitTemplate_82(msg : com.fc.castle.data.template.UnitTemplate, output : NetDataOutput) : void {
		output.writeInt(msg.type);
		output.writeJavaUTF(msg.csprite_id);
		output.writeJavaUTF(msg.icon);
		output.writeInt(msg.motionType);
		output.writeInt(msg.fightType);
		output.writeInt(msg.attack);
		output.writeInt(msg.attackType);
		output.writeInt(msg.attackCD);
		output.writeInt(msg.attackRange);
		output.writeJavaUTF(msg.attackEffect);
		output.writeInt(msg.guardRange);
		output.writeInt(msg.defense);
		output.writeInt(msg.defenseType);
		output.writeInt(msg.hp);
		output.writeInt(msg.mp);
		output.writeFloat(msg.moveSpeed);
		output.writeInt(msg.trainingTime);
		output.writeInt(msg.cost);
		output.writeIntArray(msg.skills);
		output.writeJavaUTF(msg.name);
		output.writeJavaUTF(msg.description);
	}



	}

}