package com.fc.castle.gfx.battle
{
	import com.cell.gfx.CellSprite;
	import com.cell.gfx.game.CCD;
	import com.cell.gfx.game.CSprite;
	import com.cell.gfx.game.CSpriteBuffer;
	import com.cell.math.IVector2D;
	import com.cell.math.MathVector;
	import com.cell.math.TVector2D;
	import com.cell.ui.ImageBox;
	import com.cell.ui.SimpleProgress;
	import com.cell.util.CMath;
	import com.cell.util.Map;
	import com.cell.util.StringUtil;
	import com.cell.util.Util;
	import com.fc.castle.data.SoldierData;
	import com.fc.castle.data.template.BuffTemplate;
	import com.fc.castle.data.template.Enums.AttackType;
	import com.fc.castle.data.template.Enums.FightType;
	import com.fc.castle.data.template.Enums.SkillSpecialEffect;
	import com.fc.castle.data.template.UnitTemplate;
	import com.fc.castle.formual.Formual;
	import com.fc.castle.formual.FormualPlayer;
	import com.fc.castle.formual.FormualSoldier;
	import com.fc.castle.gfx.battle.ai.ForcePlayer;
	import com.fc.castle.gfx.battle.buff.Buff;
	import com.fc.castle.gfx.battle.effect.BuffEffect;
	import com.fc.castle.gfx.battle.effect.EffectNumber;
	import com.fc.castle.gfx.battle.effect.SoulStars;
	import com.fc.castle.gfx.battle.event.SoldierDeadEvent;
	import com.fc.castle.gfx.battle.res.AnimateEnum;
	import com.fc.castle.gfx.battle.res.UnitInfo;
	import com.fc.castle.gfx.battle.ui.BuffState;
	import com.fc.castle.net.client.DataManager;
	import com.fc.castle.res.CResourceManager;
	import com.fc.castle.res.Res;
	import com.fc.castlecraft.Config;
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.BlendMode;
	import flash.display.Sprite;
	import flash.geom.Point;
	import flash.geom.Rectangle;
	

	public class BattleSoldier extends BattleUnit implements FormualSoldier
	{
//		private static const FRAME_COMP : int = 1
		
		private var _uuid	: int;
		private var sd		: SoldierData;
		private var tp		: UnitTemplate;
		
		private var lb_hp	: SimpleProgress;
		private var lb_buff	: BuffState;
		
		
		private var timer	: int;
		
		/**出生时在的轨道*/
		private var _trackY : int;
		private var _force 	: int;		
		private var _player : ForcePlayer;

		/***/
		private var _hp 	: Number;
		private var _hp_max	: Number;
		private var _mp		: Number;

		
		private static const STATE_STAND		: int = 0x00;
		private static const STATE_RUNNING 		: int = 0x01;
		private static const STATE_ATTACK 		: int = 0x02;

		private static const STATE_DEAD	 		: int = 0x10;
		private static const STATE_GARBAGE		: int = 0x11;
		private var _state : int = 0;
		
		
		private var target_player	: ForcePlayer;
		private var target_pos		: IVector2D;
		
		/**如果发射导弹，则存储导弹对象精灵*/
		internal var effect_spr		: CSpriteBuffer;
		
		internal var unit_info		: UnitInfo;
		internal var ccd_body		: CCD;
		
		
		private var next_blend		: String;
		private var next_blend_time	: int;
		
		private var buffs 			: Map = new Map();
		
		private var buff_state		: BuffTemplate = new BuffTemplate();
		
		private var move_speed		: Number = 0;
		
		private var cool_down		: int = 0;
		
		public function BattleSoldier(battle:StageBattle, sd:SoldierData, player:ForcePlayer, worldX:int, worldY:int)
		{
			this._uuid		= battle.getUniqueIndex();
			this._player 	= player;
			this._force 	= player.force;
			this.sd 		= sd;
			this.tp 		= DataManager.getUnitTemplate(sd.unitType);
			
			super(battle, battle.battleRes.getActorSprite(tp));
			
			this.x = worldX;
			this.y = worldY;
			this._trackY = int(worldY/battle.world.getCellH());
			this.priority = BattleWorld.LAYER_LAND;
			
			// label
			{
				lb_hp = new SimpleProgress(40, 5, 0, 1);
				lb_hp.setColor(0x000000, 0x000000, 0x00ff00);
				lb_hp.x = -20;
				lb_hp.y = getCSprite().getVisibleTop();
				addChild(lb_hp);
				
				lb_buff = new BuffState();
				lb_buff.x = 0;
				lb_buff.y = getCSprite().getVisibleTop() - 1;
				addChild(lb_buff);
				
				var shadow : Bitmap = new Bitmap(battle.getRes().gfx_unit_shadow);
				shadow.x = -shadow.width/2;
				shadow.y = -shadow.height/2;
				addChildAt(shadow, 0);
			}
			// missle and attack
			{
				this.unit_info = battle.battleRes.getUnitInfo(tp.csprite_id);
				this.ccd_body = getCSprite().getFrameCDBounds(
					getCSprite().findAnimateIndex(AnimateEnum.ACTOR_STAND), 0,
					CSprite.CD_TYPE_MAP);	
				if (ccd_body.X1 == ccd_body.X2 || ccd_body.Y1 == ccd_body.Y2) {
					throw new Error("Null Body CD!");
				}
				// 计算被导弹命中的点或近战光影
				try {
					this.effect_spr = battle.battleRes.getEffectSpriteBuffer(tp.attackEffect);
				} catch(err:Error) {
					trace("单位: ["+tp.name+"]没有攻击特效 : ");
				}
			}
			this.target_player 	= battle.world.getEnemyForce(force);
			this.target_pos 	= new TVector2D(target_player.holy.x, worldY);
			
			this._hp 		= tp.hp;
			this._hp_max 	= tp.hp;
			
			this._mp 		= tp.mp;
			
			this.move_speed	= tp.moveSpeed;
			this.cool_down	= tp.attackCD;
			
			startRunning();
		}
		
		public function get uuid():int
		{
			return _uuid;
		}

		override protected function render(buff:BitmapData, anim:int, frame:int, x:Number, y:Number):void
		{
			this._bitmap.x = x;
			this._bitmap.y = y;
			this._bitmap.bitmapData = buff;
			
			if (next_blend_time > 0) {
				this._bitmap.blendMode = next_blend;
				next_blend_time--;
			} else {	
				this._bitmap.blendMode = BlendMode.NORMAL;
			}
		}

//		-------------------------------------------------------------------------------------------------------------

		public function getBody() : CCD
		{
			return ccd_body;
		}
		
		public function getSoldierData() : SoldierData
		{
			return sd;
		}
		
		public function getUnitTemplate() : UnitTemplate
		{
			return tp;
		}
		
		public function getSpriteInfo() : UnitInfo
		{
			return unit_info;
		}
		
		public function get force() : int
		{
			return _force;
		}
		
		public function get trackY() : int 
		{
			return _trackY;
		}
		
//		-------------------------------------------------------------------------------------------------------------

		public function getBuffState() : BuffTemplate
		{
			return this.buff_state;
		}
		
		public function addBuff(type:int) : void
		{
			try {
				var buff : Buff = new Buff(type);
				buffs.put(type, buff);
				buff.onStart(this);
				resetBuff();
				trace("add buff : " + buff.data.name);
				lb_buff.addBuff(buff.data);
			} catch (e:Error) {
				trace(e.message);
			}
		}
		
		public function removeBuff(type:int) : void
		{
			var buff : Buff = buffs.remove(type);
			if (buff != null) {
				buff.onStop(this);
				lb_buff.removeBuff(type);
			}
			resetBuff();
			trace("remove buff : " + buff.data.name);
		}
		
		private function resetBuff() : void
		{
			var add_max_old : Number = _hp_max - tp.hp;
			
			this.buff_state = Formual.createBuffState(buffs);
			
			var add_max	: Number = 0;
			// 血量增加,[绝对值]
			add_max += buff_state.addHP;
			// 血量增加％,[百分比]
			add_max += tp.hp * (buff_state.enhanceHP / 100.0);
			
			this._hp_max = tp.hp + add_max;
		
			this._hp += _hp * ((add_max - add_max_old) / tp.hp);
			
			this.move_speed = tp.moveSpeed + tp.moveSpeed * (buff_state.moveRating / 100.0);
			this.move_speed = Math.max(0, this.move_speed);
			
			this.cool_down = tp.attackCD - tp.attackCD * (buff_state.hasteRating / 100.0);
			this.cool_down = Math.max(1, this.cool_down);
		}
		
		private function updateBuff() : void
		{
			for each (var buff : Buff in buffs) {
				buff.onUpdate(this);
			}
			for each (var buffe : Buff in buffs.values()) {
				if (buffe.isEnd()) {
					removeBuff(buffe.type);
				}
			}
		}
		
		
		public function makeSpecialEffect(sender:FormualPlayer, sp:int) : void
		{
			switch (sp) {
			case SkillSpecialEffect.CURSE:
				if (sender != _player) {
					for each (var buff_c : Buff in buffs.values()) {
						if (!buff_c.data.debuff) {
							removeBuff(buff_c.type);
						}
					}
				}
				break;
			case SkillSpecialEffect.PURGE:
				if (sender == _player) {
					for each (var buff_p : Buff in buffs.values()) {
						if (buff_p.data.debuff) {
							removeBuff(buff_p.type);
						}
					}
				}
				break;
			case SkillSpecialEffect.RENEGADE:
				
				break;
			}
		}
		
		
//		-------------------------------------------------------------------------------------------------------------

		/**该单位是否活动，即是否在场景里，必须有生命值*/
		override public function get active() : Boolean
		{
			if (_hp > 0 && parent is BattleWorld) {
				return true;
			} else {
				return false;
			}
		}
		
		override public function addHP(v:Number) : Number
		{
			if(_hp<=0)
				return _hp;
			
			if (v < 0 && _hp > 0) {
				battle.getWorld().spawnAttackNumber(v, 
					x + Util.getRandom(-10, 10), 
					y + getCSprite().getVisibleTop());
			} else if (v > 0) {
				battle.getWorld().spawnRecoverNumber(v, 
					x + Util.getRandom(-10, 10), 
					y + getCSprite().getVisibleTop());
			}
			
			if (_hp + v <= 0) {
				startDead();
				_hp = 0;
			} else if (_hp + v > _hp_max) {
				_hp = _hp_max;
			} else {
				_hp += v;
			}
			setBlendState(BlendMode.ADD, 3);
			return _hp;
		}
		
		override public function getHP() : Number
		{
			return _hp;
		}
		
		override public function getMaxHP() : Number
		{
			return _hp_max;
		}
		
		
		
		/**已不需要存在于场景中*/
		public function isGarbage() : Boolean
		{
			return _state == STATE_GARBAGE;
		}
		
//		-----------------------------------------------------------------------------------------------------------------------------
		
		/**找到离自己最近的，并且同一轨道上的敌人*/
		private function getNearTrackEnemy() : BattleSoldier
		{
			var trackEnemies : Array = battle.world.getTrackEnemy(_force, _trackY);
			if (trackEnemies.length>0) {
				return getNearEnemy(trackEnemies);
			}
			return null;
		}
		
		private function getNearEnemy(trackEnemies : Array) : BattleSoldier
		{
			var enemy : BattleSoldier = null;
			var distance : Number = int.MAX_VALUE;
			for each (var e : BattleSoldier in trackEnemies) {
				if (e.active) {
					var d : Number = Math.abs(this.x - e.x);
					if (d < distance) {
						if (e._player.region_data) {
							
						}
						distance = d;
						enemy = e;
					}
				}
			}
			return enemy;
		}
		
		private function findNextEnemy() : void 
		{
			if (attack_enemy == null || !attack_enemy.active) 
			{
				attack_enemy = getNearTrackEnemy();
			}
			if (attack_enemy == null) 
			{
				attack_enemy = target_player.holy;
			}
		}
		
		private function moveToTarget(dst_pos : IVector2D) : void
		{
			// 横向移动
			if (dst_pos.getVectorX() > x)  {
				this.x += (move_speed);
				AnimateEnum.setUnitDirect(this, 1);
			} else if (dst_pos.getVectorX() < x) {
				this.x -= (move_speed);
				AnimateEnum.setUnitDirect(this, -1);
			}
		}
		
		public function getDirect() : Number
		{
			return -scaleX;
		}
		
//		---------------------------------------------------------------------------------------------------------------
		
		private function nextFrameComp() : int
		{
			return super.nextFrame();
		}
		
		private function nextCycFrameComp() : int
		{
			return super.nextCycFrame();
		}
		
		override protected function onUpdate():void
		{
			if (isGarbage() && parent != null) {
				parent.removeChild(this);
			} else {
				timer ++;
				lb_hp.setValue(getHP(), 0, getMaxHP());
				switch(_state) {
					case STATE_RUNNING:
						updateRuning();
						break;
					case STATE_ATTACK:
						updateAttack();
						break;
					case STATE_DEAD:
						updateDead();
						break;
				}
				updateBuff();
			}
		}
		
		
		public function setBlendState(blend:String, time:int) : void
		{
			this.next_blend = blend;
			this.next_blend_time = time;
		}
		
//		------------------------------------------------------------------------------------------------------------------
//		State Running 
//		------------------------------------------------------------------------------------------------------------------
		
		internal function startRunning() : void
		{
			if (_hp<=0) {
				return;
			}
			this._state = STATE_RUNNING;
			AnimateEnum.setActorAnimate(this, AnimateEnum.ACTOR_WALK);
//			trace("start running");
		}
		
		private function updateRuning() : void
		{
			this.nextCycFrameComp();
			
//			if (CMath.isInclude(this.x, target_player.region_bounds.x, target_player.region_bounds.x + target_player.region_bounds.width))
//			var target_direct : Number = target_player.holy.x - this.x;
			if (attack_enemy == target_player.holy && !target_player.active)
			{
				startDead();
//				battle.world.removeChild(this);
			}
			else
			{
				if (attack_enemy == target_player.holy && battle.world.getTrackSoldierCount(target_player.force, _trackY) > 0) {
					attack_enemy = null;
				}
				
				findNextEnemy();
				
				if (attack_enemy != null) 
				{
					// 目标单位在攻击范围内
					if (Math.abs(attack_enemy.x - x) <= tp.attackRange) {
						startAttack(attack_enemy);
					} else {
						moveToTarget(attack_enemy);
					}
				}
				else 
				{
					moveToTarget(target_pos);
				}
			}
			
		}

		
//		------------------------------------------------------------------------------------------------------------------
//		State Attack 
//		------------------------------------------------------------------------------------------------------------------
		
		private var attack_timer 	: int;
		private var attack_down 	: Boolean;		
		private var attack_enemy	: BattleUnit;

		
		internal function startAttack(target_enemy : BattleUnit) : void
		{
			if (_hp<=0) {
				return;
			}
			this._state = STATE_ATTACK;
			AnimateEnum.setActorAnimate(this, AnimateEnum.ACTOR_ATTACK);
			AnimateEnum.setUnitDirect(this, target_enemy.x - x);
			this.setCurrentFrame(0);
			this.attack_timer = 0;
			this.attack_down = false;
			this.attack_enemy = target_enemy;
//			trace("start attack");
		}
		
		private function updateAttack() : void
		{
			/*如果攻击速度过快，则以结束攻击动作时直接判断*/
			if (attack_timer >= cool_down) {
				if (!attack_down) {
					doAttack();
				}
				if (!attack_enemy.active) {
					startRunning();
				} else {
					if (attack_enemy == target_player.holy && battle.world.getTrackSoldierCount(target_player.force, trackY)>0) {
						attack_enemy = null;
						startRunning();
					} else {
						var dlen : Number = Math.abs(attack_enemy.x - x);
						if (dlen <= tp.attackRange) {
							startAttack(attack_enemy);
						} else {
							startRunning();
						}
					}
				}
			} 
			else 
			{
				if (attack_down == false) 
				{
					if (getCurrentFrame() == unit_info.attack_check_frame) 
					{
						doAttack();
					}
					else if (nextFrameComp()==0) 
					{
						doAttack();
					}
				}
				else 
				{
					if (nextFrameComp() == 0) {
						AnimateEnum.setActorAnimate(this, AnimateEnum.ACTOR_STAND);
					}
				}
			}
			attack_timer ++;
		}
		
		
		private function doAttack() : void
		{
			attack_down = true;
			switch(tp.fightType) {
			case FightType.MISSILE:
				if (attack_enemy.active) {
					if (effect_spr==null) {
						Formual.onAttack(this, attack_enemy);
					} else {
						var missle : BattleMissle = new BattleMissle(battle, 
							tp.attackEffect, 
							effect_spr,
							x, y, 
							this, attack_enemy);
						this.world.addChild(missle);
					}
				}
				break;
			case FightType.NORMAL:	
				Formual.onAttack(this, attack_enemy);
				if (Config.ENABLE_EFFECT && effect_spr!=null) 
				{
					var slash : BuffEffect = new BuffEffect(
						effect_spr, 
						AnimateEnum.SLASH_RUN,
						unit_info.missle_lanch_rect.X1, 
						unit_info.missle_lanch_rect.Y1);
					this.addChild(slash);
					
					battle.world.spawnBodyEffect(
						effect_spr, 
						attack_enemy, 
						AnimateEnum.SLASH_HURT);
				} 
				break;
			default:
				Formual.onAttack(this, attack_enemy);
				break;
			}
		}
		
//		------------------------------------------------------------------------------------------------------------------
//		State Attack Holy
//		------------------------------------------------------------------------------------------------------------------

//		internal function startAttackHoly() : void
//		{
//			if (_hp<=0) {
//				return;
//			}
//			this._state = STATE_ATTACK_HOLY;
//			AnimateEnum.setActorAnimate(this, AnimateEnum.ACTOR_ATTACK);
//			AnimateEnum.setUnitDirect(this, target_player.holy.x - x);
//			this.setCurrentFrame(0);
//			this.timer_attack = 0;
//			this.attack_down = false;
//		}
//		
//		private function updateAttackHoly() : void
//		{
//			/*如果攻击速度过快，则以结束攻击动作时直接判断*/
//			if (timer_attack >= tp.attackCD) {
//				if (!attack_down) {
//					doAttackHoly();
//				}
//				if (!target_player.active) {
////					startRunning();
//				} else {
//					var dlen : Number = Math.abs(target_player.holy.x - x);
//					if (dlen <= tp.attackRange) {
//						startAttackHoly();
//					} else {
////						startRunning();
//					}
//				}
//			} 
//			else 
//			{
//				if (attack_down == false) 
//				{
//					if (getCurrentFrame() == unit_info.attack_check_frame) 
//					{
//						doAttackHoly();
//					}
//					else if (nextFrameComp()==0) 
//					{
//						doAttackHoly();
//					}
//				}
//				else 
//				{
//					if (nextFrameComp() == 0) {
//						AnimateEnum.setActorAnimate(this, AnimateEnum.ACTOR_STAND);
//					}
//				}
//			}
//			timer_attack ++;
//		}
//		
//		private function doAttackHoly() : void
//		{
//			attack_down = true;
//			switch(tp.fightType) {
//				case FightType.MISSILE:
//					if (target_player.active) {
//						if (effect_spr==null) {
//							Formual.onAttack(this, target_player);
//						} else {
//							var missle : BattleMissle = new BattleMissle(battle, 
//								tp.attackEffect, 
//								effect_spr,
//								x, y, 
//								this, target_player.holy);
//							this.world.addChild(missle);
//						}
//					}
//					break;
//				case FightType.NORMAL:	
//					Formual.onAttack(this, target_enemy);
//					if (Config.ENABLE_EFFECT && effect_spr!=null) 
//					{
//						battle.world.spawnEffect(effect_spr, 
//							x + unit_info.missle_lanch_offset.x * scaleX, 
//							y + unit_info.missle_lanch_offset.y, 
//							AnimateEnum.SLASH_RUN,
//							target_enemy.x - x,
//							BattleWorld.CELLH);
//						
//						battle.world.spawnEffectBody(
//							effect_spr, 
//							target_enemy, 
//							AnimateEnum.SLASH_HURT,
//							target_enemy.x - x);
//					} 
//					break;
//				default:
//					Formual.onAttack(this, target_enemy);
//					break;
//			}
//		}
		
//		------------------------------------------------------------------------------------------------------------------
//		State Dead
//		------------------------------------------------------------------------------------------------------------------
		
		private const 	dead_timer_max 	: int = 10;
		private var 	dead_timer 		: int = 0;
		
		internal function startDead() : void
		{
			this._state = STATE_DEAD;
			this.dead_timer = 0;
			if (Config.ENABLE_EFFECT) {
				battle.getWorld().addChild(
					new SoulStars(battle, this, dead_timer_max));
			}
			
			battle.addBattleSoldierDeadEvent(this);
		}
		
		
		private function updateDead() : void
		{
			if (this.alpha > 0) {
				this.alpha -= 0.1;
			}
			if (this.dead_timer >= dead_timer_max) {
				_player.dispatchEvent(new SoldierDeadEvent(this));
				this._state = STATE_GARBAGE;
			}
			this.dead_timer++;
		}
		
		
		public function getBodyMiddleX():Number
		{
			return x  + getBody().X1 + getBody().getWidth() * 0.5
		}
		
		public function getBodyMiddleY():Number
		{
			return y  + getBody().Y1 + getBody().getHeight() * 0.5
		}
		
//		------------------------------------------------------------------------------------------------------------------
//		
//		------------------------------------------------------------------------------------------------------------------
		
	
		
	}
}