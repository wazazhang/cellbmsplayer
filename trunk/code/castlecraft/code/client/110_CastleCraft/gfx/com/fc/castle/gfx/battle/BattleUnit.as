package com.fc.castle.gfx.battle
{
	import com.cell.gfx.game.CSpriteBuffer;
	import com.cell.gfx.game.worldcraft.CellCSprite;
	import com.cell.gfx.game.worldcraft.CellCSpriteBuffer;
	import com.cell.gfx.game.worldcraft.CellCSpriteGraphics;
	import com.fc.castle.formual.FormualObject;

	public class BattleUnit extends CellCSpriteBuffer implements FormualObject
	{
		public var priority : int = 0;
		
		public var battle : StageBattle;
		
		public function BattleUnit(battle:StageBattle, spr:CSpriteBuffer)
		{
			this.battle = battle;
			super(spr);
		}
		
		public function get active() : Boolean
		{
			return false;
		}
		
		public function addHP(v:Number) : Number
		{
			return 0;
		}
		
		public function getHP() : Number
		{
			return 0;
		}
		
		public function getMaxHP() : Number
		{
			return 0;
		}
		
	}
}