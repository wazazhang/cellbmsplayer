package Class
{
	public class WebResource
	{
		
		private static var webRoot:String = '';
		
		public static function setWebRoot(root:String) : void
		{
			WebResource.webRoot = root + "/";
		}
		
		public static function get fastBt():String
		{
			return  webRoot+'image/button/fastBt.png';
		}
		
		public static function get guestHead():String
		{
			return  webRoot+'image/head.png';
		}
		
		public static function get start():String
		{
			return  webRoot+'image/start.png';
		}
		
		public static function get startSwf():String
		{
			return  webRoot+'image/start.swf';
		}
		
		public static function get languageUrl() : String
		{
			return  webRoot+"Language/chinese.xml";
		}
		
		public function WebResource()
		{

		}
	}
}