package com.fc.castle.gfx.battle.ui
{
	import com.cell.gfx.CellSprite;
	import com.cell.util.CMath;
	import com.fc.castle.gfx.battle.BattleObject;
	import com.fc.castle.gfx.battle.BattleWorld;
	import com.fc.castle.gfx.battle.StageBattle;
	
	import flash.events.Event;
	import flash.utils.getTimer;

	public class BuildHilightGrid extends CellSprite
	{		
		private var timer : int = 0;
		
		public function BuildHilightGrid(battle:StageBattle)
		{
			this.graphics.beginFill(0xffffff, 1);
			this.graphics.drawRect(1, 1, 
				battle.getWorld().getCellW()-2, 
				battle.getWorld().getCellH()-2);
			this.graphics.endFill();
			this.alpha = 0;
		}

		override protected function update(e:Event):void
		{
			this.alpha = Math.abs(Math.sin(CMath.toDegree(timer*10))) * 0.5;
			
			timer++;
		}
		
		
	}
}