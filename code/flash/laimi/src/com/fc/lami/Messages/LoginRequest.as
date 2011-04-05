package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;


	/**
	 * Java Class [22] [com.fc.lami.Messages.LoginRequest]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	public class LoginRequest extends Message
	{
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		public var name :  String;

		/**
		 * @param name as <font color=#0000ff>java.lang.String</font>		 */
		public function LoginRequest(
			name :  String = null) 
		{
			this.name = name;
		}
	}
}