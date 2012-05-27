package com.fc.castle.gfx.battle
{
	import com.cell.gfx.game.CMap;
	import com.cell.gfx.game.CMapView;

	public class BattleMap extends CMapView
	{
		public var priority 	: int = 0;
		
		public function BattleMap(map:CMap, viewWidth:int, viewHeight:int)
		{
			super(map, viewWidth, viewHeight);
			this.priority = BattleWorld.LAYER_MAP;
		}
		
	}
}