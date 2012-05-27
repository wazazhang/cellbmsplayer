package com.fc.castle.platform.sina
{
	import com.adobe.net.URI;
	import com.cell.net.http.HttpRequest;
	import com.fc.castle.net.client.http.HttpRequestWrapper;
	import com.fc.castle.platform.Platform;
	
	import flash.events.ErrorEvent;
	import flash.events.Event;
	import flash.events.IOErrorEvent;
	import flash.net.URLRequest;
	import flash.utils.ByteArray;
	
	import org.httpclient.HttpClient;
	import org.httpclient.events.HttpDataEvent;
	import org.httpclient.events.HttpResponseEvent;
	import org.httpclient.events.HttpStatusEvent;

	public class SinaPlatform implements Platform
	{
		private var token;
		
		public function SinaPlatform()
		{
			
		}
		
	
		
		
//		客户端的验证授权（Resource Owner Password Credentials）
//		基本流程
//		
//		1．调用
//		https://api.weibo.com/oauth2/access_token?
//		client_id=YOUR_CLIENT_ID&
//		client_secret=YOUR_CLIENT_SECRET&
//		grant_type=password&
//		username=USER_NAME&
//		password=PASSWORD
//		返回值 { "access_token":"SlAV32hkKG", "expires_in":3600 }
//		
//		2. 使用获得的OAuth2.0 Access Token调用API
//		
//		注：客户端的验证授权需要申请
		
		public function login(user:String, password:String, complete:Function, error:Function):void
		{
//			var client:HttpClient = new HttpClient();
//			
//			client.listener.onStatus = function(event:HttpStatusEvent):void {
//				// Notified of response (with headers but not content)
//			};
//			
//			client.listener.onData = function(event:HttpDataEvent):void {
//				// For string data
//				var stringData:String = event.readUTFBytes();
//				
//				// For data
//				var data:ByteArray = event.bytes;    
//			};
//			
//			client.listener.onComplete = function(event:HttpResponseEvent):void {
//				// Notified when complete (after status and data)
//				complete.call(null, event);
//			};
//			
//			client.listener.onError = function(event:ErrorEvent):void {
//				var errorMessage:String = event.text;
//				complete.call(null, event);
//			};      
//			
//			var uri:URI = new URI("https://api.weibo.com/oauth2/access_token?" +
//				"client_id=3256609410&" +
//				"client_secret=6022eb8fe9d93f037548c80fddf5d274&" +
//				"grant_type=password&" +
//				"username="+user+"&" +
//				"password="+password);
//			client.get(uri);

			
			var req : HttpRequest = new HttpRequest(new URLRequest("https://api.weibo.com/oauth2/access_token?" +
				"client_id=3256609410&" +
				"client_secret=6022eb8fe9d93f037548c80fddf5d274&" +
				"grant_type=password&" +
				"username="+user+"&" +
				"password="+password));
			req.loader.addEventListener(Event.COMPLETE, complete);
			req.loader.addEventListener(IOErrorEvent.IO_ERROR, error);	
			req.post();

		}
	}
}