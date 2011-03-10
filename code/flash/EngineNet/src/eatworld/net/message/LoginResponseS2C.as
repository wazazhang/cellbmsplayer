package eatworld.net.message
{
	import eatworld.net.EatMessages;
	import com.gt.io.IODeserialize;
	import com.gt.io.IOSerialize;
	import com.gt.net.MessageHeader;
	
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;
	
	public class LoginResponseS2C extends MessageHeader
	{
		
		public static const RESULT_SUCCEED : int			= 1;
		public static const RESULT_FAILED : int				= -1;
		public static const RESULT_AUTHORITY_ERROR : int	= -2;

		public static function create_SUCCEED() : LoginResponseS2C {
			var ret : LoginResponseS2C = new LoginResponseS2C();
			ret.Result = RESULT_SUCCEED;
			return ret;
		}
		
		public static function create_FAILED( reason : String) : LoginResponseS2C {
			var ret : LoginResponseS2C = new LoginResponseS2C();
			ret.Result = RESULT_FAILED;
			ret.Reason = reason;
			return ret;
		}
		
		public static function create_AUTHORITY_ERROR( reason : String) : LoginResponseS2C {
			var ret : LoginResponseS2C = new LoginResponseS2C();
			ret.Result = RESULT_AUTHORITY_ERROR;
			ret.Reason = reason; 
			return ret;
		}
		
		////////////////////////////////////////////////////

		public var Result : int ;
		public var Reason : String ;
		
		public function LoginResponseS2C()
		{
			super(EatMessages.LOGIN_RESPONSE_S2C);
		}
		
		override public function deserialize( buffer : IDataInput) : void {
			Result		= IODeserialize.getInt(buffer);
			if (Result == RESULT_SUCCEED) {
			}else{
				Reason	= IODeserialize.getString(buffer);
			}
		}

		override public function serialize( buffer : IDataOutput) : void {
			IOSerialize.putInt(buffer, Result);
			if (Result == RESULT_SUCCEED) {
			}else{
				IOSerialize.putString(buffer, Reason);
			}
		}
		
		public function toString() : String {
			return "LoginResponseS2C [" + Result + "," + Reason + "," +"]"; 
		}
	

	}
}