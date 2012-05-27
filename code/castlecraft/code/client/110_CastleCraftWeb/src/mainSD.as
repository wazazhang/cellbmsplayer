package
{
	import com.cell.gfx.CellScreenManager;
	import com.fc.castle.net.client.http.HttpClient;
	import com.fc.castle.net.client.socket.SocketClient;
	import com.fc.castle.platform.test.TestPlatform;
	import com.fc.castle.res.CResourceManager;
	import com.fc.castle.screens.Screens;
	import com.fc.castlecraft.AutoLogin;
	
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.system.Security;
	
	[SWF(width="800", height="480", frameRate="24")]
	public class mainSD extends CellScreenManager
	{
		public static var WIDTH 	: int = 800;
		public static var HEIGHT 	: int = 480;

		public function mainSD()
		{
			Security.allowDomain("*");
			
			AutoLogin.SERVER_URL 	= "http://59.83.32.121:8088/110_CastleCraft_WS";
			///<TOMCAT_HOME>/webapps/ROOT/CastleCraft
			AutoLogin.RES_ROOT 		= "http://59.83.32.121/castle/resource/";
			AutoLogin.LOGIN_USER	= "testsd";
			AutoLogin.LOGIN_PSWD	= "";
			AutoLogin.LOCATION		= "zh_CN"; 

			
			super(WIDTH, HEIGHT, new Screens(this, WIDTH, HEIGHT, new TestPlatform()));
			
			changeScreen(Screens.SCREEN_LOGIN);
		}
		
		
	}
}