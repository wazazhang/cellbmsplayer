package com.fc.castle.formual
{
	import com.cell.util.Map;
	import com.cell.util.StringUtil;
	import com.fc.castle.data.SoldierData;
	import com.fc.castle.data.template.BuffTemplate;
	import com.fc.castle.data.template.Enums.AttackType;
	import com.fc.castle.data.template.Enums.SkillSpecialEffect;
	import com.fc.castle.data.template.FormualMap;
	import com.fc.castle.data.template.SkillTemplate;
	import com.fc.castle.data.template.UnitTemplate;

	public class Formual
	{
		private static var formual_map : FormualMap;
		private static var ammor_map : Map;
		
		public static function init(map : FormualMap) : void
		{
			trace("-----------------------------------------------------------");
			trace("- Init Formual");
			trace("-----------------------------------------------------------");
			formual_map = map;
			ammor_map = new Map();
			for each (var o : * in formual_map.ammor_map) {
				var args : Array = o;
				var attackType : int = int(args[0]);
				var defenseType : int = int(args[1]);
				if (!ammor_map.contains(attackType)) {
					ammor_map.put(attackType, new Map());
				}
				ammor_map[attackType][defenseType] = Number(args[2]);
			}
		}
		
		public static function onAttack(src:FormualSoldier, target:FormualObject) : void
		{
			if (target is FormualSoldier) {
				onAttackUnit(src, target as FormualSoldier);
			} else {
				var stp : UnitTemplate 	= src.getUnitTemplate();
				var ssd : SoldierData 	= src.getSoldierData();
				var dhp : Number = stp.attack;
				target.addHP(-dhp);
			}
		}
		
		public static function onAttackUnit(src:FormualSoldier, target:FormualSoldier) : void
		{
			var stp : UnitTemplate 	= src.getUnitTemplate();
			var sbs : BuffTemplate	= src.getBuffState();
			var ssd : SoldierData 	= src.getSoldierData();
			
			var dtp : UnitTemplate 	= target.getUnitTemplate();
			var dbs : BuffTemplate	= target.getBuffState();
			var dsd : SoldierData 	= target.getSoldierData();
			
			
			var atk : Number = stp.attack;
			// 攻击增加直接,[绝对值]
			atk += sbs.addDamage;
			// 攻击增加％,[百分比]
			atk += stp.attack * (sbs.enhanceDamage / 100.0);
			
			// 伤害 ＝ 攻击力 ＊ 攻防；
			var ed : Number = ammor_map[stp.attackType][dtp.defenseType];
			
			var dhp : Number = atk * ed;
			
			// 伤害减免%,[百分比]
			dhp -= dhp * (dbs.damageReduction / 100.0);
			
			target.addHP(-dhp);
		}
		
		
		public static function countAttackUnit(src:UnitTemplate , target:UnitTemplate) : Number
		{
			var ed : Number = ammor_map[src.attackType][target.defenseType];
			var dhp : Number = src.attack * ed;
			return dhp;
		}
		
		public static function onSkillAttack(tp:SkillTemplate, src:FormualPlayer, target:FormualSoldier) : void
		{
			target.addHP(-tp.attack);
			
			for each (var btype:int in tp.buffList) {
				target.addBuff(btype);
			}
			
			for each (var sp:int in tp.specialEffects) {
				target.makeSpecialEffect(src, sp);
			}
		}
		
		
		public static function createBuffState(buffs:Map) : BuffTemplate
		{
			var buff_state : BuffTemplate = new BuffTemplate();
			
			for each (var buff : FormualBuff in buffs)
			{
				var bt : BuffTemplate = buff.data;
				
				buff_state.addDamage 		+= bt.addDamage;
				buff_state.addDefense 		+= bt.addDefense;
				buff_state.addHP			+= bt.addHP;
				buff_state.damageReduction	+= bt.damageReduction;
				buff_state.enhanceDamage	+= bt.enhanceDamage;
				buff_state.enhanceDefens	+= bt.enhanceDefens;
				buff_state.enhanceHP		+= bt.enhanceHP;
				buff_state.hasteRating		+= bt.hasteRating;
				buff_state.moveRating		+= bt.moveRating;
			}
			
			return buff_state;
		}
	}
}