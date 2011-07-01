package com.fc.lami.ui
{
	import flash.display.MovieClip;
	import flash.events.Event;
	
	import mx.controls.SWFLoader;
	import mx.events.FlexEvent;
	
	public class LamiSwfLoader extends SWFLoader
	{
		private var auto_close : Boolean = false;
		
		private var loaded_movie : MovieClip;
		
		private var playing_movie : MovieClip;
		
		public function LamiSwfLoader(source:Object)
		{
			super.source = source;
			super.visible = true;
			super.mouseEnabled = false;
			super.mouseChildren = false;
			super.autoLoad = false;
			super.addEventListener(Event.INIT, complete);
			super.addEventListener(Event.ENTER_FRAME, movie_update);
			super.load(source);
		}
		
		/**从指定帧开始播放 SWF 文件。*/
		public function gotoAndPlay(mc:Object, 
									frame:Object, 
									scene:String = null, 
									auto_close:Boolean = false) : LamiSwfLoader
		{
			this.auto_close = auto_close;
			this.playing_movie = mc as MovieClip;
			if (this.playing_movie != null) {
				this.playing_movie.gotoAndPlay(frame, scene);
			}
			return this;
		}
		
		/**将播放头移到影片剪辑的指定帧并停在那里。*/
		public function gotoAndStop(mc:Object, 
									frame:Object, 
									scene:String = null, 
									auto_close:Boolean = false) : LamiSwfLoader
		{
			this.auto_close = auto_close;
			this.playing_movie = mc as MovieClip;
			if (this.playing_movie != null) {
				this.playing_movie.gotoAndPlay(frame, scene);
			}
			return this;
		}
		
		public function close() : void {
			if (this.parent != null) {
				this.parent.removeChild(this);
				this.removeEventListener(Event.ENTER_FRAME, movie_update);
			}
		}

		public function movie() : MovieClip {
			return this.loaded_movie;
		}
		
		private function complete(e:Event):void
		{
			this.loaded_movie = (content as MovieClip);
		}
		
		private function movie_update(e:Event) : void
		{
			if (auto_close && playing_movie != null) {
				if (playing_movie.currentFrame >= playing_movie.totalFrames) {
					close();
				}
			}
		}

		
	}
}