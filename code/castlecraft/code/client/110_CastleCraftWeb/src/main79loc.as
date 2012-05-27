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
	public class main79loc extends CellScreenManager
	{
		public static var WIDTH 	: int = 800;
		public static var HEIGHT 	: int = 480;
		public function main79loc()
		{
			Security.allowDomain("*");
			AutoLogin.SERVER_URL 	= "http://127.0.0.1:8080/110_CastleCraft_WS";
			AutoLogin.RES_ROOT 		= "D:/Castle/castlecraft/data/resource/";
			AutoLogin.LOGIN_USER	= "ooo";
			AutoLogin.LOGIN_PSWD	= "";
			AutoLogin.LOCATION		= "zh_CN";
			super(WIDTH, HEIGHT, new Screens(this, WIDTH, HEIGHT, new TestPlatform()));
			changeScreen(Screens.SCREEN_LOGIN);
		}		
	}
}