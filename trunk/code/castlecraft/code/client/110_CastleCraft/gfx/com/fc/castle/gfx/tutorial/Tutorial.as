package com.fc.castle.gfx.tutorial
{
	import com.cell.gfx.CellSprite;
	import com.cell.io.IOUtil;
	import com.cell.util.Map;
	import com.fc.castle.data.message.Messages.CommitGuideRequest;
	import com.fc.castle.gfx.battle.BattleBuilding;
	import com.fc.castle.gfx.battle.StageBattle;
	import com.fc.castle.screens.Screens;
	import com.fc.castlecraft.AutoLogin;
	
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.net.URLLoader;
	import flash.utils.ByteArray;
	
	public class Tutorial extends CellSprite
	{
		
		public static const BEGIN_TUTORIAL:int = 0;
		public static const BATTLE_FORCE_TUTORIAL:int = 1;
		public static const BATTLE_FORCE_TUTORIAL2:int = 2;
		public static const BATTLE_FORCE_TUTORIAL3:int = 3;
		public static const EXPLORE_TUTORIAL:int = 4;
		
		public var ID:int;
		
		protected var loader : URLLoader;
		protected var isStarted:Boolean = false;
		protected var isEnded:Boolean = false;
		protected var curDialog:NpcDialog;
		
		public function Tutorial(file:String)
		{
			super();
			
			var url : String = getGuideURL(file);
			trace("loading guide file : " + url);
			loader = IOUtil.loadURL(url, loaded);
		}
		
		private function loaded(e:Event) : void
		{
			if(!isStarted) {
				isStarted = true;
				onStart();
			}
		}
		
		
		final override protected function update(e:Event) : void
		{				
			if(!isStarted)
			{
			}
			else if(!isEnded)
			{
				onUpdate();
				if (parent.getChildIndex(this)!=parent.numChildren-1){
					parent.swapChildren(this, parent.getChildAt(parent.numChildren-1));
				}
			}
		}
		
		protected function onUpdate():void
		{
			
		}
		
		protected function onStart():void
		{
			
		}
	
		public function complate():void
		{
			isEnded = true;
			parent.removeChild(this);
			
			
			Screens.client.getPlayerQuest().guide_steps = ID+1;
			Screens.client.sendRequest(new CommitGuideRequest(Screens.client.getPlayer().player_id,ID+1));
		}
		
		
		public static function getBattleTorial(id:int,sb:StageBattle):BattleTutorial
		{
			switch(id)
			{
				case BATTLE_FORCE_TUTORIAL: return new BattleTutorial_00(sb);	
				case BATTLE_FORCE_TUTORIAL2: return new BattleTutorial_01(sb);
				case BATTLE_FORCE_TUTORIAL3: return new BattleTutorial_02(sb);
				default: return null
			}
		}
		public static function getNormalTorial(id:int):Tutorial
		{
			switch(id)
			{
				case BEGIN_TUTORIAL: return new Tutorial_00();	
				case EXPLORE_TUTORIAL: return new Tutorial_01();
				default: return null
			}
		}
		
		
//		protected function getMapByByteArray(byte:ByteArray):Map
//		{
//			var str:String =   byte.readUTFBytes(byte.bytesAvailable);  
//			var map:Map =  Map.readFromProperties(str);
//			return map;
//		}
		
		protected function getGuideURL(file:String) : String
		{
			return "location/" + AutoLogin.LOCATION + "/guide/"+file;
		}
	}
}