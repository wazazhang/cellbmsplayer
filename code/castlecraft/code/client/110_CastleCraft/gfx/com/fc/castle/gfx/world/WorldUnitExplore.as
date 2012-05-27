package com.fc.castle.gfx.world
{
	import com.cell.gameedit.object.worldset.SpriteObject;
	import com.cell.gfx.game.CSprite;
	import com.cell.gfx.game.CSpriteBuffer;
	import com.cell.ui.Anchor;
	import com.cell.ui.TextLable;
	import com.cell.ui.component.Alert;
	import com.cell.ui.component.Lable;
	import com.cell.util.Map;
	import com.cell.util.StringUtil;
	import com.fc.castle.data.ExploreData;
	import com.fc.castle.data.ExploreState;
	import com.fc.castle.data.message.Messages.GetEventTemplateRequest;
	import com.fc.castle.data.message.Messages.GetEventTemplateResponse;
	import com.fc.castle.data.message.Messages.GetExploreStateRequest;
	import com.fc.castle.data.message.Messages.GetExploreStateResponse;
	import com.fc.castle.data.template.EventTemplate;
	import com.fc.castle.gfx.LoadingManager;
	import com.fc.castle.net.client.CClientEvent;
	import com.fc.castle.screens.Screens;
	import com.fc.castle.ui.FormExploerData;
	import com.fc.castlecraft.LanguageManager;
	
	import flash.events.MouseEvent;
	import flash.text.TextField;
	import flash.text.TextFieldAutoSize;
	import flash.text.TextFieldType;
	
	public class WorldUnitExplore extends WorldUnit
	{
		private var _unitName:String;
		private var _properties:Map;
		private var _data:ExploreData;
//		private var event_temp:EventTemplate;
		private var last_time_sec:Number;
//		private var time_text:Lable;
//		private var es:ExploreState;
		private var headFormat:String;
		
		public function WorldUnitExplore(ws:WorldScene, wd:SpriteObject, csprite:CSpriteBuffer, ed:ExploreData)
		{
			super(ws, wd, csprite);
			
			
			this._unitName = wd.UnitName;
			this._properties = Map.readFromProperties(wd.Data);
			this._data = ed;
			this.headFormat = LanguageManager.getText("scene.exploreHead");
			
			setBottomText(ed.explore_name);
			
			this.last_time_sec = 0;
			if (ed.last_time != null) {
				this.last_time_sec = ed.last_time.getTime() / 1000;
			}
			updateState();
			
			addEventListener(MouseEvent.MOUSE_DOWN, downHandle);
			addEventListener(MouseEvent.CLICK,clickHandle);
			
		}
		
		public function  get unitName():String
		{
			return _unitName;
		}		
		
		private var mouse_down_x : Number;
		private var mouse_down_y : Number;
		
		private function downHandle(event:MouseEvent):void
		{
			mouse_down_x = event.stageX;
			mouse_down_y = event.stageY;
		}
		
		private function clickHandle(event:MouseEvent):void
		{
			if (Math.abs(mouse_down_x-event.stageX)<20 && Math.abs(mouse_down_y-event.stageY)<20){
				var getEventTemp:GetExploreStateRequest = new GetExploreStateRequest(
					Screens.client.getPlayerID(),
					_unitName, 
					scene.getPlayerID());
				Screens.client.sendRequest(getEventTemp, onGetTempSuccess, onGetTempFailed);
				LoadingManager.show(Screens.getRoot().getCurrentScreen());
			}
		}	
		
		private function onGetTempSuccess(event:CClientEvent):void
		{
			LoadingManager.close();
			var res:GetExploreStateResponse = (event.response as GetExploreStateResponse);
			if(res.result == GetEventTemplateResponse.RESULT_SUCCEED)
			{
				var formBattleEnter:FormExploerData = new FormExploerData(res, 
					_unitName, 
					getCSprite().copy(),
					scene.getPlayerID()
				);
				Screens.getRoot().getCurrentScreen().addChild(formBattleEnter);
				formBattleEnter.setCenter(Screens.getRoot().getCurrentScreen());
			}
			else
			{
				Alert.showAlertText("发生未知错误.");
			}
		}	
		
		private function onGetTempFailed(event:CClientEvent):void
		{
			LoadingManager.close();
			Alert.showAlertText("网络错误.");
		}
		
		private var timer:int = 0;
		
		private function updateState() : void
		{
			var curtime_sec : Number = new Date().getTime() / 1000;
			
			if (last_time_sec+_data.refreshTime>curtime_sec)
			{
				var lastName : String = _data.last_explorer == null ? "" : _data.last_explorer.playerName;
				var tmp:int = (int)(last_time_sec+_data.refreshTime-curtime_sec);
				setHeadText(StringUtil.format(headFormat, formatHHMMSS(tmp), lastName));
				this.mouseEnabled = false;
				this.head_text.visible = true;
			}
			else
			{
				this.mouseEnabled = true;
				this.head_text.visible = false;
			}
		}
		
		protected override function onUpdate():void
		{
			if (timer%30==0)
			{
				updateState();
			}
			timer++;
		}
		
		private function formatHHMMSS(time:int):String
		{
			var str:String = new String();
			var ss:int = time%60;
			time = time/60;
			var mm:int = time%60;
			time = time/60;
			var hh:int = time;
			if (hh<100){
				var strh:String = ""+(100+hh);
				str += strh.substr(1)+":";
			}
			var strm:String = ""+(100+mm);
			str += strm.substr(1)+":";
			var strs:String = ""+(100+ss);
			str += strs.substr(1);
			return str;
		}
	}
}