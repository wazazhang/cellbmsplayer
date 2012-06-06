package
{
	import com.cell.gfx.CellScreenManager;
	import com.cell.openapi.SaveDataEvent;
	import com.cell.persistance.SaveDataShared;
	import com.fc.castle.net.client.http.HttpClient;
	import com.fc.castle.net.client.socket.SocketClient;
	import com.fc.castle.platform.test.TestPlatform;
	import com.fc.castle.res.CResourceManager;
	import com.fc.castle.screens.Screens;
	import com.fc.castlecraft.AutoLogin;
	
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.system.Security;
	import flash.utils.ByteArray;
	
	[SWF(width="800", height="480", frameRate="24")]
	public class mainPC extends CellScreenManager
	{
		public static var WIDTH 	: int = 800;
		public static var HEIGHT 	: int = 480;

		public function mainPC()
		{
			Security.allowDomain("*");
			
			AutoLogin.SERVER_URL 	= "http://localhost:80/110_CastleCraft_WS";
			///<TOMCAT_HOME>/webapps/ROOT/CastleCraft
			AutoLogin.RES_ROOT 		= "E:/Projects/elex_svn/castlecraft/data/resource/";
			AutoLogin.LOGIN_USER	= "xxxx";
			AutoLogin.LOGIN_PSWD	= "";
			AutoLogin.LOCATION		= "zh_CN"; 

			
			new Screens(this, WIDTH, HEIGHT, new TestPlatform());
			super(WIDTH, HEIGHT);
			
			changeScreen(Screens.SCREEN_LOGIN);
			
			{
				var sd : ByteArray = new ByteArray();
				sd.writeUTF("test");
				
				var sv : SaveDataShared = new SaveDataShared("test");
				sv.addEventListener(SaveDataEvent.SAVED,  saved);
				sv.save(sd);
				
				var ld : SaveDataShared = new SaveDataShared("test");
				ld.addEventListener(SaveDataEvent.LOADED, loaded);
				ld.load();
			}
			
		}
		
		private function saved(e:SaveDataEvent) : void
		{
			trace("saved");
			
		}
		
		private function loaded(e:SaveDataEvent) : void
		{
			trace("loaded");
			
			trace(e.data.getLoadedData().readUTF());
		}
		
	}
}