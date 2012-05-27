package com.fc.castle.gfx.battle.buff
{
	import com.fc.castle.data.template.BuffTemplate;
	import com.fc.castle.formual.FormualBuff;
	import com.fc.castle.net.client.DataManager;

	public class Buff extends FormualBuff
	{
		
		public function Buff(type:int) 
		{
			var template : BuffTemplate = DataManager.getBuffTemplate(type);
			super(template);
			if (template == null) {
				throw new Error("there is no buff template ["+type+"]");
			}
		}
		
	}
}