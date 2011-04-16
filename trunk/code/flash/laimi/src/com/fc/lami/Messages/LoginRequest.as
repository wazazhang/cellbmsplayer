package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [26] [com.fc.lami.Messages.LoginRequest]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class LoginRequest extends Message
	{
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var name :  String;
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var validate :  String;

		/**
		 * @param name as <font color=#0000ff>java.lang.String</font>
		 * @param validate as <font color=#0000ff>java.lang.String</font>		 */
		public function LoginRequest(
			name :  String = null,
			validate :  String = null) 
		{
			this.name = name;
			this.validate = validate;
		}
	}
}