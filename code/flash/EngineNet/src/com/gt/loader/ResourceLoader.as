package com.gt.loader
{
	import flash.display.Loader;
	import flash.display.LoaderInfo;
	import flash.events.*;
	import flash.net.URLRequest;
	
	public class ResourceLoader
	{
		public static const EVENT_IO_ERROR : int 		= -1;
		public static const EVENT_PROGRESS : int 		= 1;
		public static const EVENT_COMPLETE : int 		= 2;
		public static const EVENT_UNLOAD : int 			= 3;
		
		public var loader : Loader = new Loader();
		
		public var listener : ResourceLoaderListener;
		
		public function ResourceLoader(url : String, lis : ResourceLoaderListener)
		{
			var request:URLRequest = new URLRequest(url);
            configureListeners(loader.contentLoaderInfo);
            listener = lis;
            loader.load(request);
		}

		public function distroy() : void {
			loader.close();
			loader.unload();
		}

        private function configureListeners(dispatcher:IEventDispatcher):void {
            dispatcher.addEventListener(Event.COMPLETE, completeHandler);
            dispatcher.addEventListener(HTTPStatusEvent.HTTP_STATUS, httpStatusHandler);
            dispatcher.addEventListener(Event.INIT, initHandler);
            dispatcher.addEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);
            dispatcher.addEventListener(Event.OPEN, openHandler);
            dispatcher.addEventListener(ProgressEvent.PROGRESS, progressHandler);
            dispatcher.addEventListener(Event.UNLOAD, unLoadHandler);
        }

		private function removeListeners(dispatcher:IEventDispatcher):void {
            dispatcher.removeEventListener(Event.COMPLETE, completeHandler);
            dispatcher.removeEventListener(HTTPStatusEvent.HTTP_STATUS, httpStatusHandler);
            dispatcher.removeEventListener(Event.INIT, initHandler);
            dispatcher.removeEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);
            dispatcher.removeEventListener(Event.OPEN, openHandler);
            dispatcher.removeEventListener(ProgressEvent.PROGRESS, progressHandler);
            dispatcher.removeEventListener(Event.UNLOAD, unLoadHandler);
        }

        private function completeHandler(event:Event):void {
            trace("completeHandler: " + event);
			if (event.target is LoaderInfo)
			{
				var info : LoaderInfo = LoaderInfo(event.target);
				
				if (info.contentType!=null) {
					listener.response(info.url, info.content, EVENT_COMPLETE);
				}
				else{
					listener.response(info.url, info.bytes, EVENT_COMPLETE);
				}
			}
        }

        private function httpStatusHandler(event:HTTPStatusEvent):void {
            trace("httpStatusHandler: " + event);
        }

        private function initHandler(event:Event):void {
            trace("initHandler: " + event);
        }

        private function ioErrorHandler(event:IOErrorEvent):void {
            trace("ioErrorHandler: " + event);
        }

        private function openHandler(event:Event):void {
            trace("openHandler: " + event);
        }

        private function progressHandler(event:ProgressEvent):void {
            trace("progressHandler: bytesLoaded=" + event.bytesLoaded + " bytesTotal=" + event.bytesTotal);
        }

        private function unLoadHandler(event:Event):void {
            trace("unLoadHandler: " + event);
        }

	}
}