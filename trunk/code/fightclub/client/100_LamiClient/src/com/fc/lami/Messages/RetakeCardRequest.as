package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [60] [com.fc.lami.Messages.RetakeCardRequest]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class RetakeCardRequest extends Message
	{
		/** Java type is : <font color=#0000ff>int[]</font> */
		[JavaType(name="int[]", leaf_type=NetDataTypes.TYPE_INT)]
		public var cards :  Array;

		/**
		 * @param cards as <font color=#0000ff>int[]</font>		 */
		public function RetakeCardRequest(
			cards :  Array = null) 
		{
			this.cards = cards;
		}
	}
}