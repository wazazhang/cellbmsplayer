package com.fc.castle.net.client.http
{
	import avmplus.getQualifiedClassName;
	
	import com.adobe.serialization.json.JSON;
	import com.adobe.serialization.json.JSONDecoder;
	import com.adobe.utils.XMLUtil;
	import com.cell.crypo.MD5;
	import com.cell.io.TextReader;
	import com.cell.io.TextWriter;
	import com.cell.net.http.HttpRequest;
	import com.cell.net.io.MutualMessage;
	import com.cell.net.io.BinNetDataInput;
	import com.cell.net.io.BinNetDataOutput;
	import com.cell.net.io.TextNetDataInput;
	import com.cell.net.io.TextNetDataOutput;
	import com.cell.ui.component.Alert;
	import com.cell.util.StringUtil;
	import com.fc.castle.data.message.Messages.BattleStartRequest;
	import com.fc.castle.data.message.Messages.BattleStartResponse;
	import com.fc.castle.data.message.Messages.GetExploreDataListRequest;
	import com.fc.castle.data.message.Messages.GetExploreDataListResponse;
	import com.fc.castle.data.message.Messages.GetUnitTemplateRequest;
	import com.fc.castle.data.message.Messages.LoginRequest;
	import com.fc.castle.data.message.Messages.LoginResponse;
	import com.fc.castle.data.template.FormualMap;
	import com.fc.castle.formual.Formual;
	import com.fc.castle.net.client.CClient;
	import com.fc.castle.net.client.CClientEvent;
	import com.fc.castlecraft.AutoLogin;
	
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.IOErrorEvent;
	import flash.net.URLRequest;
	import flash.net.URLRequestHeader;
	import flash.utils.ByteArray;
	
		
	public class HttpClient extends CClient
	{
				
		public function HttpClient()
		{
		}
		
		private function createHttpRequest(name:String, request:MutualMessage, complete:Function, error:Function) : HttpRequest
		{
			var req : HttpRequest = new HttpRequest(new URLRequest(AutoLogin.SERVER_URL + "/" + name));
			
			// 设置头信息
			req.putRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			// 设置数据
			req.param.type  = message_factory.getType(request);
			req.param.data 	= encode(request);
			// 设置验证信息
			if (super._login != null) {
				req.putRequestHeader("FC-PID", super.getPlayer().player_id+"");
				req.putRequestHeader("FC-KEY", super._login.login_key);
			}
			
			var warp : HttpRequestWrapper = new HttpRequestWrapper(complete, error, this, req, request);
			
			return req;
		}
		
		internal function syncResponseWrapper(e:CClientEvent) : void
		{
			super.syncResponse(e);
		}
		
		internal function decode(data:Object) : MutualMessage
		{
			var msg:MutualMessage = null;
			try {
				var reader  : TextReader		= new TextReader(data as String);
				var decoder : TextNetDataInput	= new TextNetDataInput(message_factory, reader);
//				var reader	: ByteArray			= StringUtil.hex2bin(data as String);
//				var decoder : BinNetDataInput	= new BinNetDataInput(message_factory, reader);
				msg = decoder.readMutual();
				return msg;
			} catch (e:Error) {
				trace("decode : " + e + "\n" + e.getStackTrace());
			}
			return null;
		}
		
		internal function encode(data:MutualMessage) : Object
		{
			var writer 		: TextWriter 		= new TextWriter();
			var out_data	: TextNetDataOutput = new TextNetDataOutput(message_factory, writer);
//			var writer		: ByteArray			= new ByteArray();
//			var out_data	: BinNetDataOutput	= new BinNetDataOutput(message_factory, writer);
			out_data.writeMutual(data);
			return writer.toString();
		}
		
		
//		-----------------------------------------------------------------------------------------------------------
		
		override protected function send(r:MutualMessage, listener:Function, error:Function) : void
		{
			var req : HttpRequest = createHttpRequest("WS", r, listener, error);
			trace("send : " + req.param.type);
			trace("     : " + req.param.data);
			req.post();
		}
		
		

	}
}