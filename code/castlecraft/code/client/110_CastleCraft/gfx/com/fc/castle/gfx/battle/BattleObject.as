package com.fc.castle.gfx.battle
{
	import com.cell.gfx.game.worldcraft.CellUnit;

	public class BattleObject extends CellUnit
	{
		public var priority : int = 0;
		
		public var battle : StageBattle;
		
		public function BattleObject(battle:StageBattle)
		{
			this.battle = battle;
		}
	}
}