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
	
	[SWF(width="800", height="480", frameRate="24")]
	public class main extends CellScreenManager
	{
		public static var WIDTH 	: int = 800;
		public static var HEIGHT 	: int = 480;
		
		public function main()
		{
			AutoLogin.SERVER_URL 	= "http://59.83.32.121:8088/110_CastleCraft_WS";
			AutoLogin.RES_ROOT 		= "/";
			AutoLogin.LOGIN_USER	= "sigon";
			AutoLogin.LOGIN_PSWD	= "";
			
			super(WIDTH, HEIGHT, new Screens(this, WIDTH, HEIGHT, new TestPlatform()));
			
			changeScreen(Screens.SCREEN_LOGIN);
		}
		
		private function onExit(evt:Event):void
		{
			NativeApplication.nativeApplication.exit()
		}
		
	}
}