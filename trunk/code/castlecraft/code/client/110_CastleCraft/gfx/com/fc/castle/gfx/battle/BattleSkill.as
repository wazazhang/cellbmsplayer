package com.fc.castle.gfx.battle
{
	
	import com.cell.gfx.game.worldcraft.CellCSpriteBuffer;
	import com.cell.util.Arrays;
	import com.fc.castle.data.SkillData;
	import com.fc.castle.data.template.Enums.SkillTargetType;
	import com.fc.castle.data.template.SkillTemplate;
	import com.fc.castle.formual.Formual;
	import com.fc.castle.gfx.battle.ai.ForcePlayer;
	import com.fc.castle.gfx.battle.res.AnimateEnum;
	import com.fc.castle.gfx.battle.res.EffectInfo;
	import com.fc.castle.gfx.battle.res.SkillBehavior;
	
	
	

	public class BattleSkill extends BattleObject
	{
		private var _uuid		: int;
		
		private var _sd			: SkillData;
		
		private var _tp			: SkillTemplate;
		
		private var _force		: ForcePlayer;
		
		private var _timer		: int;
		
		private var _targets	: Array;
		
		private var _behavior	: SkillBehavior;
		
		public function BattleSkill(battle:StageBattle,
									sd:SkillData,
									tp:SkillTemplate, 
									player:ForcePlayer,
									worldX:int, 
									worldY:int)
		{
			super(battle);
			this._uuid		= battle.getUniqueIndex();
			this.priority = BattleWorld.LAYER_LAND + 1;
			this._sd = sd;
			this._tp = tp;
			this._force = player;
			this.x = worldX;
			this.y = worldY;
			this._timer = 0;

			this._behavior = SkillBehavior.createBehavior(tp.spriteBehavior);
			this._behavior.init(this);
			this._behavior.onInit();
		}
		
		
		public function get uuid():int
		{
			return _uuid;
		}

		public function get skillData() : SkillData 
		{
			return _sd;
		}
		
		public function get skillTemplate() : SkillTemplate
		{
			return _tp;
		}
		
		public function get forcePlayer() : ForcePlayer
		{
			return _force;
		}
		
		public function get timer(): int 
		{
			return _timer;
		}
		
		public function get targets() : Array
		{
			return _targets;
		}
		
		
		
		override protected function onUpdate():void
		{
			this._behavior.onUpdate();
			
			_timer ++;
		}
		
		public function close() : void
		{
			if (parent != null) {
				parent.removeChild(this);
			}
		}
		
		public function pickUnit() : BattleSoldier
		{
			switch (_tp.targetType) 
			{
				case SkillTargetType.ENEMY:
					return battle.getWorld().pickForceSoldier(
						_force.force_enemy, x, y);
					
				case SkillTargetType.FIREND:
					return battle.getWorld().pickForceSoldier(
						_force.force, x, y);
					
				case SkillTargetType.BOTH:
					return battle.getWorld().pickSoldier(
						x, y);
					
				case SkillTargetType.RANDOM_ENEMY:
					return battle.getWorld().pickForceRandomSoldier(
						_force.force_enemy);
					
				case SkillTargetType.RANDOM_FIREND:
					return battle.getWorld().pickForceRandomSoldier(
						_force.force);
			}
			return null;
		}
		
		public function pickTargets() : void
		{
			this._targets = new Array();

			if (_tp.aoe == 0 || _tp.range == 0) {
				var picked : BattleSoldier = pickUnit();
				if (picked != null) {
					this.x = picked.x;
					this.y = picked.y;
					targets.push(picked);
				} else {
					trace("["+_tp.name+"] No picked unit select!");
				}
			}
			else 
			{
				var limit : int = _tp.aoe;
				if (limit < 0) {
					limit = int.MAX_VALUE;
				}
				switch (_tp.targetType) 
				{
					case SkillTargetType.BOTH:
						_targets = battle.getWorld().pickSoldiers(x, y, _tp.range, limit);
						break;
					
					case SkillTargetType.ENEMY:
						_targets = battle.getWorld().pickForceSoldiers(_force.force_enemy, 
							x, y, _tp.range, limit);
						break;
					
					case SkillTargetType.FIREND:
						_targets = battle.getWorld().pickForceSoldiers(_force.force, 
							x, y, _tp.range, limit);
						break;
					
					case SkillTargetType.RANDOM_ENEMY:
					case SkillTargetType.RANDOM_FIREND:
						trace("["+_tp.name+"] 设置多个目标不支持随机目标类型");
						break;
				}
			}
		}

		public function doSkill() : void
		{
			this.pickTargets();
			for each (var o:BattleSoldier in this.targets) {
				Formual.onSkillAttack(this.skillTemplate, _force, o);
			}
		}
		
	}
}