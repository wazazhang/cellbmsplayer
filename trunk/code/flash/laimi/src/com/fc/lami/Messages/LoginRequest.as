package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [32] [com.fc.lami.Messages.LoginRequest]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class LoginRequest extends Message
	{
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var uid :  String;
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var name :  String;
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var head_url :  String;
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var validate :  String;
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var version :  String;

		/**
		 * @param uid as <font color=#0000ff>java.lang.String</font>
		 * @param name as <font color=#0000ff>java.lang.String</font>
		 * @param head_url as <font color=#0000ff>java.lang.String</font>
		 * @param validate as <font color=#0000ff>java.lang.String</font>
		 * @param version as <font color=#0000ff>java.lang.String</font>		 */
		public function LoginRequest(
			uid :  String = null,
			name :  String = null,
			head_url :  String = null,
			validate :  String = null,
			version :  String = null) 
		{
			this.uid = uid;
			this.name = name;
			this.head_url = head_url;
			this.validate = validate;
			this.version = version;
		}
	}
}