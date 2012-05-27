package com.fc.castle.gfx.battle
{
	import com.cell.gameedit.object.worldset.SpriteObject;
	import com.cell.gfx.game.CCD;
	import com.cell.util.CMath;
	import com.cell.util.Map;
	import com.cell.util.Util;
	import com.fc.castle.formual.FormualObject;
	import com.fc.castle.gfx.battle.ai.ForcePlayer;
	import com.fc.castle.gfx.battle.effect.SoulStar;
	import com.fc.castle.gfx.battle.effect.Transport;
	import com.fc.castlecraft.Config;
	
	import flash.display.BitmapData;
	import flash.display.BlendMode;
	import flash.events.Event;
	import flash.utils.getTimer;

	public class BattleMapHoly extends BattleMapObject
	{
		private var ccd 			: CCD;
		private var force_player 	: ForcePlayer;
		private var magic_tp		: Transport;
		
		public function BattleMapHoly(battle:StageBattle, data:SpriteObject, properties:Map)
		{
			super(battle, data, properties);
			
			this.ccd = getCSprite().getFrameImageBounds(0, 0);
		}
		
		override protected function render(buff:BitmapData, anim:int, frame:int, x:Number, y:Number):void
		{
			if (active) {
				super.render(buff, anim, frame, x, y + Math.sin(CMath.toDegree(getTimer()/8))*10);
			} else {
				super.render(buff, anim, frame, x, y);
				this.blendMode = BlendMode.MULTIPLY;
			}
		}
		
		
		
		override protected function onUpdate():void
		{
			super.onUpdate();
			
			if (active) {
				if (Config.ENABLE_EFFECT) {
					var child : SoulStar = new SoulStar(battle.getRes().effect_soulstar);
					child.x = Util.getRandom(ccd.X1, ccd.X2);
					child.y = Util.getRandom(ccd.Y1, ccd.Y2);
					addChild(child);
				}
			}
		}
		
		public function bindPlayer(player:ForcePlayer) : void
		{
			this.force_player = player;
			if (player.force == ForcePlayer.FORCE_A) {
				this.magic_tp = new Transport(battle, battle.getRes().effect_tpA);
			} else {
				this.magic_tp = new Transport(battle, battle.getRes().effect_tpB);
			}
			this.magic_tp.x = x;
			this.magic_tp.y = y;
			battle.world.addChild(magic_tp);
			
		}
		
		override public function get active() : Boolean
		{
			return force_player.active;
		}
		
		override public function addHP(v:Number) : Number
		{
			if (force_player.getHP() > 0) {
				battle.getWorld().spawnAttackNumber(
					v, 
					x + Util.getRandom(getCSprite().getVisibleLeft(), getCSprite().getVisibleRight()), 
					y + getCSprite().getVisibleTop(),
					true);
			}
			var curHP : Number = force_player.addHP(v);
			if (curHP <= 0) {
				battle.holyDesotyed(this);
			}
			return curHP;
		}
		
		override public function getHP() : Number
		{
			return force_player.getHP();
		}
		
	}
}