package com.fc.castle.screens
{
	import com.cell.gfx.CellScreen;
	import com.cell.gfx.CellScreenManager;
	import com.fc.castle.data.message.Messages.BattleStartResponse;
	import com.fc.castle.gfx.battle.StageBattle;
	import com.fc.castle.gfx.battle.ai.ForcePlayer;
	import com.fc.castle.gfx.battle.ai.ForcePlayerComputer;
	import com.fc.castle.gfx.battle.res.CBattleResource;
	import com.fc.castle.gfx.battle.ui.BattleHUD;
	import com.fc.castle.gfx.battle.ui.ForcePlayerHuman;
	import com.fc.castle.gfx.battle.ui.SystemMenu;

	public class ScreenBattle extends CellScreen
	{
		private var game_stage 		: StageBattle;
		
		private var system_menu		: SystemMenu;
		
		private var battle_hud		: BattleHUD;

		public function ScreenBattle()
		{
		}
		
		override public function added(root:CellScreenManager, args:Array):void
		{
			var battleData	: BattleStartResponse 	= args[0];
			var battleRes 	: CBattleResource 		= args[1];
			
			var forceA : ForcePlayer = new ForcePlayerHuman
				(battleData.forceA, ForcePlayer.FORCE_A);
			
			var forceB : ForcePlayer = new ForcePlayerComputer
				(battleData.forceB, ForcePlayer.FORCE_B);
			
			var players : Vector.<ForcePlayer> = new Vector.<ForcePlayer>();
			players.push(forceA);
			players.push(forceB);
			
			
			this.game_stage = new StageBattle(battleData, battleRes, players);
			addChild(game_stage);
			
			this.battle_hud = new BattleHUD(game_stage, forceA)
			addChild(battle_hud);
			
			this.system_menu = new SystemMenu(game_stage);
			this.system_menu.x = Screens.WIDTH - 32;
			this.system_menu.y = 32;
			addChild(system_menu);

		}
		
		override public function removed(root:CellScreenManager):void
		{
			
		}
		
		override public function update():void
		{
			game_stage.setPause(system_menu.isPause());
			
			var enable : Boolean = !game_stage.isPause() && !game_stage.isOver();
			battle_hud.mouseEnabled 	= enable;
			battle_hud.mouseChildren 	= enable;
			game_stage.mouseEnabled		= enable;
			game_stage.mouseChildren	= enable;
			
			system_menu.visible			= !game_stage.isOver()
		}
		
		
	}
}