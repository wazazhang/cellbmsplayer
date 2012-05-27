package com.fc.castle.gfx.tutorial
{
	import com.cell.ui.Anchor;
	import com.cell.ui.ImageButton;
	import com.cell.ui.component.Alert;
	import com.cell.util.ImageUtil;
	import com.cell.util.Map;
	import com.fc.castle.data.SkillData;
	import com.fc.castle.data.SkillDatas;
	import com.fc.castle.data.SoldierData;
	import com.fc.castle.data.SoldierDatas;
	import com.fc.castle.data.message.Messages.BattleStartRequest;
	import com.fc.castle.data.message.Messages.BattleStartResponse;
	import com.fc.castle.gfx.battle.ui.HelpArrow;
	import com.fc.castle.gfx.world.StageWorld;
	import com.fc.castle.gfx.world.WorldUnitExplore;
	import com.fc.castle.net.client.CClientEvent;
	import com.fc.castle.res.Res;
	import com.fc.castle.screens.ScreenMainMenu;
	import com.fc.castle.screens.Screens;
	import com.fc.castle.ui.FormExploerData;
	import com.fc.castle.ui.FormReadyForBattle;
	import com.fc.castle.ui.card.Card;
	import com.fc.castle.ui.card.CardSlot;
	
	import flash.display.Bitmap;
	import flash.display.DisplayObject;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Point;

	public class Tutorial_01 extends Tutorial
	{
		private var arrow:HelpArrow = new HelpArrow();
		
		private var btn:ImageButton;
		private var explore:WorldUnitExplore;
		private var stage_world:StageWorld;
		
		public function Tutorial_01()
		{
			super("guide_4.properties");
			ID = Tutorial.EXPLORE_TUTORIAL;
		}
		
		override protected function onStart():void
		{
			super.onStart();
			curDialog = new NpcDialog(Map.readFromProperties(loader.data));
			curDialog.setDialogY(180);
			curDialog.lock = true;
			addChild(curDialog);
			curDialog.next();
			this.addEventListener(MouseEvent.CLICK,clickHandle);
			
			stage_world = (parent as ScreenMainMenu).getStageWorld();
			btn = ImageButton.createImageButtonScaleBitmap(
				ImageUtil.combineImageClass(Anchor.ANCHOR_CENTER, Res.ui_hl).bitmapData,1.1);
		}
		
		private function clickHandle(event:MouseEvent):void
		{
			if(curDialog.isEnd)
			{
				this.removeEventListener(MouseEvent.CLICK,clickHandle);
				complate();
			}
			else
			{
				curDialog.next();
				if (curDialog.getStep() == 4){ //点击金堆
					this.removeEventListener(MouseEvent.CLICK,clickHandle);
					curDialog.lock = true;
					this.addChild(arrow);
					arrow.x = 188+(64-arrow.width)*0.5;
					arrow.y = 344-10;
					this.addChild(btn);
					btn.x = 188;
					btn.y = 344;
					btn.addEventListener(MouseEvent.MOUSE_DOWN, downExplore);
					btn.addEventListener(MouseEvent.CLICK, clickExplore);
					explore = stage_world.getWorld().getWorldUnit("S011_explore_random") as WorldUnitExplore;
				}
			}
		}
		
		private function downExplore(event:MouseEvent):void
		{
			explore.dispatchEvent(event);
		}
		
		private function clickExplore(event:MouseEvent):void
		{
			btn.removeEventListener(MouseEvent.MOUSE_DOWN, downExplore);
			btn.removeEventListener(MouseEvent.CLICK, clickExplore);
//			this.removeChild(btn);
			arrow.visible = false;
			explore.dispatchEvent(event);
			curDialog.next();
//			btn.width = 60;
//			btn.height = 60;
//			btn.x = 662;
//			btn.y = 401;
//			btn.addEventListener(MouseEvent.CLICK, clickOK_1);
		}
		
		private var form_explorer: FormExploerData;
		
		private var form_ready:FormReadyForBattle;
		
		private var step6inited:Boolean = false;
		
		private var cards:Array = new Array();
		
		private var card_index:int = 0;
		
		private var cur_card:Card;
		
		override protected function onUpdate():void
		{
			if (curDialog.getStep()==5 && form_explorer==null){
				var ch:DisplayObject = parent.getChildAt(parent.getChildIndex(this)-1);
				if (ch != null && ch is FormExploerData){
					form_explorer = ch as FormExploerData;
				}
				
				if (form_explorer!=null){
					btn.width = 53;
					btn.height = 53;
					btn.x = 662;
					btn.y = 401;
					arrow.x = btn.x+(64-arrow.width)*0.5;
					arrow.y = btn.y-10;
					arrow.visible = true;
					btn.addEventListener(MouseEvent.CLICK, clickOK_1);
				}
			}else if (curDialog.getStep() == 6){ 
				if (form_ready==null){
					var ch2:DisplayObject = parent.getChildAt(parent.getChildIndex(this)-1);
					if (ch2 != null && ch2 is FormReadyForBattle){
						form_ready = ch2 as FormReadyForBattle;
					}
				}else if (step6inited==false){
					var sa1:Array = form_ready.getAllSoldiers();
					var sa2:Array = form_ready.getAllSkills();
					if (sa1 == null || sa2 == null){
						return;
					}
					for each(var cs1:Card in sa1){
						cards.push(cs1);
					}
					for each(var cs2:Card in sa2){
						cards.push(cs2);
					}
					cur_card = cards[card_index];
					card_index++;
//					btn.width = 53;
//					btn.height = 53;
					
					var point1:Point = cur_card.localToGlobal(new Point())
					btn.x = point1.x;
					btn.y = point1.y;
					arrow.x = btn.x+(64-arrow.width)*0.5;
					arrow.y = btn.y-10;
					btn.addEventListener(MouseEvent.CLICK, selectBuilding1);
					step6inited = true;
				}
			}
		}
		
		private function clickOK_1(event:MouseEvent):void
		{
			btn.removeEventListener(MouseEvent.CLICK, clickOK_1);
			form_explorer.btnOK.dispatchEvent(event);
			curDialog.next();
			
		}
		
		private function selectBuilding1(event:MouseEvent):void
		{
//			btn.removeEventListener(MouseEvent.CLICK, selectBuilding1);
			cur_card.dispatchEvent(event);
			if (card_index>=cards.length){
				btn.removeEventListener(MouseEvent.CLICK, selectBuilding1);
				cur_card = null;
				btn.x = 735;
				btn.y = 416;
				arrow.x = btn.x+(64-arrow.width)*0.5;
				arrow.y = btn.y-10;
				btn.addEventListener(MouseEvent.CLICK, clickOK_2);
			}else{
				cur_card = cards[card_index];
				card_index++;
				var point1:Point = cur_card.localToGlobal(new Point())
				btn.x = point1.x;
				btn.y = point1.y;
				arrow.x = btn.x+(64-arrow.width)*0.5;
				arrow.y = btn.y-10;
			}
			
		}
		
		private function clickOK_2(event:MouseEvent):void
		{
			btn.removeEventListener(MouseEvent.CLICK, clickOK_2);
			arrow.visible = false;
			btn.visible = false;
			complate();
			form_ready.btnOK.dispatchEvent(event);
		}
		
		override public function complate():void
		{
			super.complate();
//			var startRequest:BattleStartRequest = new BattleStartRequest(
//				Screens.client.getPlayer().player_id,
//				BattleStartRequest.TYPE_GUIDE);
//			Screens.client.sendRequest(startRequest,onStartResponse,onError);
		}
		
//		private function onStartResponse(e:CClientEvent) : void
//		{
//			var res : BattleStartResponse = e.response as BattleStartResponse;
//			if (res.result == BattleStartResponse.RESULT_SUCCEED) {
//				Screens.getRoot().changeScreen(Screens.SCREEN_BATTLE_LOADING, [res]);
//			} else {
//				Alert.showAlertText("无法开始战斗！", "错误", false, true);
//				//addClickEvent();
//			}
//		}
		
//		private function onError(e:CClientEvent) : void
//		{
//			Alert.showAlertText("无法开始战斗！", "网络错误", false, true);
//			//addClickEvent();
//		}
		
		private function addClickEvent():void
		{
			this.addEventListener(MouseEvent.CLICK,clickHandle)
		}
//		private function resend():void
//		{
//			this.removeEventListener(MouseEvent.CLICK,clickHandle);
//			var startRequest:BattleStartRequest = new BattleStartRequest(
//				Screens.client.getPlayer().player_id,
//				BattleStartRequest.TYPE_GUIDE);
//			Screens.client.sendRequest(startRequest,onStartResponse,onError)
//		}
	}
}