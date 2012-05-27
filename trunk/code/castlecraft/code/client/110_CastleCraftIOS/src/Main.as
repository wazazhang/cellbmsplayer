package
{
	import com.cell.gfx.CellScreenManager;
	import com.fc.castle.net.client.http.HttpClient;
	import com.fc.castle.net.client.socket.SocketClient;
	import com.fc.castle.platform.test.TestPlatform;
	import com.fc.castle.res.CResourceManager;
	import com.fc.castle.screens.Screens;
	import com.fc.castlecraft.AutoLogin;
	
	import flash.desktop.NativeApplication;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.system.Security;
	
	[SWF(width="1024", height="768", frameRate="24")]
	public class Main extends CellScreenManager
	{
		public static var WIDTH 	: int = 1024;
		public static var HEIGHT 	: int = 768;
		
		public function Main()
		{			
			AutoLogin.SERVER_URL 	= "http://59.83.32.121:8088/110_CastleCraft_WS";
			AutoLogin.RES_ROOT 		= "/";
			AutoLogin.LOGIN_USER	= "sigon";
			AutoLogin.LOGIN_PSWD	= "";
			
			super(WIDTH, HEIGHT, new Screens(this, WIDTH, HEIGHT, new TestPlatform()));
			
			changeScreen(Screens.SCREEN_LOGIN);
			
			addEventListener(Event.DEACTIVATE, onExit);
		}
		
		private function onExit(evt:Event):void
		{
			NativeApplication.nativeApplication.exit()
		}
		
	}
	
}