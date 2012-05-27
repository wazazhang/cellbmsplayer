package com.fc.castle.screens
{
	import com.cell.gfx.CellScreen;
	import com.cell.gfx.CellScreenManager;
	import com.cell.gfx.IScreenAdapter;
	import com.fc.castle.net.client.CClient;
	import com.fc.castle.net.client.http.HttpClient;
	import com.fc.castle.platform.Platform;
	import com.fc.castle.res.CLayoutManager;
	import com.fc.castle.res.CResourceManager;
	
	import flash.events.Event;

	public class Screens implements IScreenAdapter
	{
		
		public static const SCREEN_LOADING			: String = "ScreenLoading";
		public static const SCREEN_LOGIN			: String = "ScreenLogin";
		public static const SCREEN_LOGIN_SOCKET		: String = "ScreenLoginSocket";
		public static const SCREEN_MAIN_MENU		: String = "ScreenMainMenu";
		public static const SCREEN_BATTLE			: String = "ScreenBattle";
		public static const SCREEN_BATTLE_LOADING	: String = "ScreenBattleLoading";

//		--------------------------------------------------------------------------------------------------
		
		private static var _width 	: int = 320;
		private static var _height 	: int = 480;
		
		private static var _root 			: CellScreenManager;
		
		private static var _client 			: CClient;
		
		private static var _res_manager 	: CResourceManager;
		private static var _layout_manager	: CLayoutManager;
		
		private static var _platform 		: Platform;
		
		public function Screens(s:CellScreenManager, 
								width:int,
								height:int, 
								platform:Platform)
		{
			_root				= s;
			_width				= width;
			_height 			= height;
			
			_platform			= platform;
			
			_client				= new HttpClient();
			_res_manager 		= new CResourceManager();
			_layout_manager 	= new CLayoutManager();
			
		}
		
		public function createScreen(root:CellScreenManager, name:String) : CellScreen
		{
			switch (name) {
				case SCREEN_LOADING:
					return new ScreenLoading();
					
				case SCREEN_LOGIN_SOCKET:
					return new ScreenLoginSocket();
				case SCREEN_LOGIN:
					return new ScreenLogin();	
					
				case SCREEN_MAIN_MENU:
					return new ScreenMainMenu();
					
				case SCREEN_BATTLE:
					return new ScreenBattle();
				case SCREEN_BATTLE_LOADING:
					return new ScreenBattleLoading();
					
			}
			return null;
		}
		
		public static function get WIDTH() : int
		{
			return _width;
		}
		
		public static function get HEIGHT() : int
		{
			return _height;
		}
		
		public static function getRoot() : CellScreenManager
		{
			return _root;
		}
		
		public static function get client() : CClient
		{
			return _client;
		}
		
		public static function get platform() : Platform
		{
			return _platform;
		}
	}
}