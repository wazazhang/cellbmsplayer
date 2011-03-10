package eatworld.net.message
{
	import eatworld.net.EatMessages;
	import com.gt.net.MessageHeader;
	import com.gt.io.*;
	
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;
	
	
	
	public class LoginRequestC2S extends MessageHeader
	{
		
		public static function create_Login( user : String,  pwd : String) : LoginRequestC2S {
			var login : LoginRequestC2S = new LoginRequestC2S();
			login.UserName 	= user;
			login.UserPWD 	= pwd;
			return login;
		}

		////////////////////////////////////////////////////

		public var UserName : String;
		public var UserPWD : String;
		
		public function LoginRequestC2S() {
			super(EatMessages.LOGIN_REQUEST_C2S);
		}
		
		override public function deserialize( buffer : IDataInput) : void {
			UserName	= IODeserialize.getString(buffer);
			UserPWD		= IODeserialize.getString(buffer);
		}

		override public function serialize( buffer : IDataOutput) : void {
			IOSerialize.putString(buffer, UserName);
			IOSerialize.putString(buffer, UserPWD);
		}
		
		public function toString() : String {
			return "LoginRequestC2S [" + UserName + "," + UserPWD +"]"; 
		}
	

	}
}