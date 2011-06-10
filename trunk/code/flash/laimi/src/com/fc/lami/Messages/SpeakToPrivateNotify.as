package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [65] [com.fc.lami.Messages.SpeakToPrivateNotify]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class SpeakToPrivateNotify extends Message
	{
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var player_uid :  String;
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var message :  String;

		/**
		 * @param player_uid as <font color=#0000ff>java.lang.String</font>
		 * @param message as <font color=#0000ff>java.lang.String</font>		 */
		public function SpeakToPrivateNotify(
			player_uid :  String = null,
			message :  String = null) 
		{
			this.player_uid = player_uid;
			this.message = message;
		}
	}
}