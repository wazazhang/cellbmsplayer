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
		/** Java type is : <font color=#0000ff>com.fc.lami.Messages.PlayerData</font> */
		[JavaType(name="com.fc.lami.Messages.PlayerData", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
		public var player :  com.fc.lami.Messages.PlayerData;
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var validate :  String;
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var version :  String;
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var platform :  String;

		/**
		 * @param player as <font color=#0000ff>com.fc.lami.Messages.PlayerData</font>
		 * @param validate as <font color=#0000ff>java.lang.String</font>
		 * @param version as <font color=#0000ff>java.lang.String</font>
		 * @param platform as <font color=#0000ff>java.lang.String</font>		 */
		public function LoginRequest(
			player :  com.fc.lami.Messages.PlayerData = null,
			validate :  String = null,
			version :  String = null,
			platform :  String = null) 
		{
			this.player = player;
			this.validate = validate;
			this.version = version;
			this.platform = platform;
		}
	}
}