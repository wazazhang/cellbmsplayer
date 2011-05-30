package Class
{
	import flash.events.TimerEvent;
	import flash.utils.Timer;
	
	import mx.controls.ProgressBar;
	public class TimesCtr
	{
		
		
		public  var optionTime:int = 12;
		public  var sumTime:int;
		
		public  var oprTimer:Timer = new Timer(100,optionTime*10);
		public  var sumTimer:Timer = new Timer(100,sumTime*10);
		
		public  var optionTimeBar:ProgressBar;
		public  var sumTimeBar:ProgressBar;
		
		private  var oprColor:Number =  0x0000ff;
		private  var sumColor:Number =  0x0000ff;
		
		public function TimesCtr()
		{
			
		}
		/*
		public  function inits():void
		{
			 oprTimer.addEventListener(TimerEvent.TIMER, oprTimerHandler);
			 sumTimer.addEventListener(TimerEvent.TIMER, sumTimerHandler);
			 
             oprTimer.addEventListener(TimerEvent.TIMER_COMPLETE, completeHandler);
             sumTimer.addEventListener(TimerEvent.TIMER_COMPLETE, completeHandler);   
		}
		*/
		
		
		public  function oprTimerHandler(event:TimerEvent):void
		{
            optionTimeBar.setProgress(optionTime*800 - oprTimer.delay * oprTimer.currentCount, optionTime*800); 
			
			var point:Number = (oprTimer.delay * oprTimer.currentCount / (optionTime*800))
			
			oprColor = 0xff0000 * point; 
			
			oprColor = oprColor - oprColor % 0x010000;
			
			oprColor = oprColor + 0xff * (1-point);
			
			optionTimeBar.setStyle("barColor",oprColor);
		}
		
		public  function sumTimerHandler(event:TimerEvent):void
		{
            sumTimeBar.setProgress(sumTime*800 - sumTimer.delay * sumTimer.currentCount, sumTime*800); 
			
			var point:Number = (sumTimer.delay * sumTimer.currentCount / (sumTime*800))
			
			sumColor = 0xff0000 * point; 
			
			sumColor = sumColor - sumColor % 0x010000;
			
			sumColor = sumColor + 0xff * (1-point);
			
			sumTimeBar.setStyle("barColor",sumColor);
			
			
			var d:Date = new Date();
			Server.timer = d.getSeconds();
			Server.timerStr = (sumTimer.delay * sumTimer.currentCount) + '/' + (sumTime*1000*4/5);
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
			
			optionTimeBar.setProgress(0, optionTime*1000*4/5); 
			sumTimeBar.setProgress(0, optionTime*1000*4/5); 
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
		
		public  function oprTimerSet(time:int):void
		{
			oprTimer = new Timer(100, time/100);
			optionTime = time/1000;
			oprTimer.addEventListener(TimerEvent.TIMER, oprTimerHandler);
			oprTimer.addEventListener(TimerEvent.TIMER_COMPLETE, completeHandler);   
		}
	}
}