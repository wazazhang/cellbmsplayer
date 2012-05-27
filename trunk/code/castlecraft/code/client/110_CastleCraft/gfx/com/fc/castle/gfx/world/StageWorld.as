package com.fc.castle.gfx.world
{
	import com.cell.gfx.CellSprite;
	import com.cell.gfx.game.worldcraft.CellMapWorld;
	import com.cell.gfx.rpg.intention.TouchCameraMotion;
	import com.cell.ui.component.Alert;
	import com.cell.ui.component.TextButton;
	import com.cell.util.StringUtil;
	import com.fc.castle.data.message.Messages.BattleStartRequest;
	import com.fc.castle.data.message.Messages.GetExploreDataListRequest;
	import com.fc.castle.data.message.Messages.GetExploreDataListResponse;
	import com.fc.castle.gfx.LoadingManager;
	import com.fc.castle.net.client.CClientEvent;
	import com.fc.castle.res.CResourceManager;
	import com.fc.castle.screens.Screens;
	import com.fc.castle.ui.FormReadyForBattle;
	
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import flash.geom.Vector3D;
	
	
	/**
	 * 可能是自己也可能是别的玩家
	 */
	public class StageWorld extends CellSprite
	{
		private var world : WorldScene;
		
		private var loading : Boolean = false;
		
		private var btn_return_owen		: TextButton;
		
		private var btn_attack			: TextButton;
		
		private var camera_motion		: TouchCameraMotion;
		
		private var is_inside : Boolean = false; 
		
		private var exploreDataListResponse:GetExploreDataListResponse;
		

		public function StageWorld()
		{
			camera_motion = new TouchCameraMotion();
		}
		
		override protected function added(e:Event):void
		{
			
			addEventListener(MouseEvent.MOUSE_DOWN,	onCameraStart);
			addEventListener(MouseEvent.MOUSE_UP, 	onCameraOver);
			addEventListener(MouseEvent.MOUSE_OUT,	onCameraOut);
			addEventListener(MouseEvent.MOUSE_MOVE,	onCameraMove);
			
			requestExploreDataList(Screens.client.getPlayerID());
			
			
			btn_return_owen = new TextButton(
				"返回", 200, 30);
			btn_return_owen.visible = false;
			btn_return_owen.x = 0;
			btn_return_owen.y = 0;
			btn_return_owen.addEventListener(MouseEvent.CLICK, onClickReturnOwen);
			this.addChild(btn_return_owen);
			
			
			btn_attack = new TextButton(
				"进攻", 200, 30);
			btn_attack.visible = false;
			btn_attack.x = 0;
			btn_attack.y = 30;
			btn_attack.addEventListener(MouseEvent.CLICK, onClickAttack);
			this.addChild(btn_attack);
		}
		
		
		
		override protected function removed(e:Event):void
		{
			
		}
		
		override protected function update(e:Event):void
		{
			if (world != null) 
			{
				// update
				camera_motion.updateCamera(this, world.getCamera());
				
				world.update();
				world.render();
			}
		}
		
		public function requestExploreDataList(targetPlayerID:int) : void
		{
			if (this.loading) {
				return;
			}
			this.loading = true;
			LoadingManager.show(Screens.getRoot());
			if (world != null) {
				removeChild(world);
				world = null;
			}
			Screens.client.sendRequest(new GetExploreDataListRequest(Screens.client.getPlayerID(), targetPlayerID),
				getExploreSuccess,
				getExploreFailed);
		}
		
		
		//		--------------------------------------------------------------------------------------------------
		//		
		//		--------------------------------------------------------------------------------------------------
		
		protected function getExploreSuccess(e:CClientEvent):void
		{
			this.loading = false;
			
			exploreDataListResponse = (e.response as GetExploreDataListResponse);
			
			if(exploreDataListResponse.result == GetExploreDataListResponse.RESULT_SUCCEED)
			{
				var wd : WorldScene = new WorldScene(
					Screens.WIDTH, 
					Screens.HEIGHT, 
					CResourceManager.getOwnerScene(), 
					exploreDataListResponse, is_inside);
				addChildAt(wd, 0);
				this.world = wd;
				
				if (exploreDataListResponse.targetPlayer.playerID != Screens.client.getPlayerID()) {
					this.btn_return_owen.visible = true;
					this.btn_attack.visible=true;
				} else {
					this.btn_return_owen.visible = false;
					this.btn_attack.visible=false;
				}
			}
			else
			{
				Alert.showAlertText("获取探索点失败!");
			}	
			LoadingManager.close();
		}
		
		protected function getExploreFailed(e:CClientEvent):void
		{
			this.loading = false;
			
			Alert.showAlertText("网络连接失败");
			LoadingManager.close();
		}
		
		public function getWorld():WorldScene
		{
			return world;
		}
		
//		--------------------------------------------------------------------------------------------------
//		摄像机移动逻辑
//		--------------------------------------------------------------------------------------------------
		
		private function onCameraStart(e:MouseEvent) : void
		{
			if (world != null) 
			{
				camera_motion.onDragStart(this, world.getCamera());
			}
		}
		
		private function onCameraMove(e:MouseEvent) : void
		{
			if (world != null) 
			{
				camera_motion.onDragMove(this, world.getCamera());
			}
		}	
		
		private function onCameraOver(e:MouseEvent) : void
		{
			if (world != null) 
			{
				camera_motion.onDragOver(this, world.getCamera());
			}
		}
		
		private function onCameraOut(e:MouseEvent) : void
		{
			if (world != null) 
			{
//				camera_motion.onDragOver(this, world.getCamera());
			}
		}
		
		
//		--------------------------------------------------------------------------------------------------
		
		
		private function onClickReturnOwen(e:MouseEvent) : void
		{
			requestExploreDataList(Screens.client.getPlayerID());
		}
		
		private function onClickAttack(e:MouseEvent) : void
		{
			Screens.getRoot().getCurrentScreen().addChild(
				new FormReadyForBattle(BattleStartRequest.TYPE_PLAYER, null, world.getPlayerID())
			);
		}
		
// -------进城出城-----------------------------------------------------------------------------------		
			
		public function goHome():void
		{
			if (this.loading) {
				return;
			}
			is_inside = true;
			LoadingManager.show(Screens.getRoot());
			if (world != null) {
				removeChild(world);
				world = null;
			}
			
			var wd : WorldScene = new WorldScene(
				Screens.WIDTH, 
				Screens.HEIGHT, 
				CResourceManager.getOwnerScene(), 
				exploreDataListResponse, is_inside);
			addChildAt(wd, 0);
			this.world = wd;
			
			if (exploreDataListResponse.targetPlayer.playerID != Screens.client.getPlayerID()) {
				this.btn_return_owen.visible = true;
				this.btn_attack.visible=true;
			} else {
				this.btn_return_owen.visible = false;
				this.btn_attack.visible=false;
			}
			loading = false
			LoadingManager.close();
		}
		
		public function goWorld():void
		{
			if (this.loading) {
				return;
			}
			is_inside = false;
			LoadingManager.show(Screens.getRoot());
			if (world != null) {
				removeChild(world);
				world = null;
			}
			
			var wd : WorldScene = new WorldScene(
				Screens.WIDTH, 
				Screens.HEIGHT, 
				CResourceManager.getOwnerScene(), 
				exploreDataListResponse, is_inside);
			addChildAt(wd, 0);
			this.world = wd;
			
			if (exploreDataListResponse.targetPlayer.playerID != Screens.client.getPlayerID()) {
				this.btn_return_owen.visible = true;
				this.btn_attack.visible=true;
			} else {
				this.btn_return_owen.visible = false;
				this.btn_attack.visible=false;
			}
			loading = false
			LoadingManager.close();
		}
	}
}