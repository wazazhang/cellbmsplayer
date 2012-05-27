package com.fc.castle.ui.card
{
	import com.cell.gfx.CellSprite;
	import com.cell.ui.component.Panel;
	import com.cell.util.Map;
	import com.fc.castle.data.ItemData;
	import com.fc.castle.data.ItemDatas;
	import com.fc.castle.data.ShopItem;
	import com.fc.castle.data.SkillData;
	import com.fc.castle.data.SkillDatas;
	import com.fc.castle.data.SoldierData;
	import com.fc.castle.data.SoldierDatas;
	import com.fc.castle.gfx.CAlert;
	import com.fc.castle.net.client.DataManager;
	import com.fc.castle.ui.demo.BaseInfo;
	
	public class SyncCardInfoPan extends Panel
	{			
		private var type : int;
		private var data : *;
		private var w : int;
		private var h : int;
		
		private var content:BaseInfo;
		
		public function SyncCardInfoPan(data:*, w:int, h:int)
		{
			super(w, h);
			
			this.mouseEnabled  = false;
			this.mouseChildren = false;
			this.w = w;
			this.h = h;
			this.data = data;
			
			if (data is SoldierData) {
				type = data.unitType;
				DataManager.getUnitTemplates ([data], onComplete, onError);
			} else if (data is SkillData) {
				type = data.skillType;
				DataManager.getSkillTemplates([data], onComplete, onError);
			} else if (data is ItemData) {
				type = data.itemType;
				DataManager.getItemTemplates ([data], onComplete, onError);
			}
		}
		
		private function onComplete(types:Map):void
		{
			content = BaseInfo.createCardInfoPan(types.get(type));
			content.resize(w, h);
			addChild(content);
		}		
		
		private function onError():void
		{
			CAlert.showErrorNetwork();
		}
	}
}