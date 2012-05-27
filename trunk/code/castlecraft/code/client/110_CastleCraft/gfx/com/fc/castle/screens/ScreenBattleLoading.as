package com.fc.castle.screens
{
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gameedit.ResourceLoader;
	import com.cell.gameedit.ResourceLoaderQueue;
	import com.cell.gfx.CellScreen;
	import com.cell.gfx.CellScreenManager;
	import com.cell.ui.SimpleProgress;
	import com.cell.ui.component.Alert;
	import com.cell.util.Arrays;
	import com.cell.util.Map;
	import com.fc.castle.data.message.Messages.BattleStartResponse;
	import com.fc.castle.data.template.UnitTemplate;
	import com.fc.castle.formual.Formual;
	import com.fc.castle.gfx.battle.res.CBattleResource;
	import com.fc.castle.net.client.DataManager;
	import com.fc.castle.res.CResourceManager;
	import com.fc.castlecraft.LanguageManager;
	
	import flash.events.Event;
	import flash.events.MouseEvent;

	public class ScreenBattleLoading extends CellScreen
	{		
		private var data : BattleStartResponse;
		
		private var battleRes : CBattleResource;
		
		private var progress : SimpleProgress;
		
		public function ScreenBattleLoading()
		{
		}
		
		override public function added(root:CellScreenManager, args:Array):void
		{
			this.data = args[0];
			
			Formual.init(data.formual_map);
			
			battleRes = new CBattleResource(data);
			battleRes.queue.addEventListener(ResourceEvent.LOADED, res_loaded);
			battleRes.queue.addEventListener(ResourceEvent.ERROR, res_error);
			
			DataManager.getUnitTemplates(
				Arrays.arrayLink(
					data.forceA.soldiers.datas, 
					data.forceB.soldiers.datas
				), unit_complete, data_error);
			
		
			progress = new SimpleProgress(Screens.WIDTH-32, 22);
			progress.x = 16;
			progress.y = Screens.HEIGHT - 60;
			addChild(progress);
		}
		
		override public function removed(root:CellScreenManager):void
		{

		}
		
		
		override public function update():void
		{
			if (battleRes != null) {
				progress.percent = battleRes.queue.percent;
			}
		}

		
//		-------------------------------------------------------------------------------------
		
		private function unit_complete(map:Map) : void
		{
			battleRes.pushUnits(map);
			
			DataManager.getSkillTemplates(
				Arrays.arrayLink(
					data.forceA.skills.datas, 
					data.forceB.skills.datas
				), skill_complete, data_error);
		}
		
		private function skill_complete(map:Map) : void
		{
			battleRes.pushSkills(map);
			
			battleRes.queue.load();
		}
		
		private function data_error() : void
		{
			Alert.showAlertText("网络超时","错误").btnCancel.addEventListener(
				MouseEvent.CLICK, 
				onErrorClick
			);
		}
		
		private function res_loaded(e:ResourceEvent) : void
		{
			if (battleRes.queue.isDone()) {
				Screens.getRoot().changeScreen(Screens.SCREEN_BATTLE, [data, battleRes]);
				trace("percent = " + battleRes.queue.percent);
			}
		}
		
		private function res_error(e:ResourceEvent) : void
		{
			Alert.showAlertText("资源加载失败","错误").btnCancel.addEventListener(
				MouseEvent.CLICK, 
				onErrorClick
			);
		}
		
		private function onErrorClick(e:Event) : void 
		{
			Screens.getRoot().changeScreen(Screens.SCREEN_MAIN_MENU);
		}
		
	}
}