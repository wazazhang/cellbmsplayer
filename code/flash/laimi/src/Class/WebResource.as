package Class
{
	public class WebResource
	{
		
		private static var webRoot:String = '';
		
		public static function setWebRoot(root:String) : void
		{
			WebResource.webRoot = root;
		}
		
		public static function get fastBt():String
		{
			return  webRoot+'image/button/fastBt.png';
		}
		
		public function WebResource()
		{

		}
	}
}