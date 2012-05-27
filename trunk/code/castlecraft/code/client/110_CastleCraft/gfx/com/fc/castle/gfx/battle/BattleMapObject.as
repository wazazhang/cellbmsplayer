package com.fc.castle.gfx.battle
{
	import com.cell.gameedit.object.worldset.SpriteObject;
	import com.cell.gfx.game.CSpriteBuffer;
	import com.cell.util.Map;

	public class BattleMapObject extends BattleUnit
	{
		public static const TYPE_HOLY 		: String = "Holy";
		
		public static const REGION_BASE 	: String = "BaseRegion";
		
		private var properties	: Map;
		private var data		: SpriteObject;
		
		private var type		: String;
		private var force		: int;
		
		public function BattleMapObject(battle:StageBattle, data:SpriteObject, properties:Map)
		{
			super(battle, battle.getRes().res_map.getSpriteBuffer(data.SprID));
			this.battle = battle;
			this.data = data;
			this.properties = properties;
			
			this.type = properties.get("type");
			this.force = int(properties.get("force"));
			this.x = data.X;
			this.y = data.Y;
			this.setCurrentAnimate(data.Anim);
			this.setCurrentFrame(0);
		}
		
		public function getType() : String
		{
			return type;
		}
		
		public function getForce() : int
		{
			return force;
		}
		
		public static function craeteMapObject(battle:StageBattle, data:SpriteObject) : BattleMapObject
		{
			var properties : Map = Map.readFromProperties(data.Data);
			if (properties.type == TYPE_HOLY) {
				return new BattleMapHoly(battle, data, properties);
			} else {
				return new BattleMapObject(battle, data, properties);
			}
		}
	}
}