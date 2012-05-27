package com.fc.castle.net.client
{	
	import com.cell.crypo.MD5;
	import com.cell.net.io.MutualMessage;
	import com.cell.util.Map;
	import com.fc.castle.data.Mail;
	import com.fc.castle.data.PlayerData;
	import com.fc.castle.data.PlayerQuestData;
	import com.fc.castle.data.message.MessageCodec;
	import com.fc.castle.data.message.Messages.BattleStartRequest;
	import com.fc.castle.data.message.Messages.BuyShopItemRequest;
	import com.fc.castle.data.message.Messages.BuyShopItemResponse;
	import com.fc.castle.data.message.Messages.CommitBattleResultResponse;
	import com.fc.castle.data.message.Messages.CommitGuideRequest;
	import com.fc.castle.data.message.Messages.GetExploreDataListResponse;
	import com.fc.castle.data.message.Messages.GetExploreStateResponse;
	import com.fc.castle.data.message.Messages.GetPlayerQuestRequest;
	import com.fc.castle.data.message.Messages.GetSkillTemplateRequest;
	import com.fc.castle.data.message.Messages.GetUnitTemplateRequest;
	import com.fc.castle.data.message.Messages.LoginRequest;
	import com.fc.castle.data.message.Messages.LoginResponse;
	import com.fc.castle.data.message.Messages.OrganizeDefenseRequest;
	import com.fc.castle.data.message.Messages.OrganizeDefenseResponse;
	import com.fc.castle.data.message.Messages.RefreshPlayerDataRequest;
	import com.fc.castle.data.message.Messages.RefreshPlayerDataResponse;
	import com.fc.castle.data.message.Messages.UseItemRequest;
	import com.fc.castle.data.message.Messages.UseItemResponse;
	import com.fc.castle.data.message.Request;
	import com.fc.castle.formual.Formual;
	import com.fc.castle.gfx.tutorial.GuideStepMap;
	
	import flash.events.EventDispatcher;
	
	[Event(name=CClientEvent.RESPONSE,	type="com.fc.castle.net.client.CClientEvent")]  
	public class CClient extends EventDispatcher
	{

		public static var message_factory : MessageCodec = new MessageCodec();
		
		public function CClient(){
//			gsm = new GuideStepMap();
		}
		
		private static var gsm:GuideStepMap;
		
		public static function getGuideStepMap():GuideStepMap
		{
			if (gsm==null){
				gsm = new GuideStepMap();
			}
			return gsm;
		}
//		-----------------------------------------------------------------------------------------------------------------------------
//		Input
//		-----------------------------------------------------------------------------------------------------------------------------
		
		
		protected var _login:LoginResponse;
		
		protected function syncResponse(e:CClientEvent) : void
		{
			var request:*  = e.request;
			var response:* = e.response;
			
			// 同步登录数据
			if (response is LoginResponse)
			{
				this._login = response as LoginResponse;
				if (_login.result != LoginResponse.RESULT_SUCCEED) {
					this._login = null;
				}
			}
			// 同步登玩家探索电数据
			else if (response is GetExploreDataListResponse)
			{
				// 此处查询可能不是自己，有可能是别的玩家
			}
			// 同步登玩家探索电数据
			else if (response is CommitBattleResultResponse)
			{
				var res:CommitBattleResultResponse = response as CommitBattleResultResponse;
				if (response.result == CommitBattleResultResponse.RESULT_SUCCEED) {
					if (res.explore_state != null) {
						getPlayer().exploreStates.put(res.explore_state.UnitName, res.explore_state);
					}
				}
			}
			// 同步玩家数据
			else if (response is RefreshPlayerDataResponse)
			{
				var rpdr:RefreshPlayerDataResponse = response as RefreshPlayerDataResponse;
				if (rpdr.result == RefreshPlayerDataResponse.RESULT_SUCCEED) {
					_login.player_data[0] = rpdr.player;
				}
			}
			// 同步道具使用数据
			else if (response is UseItemResponse) 
			{
				var uiq:UseItemRequest	= request;
				var uir:UseItemResponse = response as UseItemResponse;
				if (uir.result == UseItemResponse.RESULT_SUCCEED) {
					getPlayer().items.datas[uiq.indexOfItems] = uir.item_slot;
				}
			}
			// 同步购买后数据
			else if (response is BuyShopItemResponse)
			{
				var bsip : BuyShopItemResponse = response as BuyShopItemResponse;
				var bsiq : BuyShopItemRequest  = request as BuyShopItemRequest;
				if (bsip.result == BuyShopItemResponse.RESULT_SUCCEED) {
					getPlayer().items.datas[bsip.bag_index] = bsip.bag_item;
					getPlayer().coin = bsip.my_coin;
				}
			}
			// 同步布防数据
			else if (response is OrganizeDefenseResponse)
			{
				var odr : OrganizeDefenseResponse = response;
				var odq : OrganizeDefenseRequest  = request;
				if (odr.result == OrganizeDefenseResponse.RESULT_SUCCEED) {
					getPlayer().organizeDefense = odq;
				}
			}
			
			dispatchEvent(e);
		}
		
		public function getPlayer() : PlayerData
		{
			return _login.player_data[0] as PlayerData;
		}
		
		public function getPlayerID() : int
		{
			return (_login.player_data[0] as PlayerData).player_id;
		}
		
		public function getPlayerQuest(): PlayerQuestData
		{
			if (_login.player_quests.length == 0) {
				_login.player_quests = new Array();
				var pqd:PlayerQuestData = new PlayerQuestData(getPlayer().player_id);
				_login.player_quests.push(pqd);
			}
			return _login.player_quests[0] as PlayerQuestData;
		}
//
//		public function getExploreDataList() : Map
//		{
//			return _explore_datas.explores;
//		}
//		
		public function refreshPlayerData(response:Function=null, error:Function=null):void
		{
			send(new RefreshPlayerDataRequest(getPlayerID()), response, error);
		}
		
		
		
//		-----------------------------------------------------------------------------------------------------------------------------
//		output
//		-----------------------------------------------------------------------------------------------------------------------------

		/** abstract */
		protected function send(req:MutualMessage, complete:Function, error:Function) : void{}
		
		
		
		public function sendLogin(account_id :  String,
									sign :  String,
									create :  Boolean, 
									listener:Function=null, error:Function=null) : void
		{
			var r : LoginRequest = new LoginRequest(0, account_id, sign, create, account_id);
			r.sign = MD5.hash(r.sign);;
			send(r, listener, error);
		}
		
		public function sendRequest(r:Request, listener:Function=null, error:Function=null) : void
		{
			send(r, listener, error);
		}
		
	}
}