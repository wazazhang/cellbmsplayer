package com.fc.castle.gfx.battle.ui
{
	import com.cell.gameedit.object.WorldSet;
	import com.cell.gfx.CellSprite;
	import com.cell.util.Util;
	import com.fc.castle.gfx.battle.BattleObject;
	import com.fc.castle.gfx.battle.BattleWorld;
	import com.fc.castle.gfx.battle.StageBattle;

	public class BuildHilight extends BattleObject
	{		
		private var build_hilight_x : CellSprite;
		private var build_hilight_y : CellSprite;
		private var build_na_x : CellSprite;
		private var build_na_y : CellSprite;
		
		private var listed : Vector.<CellSprite>;
		
		private var forceplayer : ForcePlayerHuman;
		
		public function BuildHilight(battle:StageBattle, forceplayer:ForcePlayerHuman)
		{
			super(battle);
			
			this.forceplayer = forceplayer;
			
			this.priority = BattleWorld.LAYER_MAP_HI;
		}
		
		public function setFocusPos() : void
		{
			var gx : int = battle.getWorld().mouseX / battle.getWorld().getCellW();
			var gy : int = battle.getWorld().mouseY / battle.getWorld().getCellH();
			
			build_hilight_x.x = 0;
			build_hilight_x.y = gy * battle.getWorld().getCellH();
			
			build_hilight_y.x = gx * battle.getWorld().getCellW();
			build_hilight_y.y = 0;
			
			build_na_x.x = 0;
			build_na_x.y = gy * battle.getWorld().getCellH();
			
			build_na_y.x = gx * battle.getWorld().getCellW();
			build_na_y.y = 0;
			
			if (battle.getWorld().isCanBuild(battle.getWorld().mouseX, battle.getWorld().mouseY, forceplayer)) {
				build_hilight_x.visible = true;
				build_hilight_y.visible = true;
				build_na_x.visible = false;
				build_na_y.visible = false;
			} else {
				build_hilight_x.visible = false;
				build_hilight_y.visible = false;
				build_na_x.visible = true;
				build_na_y.visible = true;
			}
		}
		
		// 添加建造时上的提示
		public function addBuildHilight() : void
		{
			if (!super.battle.getWorld().contains(this))
			{
				var cellw : int = battle.getWorld().getCellW();
				var cellh : int = battle.getWorld().getCellH();
				
				
				build_hilight_x = new CellSprite();
				build_hilight_y = new CellSprite();
				build_na_x = new CellSprite();
				build_na_y = new CellSprite();
				listed = new Vector.<CellSprite>();
				
				build_hilight_x.graphics.beginFill(0xffffff, 0.5);
				build_hilight_x.graphics.drawRect(0, 0, 
					battle.getWorld().getWorldWidth(), 
					battle.getWorld().getCellH());
				build_hilight_x.graphics.endFill();
				
				build_hilight_y.graphics.beginFill(0xffffff, 0.5);
				build_hilight_y.graphics.drawRect(0, 0, 
					battle.getWorld().getCellW(), 
					battle.getWorld().getWorldHeight());
				build_hilight_y.graphics.endFill();
				
				
				build_na_x.graphics.beginFill(0xff0000, 0.5);
				build_na_x.graphics.drawRect(0, 0, 
					battle.getWorld().getWorldWidth(), 
					battle.getWorld().getCellH());
				build_na_x.graphics.endFill();
				
				build_na_y.graphics.beginFill(0xff0000, 0.5);
				build_na_y.graphics.drawRect(0, 0, 
					battle.getWorld().getCellW(), 
					battle.getWorld().getWorldHeight());
				build_na_y.graphics.endFill();
				
				this.addChild(build_hilight_x);
				this.addChild(build_hilight_y);
				this.addChild(build_na_x);
				this.addChild(build_na_y);
				
				for (var x:int=battle.getWorld().getGridXCount()-1; x>=0; --x) {
					for (var y:int=battle.getWorld().getGridYCount()-1; y>=0; --y) {
						if (battle.getWorld().isCanBuild(x*cellw, y*cellh, forceplayer)) {
							var grid : BuildHilightGrid = new BuildHilightGrid(battle);
							grid.x = x * cellw;
							grid.y = y * cellh;
							listed.push(grid);
							this.addChild(grid);
						}
					}
				}
				
				super.battle.getWorld().addChild(this);
				super.battle.getWorld().sortUnits();
			}
		}
		
		// 移除建造时上的提示
		public function removeBuildHilight() : void
		{
			if (super.battle.getWorld().contains(this))
			{
				super.battle.getWorld().removeChild(this);
				
				Util.clearChilds(this);
				
				build_hilight_x = null;
				build_hilight_y = null;
				build_na_x = null;
				build_na_y = null;
				listed = null;
			}
		}
	}
}