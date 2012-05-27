package com.fc.castle.ui.card
{
	import com.cell.gfx.CellSprite;
	import com.cell.ui.component.Panel;
	import com.fc.castle.data.SoldierData;
	import com.fc.castle.data.template.Enums.AttackType;
	import com.fc.castle.data.template.Enums.DefenseType;
	import com.fc.castle.data.template.Enums.FightType;
	import com.fc.castle.data.template.UnitTemplate;
	import com.fc.castle.net.client.DataManager;
	import com.fc.castle.res.Res;
	import com.fc.castle.screens.Screens;
	import com.fc.castle.ui.demo.UnitInfo;
	import com.fc.castlecraft.LanguageManager;
	
	import flash.display.Sprite;
	import flash.text.TextField;
	import flash.text.TextFormat;
	
	import mx.controls.Text;
	
	public class SoldierDetailPan extends Panel
	{			
		private var w : int;
		private var h : int;

		private var content:UnitInfo;
		
		public function SoldierDetailPan(type:int, w:int, h:int)
		{
			super(w, h);
			this.mouseEnabled = false;
			this.mouseChildren = false;
			this.w = w;
			this.h = h;
			setSoldier(type);
		}
		
		public function setSoldier(type:int):void
		{
			if (content != null) {
				this.removeChild(content);
			}
			content = new UnitInfo(DataManager.getUnitTemplate(type));
			content.resize(w, h);
			addChild(content);
		}		
		
	}
}