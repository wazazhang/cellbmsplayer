package com.fc.castle.gfx.battle
{
	import com.cell.gfx.game.CSpriteBuffer;
	import com.cell.gfx.game.worldcraft.CellCSpriteBuffer;
	import com.cell.gfx.game.worldcraft.CellUnit;
	import com.cell.math.MathVector;
	import com.cell.util.CMath;
	import com.fc.castle.formual.Formual;
	import com.fc.castle.formual.FormualObject;
	import com.fc.castle.formual.FormualSoldier;
	import com.fc.castle.gfx.battle.res.AnimateEnum;
	import com.fc.castle.gfx.battle.res.EffectInfo;
	import com.fc.castlecraft.Config;
	
	import flash.display.BitmapData;
	import flash.display.BlendMode;
	import flash.geom.Point;

	public class BattleMissle extends BattleObject
	{
		private static var G 	: Number = 2.2;
		
		private var meta		: CellCSpriteBuffer;
		
		private var bs_src		: BattleSoldier 
		private var bs_target	: BattleUnit;
		
		private var effect_info : EffectInfo;
		
		private var _timer		: int;
		private var _running	: Boolean;
		
		private var vy 			: Number;
		private var timer_max 	: int;
		
		private var track_index : int;
		private var hurt_index	: int;
		
		private var direct 		: Number;
		
		private var move_degree	: Number;
		
		public function BattleMissle(battle:StageBattle, 
									 csprite_id:String, 
									 csprite:CSpriteBuffer,
									 worldX:Number, worldY:Number,
									 src:BattleSoldier, 
									 target:BattleUnit)
		{
			super(battle);
			
//			this.blendMode = BlendMode.SCREEN;
			this.bs_src = src;
			this.bs_target = target;
			this.effect_info = battle.battleRes.getEffectInfo(csprite_id);
			this.meta = new CellCSpriteBuffer(csprite.copy());
			this.addChild(meta);
			this.meta.y = src.unit_info.missle_lanch_rect.getMedianY();
			
			if (effect_info.blend!=null) {
				this.blendMode = effect_info.blend;
			}
			
			this.x = worldX;
			this.y = worldY;
			this.priority = target.priority + 1;
			this.track_index = meta.spr.findAnimateIndex(AnimateEnum.MISSLE_TRACK);
			this.hurt_index  = meta.spr.findAnimateIndex(AnimateEnum.SLASH_HURT);
			AnimateEnum.setMissleAnimate(meta, AnimateEnum.MISSLE_RUN);
			
			if (effect_info.action == EffectInfo.ACTION_PARA) {
				var distance : Number = MathVector.getDistance2Point(target.x, target.y, src.x, src.y);
				timer_max = distance / effect_info.speed;
				vy = - timer_max * G / 2;
			} else {
				vy = 0;
			}
			
			_timer = 0;
			_running = true;
		}
		
		override protected function onUpdate():void
		{
			meta.renderSelf();
			if (_running)
			{
				var dx : Number = bs_target.x; 
				var dy : Number = bs_target.y;
			
				var ddx : Number = dx - x;
				var ddy : Number = dy - y;
				var mdx : Number = Math.abs(ddx);
				var mdy : Number = Math.abs(ddy);
				
				move_degree = Math.atan2(ddy, ddx);
				
				if (mdx < effect_info.speed && mdy < effect_info.speed) 
				{
					if (!bs_target.active) 
					{
						if (parent != null) {
							parent.removeChild(this);
						}
					}
					else
					{
						// 击中
						Formual.onAttack(bs_src, bs_target);
						if (Config.ENABLE_EFFECT && hurt_index >= 0) 
						{
							battle.world.spawnBodyEffect(
								meta.getSprBuffer(), 
								bs_target, 
								AnimateEnum.MISSLE_HURT);
						}
						AnimateEnum.setMissleAnimate(meta, AnimateEnum.MISSLE_EXP);
						
					}
					_running = false;
				} 
				else 
				{
					var mx : Number = Math.cos(move_degree) * effect_info.speed;
					var my : Number = Math.sin(move_degree) * effect_info.speed;
					this.x += mx;
					this.y += my;
					
					if (effect_info.direct == EffectInfo.DIRECT_TARGET) {
						direct = CMath.toAngle(Math.atan2(my + vy, mx));
					}
					else if (effect_info.direct == EffectInfo.DIRECT_ROT) {
						direct += 10;
					}
					if (effect_info.action == EffectInfo.ACTION_PARA) {
						this.vy += G;
						meta.y += vy;
					}
					// tail
					if (Config.ENABLE_MISSLE_TAIL && track_index >= 0)
					{
						var tl : BattleMissleTail = new BattleMissleTail(
							battle,
							meta.getSprBuffer(), 
							AnimateEnum.MISSLE_TRACK, 
							this.x, this.y, 
							direct, meta.y,
							this.priority);
						world.addChildAt(tl, world.getChildIndex(this));
					}
					
				}
				meta.rotation = direct;
			}
			else 
			{
				if (meta.nextFrame() == 0) {
					if (parent != null) {
						parent.removeChild(this);
					}
				}
				if (Math.cos(move_degree) > 0) {
					meta.scaleX = 1;
				} else {
					meta.scaleX = -1;
				}
				meta.rotation = 0;
				
			}
			this._timer ++;
			
		}
		
		
	}
	
}


import com.cell.gfx.game.CSpriteBuffer;
import com.cell.gfx.game.worldcraft.CellCSpriteBuffer;
import com.fc.castle.gfx.battle.BattleObject;
import com.fc.castle.gfx.battle.StageBattle;

import flash.display.BitmapData;
import flash.display.BlendMode;

class BattleMissleTail extends BattleObject
{		
	private var meta : CellCSpriteBuffer;
	private var direct : Number;
	
	public function BattleMissleTail(battle:StageBattle,
									 spr:CSpriteBuffer, 
									 animName:String,
									 worldX:Number, 
									 worldY:Number,
									 direct:Number = 0, 
									 hi:Number = 0,
									 priority:Number = 0)
	{
		super(battle);
		meta = new CellCSpriteBuffer(spr.copy());
		meta.setCurrentAnimateName(animName);
		meta.setCurrentFrame(0);
		meta.rotation = direct;
		meta.y = hi;
		this.addChild(meta);
		//			this.blendMode = BlendMode.SCREEN;
		
		this.direct = direct;
		this.x = worldX;
		this.y = worldY;
		this.priority = priority;
	}
	
	override protected function onUpdate():void
	{
		meta.renderSelf();
		meta.rotation = direct;
		if (meta.nextFrame() == 0) {
			if (parent != null) {
				parent.removeChild(this);
			}
		}
	}
}