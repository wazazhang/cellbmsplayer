package com.fc.castle.screens
{
	import com.adobe.serialization.json.JSON;
	import com.adobe.serialization.json.JSONDecoder;
	import com.cell.crypo.MD5;
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gameedit.ResourceLoader;
	import com.cell.gameedit.ResourceLoaderQueue;
	import com.cell.gfx.CellScreen;
	import com.cell.gfx.CellScreenManager;
	import com.cell.net.http.HttpRequest;
	import com.cell.net.io.MutualMessage;
	import com.cell.ui.SimpleProgress;
	import com.cell.ui.component.Alert;
	import com.cell.util.StringUtil;
	import com.fc.castle.data.message.Messages.LoginResponse;
	import com.fc.castle.net.client.CClient;
	import com.fc.castle.net.client.CClientEvent;
	import com.fc.castle.net.client.DataManager;
	import com.fc.castle.net.client.http.HttpClient;
	import com.fc.castle.net.client.socket.SocketClient;
	import com.fc.castle.res.CResourceManager;
	import com.fc.castle.res.Res;
	import com.fc.castlecraft.LanguageManager;
	
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.net.URLRequestHeader;
	
	import mx.resources.ResourceManager;
	


	public class ScreenLoading extends CellScreen
	{
		private var queue : ResourceLoaderQueue = new ResourceLoaderQueue();
		
		private var progress : SimpleProgress;
		
		public function ScreenLoading()
		{
		}
		
		override public function added(root:CellScreenManager, args:Array):void
		{		
			Res.init();
			
			LanguageManager.loadLanguage();			
						
			progress = new SimpleProgress(Screens.WIDTH-32, 22);
			progress.x = 16;
			progress.y = Screens.HEIGHT - 60;
			addChild(progress);
			
			queue.addEventListener(ResourceEvent.LOADED, onLoaded);
			queue.addEventListener(ResourceEvent.ERROR, onError);
			
			queue.push(CResourceManager.createCommonObject());
			queue.push(CResourceManager.createOwnerScene());
			queue.push(CResourceManager.createIcons());
			
			DataManager.getAllBuffTemplate(onError, onTemplateSucceed);
		}
		
		override public function removed(root:CellScreenManager):void
		{
		}
		
		override public function transitionCompleted():void
		{
			
			
		}
		
		override public function update():void
		{
			progress.percent = queue.percent;
		}
		
//		----------------------------------------------------------------------------------------------------------
		
		private function onTemplateSucceed(e:Event) : void
		{
			queue.load();
		}
		
		private function onLoaded(e:ResourceEvent) : void
		{
			getParent().changeScreen(Screens.SCREEN_MAIN_MENU);
//			if (Screens.client is SocketClient)  {
//				getParent().changeScreen(Screens.SCREEN_LOGIN_SOCKET);
//			} else if (Screens.client is HttpClient) {
//			}
		}
		
		private function onError(e:Event) : void
		{
			Alert.showAlertText("无法加载资源", "错误", false, true).
				btnOK.addEventListener(MouseEvent.CLICK,
				function onOK(e:Event) : void
				{
					Screens.getRoot().changeScreen(Screens.SCREEN_LOGIN);
				} 
			);
		}
	}
}