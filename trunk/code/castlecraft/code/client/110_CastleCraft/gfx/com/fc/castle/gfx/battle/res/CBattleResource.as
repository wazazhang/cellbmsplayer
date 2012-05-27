package com.fc.castle.gfx.battle.res
{
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gameedit.ResourceLoader;
	import com.cell.gameedit.ResourceLoaderQueue;
	import com.cell.gameedit.object.SpriteSet;
	import com.cell.gameedit.object.WorldSet;
	import com.cell.gameedit.object.worldset.MapObject;
	import com.cell.gfx.game.CMap;
	import com.cell.gfx.game.CSprite;
	import com.cell.gfx.game.CSpriteBuffer;
	import com.cell.ui.ImageNumber;
	import com.cell.util.Map;
	import com.cell.util.StringUtil;
	import com.fc.castle.data.message.Messages.BattleStartResponse;
	import com.fc.castle.data.template.SkillTemplate;
	import com.fc.castle.data.template.UnitTemplate;
	import com.fc.castle.res.CResourceManager;
	import com.fc.castle.res.Res;
//	import com.fc.castle.ui.ImageNumber2;
	
	import flash.display.BitmapData;

	public class CBattleResource
	{
		private var map_cpj		: String;
		private var map_cmapid	: String;
		
		public var queue : ResourceLoaderQueue = new ResourceLoaderQueue();
		
		public var res_map 		: ResourceLoader;
		public var res_actors 	: Map = new Map();
		public var res_effects 	: Map = new Map();
		
		public var map_effect_info 	: Map = new Map();
		public var map_unit_info 	: Map = new Map();
		
//		--------------------------------------------------------------------------------------------
		
		public var gfx_unit_shadow	: BitmapData = Res.createBitmap(Res.gfx_unit_shadow).bitmapData;
		public var gfx_transport	: BitmapData = Res.createBitmap(Res.gfx_transport).bitmapData;
		public var effect_transport	: BitmapData = Res.createBitmap(Res.effect_transport).bitmapData;
		public var effect_soulstar	: BitmapData = Res.createBitmap(Res.effect_soulstar).bitmapData;
		public var effect_tpA		: BitmapData = Res.createBitmap(Res.effect_tp0).bitmapData;
		public var effect_tpB		: BitmapData = Res.createBitmap(Res.effect_tp1).bitmapData;
		
		
		public var text_number_y		: ImageNumber = Res.number.copy(0);
		public var text_number_g 		: ImageNumber = Res.number.copy(0);
		
//		--------------------------------------------------------------------------------------------
			
		public function CBattleResource(res:BattleStartResponse)
		{
			this.text_number_y.setColor(0xffffff00);
			this.text_number_g.setColor(0xff00ff00);
			
			var mkv : Array = StringUtil.splitString(res.cmap_id, "/");
			map_cpj 	= mkv[0];
			map_cmapid 	= mkv[1];
			
			res_map = CResourceManager.createMapResource(map_cpj);
			queue.push(res_map);
			
			queue.addEventListener(ResourceEvent.LOADED, res_loaded);
		}
		
		public function pushSkills(skill_templates:Map) : void
		{
			for each (var st:SkillTemplate in skill_templates)
			{
				if (st.spriteEffect != null && st.spriteEffect.length > 0) {
					var kv : Array = StringUtil.splitString(st.spriteEffect, "/");
					var re : ResourceLoader = res_effects.get(kv[0]);
					if (re == null) {
						re = CResourceManager.createEffectResource(kv[0])
						res_effects.put(kv[0], re);
						queue.push(re);
					}
				}
			}
		}
		
		public function pushUnits(unit_templates:Map) : void
		{
			for each (var ut:UnitTemplate in unit_templates) 
			{
				var kv : Array = StringUtil.splitString(ut.csprite_id, "/");
				
				var ra : ResourceLoader = res_actors.get(kv[0]);
				if (ra == null) {
					ra = CResourceManager.createActorResource(kv[0])
					res_actors.put(kv[0], ra);
					queue.push(ra);
				}
				
				if (ut.attackEffect != null && ut.attackEffect.length > 0) {
					kv = StringUtil.splitString(ut.attackEffect, "/");
					var re : ResourceLoader = res_effects.get(kv[0]);
					if (re == null) {
						re = CResourceManager.createEffectResource(kv[0])
						res_effects.put(kv[0], re);
						queue.push(re);
					}
				}
			}
		}
		
		public static function getUnitRes(ut:UnitTemplate):ResourceLoader
		{
			var kv : Array = StringUtil.splitString(ut.csprite_id, "/");
			var ra : ResourceLoader = CResourceManager.createActorResource(kv[0]);
			return ra;
		}
		
		
		
		
		private function res_loaded(e:ResourceEvent) : void
		{
			for each (var ag : String in res_actors.keys()) {
				var actor : ResourceLoader = res_actors.get(ag);
				for each (var ss : SpriteSet in actor.getOutput().getSprTable()) {
					map_unit_info.put(ss.Name, new UnitInfo(actor.getSpriteTemplate(ss.Name), ss.Name));
				}
			}
			
			for each (var eg : String in res_effects.keys()) {
				var effect : ResourceLoader = res_effects.get(eg);
				for each (var es : SpriteSet in effect.getOutput().getSprTable()) {
					map_effect_info.put(es.Name, new EffectInfo(es));
				}
			}
			
		}
		
//		------------------------------------------------------------------------------------------------
		
		public function getActorSprite(tp:UnitTemplate) : CSpriteBuffer
		{
			var kv : Array = StringUtil.splitString(tp.csprite_id, "/");
			var res : ResourceLoader = res_actors[kv[0]];
			if (res == null) {
				trace("unit not found : " + tp.csprite_id);
			}
			return res.getSpriteBuffer(kv[1]);
		}
		
		public function getUnitInfo(csprite_id:String) : UnitInfo
		{
			var kv : Array = StringUtil.splitString(csprite_id, "/");
			return map_unit_info.get(kv[1]);
		}
		
		
//		------------------------------------------------------------------------------------------------
		
		public function getEffectSpriteBuffer(csprite_id:String) : CSpriteBuffer
		{
			var kv : Array = StringUtil.splitString(csprite_id, "/");
			var res : ResourceLoader = res_effects[kv[0]];
			if (res == null) {
				trace("effect not found : " + csprite_id);
			}
			return res.getSpriteBuffer(kv[1]);
		}
		
		public function getEffectSprite(csprite_id:String) : CSprite
		{
			var kv : Array = StringUtil.splitString(csprite_id, "/");
			var res : ResourceLoader = res_effects[kv[0]];
			if (res == null) {
				trace("effect spr not found : " + csprite_id);
			}
			return res.getSprite(kv[1]);
		}

		public function getEffectInfo(csprite_id:String) : EffectInfo
		{
			var kv : Array = StringUtil.splitString(csprite_id, "/");
			return map_effect_info.get(kv[1]);
		}
		
//		------------------------------------------------------------------------------------------------
		
		public function getBattleSceneSet() : WorldSet
		{
			return res_map.getSetWorld(map_cmapid)
		}
		
		public function getResMap(ws:MapObject) : CMap
		{
			return res_map.getMap(ws.MapID);
		}
		
	}
}