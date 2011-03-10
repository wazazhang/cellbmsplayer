package com.gt.game
{
	import flash.display.Loader;
	import flash.display.LoaderInfo;
	import flash.display.Sprite;
	import flash.events.*;
	import flash.net.URLRequest;
    
	public class CImage extends Sprite
	{
		public function CImage(url:String = null)
		{
			var loader:Loader = new Loader();
			
			loader.contentLoaderInfo.addEventListener(Event.COMPLETE, completeHandler);
            loader.contentLoaderInfo.addEventListener(HTTPStatusEvent.HTTP_STATUS, httpStatusHandler);
            loader.contentLoaderInfo.addEventListener(Event.INIT, initHandler);
            loader.contentLoaderInfo.addEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);
            loader.contentLoaderInfo.addEventListener(Event.OPEN, openHandler);
            loader.contentLoaderInfo.addEventListener(ProgressEvent.PROGRESS, progressHandler);
            loader.contentLoaderInfo.addEventListener(Event.UNLOAD, unLoadHandler);
			
			var request:URLRequest = new URLRequest(url);
			
            loader.load(request);
		}
	

        private function completeHandler(event:Event):void {
            trace("completeHandler: " + event);
            
            var contentLoaderInfo : LoaderInfo = LoaderInfo(event.target);
            
            addChild(contentLoaderInfo.content);
            
            contentLoaderInfo.removeEventListener(Event.COMPLETE, completeHandler);
            contentLoaderInfo.removeEventListener(HTTPStatusEvent.HTTP_STATUS, httpStatusHandler);
            contentLoaderInfo.removeEventListener(Event.INIT, initHandler);
            contentLoaderInfo.removeEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);
            contentLoaderInfo.removeEventListener(Event.OPEN, openHandler);
            contentLoaderInfo.removeEventListener(ProgressEvent.PROGRESS, progressHandler);
            contentLoaderInfo.removeEventListener(Event.UNLOAD, unLoadHandler);
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