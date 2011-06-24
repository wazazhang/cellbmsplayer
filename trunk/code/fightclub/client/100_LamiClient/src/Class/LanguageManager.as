package Class
{
	import flash.events.Event;
	import flash.net.URLLoader;
	import flash.net.URLRequest;

	public class LanguageManager
	{
		
		private static var language:XML;
		
		
		public function LanguageManager()
		{
			
		}
		
		
		public static function getText(key:String):String
		{
			//var s:String = language.language.string.(@name==key)
			
			//var s:String = language.language.string[0]
			return language.language.string.(@name==key);
		}
		
		
		public static function loadLanguage(listener:Function):void
		{
			var loader:URLLoader=new URLLoader(new URLRequest(WebResource.languageUrl));
			loader.addEventListener(Event.COMPLETE,onComplete);		
			loader.addEventListener(Event.COMPLETE,listener);
			loader.load(new URLRequest(WebResource.languageUrl));
		}
		
		
		private static function onComplete(event:Event):void
		{
			var result:URLLoader=URLLoader(event.target);
			language=XML(result.data);
			//Alert.show(language.server.label[2]);
		}
		
	}
}