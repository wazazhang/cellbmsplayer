package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [59] [com.fc.lami.Messages.RetakeCardNotify]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class RetakeCardNotify extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var player_id :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var x :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var y :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var n :  int;

		/**
		 * @param player_id as <font color=#0000ff>int</font>
		 * @param x as <font color=#0000ff>int</font>
		 * @param y as <font color=#0000ff>int</font>
		 * @param n as <font color=#0000ff>int</font>		 */
		public function RetakeCardNotify(
			player_id :  int = 0,
			x :  int = 0,
			y :  int = 0,
			n :  int = 0) 
		{
			this.player_id = player_id;
			this.x = x;
			this.y = y;
			this.n = n;
		}
	}
}