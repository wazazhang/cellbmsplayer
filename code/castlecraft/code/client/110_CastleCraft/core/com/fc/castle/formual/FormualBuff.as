package com.fc.castle.formual
{
	import com.fc.castle.data.template.BuffTemplate;

	public class FormualBuff
	{
		private var _data 		: BuffTemplate;
		
		private var _timer 		: int;
		
		
		public function FormualBuff(data:BuffTemplate) 
		{
			this._data = data;
			this._timer = 0;
		}
		
		public function get type() : int
		{
			return this._data.type;
		}
		
		public function get data() : BuffTemplate
		{
			return this._data;
		}
		
		public function isEnd() : Boolean
		{
			return this._timer >= _data.time;
		}
		
		public function onUpdate(target:FormualSoldier) : void
		{
			if (_data.burning != 0 && _data.burningInterval > 0) {
				if (_timer % _data.burningInterval == 0) {
					target.addHP(-_data.burning);
					onBurningTrigger();
				}
			}
			this._timer ++;
		}
				
		public function onStart(target:FormualSoldier) : void
		{
		
		}
		
		public function onStop(target:FormualSoldier) : void
		{
			
		}
		
		public function onBurningTrigger() : void
		{
			
		}
	}
}