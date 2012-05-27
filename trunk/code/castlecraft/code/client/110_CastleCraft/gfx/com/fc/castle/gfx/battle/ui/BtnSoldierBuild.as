package com.fc.castle.gfx.battle.ui
{
	import com.cell.gfx.CellSprite;
	import com.cell.ui.Anchor;
	import com.cell.ui.ImageBox;
	import com.cell.ui.ImageButton;
	import com.cell.ui.ImageGroup;
	import com.cell.ui.ImageNumber;
	import com.cell.ui.TouchToggleButton;
	import com.cell.ui.component.Pan;
	import com.cell.ui.layout.UIRect;
	import com.fc.castle.data.SoldierData;
	import com.fc.castle.data.template.UnitTemplate;
	import com.fc.castle.gfx.battle.StageBattle;
	import com.fc.castle.gfx.battle.ai.ForcePlayer;
	import com.fc.castle.net.client.DataManager;
	import com.fc.castle.res.CResourceManager;
	import com.fc.castle.res.Res;
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.events.MouseEvent;

	public class BtnSoldierBuild extends CellSprite
	{
		private const WINK_COLER:int = 0xffffff;
		
		private var battle : StageBattle;
		private var battle_force : ForcePlayerHuman;
		
		private var cost_number : ImageNumber;
		private var error_number : ImageNumber;
		
		private var sd:SoldierData;
		
		private var tp:UnitTemplate;
		
		private var body : TouchToggleButton;
		
		private var error_timer : int = 0;
		
		private var _isWink:Boolean;
		
		private var winkSprite:Sprite;
		
		private var timer:int;
		
		public function BtnSoldierBuild(battle : StageBattle, 
										battle_force : ForcePlayerHuman, 
										bg : BitmapData, 
										hibg : BitmapData, 
										cost_number : ImageNumber,
										error_number : ImageNumber,
										sd : SoldierData)
		{
			this.battle = battle;
			this.battle_force = battle_force;
			this.sd = sd;
			this.mouseEnabled = false;
			
			this.body = new TouchToggleButton(new Bitmap(bg), new Bitmap(hibg));
			addChild(body);
			
			winkSprite = new Sprite();
			winkSprite.mouseEnabled = false;
			//winkSprite.graphics.beginFill(WINK_COLER);
			
			
			if (sd != null) 
			{
				this.body.addEventListener(MouseEvent.MOUSE_DOWN, onMouseDown);
				
				this.tp = DataManager.getUnitTemplate(sd.unitType);
				

				var hdimg : ImageBox = ImageBox.createImageBox(
					CResourceManager.createActorHeadUrl(tp),
					Anchor.ANCHOR_HCENTER | Anchor.ANCHOR_VCENTER);
				hdimg.mouseEnabled = false;
				hdimg.mouseChildren = false;
				hdimg.x = width/2;
				hdimg.y = height/2;
				addChild(hdimg);
				
				this.cost_number = cost_number.copy(tp.cost);
				this.cost_number.anchor = Anchor.ANCHOR_HCENTER | Anchor.ANCHOR_BOTTOM;
				this.cost_number.x = width/2;
				this.cost_number.y = height-4;
				this.cost_number.visible = true;
				addChild(this.cost_number);
				
				this.error_number = error_number.copy(tp.cost);
				this.error_number.anchor = Anchor.ANCHOR_HCENTER | Anchor.ANCHOR_BOTTOM;
				this.error_number.x = width/2;
				this.error_number.y = height-4;
				this.error_number.visible = false;
				addChild(this.error_number);
			}
			else
			{
				this.mouseChildren = false;
			}
			
			
		}
		
		public function get isWink():Boolean
		{
			return _isWink;
		}

		public function set isWink(value:Boolean):void
		{
			_isWink = value;
			if(value)
				addChild(winkSprite);
			else
				removeChild(winkSprite);
		}

		public function getSoldierData() : SoldierData
		{
			return sd;
		}
		
		public function getUnitTemplate() : UnitTemplate
		{
			return tp;
		}
		
		public function isSelected() : Boolean
		{
			return this.body.getSelected();
		}
		
		override protected function update(e:Event):void
		{
			timer++;
			
			if (error_timer>0) {
				error_timer -- ;
				if (error_timer<=0) {
					cost_number.visible = true;
					error_number.visible = false;
				} else {
					if (error_timer%2==0) {
						cost_number.visible = true;
						error_number.visible = false;
					} else {
						error_number.visible = true;
						cost_number.visible = false;
					}
				}
			}
			
			if(_isWink)
			{
				winkSprite.graphics.clear();
				winkSprite.graphics.beginFill(WINK_COLER,Math.sin((timer*4%180/180)*Math.PI) * 0.5);
				winkSprite.graphics.drawRect(0,0,width,height);
			}
			
		}
		
		
		
		
		
		private function onMouseDown(e:Event) : void
		{
			battle_force.buildBtnDown(this);
		}
		
		internal function canNotBuild() : void
		{
			error_timer = 10;
			this.body.setSelected(false);
		}
		
		internal function cancelBuild() : void
		{
			this.body.setSelected(false);
		}
		
	}
}