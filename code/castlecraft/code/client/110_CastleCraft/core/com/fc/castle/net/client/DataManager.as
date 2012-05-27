package com.fc.castle.net.client
{
	import com.cell.util.Arrays;
	import com.cell.util.Map;
	import com.fc.castle.data.ItemData;
	import com.fc.castle.data.ShopItem;
	import com.fc.castle.data.SkillData;
	import com.fc.castle.data.SoldierData;
	import com.fc.castle.data.message.AbstractTemplate;
	import com.fc.castle.data.message.Messages.GetBuffTemplateRequest;
	import com.fc.castle.data.message.Messages.GetBuffTemplateResponse;
	import com.fc.castle.data.message.Messages.GetItemTemplateRequest;
	import com.fc.castle.data.message.Messages.GetItemTemplateResponse;
	import com.fc.castle.data.message.Messages.GetSkillTemplateRequest;
	import com.fc.castle.data.message.Messages.GetSkillTemplateResponse;
	import com.fc.castle.data.message.Messages.GetUnitTemplateRequest;
	import com.fc.castle.data.message.Messages.GetUnitTemplateResponse;
	import com.fc.castle.data.template.BuffTemplate;
	import com.fc.castle.data.template.ItemTemplate;
	import com.fc.castle.data.template.SkillTemplate;
	import com.fc.castle.data.template.UnitTemplate;
	import com.fc.castle.screens.Screens;

	public class DataManager
	{
		
		public static function getDataTemplate(data:*) : AbstractTemplate
		{
			if (data is SoldierData)
			{
				return DataManager.getUnitTemplate(data.unitType);
			}
			else if (data is SkillData) 
			{
				return DataManager.getSkillTemplate(data.skillType);
			}
			else if (data is ItemData)
			{
				return DataManager.getItemTemplate(data.itemType);
			}
			else if (data is ShopItem)
			{
				return DataManager.getItemTemplate(data.itemType);
			}
			return null;
		}
//		------------------------------------------------------------------------------------------------------------
//		
//		------------------------------------------------------------------------------------------------------------
		
		
		private static var template_units : Map = new Map();
		
		public static function getUnitTemplate(unitType:int) : UnitTemplate
		{
			return template_units.get(unitType);
		}
		
		/**
		 * @param soldier_data_array [SoldierData]
		 * @param complete
		 * @param error
		 * 
		 */
		public static function getUnitTemplates(soldier_data_array:Array, complete:Function=null, error:Function=null) : void 
		{
			var int_array : Array = new Array();
			for each(var a : SoldierData in soldier_data_array) {
				int_array.push(a.unitType);
			}
			DataManager.getUnitTemplateMap(int_array, complete, error);
		}
		
		/**<pre>
		 * int arrray
		 * function complete(map:Map[UnitType, UnitTemplate]) : void; 
		 * function error() : void;
		 */
		public static function getUnitTemplateMap(int_array:Array, complete:Function=null, error:Function=null) : void
		{
			int_array = Arrays.toSet(int_array);
			var ret : Map = template_units.getMapSet(int_array);
			if (ret.size() == int_array.length) {
				complete.call(null, ret);
			} else {
				var incoming_ids : Array = Arrays.unionSet(template_units.keys(), int_array);
				if (incoming_ids.length > 0) {
					Screens.client.sendRequest(new GetUnitTemplateRequest(0, incoming_ids), 
						function response(e:CClientEvent) : void
						{
							var res : GetUnitTemplateResponse = e.response as GetUnitTemplateResponse;
							DataManager.putUnits(res);
							var ret2 : Map = template_units.getMapSet(int_array);
							complete.call(null, ret2);
						},
						function error(e:CClientEvent) : void
						{
							error.call();
						}
					);
				} else {
					complete.call(null, ret);
				}
			}
		}
		
		private static function putUnits(res : GetUnitTemplateResponse) : void
		{
			for each(var o : UnitTemplate in res.unit_templates) {
				template_units.put(o.type, o);
			}
		}
		
		
		
		
//		------------------------------------------------------------------------------------------------------------
//		
//		------------------------------------------------------------------------------------------------------------
		
		
		
		
		private static var template_skills : Map = new Map();
		
		public static function getSkillTemplate(skillType:int) : SkillTemplate
		{
			return template_skills.get(skillType);
		}

		/**
		 * 
		 * @param data_array [SkillData]
		 * @param complete
		 * @param error
		 * 
		 */
		public static function getSkillTemplates(data_array:Array, complete:Function=null, error:Function=null) : void 
		{
			var int_array : Array = new Array();
			for each(var a : SkillData in data_array) {
				int_array.push(a.skillType);
			}
			DataManager.getSkillTemplateMap(int_array, complete, error);
		}
		
		/**<pre>
		 * int arrray
		 * function complete(map:Map[SkillType, SkillTemplate]) : void; 
		 * function error() : void;
		 */
		public static function getSkillTemplateMap(int_array:Array, complete:Function=null, error:Function=null) : void
		{
			int_array = Arrays.toSet(int_array);
			var ret : Map = template_skills.getMapSet(int_array);
			if (ret.size() == int_array.length) {
				complete.call(null, ret);
			} else {
				var incoming_ids : Array = Arrays.unionSet(template_skills.keys(), int_array);
				if (incoming_ids.length > 0) {
					Screens.client.sendRequest(new GetSkillTemplateRequest(0, incoming_ids), 
						function response(e:CClientEvent) : void
						{
							var res : GetSkillTemplateResponse = e.response as GetSkillTemplateResponse;
							DataManager.putSkills(res);
							var ret2 : Map = template_skills.getMapSet(int_array);
							complete.call(null, ret2);
						},
						function error(e:CClientEvent) : void
						{
							error.call();
						}
					);
				} else {
					complete.call(null, ret);
				}
			}
		}
		
		private static function putSkills(res : GetSkillTemplateResponse) : void
		{
			for each(var o : SkillTemplate in res.skill_templates) {
				template_skills.put(o.type, o);
			}
		}
		
		
		
		//		------------------------------------------------------------------------------------------------------------
		//		
		//		------------------------------------------------------------------------------------------------------------
		
		
		
		
		private static var template_items : Map = new Map();
		
		public static function getItemTemplate(itemType:int) : ItemTemplate
		{
			return template_items.get(itemType);
		}
		
		/**
		 * 
		 * @param data_array [ItemData]
		 * @param complete
		 * @param error
		 * 
		 */
		public static function getItemTemplates(data_array:Array, complete:Function=null, error:Function=null) : void 
		{
			var int_array : Array = new Array();
			for each(var a : ItemData in data_array) {
				int_array.push(a.itemType);
			}
			DataManager.getItemTemplateMap(int_array, complete, error);
		}
		
		/**
		 * 
		 * @param data_array [ShopItem]
		 * @param complete
		 * @param error
		 * 
		 */
		public static function getShopItemTemplates(data_array:Array, complete:Function=null, error:Function=null) : void 
		{
			var int_array : Array = new Array();
			for each(var a : ShopItem in data_array) {
				int_array.push(a.itemType);
			}
			DataManager.getItemTemplateMap(int_array, complete, error);
		}
		
		/**<pre>
		 * int arrray
		 * function complete(map:Map[ItemType, ItemTemplate]) : void; 
		 * function error() : void;
		 */
		public static function getItemTemplateMap(int_array:Array, complete:Function=null, error:Function=null) : void
		{
			int_array = Arrays.toSet(int_array);
			var ret : Map = template_items.getMapSet(int_array);
			if (ret.size() == int_array.length) {
				complete.call(null, ret);
			} else {
				var incoming_ids : Array = Arrays.unionSet(template_items.keys(), int_array);
				if (incoming_ids.length > 0) {
					Screens.client.sendRequest(new GetItemTemplateRequest(0, incoming_ids), 
						function response(e:CClientEvent) : void
						{
							var res : GetItemTemplateResponse = e.response as GetItemTemplateResponse;
							DataManager.putItems(res);
							var ret2 : Map = template_items.getMapSet(int_array);
							complete.call(null, ret2);
						},
						function error(e:CClientEvent) : void
						{
							error.call();
						}
					);
				} else {
					complete.call(null, ret);
				}
			}
		}
		
		private static function putItems(res : GetItemTemplateResponse) : void
		{
			for each(var o : ItemTemplate in res.item_templates) {
				template_items.put(o.type, o);
			}
		}
		
		
		//		------------------------------------------------------------------------------------------------------------
		//		
		//		------------------------------------------------------------------------------------------------------------
		
		private static var template_buffs : Map = new Map();
		
		
		public static function getBuffTemplate(type:int) : BuffTemplate
		{
			return template_buffs.get(type);
		}
		
		public static function getAllBuffTemplate(error:Function, succeed:Function) : void
		{
			Screens.client.sendRequest(new GetBuffTemplateRequest(Screens.client.getPlayerID()), 
				function response(e:CClientEvent) : void
				{
					var response : GetBuffTemplateResponse = e.response as GetBuffTemplateResponse;
					template_buffs = response.buff_templates;
					succeed.call(null, e);
				},
				function error(e:CClientEvent) : void
				{
					error.call(null, e);
				}
			);
		}
	}
	
}