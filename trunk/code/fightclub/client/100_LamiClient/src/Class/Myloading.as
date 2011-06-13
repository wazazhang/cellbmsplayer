package Class
{
	import flash.display.*;
	import flash.events.*;
	import flash.net.*;
	import flash.text.TextField;
	import flash.text.TextFormat;
	
	import mx.containers.Canvas;
	import mx.events.FlexEvent;
	import mx.graphics.RoundedRectangle;
	import mx.preloaders.DownloadProgressBar;
	
	public class Myloading extends DownloadProgressBar
	{
		private var logo:Loader;
		private var logo2:Loader;
		
		private var loadingt:Loader;
		private var txt:TextField;
		private var txt2:TextField;
		private var _preloader:Sprite;
		
		public function Myloading()
		{
			
			
			logo = new Loader();
			logo.load(new URLRequest("image/bg.jpg"));
			addChild(logo);
			
			
			logo2 = new Loader();
			logo2.load(new URLRequest("image/loadings.png"));
			addChild(logo2);
			
			loadingt = new Loader();
			loadingt.load(new URLRequest("image/loadingt.png"));
			addChild(loadingt);
				
			var style:TextFormat = new TextFormat(null,20,0xFFFFFF,'bold',null,null,null,null,"center");

			txt = new TextField();
			txt.defaultTextFormat = style;
			txt.width = 200;
			txt.selectable = false;
			txt.height = 30;
			addChild(txt);
			
			super();
			
			
		}
		//最重要的代码就在这里..重写preloader,让swf执行加载的时候~进行你希望的操作~
		public override  function set preloader(value:Sprite):void{
			_preloader = value
			//四个侦听~分别是 加载进度 / 加载完毕 / 初始化进度 / 初始化完毕
			_preloader.addEventListener(ProgressEvent.PROGRESS,load_progress);
			_preloader.addEventListener(Event.COMPLETE,load_complete);
			_preloader.addEventListener(FlexEvent.INIT_PROGRESS,init_progress);
			_preloader.addEventListener(FlexEvent.INIT_COMPLETE,init_complete);
			
			stage.addEventListener(Event.RESIZE,resize)
			resize(null);
			downloadingLabel = '';
			initializingLabel = '';
			
			//super.preloader = value
		}
		private function remove():void{
			_preloader.removeEventListener(ProgressEvent.PROGRESS,load_progress);
			_preloader.removeEventListener(Event.COMPLETE,load_complete);
			_preloader.removeEventListener(FlexEvent.INIT_PROGRESS,init_progress);
			_preloader.removeEventListener(FlexEvent.INIT_COMPLETE,init_complete);
			stage.removeEventListener(Event.RESIZE,resize)
		}
		private function resize(e:Event):void{
			logo.x = (stage.stageWidth - 976)/2;
			logo.y = 0;
			
			logo2.x = (stage.stageWidth - 597)/2;
			logo2.y =  230;
			
			
			txt.x = (stage.stageWidth - 200)/2;
			txt.y = 430;
			
			loadingt.x = logo2.x;
			loadingt.y =  359;

			graphics.clear();
			graphics.beginFill(0x000000);
			graphics.drawRect(0,0,stage.stageWidth,stage.stageHeight);
			graphics.endFill();
		}
		
		protected override function get barRect():RoundedRectangle
		{
			return new RoundedRectangle(0, 28, 570, 6, 0);
		}
		
		protected override function get barFrameRect():RoundedRectangle
		{
			return new RoundedRectangle(0, 28, 570, 4);
		}
		
		protected override function get borderRect():RoundedRectangle
		{
			return new RoundedRectangle(0, 35, 580, 8, 4);
		}
		
		
		
		private function load_progress(e:ProgressEvent):void{
			txt.text = int(e.bytesLoaded/e.bytesTotal*100)+"%";
			//loadingt.width = int(e.bytesLoaded/e.bytesTotal*100)*2;
		}
		private function load_complete(e:Event):void{
			txt.text = "加载完毕!"
		}
		private function init_progress(e:FlexEvent):void{
			txt.text = "正在初始化..."
		}
		private function init_complete(e:FlexEvent):void{
			txt.text = "初始化完毕!"
			remove()
			//最后这个地方需要dpe一个Event.COMPLETE事件..表示加载完毕让swf继续操作~
			dispatchEvent(new Event(Event.COMPLETE))
		}
	}
}