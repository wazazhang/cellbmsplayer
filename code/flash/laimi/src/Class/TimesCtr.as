package Class
{
	import flash.events.TimerEvent;
	import flash.utils.Timer;
	
	import mx.controls.ProgressBar;
	public class TimesCtr
	{
		
		
		private  const optionTime:int = 12;
		private  var sumTime:int = 90;
		public  var oprTimer:Timer = new Timer(100,optionTime*10);
		public  var sumTimer:Timer = new Timer(100,sumTime*10);
		
		public  var optionTimeBar:ProgressBar;
		public  var sumTimeBar:ProgressBar;
		
		public function TimesCtr()
		{
			
		}
		public  function inits():void
		{
			 oprTimer.addEventListener(TimerEvent.TIMER, oprTimerHandler);
			 sumTimer.addEventListener(TimerEvent.TIMER, sumTimerHandler);
             oprTimer.addEventListener(TimerEvent.TIMER_COMPLETE, completeHandler);
             sumTimer.addEventListener(TimerEvent.TIMER_COMPLETE, completeHandler);   
		}
		
		
		public  function oprTimerHandler(event:TimerEvent):void
		{
            optionTimeBar.setProgress(oprTimer.delay * oprTimer.currentCount, optionTime*1000); 
		}
		
		public  function sumTimerHandler(event:TimerEvent):void
		{
            sumTimeBar.setProgress(sumTimer.delay * sumTimer.currentCount, sumTime*1000); 
		}	
		
		public  function completeHandler(event:TimerEvent):void
		{
//			if(Game.checkHaveSendCard())
//			{
//				Game.gamer.reset();
//			}
//			Game.gamer.getOneCardFromCardpile();
			
			//start();
			//tt.text = "time up";
		}
		public  function start():void
		{
			oprTimer.reset();
			sumTimer.reset();
			oprTimer.start();
			sumTimer.start();
		}
		
		public   function reset():void
		{
			oprTimer.reset();
			oprTimer.start();
		}
		
		public  function stop():void{
			oprTimer.reset();
			sumTimer.reset();
			oprTimer.stop();
			sumTimer.stop();
		}
		
		public  function sumTimerSet(time:int):void
		{
			sumTimer = new Timer(100, time/100);
			sumTime = time/1000;
			sumTimer.addEventListener(TimerEvent.TIMER, sumTimerHandler);
			sumTimer.addEventListener(TimerEvent.TIMER_COMPLETE, completeHandler);   
		}
	}
}