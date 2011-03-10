package com.gt.game
{
	import flash.display.Graphics;
	import flash.display.Sprite;
	import flash.events.Event;
	
	public class ALayer extends Sprite
	{
		static public var IsDebugMode : Boolean = true;
		
		private var MouseState : Boolean = false;
		
		
		public function ALayer()
		{
			addEventListener(Event.ADDED_TO_STAGE, addedToStageHandle);
			addEventListener(Event.REMOVED_FROM_STAGE, removeFromStageHandle);
		}
		
		private function addedToStageHandle(e:Event) : void 
		{
			addEventListener(Event.ENTER_FRAME, enterFrameHandle);
			addEventListener(Event.ENTER_FRAME, enterFrameHandle);
			added();
		}
		
		private function removeFromStageHandle(e:Event) : void 
		{
			removeEventListener(Event.ENTER_FRAME, enterFrameHandle);
			removeEventListener(Event.ENTER_FRAME, enterFrameHandle);
			removed();
		}
		
		private function enterFrameHandle(e:Event) : void 
		{
			if(root!=null){
				update();
				render(graphics);
			}
		}
		
		/**  abstract function */
		public function init() : void {}
		/**  abstract function */
		public function destory() : void {}
		
		
		/**  abstract function */
		public function added() : void {}
		/**  abstract function */
		public function removed() : void {}
		
		
		/**  abstract function */
		public function update() : void {}
		/**  abstract function */
		public function render(g:Graphics) : void {}
		
	}
}