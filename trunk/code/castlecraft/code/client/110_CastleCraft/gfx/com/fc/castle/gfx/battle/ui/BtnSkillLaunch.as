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
	import com.fc.castle.data.SkillData;
	import com.fc.castle.data.SoldierData;
	import com.fc.castle.data.template.SkillTemplate;
	import com.fc.castle.data.template.UnitTemplate;
	import com.fc.castle.gfx.battle.StageBattle;
	import com.fc.castle.gfx.battle.ai.ForcePlayer;
	import com.fc.castle.gfx.battle.ai.SkillState;
	import com.fc.castle.net.client.DataManager;
	import com.fc.castle.res.CResourceManager;
	import com.fc.castle.res.Res;
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.events.MouseEvent;

	public class BtnSkillLaunch extends CellSprite
	{
		private var battle 			: StageBattle;
		private var battle_force 	: ForcePlayerHuman;
		
		private var cost_number 	: ImageNumber;
		private var error_number 	: ImageNumber;
		
		private var sd				: SkillData;
		private var tp				: SkillTemplate;
		private var body			: TouchToggleButton;
		private var hdimg 			: ImageBox;
		private var error_timer		: int = 0;
		
		private var skill_state		: SkillState;
		
		private var cd_layer		: Sprite;
		
		public function BtnSkillLaunch(battle : StageBattle, 
										battle_force : ForcePlayerHuman, 
										bg : BitmapData, 
										hibg : BitmapData, 
										cost_number : ImageNumber,
										error_number : ImageNumber,
										sd : SkillData)
		{
			this.battle = battle;
			this.battle_force = battle_force;
			this.sd = sd;
			this.mouseEnabled = false;

			this.body = new TouchToggleButton(new Bitmap(bg), new Bitmap(hibg));
			addChild(body);
			
			if (sd != null) 
			{

				this.body.addEventListener(MouseEvent.MOUSE_DOWN, onMouseDown);
				
				this.tp = DataManager.getSkillTemplate(sd.skillType);
				

				this.hdimg = new ImageBox(
					CResourceManager.getIcon(tp.icon),
					Anchor.ANCHOR_HCENTER | Anchor.ANCHOR_VCENTER);
				hdimg.mouseEnabled = false;
				hdimg.mouseChildren = false;
				hdimg.x = width/2;
				hdimg.y = height/2;
				addChild(hdimg);
				
				this.skill_state = battle_force.getSkillState(sd.skillType);
				this.cd_layer = new Sprite();
				addChild(this.cd_layer);
				
				this.cost_number = cost_number.copy(tp.costMP);
				this.cost_number.anchor = Anchor.ANCHOR_HCENTER | Anchor.ANCHOR_BOTTOM;
				this.cost_number.x = width/2;
				this.cost_number.y = height-4;
				this.cost_number.visible = true;
				addChild(this.cost_number);
				
				this.error_number = error_number.copy(tp.costMP);
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
		
		public function getSkillData() : SkillData
		{
			return sd;
		}
		
		public function getSkillTemplate() : SkillTemplate
		{
			return tp;
		}
		
		public function isSelected() : Boolean
		{
			return this.body.getSelected();
		}
		
		override protected function update(e:Event):void
		{
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
			
			if (skill_state != null) {
				var cd_pct : Number = skill_state.getCDPercent();
				if (cd_pct < 1) {
					var bh : Number = height * cd_pct;
					cd_layer.visible = true;
					cd_layer.graphics.clear();
					cd_layer.graphics.beginFill(0xffffff, 0.5);
					cd_layer.graphics.drawRect(0, bh, width, height - bh);
					cd_layer.graphics.endFill();
//					trace(cd_pct);
				} else {
					cd_layer.visible = false;				

				}
			}
		}
		
		private function onMouseDown(e:Event) : void
		{
			battle_force.skillBtnDown(this);
		}
		
		internal function canNotLaunch() : void
		{
			error_timer = 10;
			this.body.setSelected(false);
		}
		
		internal function cancelLaunch() : void
		{
			this.body.setSelected(false);
		}
	}
}