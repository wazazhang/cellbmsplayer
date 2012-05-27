 package com.fc.castle.gfx.battle.res
{
	import com.cell.gameedit.object.SpriteSet;
	import com.cell.util.Map;
	
	import flash.display.BlendMode;

	public class EffectInfo
	{
		/**普通移动*/
		public static const ACTION_MOVE 	: int = 0;
		/**抛物线移动*/
		public static const ACTION_PARA 	: int = 1;
		/**落体*/
		public static const ACTION_FALL 	: int = 2;
		
		public static const MAP_ACTION 		: Map = Map.initFromArray([
			["move", 	ACTION_MOVE],
			["para", 	ACTION_PARA],
			["fall", 	ACTION_FALL]
		]);
		
		/**朝向无*/
		public static const DIRECT_NA 		: int = 0;
		/**朝向目标单位*/
		public static const DIRECT_TARGET 	: int = 1;
		/**自转*/
		public static const DIRECT_ROT	 	: int = 2;
		
		public static const MAP_DIRECT 		: Map = Map.initFromArray([
			["na", 		DIRECT_NA],
			["target", 	DIRECT_TARGET],
			["rotate", 	DIRECT_ROT]
		]);
		
		
		
		
//		--------------------------------------------------------------------------------
		
		
		
//		--------------------------------------------------------------------------------
		
		/**MISSLE移动方式*/
		public var action : int = 0;
		
		/**MISSLE移动时如何调整方向*/
		public var direct : int = 0;
		
		/**MISSLE移动速度*/
		public var speed  : Number = 10;
		
		/**混合模式*/
		public var blend  : String;
		
		/**"释放延迟", "释放后延迟一段时间开始，一般配合spriteEffect使用，比如设置炸弹点，一段时间后落下！"*/
		public var hangOver : int;
		
		public function EffectInfo(set:SpriteSet)
		{
			var s_map : Map = Map.readFromProperties(set.AppendData);
			
			this.blend		= s_map.get("Blend");

			this.action		= getEnumValue(s_map, MAP_ACTION, "Action", ACTION_MOVE);
			
			this.direct		= getEnumValue(s_map, MAP_DIRECT, "Direct", DIRECT_NA);
			
			this.speed		= s_map.getNumber("Speed", 30);

			this.hangOver	= s_map.getNumber("HangOver", 0);
		}
		
		
		private static function getEnumValue(src_map:Map, enum_map:Map, key:String, defaultValue:int) : int
		{
			var s_value : String = src_map.get(key);
			if (s_value != null) {
				s_value = s_value.toLocaleLowerCase();
				if (enum_map.contains(s_value)) {
					return enum_map.get(s_value);
				}
			}
			return defaultValue;
		}
	}
}