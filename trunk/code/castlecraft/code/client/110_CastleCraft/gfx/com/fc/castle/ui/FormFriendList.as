package com.fc.castle.ui
{
	import com.cell.ui.Anchor;
	import com.cell.ui.ImageButton;
	import com.cell.ui.component.Alert;
	import com.cell.ui.component.UIComponent;
	import com.fc.castle.data.PlayerFriend;
	import com.fc.castle.data.message.Messages.GetRandomOnlinePlayersRequest;
	import com.fc.castle.data.message.Messages.GetRandomOnlinePlayersResponse;
	import com.fc.castle.gfx.world.StageWorld;
	import com.fc.castle.net.client.CClientEvent;
	import com.fc.castle.res.CLayoutManager;
	import com.fc.castle.screens.Screens;
	import com.fc.castlecraft.LanguageManager;
	
	import flash.events.Event;
	import flash.events.MouseEvent;
	import com.fc.castle.ui.relation.FriendCard;

	public class FormFriendList extends BaseForm
	{
		private var world			: StageWorld;
		
		private var _ok 			: ImageButton;
		
		public function FormFriendList(world:StageWorld)
		{
			super(Screens.WIDTH, 160);
			
			this.world = world;
			
			this.mouseChildren = true;
			this.mouseEnabled  = true;
			
			this.x = 0;
			this.y = Screens.HEIGHT - getBG().height;
			
			setTitle(LanguageManager.getText("ui.friendlist.title"));
			
			Screens.client.sendRequest(new GetRandomOnlinePlayersRequest(), onRequest, onError);
		}
		
		private function onRequest(e:CClientEvent) : void
		{
			var response : GetRandomOnlinePlayersResponse =  e.response as GetRandomOnlinePlayersResponse;
			
			var sx : int = 20;
			var sy : int = 20;
			var i : int = 0;
			for each (var o:PlayerFriend in response.friends) {
				if (o.playerID != Screens.client.getPlayerID()) {
					var pp : FriendCard = new FriendCard(o);
					pp.addEventListener(MouseEvent.CLICK, onClickPlayerFriend);
					pp.x = sx;
					pp.y = sy;
					this.addChild(pp);
					sx += pp.width + 2;
					i++;
				}
				
			}
			
			_ok = CLayoutManager.alertCreateOK();
			_ok.anchor = Anchor.ANCHOR_HCENTER | Anchor.ANCHOR_VCENTER;
			_ok.addEventListener(MouseEvent.CLICK, onOK);
			_ok.x = width -  _ok.width/2  - 20;
			_ok.y = height - _ok.height/2 - 20;
			this.addChild(_ok);
		}
		
		private function onError(e:Event) : void
		{
			Alert.showAlertText("无法验证您的帐号！", "错误", false, true);
		}
		
		private function onOK(e:Event) : void
		{
			this.removeFromParent();
		}
		
		private function onClickPlayerFriend(e:Event) : void
		{
			var pp : FriendCard = e.currentTarget as FriendCard;
			
			world.requestExploreDataList(pp.getData().playerID);
		}
		
	}
}