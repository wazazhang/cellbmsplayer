package com.gt.loader
{
    import flash.display.Loader;
    import flash.events.*;
    import flash.net.URLRequest;
    import flash.utils.Dictionary;
	
	public class ResourceManager
	{
		public static const EVENT_IO_ERROR : int 		= -1;
		public static const EVENT_PROGRESS : int 		= 1;
		public static const EVENT_COMPLETE : int 		= 2;
		public static const EVENT_UNLOAD : int 			= 3;
		
		private var Loaders : Dictionary = new Dictionary();
		
		public function ResourceManager()
		{
			
		}

        public function load(url : String, listener : ResourceLoaderListener) : void {
        	var loader : Loader = new Loader();
            var request:URLRequest = new URLRequest(url);
            configureListeners(loader.contentLoaderInfo);
            Loaders[loader.contentLoaderInfo] = loader;
            loader.load(request);
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