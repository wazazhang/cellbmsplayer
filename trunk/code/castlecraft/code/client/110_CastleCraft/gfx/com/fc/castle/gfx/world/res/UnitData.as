package com.fc.castle.gfx.world.res
{
	import com.cell.gameedit.object.SpriteSet;
	import com.cell.gameedit.object.worldset.SpriteObject;
	import com.cell.util.Map;
	
	public class UnitData
	{
		/**普通移动*/
		public static const TYPE_CASTLE 	: int = 0;
		/**抛物线移动*/
		public static const TYPE_MONSTER 	: int = 1;
		/**落体*/
		public static const TYPE_VILLAGE 	: int = 2;
		
		private static const typeMap 		: Map = Map.initFromArray([
			["castle", 		TYPE_CASTLE],
			["monster", 	TYPE_MONSTER],
			["village", 	TYPE_VILLAGE]
		]);

		public var type : int = 0;
			
		
			
		public function UnitData(obj:SpriteObject)
		{
			var s_map : Map = Map.readFromProperties(obj.Data);

			this.type = getEnumValue(s_map, typeMap, "type", TYPE_MONSTER);
			
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