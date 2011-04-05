package Class
{
	import flash.events.TimerEvent;
	import flash.utils.Timer;
	
	import mx.controls.ProgressBar;
	public class TimesCtr
	{
		
		
		private static const optionTime:int = 12;
		private static const  sumTime:int = 90;
		public static var oprTimer:Timer = new Timer(100,optionTime*10);
		public static var sumTimer:Timer = new Timer(100,sumTime*10);
		
		public static var optionTimeBar:ProgressBar;
		public static var sumTimeBar:ProgressBar;
		
		public function TimesCtr()
		{
			
		}
		public static function init():void
		{
			 oprTimer.addEventListener(TimerEvent.TIMER, oprTimerHandler);
			 sumTimer.addEventListener(TimerEvent.TIMER, sumTimerHandler);
			 
             oprTimer.addEventListener(TimerEvent.TIMER_COMPLETE, completeHandler);
             sumTimer.addEventListener(TimerEvent.TIMER_COMPLETE, completeHandler);   
		}
		
		
		public static function oprTimerHandler(event:TimerEvent):void
		{
            optionTimeBar.setProgress(oprTimer.delay * oprTimer.currentCount, optionTime*1000); 
		}
		
		public static function sumTimerHandler(event:TimerEvent):void
		{
            sumTimeBar.setProgress(sumTimer.delay * sumTimer.currentCount, sumTime*1000); 
		}	
		
		public static function completeHandler(event:TimerEvent):void
		{
			if(Game.checkHaveSendCard())
			{
			Game.gamer.reset();
			}
			Game.gamer.getOneCardFromCardpile();
			start();
			//tt.text = "time up";
		}
		public static function start():void
		{
			oprTimer.reset();
			sumTimer.reset();
			oprTimer.start();
			sumTimer.start();
		}
		
		public static  function reset():void
		{
			oprTimer.reset();
			oprTimer.start();
		}	
	}
}