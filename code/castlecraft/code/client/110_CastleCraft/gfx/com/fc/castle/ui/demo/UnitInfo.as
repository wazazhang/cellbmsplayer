package com.fc.castle.ui.demo
{
	import com.cell.ui.component.Panel;
	import com.fc.castle.data.SoldierData;
	import com.fc.castle.data.message.AbstractTemplate;
	import com.fc.castle.data.template.Enums.AttackType;
	import com.fc.castle.data.template.Enums.DefenseType;
	import com.fc.castle.data.template.Enums.FightType;
	import com.fc.castle.data.template.UnitTemplate;
	import com.fc.castle.net.client.DataManager;
	import com.fc.castle.res.CLayoutManager;
	import com.fc.castle.res.Res;
	import com.fc.castle.screens.Screens;
	import com.fc.castlecraft.LanguageManager;
	
	import flash.display.Sprite;
	import flash.text.TextField;
	import flash.text.TextFormat;

	public class UnitInfo extends BaseInfo
	{
		
		var soldierTemp:BaseDemoPlay;
		
		var tname:TextField = new TextField();
	
		var attack:TextField = new TextField();
		
		var defence:TextField = new TextField();
		
		var price:TextField = new TextField();
		
		var fightType:TextField = new TextField();
		
		var attType:TextField = new TextField();
		
		var defenceType:TextField = new TextField();
		
		var discript:TextField = new TextField();
		
		
		public function UnitInfo(unitTemp:UnitTemplate)
		{		
			soldierTemp 		= new UnitDemoPlay(unitTemp.csprite_id);
			tname.htmlText 		= LanguageManager.getText("handbook.name_center", unitTemp.name);
			attack.htmlText 	= LanguageManager.getText("handbook.attack", unitTemp.attack);
			defence.htmlText 	= LanguageManager.getText("handbook.defence", unitTemp.defense);
			price.htmlText 		= LanguageManager.getText("handbook.price", unitTemp.cost);
			fightType.htmlText 	= LanguageManager.getText("handbook.fightType", LanguageManager.getFightType(unitTemp.fightType));
			attType.htmlText 	= LanguageManager.getText("handbook.attackType", LanguageManager.getAttackType(unitTemp.attackType));
			defenceType.htmlText= LanguageManager.getText("handbook.defenceType", LanguageManager.getDefenceType(unitTemp.defenseType));
			discript.htmlText 	= LanguageManager.getText("handbook.desc", unitTemp.description);
			discript.wordWrap 	= true;
			
			super(unitTemp);

			this.addChild(soldierTemp);
			this.addChild(tname);
			this.addChild(attack);
			this.addChild(defence);
			this.addChild(price);
			this.addChild(fightType);
			this.addChild(attType);
			this.addChild(defenceType);
			this.addChild(discript);
		}
		
		override public function resize(w:int, h:int):void
		{
				soldierTemp.x = w*0.5;
				soldierTemp.y = 155;
				
				tname.width = w;
				tname.y = 150;
				
				attack.y = 170;
				attack.x = 10;
				
				defence.y = 170;
				defence.x = w*0.5 + 10;
				defence.width = w*0.5 -20;
				
				price.y = 190;
				price.x = 10;
				
				fightType.y = 190;
				fightType.x = w*0.5+10;
				fightType.width = w*0.5 -20;
				
				attType.y = 210;
				attType.x = 10;
				
				defenceType.y = 210;
				defenceType.x = w*0.5+10;
				defenceType.width = w*0.5 -20;
				
				discript.y = 230;
				discript.x = 10;
				discript.width = w - 30;
		
		}
		
		
	}
}