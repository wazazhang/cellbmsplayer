package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [49] [com.fc.lami.Messages.SendCardRequest]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class SendCardRequest extends Message
	{
		/** Java type is : <font color=#0000ff>int[]</font> */
		[JavaType(name="int[]", leaf_type=NetDataTypes.TYPE_INT)]
		public var cards :  Array;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var x :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var y :  int;

		/**
		 * @param cards as <font color=#0000ff>int[]</font>
		 * @param x as <font color=#0000ff>int</font>
		 * @param y as <font color=#0000ff>int</font>		 */
		public function SendCardRequest(
			cards :  Array = null,
			x :  int = 0,
			y :  int = 0) 
		{
			this.cards = cards;
			this.x = x;
			this.y = y;
		}
	}
}