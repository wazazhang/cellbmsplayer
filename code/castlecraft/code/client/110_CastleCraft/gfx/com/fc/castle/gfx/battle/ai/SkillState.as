package com.fc.castle.gfx.battle.ai
{
	import com.fc.castle.data.SkillData;
	import com.fc.castle.data.template.SkillTemplate;
	import com.fc.castle.net.client.DataManager;

	public class SkillState
	{		
		private var player			: ForcePlayer;
		private var sd				: SkillData;
		private var tp				: SkillTemplate;

		private var start_time		: uint;
		
		public function SkillState(sd:SkillData, player:ForcePlayer)
		{			
			this.sd = sd;
			this.tp = DataManager.getSkillTemplate(sd.skillType);
			this.player = player;
			trace("玩家["+player.data.name+"]技能["+tp.name+"]初始化!");
		}
		
		public function isCD() : Boolean
		{
			if (start_time + tp.coolDown < player.battle.getTimer()) {
				return false;
			}
			return true;
		}
		
		public function getCDPercent() : Number
		{
			var pct : Number = (player.battle.getTimer() - start_time) / tp.coolDown;
			pct = Math.min(pct, 1);
			pct = Math.max(pct, 0);
			return pct;
		}
		
		public function startCD() : void 
		{
			start_time = player.battle.getTimer();
		}
	}
}