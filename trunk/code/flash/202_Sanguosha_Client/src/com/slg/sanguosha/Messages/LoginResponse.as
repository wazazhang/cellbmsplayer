package com.slg.sanguosha.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;
	import com.slg.entity.Player;

	/**
	 * Java Class [7] [com.slg.sanguosha.Messages.LoginResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class LoginResponse extends Message
	{
		/** Java type is : <font color=#0000ff>short</font> */
		[JavaType(name="short", leaf_type=NetDataTypes.TYPE_SHORT)]
		static public const LOGIN_RESULT_SUCCESS :  int = 1;
		/** Java type is : <font color=#0000ff>short</font> */
		[JavaType(name="short", leaf_type=NetDataTypes.TYPE_SHORT)]
		static public const LOGIN_RESULT_FAIL :  int = -1;
		/** Java type is : <font color=#0000ff>short</font> */
		[JavaType(name="short", leaf_type=NetDataTypes.TYPE_SHORT)]
		static public const LOGIN_RESULT_FAIL_ALREADY_LOGIN :  int = -2;
		/** Java type is : <font color=#0000ff>short</font> */
		[JavaType(name="short", leaf_type=NetDataTypes.TYPE_SHORT)]
		static public const LOGIN_RESULT_FAIL_BAD_VERSION :  int = -3;
		/** Java type is : <font color=#0000ff>short</font> */
		[JavaType(name="short", leaf_type=NetDataTypes.TYPE_SHORT)]
		public var result :  int;
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var version :  String;
		/** Java type is : <font color=#0000ff>com.slg.entity.Player</font> */
		[JavaType(name="com.slg.entity.Player", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
		public var player_data :  com.slg.entity.Player;

		/**
		 * @param result as <font color=#0000ff>short</font>
		 * @param version as <font color=#0000ff>java.lang.String</font>
		 * @param player_data as <font color=#0000ff>com.slg.entity.Player</font>		 */
		public function LoginResponse(
			result :  int = 0,
			version :  String = null,
			player_data :  com.slg.entity.Player = null) 
		{
			this.result = result;
			this.version = version;
			this.player_data = player_data;
		}
	}
}