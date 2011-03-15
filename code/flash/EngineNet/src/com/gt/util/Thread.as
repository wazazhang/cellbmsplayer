package com.gt.util
{
	import flash.events.TimerEvent;
	import flash.utils.Timer;
	
	public class Thread
	{
		static private var _tcount : int = 0;
		
		private var _target : Runnable ;
		private var _id : int ;
		private var _name : String ;

		private var _timer : Timer;

//		public function Thread(target : Runnable = null, name : String = "thread-" + t_count)
//		{
//			_target = target;
//			_id = _tcount;
//			_name = name;
//			t_count ++;
//			
//			_timer = new Timer(0, 1);
//            _timer.addEventListener("timer", timerHandler);
//           
//		}
//
//
//
//		public function getID() : int 
//		{
//			return _id;
//		}
//
//		public function getName() : String 
//		{
//			return _name;
//		}
//
//		public function start() : void
//		{
//			_timer.start();
//		}
//
//		public function run() : void
//		{
//			if (_target!=null)
//			{
//				target.run();
//			}
//		}
//		
//
//        private function timerHandler(event:TimerEvent):void {
//			try{
//				run();
//			}catch(err:Error){
//	         	trace(err.message + "\n" + err.getStackTrace());
//			}
//        }

		
	}
}