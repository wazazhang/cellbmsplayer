package
{
	import as3reflect.ClassUtils;
	
	import com.cell.gfx.CellScreenManager;
	import com.fc.castle.data.Account;
	import com.fc.castle.net.client.http.HttpClient;
	import com.fc.castle.net.client.socket.SocketClient;
	import com.fc.castle.platform.sina.SinaPlatform;
	import com.fc.castle.platform.test.TestPlatform;
	import com.fc.castle.res.CResourceManager;
	import com.fc.castle.screens.Screens;
	import com.fc.castlecraft.AutoLogin;
	
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.system.Security;
	import flash.utils.getQualifiedClassName;
	
	import org.as3commons.lang.ClassUtils;
	
	[SWF(width="800", height="480", frameRate="24")]
	public class mainMac extends CellScreenManager
	{
		public static var WIDTH 	: int = 800;
		public static var HEIGHT 	: int = 480;
			
		public function mainMac()
		{
			Security.allowDomain("*");

			
			AutoLogin.SERVER_URL 	= "http://localhost:8080/110_CastleCraft_WS";
			AutoLogin.RES_ROOT 		= "/Volumes/Macintosh HD 2/projects/fightclub/castlecraft/data/resource/";
			AutoLogin.LOGIN_USER	= "waza";
			AutoLogin.LOGIN_PSWD	= "";
			AutoLogin.LOCATION		= "zh_CN"; 
				
			new Screens(this, WIDTH, HEIGHT, new TestPlatform());
			super(WIDTH, HEIGHT);
			
			changeScreen(Screens.SCREEN_LOGIN);
			
			var msg : com.fc.castle.data.Account = new com.fc.castle.data.Account();
			trace(typeof msg);

			trace(flash.utils.getQualifiedClassName(msg));
			
			var array : Array = new Array(0,1,2);
			array[4] = 4;
			
//			msg.
			
			trace(array);
			
		}
		
	}
}