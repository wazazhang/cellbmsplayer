package com.fc.castle.gfx.battle.ai
{
	import com.cell.gameedit.object.WorldSet;
	import com.cell.util.Arrays;
	import com.cell.util.Map;
	import com.cell.util.Util;
	import com.fc.castle.data.BattlePlayer;
	import com.fc.castle.data.SkillData;
	import com.fc.castle.data.SoldierData;
	import com.fc.castle.data.template.Enums.AttackType;
	import com.fc.castle.data.template.Enums.FightType;
	import com.fc.castle.data.template.Enums.SkillTargetType;
	import com.fc.castle.data.template.SkillTemplate;
	import com.fc.castle.data.template.UnitTemplate;
	import com.fc.castle.gfx.battle.BattleBuilding;
	import com.fc.castle.gfx.battle.BattleSoldier;
	import com.fc.castle.gfx.battle.BattleUnit;
	import com.fc.castle.gfx.battle.StageBattle;
	import com.fc.castle.net.client.DataManager;
	
	import mx.olap.aggregators.MaxAggregator;

	public class ForcePlayerComputer extends ForcePlayer
	{
		private const waitMaxTime:int = 240;
		private var next_soldier : UnitTemplate;
		private var next_soldier_data : SoldierData;
		private var enemyBuilding:Array = new Array();
		private var bestBuildPlane:BuildPlane;
		private var waitTime:int = 0;
		private var cheapestBuilding:SoldierData;
		
		private var fullRord:Array = new Array();//已经建造满的路
		
		//private var startRomdon:Boolean = false;
		
		private var enemyForce:ForcePlayer;		
		public function ForcePlayerComputer(data:BattlePlayer, force:int)
		{
			super(data, force);
		}
		
		override public function battleInit(battle:StageBattle, res_world_data:WorldSet) : ForcePlayer
		{
			var force:ForcePlayer = super.battleInit(battle, res_world_data);
			getCheapestBuilding();
			enemyForce = this.battle.getWorld().getForcePlayer(force_enemy);
			return force;
			
			
		}
		
		override public function battleUpdate():void
		{
			if (!battle.isPause())  {
				tryBuild();
				trySkill();
			}
		}
		
		
		public function getBestBuildPlane():BuildPlane
		{
			return bestBuildPlane;
		}
		
		/*override public function addAP(v:int) : int
		{
			super.addAP(v*10)
			//findBuilding();	
			return this._ap;	
		}*/
		
		private function trySkill():void
		{
			//selectSkillTarget()
			for each(var skillData:SkillData in data.skills.datas)
			{
			     var skill:SkillState =   getSkillState(skillData.skillType);

				 if(!skill.isCD())
				 {
					 var skillTemplate:SkillTemplate = DataManager.getSkillTemplate(skillData.skillType);
					 var target:BattleSoldier;
					 if(skillTemplate.targetType == SkillTargetType.ENEMY)
					 {
						 if( this.hp > this.maxHp*0.5 || sendSkillRomdon() )
						 	continue;
							 
						 target = selectSkillTarget(skillTemplate.targetType);
						 if(target!=null)
						 {
							 launchSkill(skillData,target.x + target.getBody().X1 + target.getBody().getWidth()*0.5 ,target.y + target.getBody().Y1 + target.getBody().getHeight()*0.5);
						 }
					 }
					 else if(skillTemplate.targetType == SkillTargetType.FIREND)
					 {
						 if( enemyForce.hp > enemyForce.maxHp*0.5 || sendSkillRomdon())
							 continue;
						 
						 target = selectSkillTarget(skillTemplate.targetType);
						 if(target!=null)
						 {
							 launchSkill(skillData,target.x + target.getBody().X1 + target.getBody().getWidth()*0.5 ,target.y + target.getBody().Y1 + target.getBody().getHeight()*0.5);
						 }
					 }
					 
				 }
			}
			/*function launch(target:BattleSoldier,sd:SkillData):void
			{
				if(target!=null)
				{
					if()
					launchSkill(sd,target.x ,target.y);
				}
			}*/
			
		}
		
		private function sendSkillRomdon():Boolean
		{
			return Util.getRandomInt(0,10000) > 9950;
		}
		
		
		private function selectSkillTarget(targetType:int):BattleSoldier
		{
			 var cur:BattleSoldier;
			 var maxX:Number = Number.MIN_VALUE;
			 var minX:Number = Number.MAX_VALUE;
			 for each(var trackY:int in canUserRoard)
			 {
			 	 var solids:Array;
				 
				 if (targetType  == SkillTargetType.ENEMY) {
					 solids = this.battle.getWorld().getTrackEnemy(this.force,trackY);
				 } else {
					 solids = this.battle.getWorld().getTrackSoldiers(this.force,trackY);
				 }
				 
				 for each (var bs:BattleSoldier in solids)
				 {
					 var dis:Number = Math.abs( bs.x - this.holy.x);
					
					 if(targetType  == SkillTargetType.FIREND)
					 {	 
						 if(dis >maxX )
						 {
							 cur = bs;
							 maxX =dis;
						 }
					 }
					 else if(targetType  == SkillTargetType.ENEMY)
					 {
						 if(bs.x <minX )
						 {
							 cur = bs;
							 minX =dis;
						 }
					 }
				 }
			 }
			 return cur;
		}
		
		
		private function tryBuild() : void
		{
			/*if (next_soldier == null) 
			{
				if (_ap > 0) 
				{
					var sd : SoldierData = Arrays.getRandom(data.soldiers.datas);
					
					if (sd != null) {
						build(sd);
					}
				}
			} 
			else 
			{
				if (_ap >= next_soldier.cost)
				{
					build(next_soldier_data);
				}
			}*/
			
			if(bestBuildPlane == null)
			{
				waitTime ++;
				if(waitTime >= waitMaxTime)
				{	
				/*	if(enemyBuilding.length == 0&& !startRomdon)
					{
						if(_ap >= DataManager.getUnitTemplate(cheapestBuilding.unitType).cost)
						{
							buildCheapestBuilding();
							startRomdon = true;
						}
					}
					else
					{*/
						randomBuilding();
					/*}*/
					waitTime = 0;
				}
			}else{
				if(_ap >= bestBuildPlane.soilder.cost)
				{
					if(builds(bestBuildPlane.build,bestBuildPlane.Y))
					{
						bestBuildPlane = null;
					}
				}
			}
			
			
		}
		
		/*private function build(sd : SoldierData) : void
		{
			var builded : BattleBuilding = this.buildBuilding(
				sd, 
				Util.getRandomInt(region_bounds.x, region_bounds.x + region_bounds.width  - 1),
				Util.getRandomInt(region_bounds.y, region_bounds.y + region_bounds.height - 1)
			);
			next_soldier_data = Arrays.getRandom(data.soldiers.datas);
			
			if (next_soldier_data != null) {
				next_soldier = DataManager.getUnitTemplate(sd.unitType);
				trace("ForcePlayerComputer : next soldier is [" + next_soldier.name + "]");
			} else {
				next_soldier = null;
			}
		}*/
		
		override public function onBuildBuilding(building:BattleBuilding):void
		{
			if(building.force != force)
			{
				enemyBuilding.push(building);
				findBuildingByBuilding(building);
			}
		}
		
		override public function onRemoveBuilding(building:BattleBuilding):void
		{
			if(building.force != force)
			{
				enemyBuilding.splice(enemyBuilding.indexOf(building),1);
				findBuilding();
			}
		}
		
		public function setBuildPlane(y:int):void
		{
			if (fullRord.indexOf(canUserRoard[y])<0){
				bestBuildPlane = new BuildPlane(Arrays.getRandom(data.soldiers.datas),0,canUserRoard[y]);
			}else{
				bestBuildPlane = new BuildPlane(Arrays.getRandom(data.soldiers.datas),0,getRandomY());
			}
		}
		
		private function randomBuilding():void
		{
			bestBuildPlane = new BuildPlane(Arrays.getRandom(data.soldiers.datas),0,getRandomY());
		}
		
		private function findBuildingByBuilding(building:BattleBuilding):void
		{
			/*bestBuildPlane = null;
			for each (var soldier:SoldierData    in    data.soldiers.datas)
			{
				var t:Number = AIComputer.CountThreaten(soldier.unitType,building.getSoldierData().unitType);
				if(bestBuildPlane == null)
				{
					bestBuildPlane = new BuildPlane(soldier,t,building.trackY);
				}
				else if(t>bestBuildPlane.threa)
				{
					bestBuildPlane = new BuildPlane(soldier,t,building.trackY);
				}
			}*/
			
			bestBuildPlane = AIComputer.getBuilding(building,data.soldiers.datas);
			
		}
		
		
		private function findBuilding():void
		{
			bestBuildPlane = null;
			for each (var soldier:SoldierData    in    data.soldiers.datas)
			{
				var map:Map  = new Map();
				for each (var b:BattleBuilding    in    enemyBuilding)
				{
					if(fullRord.indexOf(b.trackY) != -1)
					{
						continue;
					}
					var t:Number = AIComputer.CountThreaten(soldier.unitType,b.getSoldierData().unitType);
					if(map.contains(b.trackY))
					{
						(map.get(b.trackY) as BuildPlane).threa += t;
					}
					else
					{
						map.put(b.trackY,new BuildPlane(soldier,t,b.trackY));
					}
				}
				
				for each(var buildPlane:BuildPlane in map)
				{
					if(bestBuildPlane == null)
					{
						bestBuildPlane = buildPlane;
					}
					else if(buildPlane.threa > bestBuildPlane.threa)
					{
						bestBuildPlane = buildPlane;
					}				
				}		
			}
		}
		
		
		private function builds(sd : SoldierData , y:int):Boolean
		{
			var builded : BattleBuilding; 	
			var s:int = region_bounds.y + region_bounds.width;
			var height:int =   this.battle.getWorld().getCellH();
			var width:int =   this.battle.getWorld().getCellW();
			
			var isJinZhan:Boolean =  DataManager.getUnitTemplate(sd.unitType).fightType == FightType.NORMAL;
			
			if(isJinZhan)
			{
				for(var i:int = region_bounds.x;  i < region_bounds.x + region_bounds.width ;i+= width)
				{
					builded = this.buildBuilding(sd,i, y * height);
					if(builded!=null)
						return true;;
				}
			}
			else
			{
				for(var i:int = region_bounds.x + region_bounds.width;  i > region_bounds.x ;i -= width)
				{
					builded = this.buildBuilding(sd,i, y * height);
					if(builded!=null)
						return true;
				}	
			}
			fullRord.push(y);
			if(bestBuildPlane!=null && bestBuildPlane.Y == y)
				findBuilding();
			
			return false
		}
		/*
		private function buildCheapestBuilding():void
		{
			//this.battle.getWorld().toWorldPosY()
			builds(cheapestBuilding,getRandomY());
		}
		
		*/
		private function buildRamdonBuilding():void
		{
			var sd : SoldierData = Arrays.getRandom(data.soldiers.datas);
		}
		
		private function getCheapestBuilding():void
		{
			for each(var sd:SoldierData in data.soldiers.datas)
			{
				if(cheapestBuilding == null)
				{
					cheapestBuilding= sd;
				}
				else
				{
					if(DataManager.getUnitTemplate(cheapestBuilding.unitType).cost > DataManager.getUnitTemplate(sd.unitType).cost )
					{
						cheapestBuilding = sd;
					}
				}
			}
		}	
		
		private function getRandomY():int
		{
			var arr:Array = Arrays.unionSet(fullRord,canUserRoard);
			return Arrays.getRandom(arr);
		}
		
		
		
	}
}