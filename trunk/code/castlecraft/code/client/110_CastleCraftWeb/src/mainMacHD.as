package
{
	import com.cell.gfx.CellScreenManager;
	import com.fc.castle.data.Account;
	import com.fc.castle.net.client.http.HttpClient;
	import com.fc.castle.net.client.socket.SocketClient;
	import com.fc.castle.platform.test.TestPlatform;
	import com.fc.castle.res.CResourceManager;
	import com.fc.castle.screens.Screens;
	import com.fc.castlecraft.AutoLogin;
	
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.system.Security;
	
	[SWF(width="1024", height="768", frameRate="24")]
	public class mainMacHD extends CellScreenManager
	{
		public static var WIDTH 	: int = 1024;
		public static var HEIGHT 	: int = 768;

		public function mainMacHD()
		{
			Security.allowDomain("*");
			
			AutoLogin.SERVER_URL 	= "http://192.168.0.5:8080/110_CastleCraft_WS";
			AutoLogin.RES_ROOT 		= "http://192.168.0.5:8080/cw/data/";
			AutoLogin.LOGIN_USER	= "xxxx";
			AutoLogin.LOGIN_PSWD	= "";
			AutoLogin.LOCATION		= "en_US"; 
			
			super(WIDTH, HEIGHT, new Screens(this, WIDTH, HEIGHT, new TestPlatform()));
			
			changeScreen(Screens.SCREEN_LOGIN);
			
			
		}
		
		
	}
}