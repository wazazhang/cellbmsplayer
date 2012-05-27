package com.fc.castle.gfx.battle.ai
{
	import com.cell.ui.ImageNumber;
	import com.cell.util.Map;
	import com.fc.castle.data.SoldierData;
	import com.fc.castle.data.template.Enums.AttackType;
	import com.fc.castle.data.template.Enums.FightType;
	import com.fc.castle.data.template.UnitTemplate;
	import com.fc.castle.formual.Formual;
	import com.fc.castle.gfx.battle.BattleBuilding;
	import com.fc.castle.net.client.DataManager;
	
	import mx.messaging.channels.StreamingAMFChannel;
	import mx.utils.NameUtil;

	public class AIComputer
	{
		public function AIComputer()
		{
			
		}
		
		/*public static function CountThreaten(sourcUnitType:int,targetUnitType:int):Number
		{
			
			var sourc:UnitTemplate = DataManager.getUnitTemplate(sourcUnitType);
			var target:UnitTemplate = DataManager.getUnitTemplate(targetUnitType);
			
			var attackThreaten:Number = ( sourc.attack / sourc.attackCD ) * getAttackFactor(sourc.attackType,target.defenseType);
			var	defenceThreaten:Number = ( target.attack / target.attackCD ) * getAttackFactor(target.attackType,sourc.defenseType);
				
			
			//return (attackThreaten - defenceThreaten) / sourc.cost;
			
		}
		*/
		
		
		private static const remote_add:Number = 1.5
		public static function CountThreaten(sourcUnitType:int,targetUnitType:int):Number
		{
			var sourc:UnitTemplate = DataManager.getUnitTemplate(sourcUnitType);
			var target:UnitTemplate = DataManager.getUnitTemplate(targetUnitType);
			
			var attackThreaten:Number =  Formual.countAttackUnit(sourc,target) / sourc.attackCD;
			var	defenceThreaten:Number =  Formual.countAttackUnit(target,sourc) / target.attackCD;
			
			var time:Number = sourc.hp / defenceThreaten * attackThreaten / sourc.cost * (sourc.fightType == FightType.MISSILE?remote_add:1);
			
			//var val:Number = (attackThreaten - defenceThreaten);
			//trace(sourc.name +"对"+target.name + "造成的威胁值为:"+time);
			return time * time;
		}
		
		private var buildingMap:Map = new Map();
		
		
		public static function getBuilding(enemy:BattleBuilding,soldiers:Array):BuildPlane
		{
			var map:Map = new Map();
			var mapBuilding:Map = new Map();
			
			
			var sum:Number = 0;
			for each (var soldier:SoldierData    in   soldiers)
			{
				var t:Number = AIComputer.CountThreaten(soldier.unitType,enemy.getSoldierData().unitType);
				addBuilding(soldier,t);
			}	
			
			
			var r:Number = Math.random()*sum;
			
			for(var key:String  in map)
			{
				var val:Number =  map.get(key)
				if(r<val)
				{
					return new BuildPlane(mapBuilding.get(key),val,enemy.trackY);	
				}
				else
				{
					r -= val;
				}
			}	
			return null;
			
			
			function addBuilding(type:SoldierData,rate:Number):void
			{
				sum +=rate;
				map.put(type.unitType,rate);
				
				mapBuilding.put(type.unitType,type)
			}
		}
		
	}
}