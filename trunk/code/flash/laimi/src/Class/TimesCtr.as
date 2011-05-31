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
			
			
			optionTimeBar.setStyle("barColor",getColorByPoint(point));
		}
		
		public  function sumTimerHandler(event:TimerEvent):void
		{
            sumTimeBar.setProgress(sumTime*800 - sumTimer.delay * sumTimer.currentCount, sumTime*800); 
			
			var point:Number = (sumTimer.delay * sumTimer.currentCount / (sumTime*800))
			sumTimeBar.setStyle("barColor",getColorByPoint(point));
			
			
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
		
		
		public static function getColorByPoint(val:Number):Number
		{
			var color:Number
			
			if(val>0.5)	
			{
				color = 0xff0000;	
			}
			else
			{
				color = 0xff0000 * val * 2; 
				color = color - color % 0x010000;
			}	
			
			
			if(val<0.5)
			{
				color = color + 0x00ff00;
			}	
			else
			{
				color = color + 0xff00 * (1-val)*2;
				color = color - color % 0x000100;
			}
			return color
		}
		
	}
}