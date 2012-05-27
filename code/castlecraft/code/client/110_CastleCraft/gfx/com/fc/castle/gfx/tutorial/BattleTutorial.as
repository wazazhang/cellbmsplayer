package com.fc.castle.gfx.tutorial
{
	import com.fc.castle.gfx.battle.StageBattle;
	import com.fc.castle.gfx.battle.ai.ForcePlayer;
	import com.fc.castle.gfx.battle.ai.ForcePlayerComputer;
	import com.fc.castle.gfx.battle.ui.BtnSkillLaunch;
	import com.fc.castle.gfx.battle.ui.BtnSoldierBuild;
	import com.fc.castle.gfx.battle.ui.ForcePlayerHuman;

	public class BattleTutorial extends Tutorial
	{
		
		protected var battle:StageBattle;
		
		protected var btnSoldiers:Array = new Array();
		protected var btnSkills:Array = new Array();
		protected var humanForce:ForcePlayerHuman;
		protected var enemyForce:ForcePlayerComputer;
		
		public function BattleTutorial(battle:StageBattle, file:String)
		{
			super(file);
			this.battle = battle;
		}
		
		override protected function onStart():void
		{
			humanForce = battle.getWorld().getForcePlayer(ForcePlayer.FORCE_A) as ForcePlayerHuman;
			enemyForce = battle.getWorld().getForcePlayer(ForcePlayer.FORCE_B) as ForcePlayerComputer;
		}
		
		public function addBtnSoldierBuild(bt:BtnSoldierBuild):void
		{
			btnSoldiers.push(bt);
		}
		
		public function addBtnSkill(bt:BtnSkillLaunch):void
		{
			btnSkills.push(bt);	
		}
	}
}