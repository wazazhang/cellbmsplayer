package com.fc.castle.gfx.battle.effect
{
	import com.cell.ui.ImageNumber;
	import com.cell.util.Util;
	import com.fc.castle.gfx.battle.BattleObject;
	import com.fc.castle.gfx.battle.StageBattle;

	public class EffectNumber extends BattleObject
	{
		private var timerMax : Number = 10;
		private var timer : int = 0;
		private var critical : Boolean;
		
		public function EffectNumber(battle:StageBattle, image:ImageNumber, value:Number, critical:Boolean=true)
		{
			super(battle);
			var number : ImageNumber = image.copy(value);
			number.x = -number.width/2;
			number.y = -number.height/2;
			addChild(number);
			this.critical = critical;
		}
		
		override protected function onUpdate():void
		{
			if (timer > timerMax) 
			{
				if (parent != null) {
					parent.removeChild(this);
				}
			} 
			else
			{
				if (critical) 
				{
					this.scaleY = this.scaleX = 0.5 + Math.sin(timer / timerMax * Math.PI);			
					this.y -= 1;
				}
				else 
				{
					this.alpha = 1 - timer / timerMax;
					this.y -= 2;
				}
			}
			timer ++; 
		}
		
		
	}
}