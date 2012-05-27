package com.fc.castle.gfx.battle.ui
{
	import com.cell.gameedit.object.WorldSet;
	import com.cell.gfx.game.worldcraft.CellUnit;
	import com.fc.castle.data.BattlePlayer;
	import com.fc.castle.data.SoldierData;
	import com.fc.castle.data.template.SkillTemplate;
	import com.fc.castle.gfx.battle.BattleBuilding;
	import com.fc.castle.gfx.battle.BattleObject;
	import com.fc.castle.gfx.battle.BattleUnit;
	import com.fc.castle.gfx.battle.BattleWorld;
	import com.fc.castle.gfx.battle.StageBattle;
	import com.fc.castle.gfx.battle.ai.ForcePlayer;
	import com.fc.castle.gfx.battle.event.APTargetComplateEvent;
	import com.fc.castle.gfx.battle.event.BuildBtnDownEvent;
	import com.fc.castle.gfx.battle.event.SkillBtnDownEvent;
	import com.fc.castle.gfx.battle.res.SkillBehavior;
	
	import flash.display.Sprite;
	import flash.events.MouseEvent;

	public class ForcePlayerHuman extends ForcePlayer
	{
		private var build_button : BtnSoldierBuild = null;
		
		private var build_hilight : BuildHilight;
		
		private var skill_button : BtnSkillLaunch = null;
		
		private var target_ap:int = 0;
		
		public function ForcePlayerHuman(data:BattlePlayer, force:int)
		{
			super(data, force);
		}
		
		override public function battleInit(battle:StageBattle, res_world_data:WorldSet):ForcePlayer
		{
			super.battleInit(battle, res_world_data);
			build_hilight = new BuildHilight(battle, this);
			battle.addEventListener(MouseEvent.MOUSE_DOWN, 	onWorldMouseDown);
			battle.addEventListener(MouseEvent.MOUSE_UP, 	onWorldMouseUp);
			battle.addEventListener(MouseEvent.MOUSE_MOVE,	onWorldMouseMove);
			
			battle.getWorld().locateCameraCenter(holy.x, holy.y);
			
			return this;
		}
		
		public function setTargetAP(v:int):void
		{
			target_ap = v;
		}
		
		override public function battleUpdate():void
		{
			if (target_ap>0 && ap>target_ap){
				dispatchEvent(new APTargetComplateEvent());
			}
		}
		
//		--------------------------------------------------------------------------------------------------------------------------
// 		call from battle stage
//		--------------------------------------------------------------------------------------------------------------------------

		private function onWorldMouseDown(e:MouseEvent) : void
		{
			if (battle.isPause()) {
				return;
			}
			// 鼠标按下释放技能
			if (skill_button != null)
			{
				skillLaunch(e);
			}
		}
		
		private function onWorldMouseUp(e:MouseEvent) : void
		{
			if (battle.isPause()) {
				return;
			}
			// 鼠标松开建造士兵
			if (build_button != null) 
			{
				buildComplete(e);
			}
		}
		
		private function onWorldMouseMove(e:MouseEvent) : void
		{
			if (battle.isPause()) {
				return;
			}
			// 鼠标移动，更改建造高亮显示
			if (build_button != null) 
			{
				buildMove(e);
			}
		}
		
//		--------------------------------------------------------------------------------------------------------------------------
//		build building
//		--------------------------------------------------------------------------------------------------------------------------
		
		public var is_can_build:Boolean = true;
		
		// call from ui button
		internal function buildBtnDown(btn:BtnSoldierBuild) : void
		{
			if (is_can_build==false){
				return;
			}
			if (btn.isSelected()) {
				if (this._ap >= btn.getUnitTemplate().cost) {
					if (this.build_button != null) {
						if (this.build_button != btn) {
							this.build_button.cancelBuild();
						}
					} else {
//						this.addBuildHilight();
						this.build_hilight.addBuildHilight();
					}
					dispatchEvent(new BuildBtnDownEvent(btn));
					this.build_button = btn;
				} else {
					btn.canNotBuild();
				}
			} else {
				this.build_hilight.removeBuildHilight();
				this.build_button = null;
			}
		}
		
		// call from battle stage
		private function buildComplete(e:MouseEvent) : void
		{
			if (build_button != null) 
			{
				if (this.buildBuilding(
					build_button.getSoldierData(), 
					battle.getWorld().mouseX, 
					battle.getWorld().mouseY)) 
				{
					this.build_button.cancelBuild();
					this.build_button = null;
					this.build_hilight.removeBuildHilight();
				}
			}
		}
		
		// call from battle stage
		private function buildMove(e:MouseEvent) : void
		{
			if (build_button != null) 
			{
				build_hilight.setFocusPos();
			}
		}
		
//		--------------------------------------------------------------------------------------------------------------------------
//		launch skill
//		--------------------------------------------------------------------------------------------------------------------------

		// call from ui button
		internal function skillBtnDown(btn:BtnSkillLaunch) : void
		{
			
			if (btn.isSelected()) {
				var st : SkillTemplate = btn.getSkillTemplate();
				if (this._ap >= st.costMP && this._hp >= st.costHP && !isSkillCoolDown(st.type)) {
					if (this.skill_button != null) {
						if (this.skill_button != btn) {
							this.skill_button.cancelLaunch();
						}
					} else {

					}
					dispatchEvent(new SkillBtnDownEvent(btn));
					this.skill_button = btn;
				} else {
					btn.canNotLaunch();
				}
			} else {
				this.skill_button = null;
			}
		}
		
		
		// call from battle stage
		private function skillLaunch(e:MouseEvent) : void
		{
			if (skill_button != null)
			{
				if (this.launchSkill(
					skill_button.getSkillData(), 
					battle.getWorld().mouseX, 
					battle.getWorld().mouseY)) 
				{
					this.skill_button.cancelLaunch();
					this.skill_button = null;
				}
			}
		}
		
		public function getBuild_hilight():BuildHilight
		{
			return build_hilight;
		}

	}
}