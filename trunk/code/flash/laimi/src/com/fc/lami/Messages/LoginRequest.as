package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [34] [com.fc.lami.Messages.LoginRequest]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class LoginRequest extends Message
	{
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var platform_user_uid :  String;
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var platform_uid :  String;
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var validate :  String;
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var version :  String;

		/**
		 * @param platform_user_uid as <font color=#0000ff>java.lang.String</font>
		 * @param platform_uid as <font color=#0000ff>java.lang.String</font>
		 * @param validate as <font color=#0000ff>java.lang.String</font>
		 * @param version as <font color=#0000ff>java.lang.String</font>		 */
		public function LoginRequest(
			platform_user_uid :  String = null,
			platform_uid :  String = null,
			validate :  String = null,
			version :  String = null) 
		{
			this.platform_user_uid = platform_user_uid;
			this.platform_uid = platform_uid;
			this.validate = validate;
			this.version = version;
		}
	}
}