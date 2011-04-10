package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [30] [com.fc.lami.Messages.MoveCardResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class MoveCardResponse extends Message
	{
		/** Java type is : <font color=#0000ff>short</font> */
		[JavaType(name="short", leaf_type=NetDataTypes.TYPE_SHORT)]
		public var result :  int;

		/**
		 * @param result as <font color=#0000ff>short</font>		 */
		public function MoveCardResponse(
			result :  int = 0) 
		{
			this.result = result;
		}
	}
}