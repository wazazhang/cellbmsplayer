package Class
{
	import flash.events.TimerEvent;
	import flash.utils.Timer;
	
	import mx.controls.Alert;
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
		
		private var oprStartTime:Date; 
		private var sumStartTime:Date; 
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
			var s:Number = new Date().getTime() - oprStartTime.getTime()  ;
            optionTimeBar.setProgress(optionTime - s, optionTime); 
			var point:Number = (  s /optionTime)
			optionTimeBar.setStyle("barColor",getColorByPointBeginWithYellow(point));
		}
		
		public  function sumTimerHandler(event:TimerEvent):void
		{
			var s:Number = new Date().getTime() - sumStartTime.getTime()  ;
            sumTimeBar.setProgress(sumTime - s, sumTime); 
			var point:Number = (s / sumTime);
			sumTimeBar.setStyle("barColor",getColorByPointBeginWithYellow(point));
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
			oprStartTime = new Date();
			sumStartTime = new Date();
				
			oprTimer.reset();
			sumTimer.reset();
			oprTimer.start();
			sumTimer.start();
		}
		
		public   function reset():void
		{
			oprStartTime = new Date();
			
			oprTimer.reset();
			oprTimer.start();
		}
		
		public  function stop():void{
			
			optionTimeBar.setProgress(optionTime, optionTime); 
			sumTimeBar.setProgress(optionTime, optionTime); 
			
			optionTimeBar.setStyle("barColor",0xffff00)
			sumTimeBar.setStyle("barColor",0xffff00)	
				
			oprTimer.reset();
			sumTimer.reset();
			oprTimer.stop();
			sumTimer.stop();
		}
		
		public  function sumTimerSet(time:int):void
		{
			sumTimer = new Timer(100, time/100);
			sumTime = time;
			sumTimer.addEventListener(TimerEvent.TIMER, sumTimerHandler);
			sumTimer.addEventListener(TimerEvent.TIMER_COMPLETE, completeHandler);   
		}
		
		public  function oprTimerSet(time:int):void
		{
			oprTimer = new Timer(100, time/100);
			optionTime = time;
			oprTimer.addEventListener(TimerEvent.TIMER, oprTimerHandler);
			oprTimer.addEventListener(TimerEvent.TIMER_COMPLETE, completeHandler);   
		}
		
		//从绿到红
		public static function getColorByPoint(val:Number):Number
		{
			var color:Number
			
			if(val>0.375)
			{
				color = 0xff0000;	
			}
			else
			{
				color = 0xff0000 * val / 0.375; 
				color = color - color  % 0x010000;
			}
			
			
			if(val <= 0.375)
			{
				color = color + 0x00ff00;
			}	
			else
			{
				color = color + 0xff00 * (0.75 - val) / 0.375;
				color = color - color % 0x000100;
			}
			
			if(val>=0.75)
			{
				if((val*100 - (val*100)%1)%2==1)
				{
					color = 0xff0000
				}
				else
				{
					color = 0xffff00;
				}
			}
			return color
		}
		//从黄色到红色
		public static function getColorByPointBeginWithYellow(val:Number):Number
		{
			var color:Number
			
			
			color = 0xff0000;	
			
			
			if(val <= 0.75)
			{
				color = color + 0xff00 * (0.75 - val) / 0.75;
				color = color - color % 0x000100;
			}
			
			if(val>=0.75)
			{
				if((val*100 - (val*100)%1)%2==1)
				{
					color = 0xff0000
				}
				else
				{
					color = 0xffff00;
				}
			}
			return color
		}
		//从黄色到红色最后不闪
		public static function getColorByPointBeginWithYellowNoShine(val:Number):Number
		{
			var color:Number
			
			
			color = 0xff0000;	
			
			
			if(val <= 0.75)
			{
				color = color + 0xff00 * (0.75 - val) / 0.75;
				color = color - color % 0x000100;
			}
			
			if(val>=0.75)
			{
				color = 0xff0000
			}
			return color
		}
		
		
	}
}