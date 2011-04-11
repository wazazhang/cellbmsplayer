package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [47] [com.fc.lami.Messages.SendCardResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class SendCardResponse extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var result :  int;

		/**
		 * @param result as <font color=#0000ff>int</font>		 */
		public function SendCardResponse(
			result :  int = 0) 
		{
			this.result = result;
		}
	}
}